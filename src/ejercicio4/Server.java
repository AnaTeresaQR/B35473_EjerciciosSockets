package ejercicio4;

import java.io.IOException;
import java.net.*;

public class Server {

    private ServerSocket server;
    private Socket connection;
    private final int PORT = 8080;
    private Atender atender;
    private int counter = 1;

    public void runServer() {
        try {
            server = new ServerSocket(PORT);
            for (int i = 0; i <= 2; i++) {
                waitForConnection();
            }

        } catch (IOException ex) {
            System.out.println("" + ex.getMessage());
        }
    }

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for connection...\n");
        connection = server.accept();
        atender = new Atender(connection, counter);
        counter++;
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
