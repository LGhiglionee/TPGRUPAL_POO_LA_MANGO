package Modelo.Entidades;
import Excepciones.Juego.MazoVacioException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * — Representa el conjunto de cartas del juego.

 * Contiene las 40 cartas españolas divididas en los palos:
 * Espada, Oro, Basto y Copa,
 * con los valores 1, 2, 3, 4, 5, 6, 7, 10, 11 y 12.

 * Permite mezclar el mazo, sacar cartas una por una
 * y consultar cuántas quedan disponibles.
 */
public class Mazo {
    // === Atributos ===
    private final ArrayList<Carta> cartas;



    // === Constructores ===
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



    // === Métodos ===
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


    /**
     * Devuelve una copia de las cartas actuales del mazo.
     *
     * Retorna una lista con las cartas restantes (sin modificar el mazo original).
     */
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