package ejercicio3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

    private DataInputStream input;
    private DataOutputStream output;
    private Socket client;
    private final String HOST = "localhost";
    private final int PORT = 8080;

    public void runClient() {
        try {
            connectToServer();
            getStreams();
            processConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void connectToServer() throws IOException {
        System.out.println("Attempting connection\n");
        client = new Socket(HOST, PORT);
        System.out.println("Connected to: " + client.getInetAddress().getHostName());
    }

    private void getStreams() throws IOException {
        output = new DataOutputStream(client.getOutputStream());
        output.flush();
        input = new DataInputStream(client.getInputStream());
    }

    private void processConnection() {
        try {
            String msjBienvenida = input.readUTF();
            String primeraResp = JOptionPane.showInputDialog(null, msjBienvenida);
            boolean loop = true;
            while (loop) {
                loop = input.readBoolean();
                if (!loop) {
                    break;
                }
                output.writeChar(primeraResp.charAt(0));
                String newMessage = input.readUTF();
                primeraResp = JOptionPane.showInputDialog(null, newMessage);
            }
        } catch (EOFException ex) {
            System.out.println("Terminó el juego\n" + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void closeConnection() {
        System.out.println("\nClosing connection");
        try {
            output.close();
            input.close();

            client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().runClient();
    }
}
