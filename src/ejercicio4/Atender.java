package ejercicio4;

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
    private int clients;

    private ManejadorBanco banco = new ManejadorBanco();

    public Atender(Socket connection, int counter) {
        this.clients = counter;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            getStreams();
            if (clients == 2) {
                //processConnection();
                processConnection2();
            }
        } catch (IOException ex) {
            System.out.println("" + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
            ex.printStackTrace();
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

    private synchronized void processConnection() throws IOException {
        boolean continuar = true;
        String textCorrecto = "";
        String mascara = banco.iniciarJuego();
        String solicitud = ("Bienvenido!! JUEGO AHORCADO\n"
                + "Palabra: " + mascara + "\nDigite su letra");
        output.writeUTF(solicitud);// escribo msj de bienvenida
        while (banco.getVidas() >= 1 && !banco.verificar()) {
            output.writeBoolean(continuar); // escribo si  puede seguir jugando porque ganó o perdió
            char resp = input.readChar(); // leo la respuesta
            boolean intento = banco.intento(resp);
            mascara = banco.getMascara();
            boolean verificar = banco.verificar();
            boolean verificarPerdido = banco.perdido();
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
            if (verificarPerdido) {
                JOptionPane.showMessageDialog(null, "Perdiste :(");
                output.writeBoolean(!continuar);
                break;
            }
            String estadoActual = (textCorrecto + "\n" + mascara
                    + "\nNúmero de intentos Posibles: " + banco.getVidas());
            output.writeUTF(estadoActual); // escribo si falló o acerté
        }
    }

    private synchronized void processConnection2() throws IOException {
        boolean turnarse = true;
        while (turnarse) {
            String saludos = "Bienvenido";
            output.writeUTF(saludos);
            String resp = input.readUTF();
            output.writeUTF("SALUDOS: " + resp);
        }
    }

}
