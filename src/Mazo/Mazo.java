package Mazo;
import Excepciones.MazoVacioException;

import java.util.ArrayList;
import java.util.Collections;

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

    public Carta getCarta() throws MazoVacioException {
        if (cartas.isEmpty()) {
            throw new MazoVacioException("No es posible obtener una carta. El mazo está vacío.");
        }
        Carta cartaAleatoria = cartas.getFirst();
        cartas.removeFirst();
        return cartaAleatoria;
    }

    public ArrayList<Carta> mezclarMazo() {
        Collections.shuffle(cartas);
        return cartas;
    }

    public int cartasRestantes() {
        return cartas.size();
    }

}
