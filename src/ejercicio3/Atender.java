package ejercicio3;

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

    private ManejadorBanco banco = new ManejadorBanco();

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
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
            ex.printStackTrace();
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
        boolean continuar = true;
        String textCorrecto = "";
        //---------------------------------------------------------------
        String mascara = banco.iniciarJuego();
        String solicitud = ("Bienvenido!! JUEGO AHORCADO\n"
                + "Palabra: " + mascara + "\nDigite su letra");
        output.writeUTF(solicitud);

        while (banco.getVidas() >= 1 && !banco.verificar()) {
            output.writeBoolean(continuar);
            char resp = input.readChar();
            boolean intento = banco.intento(resp);
            mascara = banco.getMascara();
            boolean verificar = banco.verificar();
            if (resp != ' ' && !intento) {
                textCorrecto = "Falló";
            } else {
                textCorrecto = "Acertó";
            }
            if (verificar) {
                JOptionPane.showMessageDialog(null, "Ganaste :D");
                output.writeBoolean(!continuar);
                break;
            }
            if (banco.perdido()) {
                JOptionPane.showMessageDialog(null, "Perdiste :(");
                output.writeBoolean(!continuar);
                break;
            }
            String estadoActual = (textCorrecto + "\n" + mascara
                    + "\nNúmero de intentos Posibles: " + banco.getVidas());
            output.writeUTF(estadoActual);
        }
    }

}
