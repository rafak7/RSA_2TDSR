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
            Socket socket = new Socket(HOST, PORT);
            System.out.println("Conectado ao servidor...");

            SocketConnection connection = new SocketConnection(socket);

            BigInteger n = new BigInteger(connection.receive());
            BigInteger e = new BigInteger(connection.receive());

            RSA rsa = new RSA(n, e);

            String encryptedMessage = rsa.encrypt("Tranquilo");
            System.out.println("Mensagem criptografada enviada: " + encryptedMessage);

            connection.send(encryptedMessage);

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
