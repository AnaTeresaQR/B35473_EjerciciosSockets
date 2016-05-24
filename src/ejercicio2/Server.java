package ejercicio2;

import java.io.IOException;
import java.net.*;

public class Server {

    private ServerSocket server;
    private Socket connection;
    private final int PORT = 8080;
    private Atender atender;

    public void runServer() {

        try {
            server = new ServerSocket(PORT);
            while (true) {
                waitForConnection();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            closeServer();
        }
    }

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for connection...\n");
        connection = server.accept();
        atender = new Atender(connection);
        atender.start();
        System.out.println("Connection received from: " + connection.getInetAddress().getHostName());
    }

    private void closeServer() {
        System.out.println("\nTerminating server");
        try {
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().runServer();
    }
}
