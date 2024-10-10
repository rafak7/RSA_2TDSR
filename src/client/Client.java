package client;

import connection.SocketConnection;
import rsa.RSA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        // Criando a interface gráfica com Swing
        JFrame frame = new JFrame("Cliente RSA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Campo de texto para digitar a mensagem
        JTextField messageField = new JTextField(20);
        JButton sendButton = new JButton("Enviar");

        // Área de texto para mostrar a resposta do servidor
        JTextArea responseArea = new JTextArea();
        responseArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Digite a mensagem:"));
        panel.add(messageField);
        panel.add(sendButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, responseArea);

        frame.setVisible(true);

        // Ação ao clicar no botão de enviar
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Criação do socket para se conectar ao servidor
                    Socket socket = new Socket(HOST, PORT);
                    System.out.println("Conectado ao servidor...");

                    SocketConnection connection = new SocketConnection(socket);

                    // Recebe a chave pública do servidor
                    BigInteger n = new BigInteger(connection.receive());  // Recebe n
                    BigInteger eKey = new BigInteger(connection.receive());  // Recebe e
                    RSA rsa = new RSA(n, eKey);  // Cria RSA com a chave pública recebida

                    // Enviar a mensagem digitada
                    String message = messageField.getText();
                    String encryptedMessage = rsa.encrypt(message);
                    connection.send(encryptedMessage);
                    System.out.println("Mensagem criptografada enviada: " + encryptedMessage);

                    // Receber resposta do servidor
                    String encryptedResponse = connection.receive();
                    System.out.println("Resposta criptografada recebida: " + encryptedResponse);

                    // Exibir a resposta no campo de resposta
                    responseArea.setText("Resposta do servidor: " + encryptedResponse);

                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
