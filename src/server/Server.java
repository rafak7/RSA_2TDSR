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
                // Aceitando a conexão do cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Criando conexão com o cliente
                SocketConnection connection = new SocketConnection(clientSocket);

                // Instanciando RSA com as chaves fixas do servidor
                RSA rsa = new RSA();  // P, Q, N, E, D já fixados no RSA.java

                // Enviando a chave pública para o cliente
                connection.send(rsa.getPublicKeyN().toString());  // Envia N
                connection.send(rsa.getPublicKeyE().toString());  // Envia E

                // Recebendo a mensagem criptografada do cliente
                String encryptedMessage = connection.receive();
                System.out.println("Mensagem criptografada recebida: " + encryptedMessage);

                // Descriptografando a mensagem recebida
                String decryptedMessage = rsa.decrypt(encryptedMessage);
                System.out.println("Mensagem descriptografada: " + decryptedMessage);

                // Fechando a conexão com o cliente
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
