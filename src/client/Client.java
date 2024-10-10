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
        JFrame frame = new JFrame("Cliente RSA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JTextField messageField = new JTextField(20);
        JButton sendButton = new JButton("Enviar");

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

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket socket = new Socket(HOST, PORT);
                    System.out.println("Conectado ao servidor...");

                    SocketConnection connection = new SocketConnection(socket);

                    BigInteger n = new BigInteger(connection.receive());
                    BigInteger eKey = new BigInteger(connection.receive());
                    RSA rsa = new RSA(n, eKey);

                    String message = messageField.getText();
                    String encryptedMessage = rsa.encrypt(message);
                    connection.send(encryptedMessage);
                    System.out.println("Mensagem criptografada enviada: " + encryptedMessage);

                    String encryptedResponse = connection.receive();
                    System.out.println("Resposta criptografada recebida: " + encryptedResponse);

                    responseArea.setText("Resposta do servidor: " + encryptedResponse);

                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
