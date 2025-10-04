package Mazo;

public class Mazo {
    private Carta[] cartas;
    private String[] palos;
    private int[] numeros;
    private int cantCartas;


    public Mazo() {}

    public Carta[] getCartas() {
        return cartas;
    }

    public void setCartas(Carta[] cartas) {
        this.cartas = cartas;
    }

    public Mazo(String[] palos, int[] numeros) {
        this.palos = palos;
        this.numeros = numeros;
        this.cantCartas = palos.length*numeros.length;

        cartas = new Carta[cantCartas];

        int index = 0;
        for (String palo : palos) {
            for (int numero : numeros) {
                cartas[index] = new Carta(palo, numero);
                index++;
            }
        }



    }
}
