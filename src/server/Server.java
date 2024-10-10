package server;

import connection.SocketConnection;
import rsa.RSA;

import java.net.*;
import java.io.*;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor aguardando conexões...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                SocketConnection connection = new SocketConnection(clientSocket);

                // Inicializa RSA e envia a chave pública ao cliente
                RSA rsa = new RSA(1024);
                connection.send(rsa.getPublicKeyN().toString());  // Envia n
                connection.send(rsa.getPublicKeyE().toString());  // Envia e

                // Recebe mensagem criptografada do cliente
                String encryptedMessage = connection.receive();
                System.out.println("Mensagem criptografada recebida: " + encryptedMessage);

                try {
                    // Descriptografa a mensagem recebida
                    String decryptedMessage = rsa.decrypt(encryptedMessage);
                    System.out.println("Mensagem descriptografada: " + decryptedMessage);

                    // Resposta ao cliente
                    String response = "Mensagem recebida e descriptografada com sucesso!";
                    String encryptedResponse = rsa.encrypt(response);
                    connection.send(encryptedResponse);
                } catch (Exception e) {
                    System.out.println("Erro ao descriptografar a mensagem: " + e.getMessage());
                    e.printStackTrace();
                }

                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

