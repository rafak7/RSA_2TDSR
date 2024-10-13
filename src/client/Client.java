package client;

import connection.SocketConnection;
import rsa.RSA;

import java.math.BigInteger;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            // Conectando ao servidor
            Socket socket = new Socket(HOST, PORT);
            System.out.println("Conectado ao servidor...");

            // Criando conexão com o servidor
            SocketConnection connection = new SocketConnection(socket);

            // Recebendo a chave pública do servidor
            BigInteger n = new BigInteger(connection.receive());  // Recebe N
            BigInteger e = new BigInteger(connection.receive());  // Recebe E

            // Instanciando o RSA com a chave pública recebida
            RSA rsa = new RSA(n, e);

            // Criptografando a mensagem "Tranquilo"
            String encryptedMessage = rsa.encrypt("Tranquilo");  // Envia a palavra "Tranquilo"
            System.out.println("Mensagem criptografada enviada: " + encryptedMessage);

            // Enviando a mensagem criptografada ao servidor
            connection.send(encryptedMessage);

            // Fechando a conexão
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
