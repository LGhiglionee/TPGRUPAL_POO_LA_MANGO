package Modelo.Mazo;
import Excepciones.Juego.MazoVacioException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase {@code Mazo} — Representa el conjunto de cartas del juego.

 * <p>Contiene las 40 cartas españolas divididas en los palos:
 * <b>Espada</b>, <b>Oro</b>, <b>Basto</b> y <b>Copa</b>,
 * con los valores 1, 2, 3, 4, 5, 6, 7, 10, 11 y 12.</p>

 * <p>Permite mezclar el mazo, sacar cartas una por una
 * y consultar cuántas quedan disponibles.</p>
 *
 */
public class Mazo {
    // --- Lista que contiene todas las cartas del mazo.
    private final ArrayList<Carta> cartas;

    /**
     * Constructor que genera un mazo estándar de 40 cartas españolas.
     */
    public Mazo() {
        cartas = new ArrayList<Carta>();
        String[] palos = {"Espada", "Oro", "Basto", "Copa"};
        int[] numeros = {1,2,3,4,5,6,7,10,11,12};

        // Crea todas las combinaciones palo/número
        for (String palo : palos) {
            for (int numero : numeros) {
                cartas.add(new Carta(palo, numero));
            }
        }
    }
    /**
     * Devuelve una carta del mazo y la elimina de la lista.
     */
    public Carta getCarta() throws MazoVacioException {
        if (cartas.isEmpty()) {
            throw new MazoVacioException("No es posible obtener una carta. El mazo está vacío.");
        }
        // --- Toma la primera carta y la remueve
        Carta cartaAleatoria = cartas.get(0);
        cartas.remove(0);
        return cartaAleatoria;
    }

    /**
     * Mezcla el orden de las cartas del mazo de forma aleatoria.
     */
    public void mezclarMazo() {Collections.shuffle(cartas);}

    /**
     * Devuelve la cantidad de cartas restantes en el mazo.
     */
    public int cartasRestantes() {return cartas.size();}

    public ArrayList<Carta> getCartasMazo() {
        ArrayList<Carta> listacartas = new ArrayList<>();

        for (Carta c : this.cartas){
            if (c != null){
                listacartas.add(c);
            }
        }
        return listacartas;
    }
}