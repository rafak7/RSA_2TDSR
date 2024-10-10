package connection;

import java.io.*;
import java.net.*;

public class SocketConnection {
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    public SocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.output = new DataOutputStream(socket.getOutputStream());
        this.input = new DataInputStream(socket.getInputStream());
    }

    public void send(String message) throws IOException {
        output.writeUTF(message);
        output.flush();
    }

    public String receive() throws IOException {
        return input.readUTF();
    }

    public void close() throws IOException {
        input.close();
        output.close();
        socket.close();
    }
}
