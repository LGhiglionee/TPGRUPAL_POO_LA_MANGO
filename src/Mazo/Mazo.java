package Mazo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {
    private ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<Carta>();
        String[] palos = {"Espada", "Oro", "Basto", "Copa"};
        int[] numeros = {1,2,3,4,5,6,7,10,11,12};

        for (int i = 0; i < 4; i++) {
            for  (int j = 0; j < 10; j++) {
                this.cartas.add(new Carta(palos[i], numeros[j]));
            }
        }
    }

    public Carta getCarta() {
        Carta cartaAleatoria = cartas.getFirst();
        cartas.remove(0);
        return cartaAleatoria;
    }

    public ArrayList<Carta> mezclarMazo() {
        Collections.shuffle(cartas);
        return cartas;
    }

    public boolean mazoVacio() {
        return cartas.isEmpty();
    }

}
