package ejercicio3;

import java.util.Random;

public class Banco {

    private final String[] banco = {"PAJARO", "HELADO", "VACACIONES", "ESPEJO", "PERRO",
        "GATO", "PELOTA", "AGUA", "FUEGO", "CARTA"};

    public String getPalabra() {
        int indicePalabra = this.indicePalabra();
        return banco[indicePalabra];
    }

    private int indicePalabra() {
        Random random = new Random();
        int indice = random.nextInt(10);
        return indice;
    }

}
