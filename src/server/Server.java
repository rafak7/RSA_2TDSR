package server;

import connection.SocketConnection;
import rsa.RSA;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor aguardando conexões...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                SocketConnection connection = new SocketConnection(clientSocket);

                RSA rsa = new RSA();

                connection.send(rsa.getPublicKeyN().toString());
                connection.send(rsa.getPublicKeyE().toString());

                String encryptedMessage = connection.receive();
                System.out.println("Mensagem criptografada recebida: " + encryptedMessage);

                String decryptedMessage = rsa.decrypt(encryptedMessage);
                System.out.println("Mensagem descriptografada: " + decryptedMessage);

                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
