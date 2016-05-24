package ejercicio2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Ana Teresa
 */
public class Atender extends Thread {

    private DataOutputStream output;
    private DataInputStream input;
    private Socket connection;
    
    private Multiplicaciones multi = new Multiplicaciones();

    public Atender(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            getStreams();
            processConnection();
        } catch (IOException ex) {
            System.out.println("" + ex.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        System.out.println("\nTerminating connection\n\n");
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void getStreams() throws IOException {
        output = new DataOutputStream(connection.getOutputStream());
        output.flush();
        input = new DataInputStream(connection.getInputStream());
    }

    private void processConnection() throws IOException {
        String stringMessage = "Server: Connection successful";
        output.writeUTF(stringMessage);
        System.out.println("The message: \"" + stringMessage + "\" was sent");
        int cantidadCorrectas = 0;
        int cantidadIncorrectas = 0;
        for (int i = 1; i < 6; i++) {
            stringMessage = multi.StringMultiplicacion();
            output.writeUTF(i + ") " + stringMessage);
            int respuestaUsuario = input.readInt();
            multi.setResultadoUsuario(respuestaUsuario);
            if (multi.resultado()) {
                cantidadCorrectas++;
            } else {
                cantidadIncorrectas++;
            }
        }
        JOptionPane.showMessageDialog(null, "La cantidad de respuestas correctas son: " + cantidadCorrectas
                + "\nLa cantidad de incorrectas son: " + cantidadIncorrectas);
    }
}
