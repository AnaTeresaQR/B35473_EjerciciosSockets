package ejercicio3;

import java.util.Arrays;

public class ManejadorBanco {

    private Banco banco;
    private String buscada;
    private char[] mascara;
    private int vidas;

    public ManejadorBanco() {
        banco = new Banco();
        vidas = 3;
    }

    private void init() {
        buscada = banco.getPalabra();
        initMascara(buscada.length());
    }

    private void initMascara(int length) {
        mascara = new char[length];
        for (int i = 0; i < length; i++) {
            mascara[i] = '*';
        }
    }

    public String iniciarJuego() {
        init();
        return new String(mascara);
    }

    public boolean intento(char letra) {
        // No se sabe si ha acertado
        boolean acertado = false;
        // Si todavia tiene vidas

        // Se recorre la palabra
        for (int i = 0; i < buscada.length(); i++) {
            // Si encontro una sola letra acertada
            if (buscada.charAt(i) == letra) {
                // Se cambia la mascara
                mascara[i] = letra;
                // Se indica que se acierta
                acertado = true;
            }
        }
        if (acertado == false) {
            perderVida();
        }
        return acertado;

    }

    public boolean perdido() {
        return vidas == 0;
    }

    public boolean verificar() {
        System.out.println("buscada: " + buscada + "\nMascara:" + Arrays.toString(mascara));
        return buscada.equals(new String(mascara));
    }

    public void perderVida() {
        vidas--;
    }

    public String getMascara() {
        return new String(mascara);
    }

    public int getVidas() {
        return vidas;
    }

}
