package Modelo.Mazo;
import Excepciones.Juego.MazoVacioException;

import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private final ArrayList<Carta> cartas;

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
        Carta cartaAleatoria = cartas.get(0);
        cartas.remove(0);
        return cartaAleatoria;
    }

    public void mezclarMazo() {
        Collections.shuffle(cartas);
    }

    public int cartasRestantes() {
        return cartas.size();
    }
}