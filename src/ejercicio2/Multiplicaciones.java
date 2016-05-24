package ejercicio2;

import java.util.Random;

/**
 *
 * @author Ana Teresa
 */
public class Multiplicaciones {

    private int num1;
    private int num2;
    private int resultadoUsuario;
    private int resultadoCorrecto;

    public Multiplicaciones() {
    }

    private int generarRandom() {
        Random r = new Random();
        int random = r.nextInt(100) + 1;
        return random;
    }

    private int guardarMultiplicacion() {
        this.num1 = generarRandom();
        this.num2 = generarRandom();
        return num1 * num2;
    }

    public String StringMultiplicacion() {
        this.resultadoCorrecto = guardarMultiplicacion();
        return num1 + " x " + num2 + " = ";
    }

    public boolean resultado() {
        return resultadoUsuario == resultadoCorrecto;
    }

    public void setResultadoUsuario(int resultadoUsuario) {
        this.resultadoUsuario = resultadoUsuario;
    }

}
