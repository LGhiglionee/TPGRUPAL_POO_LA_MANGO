package Modelo.Entidades;

import Excepciones.Juego.MazoVacioException;

import java.util.ArrayList;

/**
 * — Representa al jugador controlado por la computadora (Bot).
 * El bot hereda de la clase Jugador y toma decisiones automáticas
 * sobre qué carta jugar en función de su dificultad, las cartas disponibles
 * y el estado del mazo.
 *
 * Sus decisiones están basadas en reglas simples o probabilísticas,
 * dependiendo del nivel de dificultad configurado al inicio.
 */
public class Bot extends Jugador {
    // === Atributos ===v
    int dificultad;



    // === Constructores ===
    /**
     * Crea un nuevo bot con la dificultad indicada.
     * Ajusta su salud inicial en función de la dificultad y lo marca como bot.

     * Parámetro: dificultad Nivel de dificultad del bot.
     */
    public Bot(int dificultad) {
        super();
        this.dificultad = dificultad;
        this.salud = 30 * dificultad;
        this.mano = false;
        this.esBot = true;
    }



    // === Métodos ===
    /**
     * Determina qué carta jugar según la dificultad.
     * Si la dificultad es 0, utiliza la estrategia simple.
     * En niveles superiores, aplica un análisis más complejo.
     *
     * Parámetro: mazo Mazo actual del juego, usado para calcular probabilidades.
     * Retorna: Carta seleccionada por el bot.
     */
    public Carta decision(Mazo mazo) throws MazoVacioException {
        if (this.dificultad == 0) {
            return analisisFacil();
        }
        return analisisMedio(mazo);
    }


    /**
     * Verifica si el bot posee al menos una carta del palo especificado.
     *
     * Parámetro: palo Palo a buscar (Espada, Oro, Basto o Copa).
     * Retorna: true si posee al menos una carta de ese palo.
     */
    public boolean hayPalo(String palo) {
        for (Carta c : this.trescartas) {
            if (c != null && palo.equals(c.getPalo())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Devuelve la carta con mayor valor numérico del palo indicado.
     *
     * Parámetro: palo Palo del que se busca la carta más alta.
     * Retorna: Carta con el número más alto dentro del palo, o null si no tiene cartas de ese tipo.
     */
    public Carta cartaMasAlta(String palo) {
        Carta max = null;
        for (Carta c : this.trescartas) {
            if (c != null && palo.equals(c.getPalo())) {
                if (max == null || c.getNumero() > max.getNumero()) {
                    max = c;
                }
            }
        }
        return max;
    }


    /**
     * Calcula el porcentaje de cartas de un palo específico que quedan en el mazo.
     *
     * Parámetro: mazo Mazo actual del juego.
     * Parámetro: palo Palo a analizar.
     * Retorna: Porcentaje (0.0 a 1.0) de cartas de ese palo en el mazo.
     */
    public float porcentajePaloMazo(Mazo mazo, String palo) {
        ArrayList<Carta> turista = mazo.getCartasMazo();
        float total = 0;
        float cartas = 0;

        while (!turista.isEmpty()) {
            Carta c = turista.get(0);
            if (c != null && palo.equals(c.getPalo())) cartas++;
            total++;
            turista.removeFirst();
        }
        return cartas / total;
    }



    // === Estrategias de decisión ===
    /**
     * Estrategia de juego simple (modo fácil).
     *
     *   Si la salud es baja y tiene "Copa", juega la más alta de ese palo.
     *   Si no, prioriza Basto → Espada → Oro.
     *   Si no tiene ninguno, juega la primera carta disponible.
     *
     * Retorna: Carta elegida.
     */
    public Carta analisisFacil() {
        if (this.salud < 10 && hayPalo("Copa")) { return cartaMasAlta("Copa");
        }
        else if (hayPalo("Basto")) { return cartaMasAlta("Basto");
        }
        else if (hayPalo("Espada")) { return cartaMasAlta("Espada");
        }
        else if (hayPalo("Oro")) { return cartaMasAlta("Oro");
        }
        else { return this.trescartas.getFirst(); }
    }


    /**
     * Estrategia de juego con dificultad media.
     * Considera la salud del bot y las probabilidades de los palos en el mazo.
     *
     * Parámetro: mazo Mazo actual del juego.
     * Retorna: Carta seleccionada por el bot.
     */
    public Carta analisisMedio(Mazo mazo) throws MazoVacioException {
        if (this.salud < 10 && hayPalo("Copa")) { return cartaMasAlta("Copa");
        }
        else if (hayPalo("Espada") && porcentajePaloMazo(mazo, "Basto") <= 0.15) { return cartaMasAlta("Espada");
        }
        else if (hayPalo("Basto") && porcentajePaloMazo(mazo, "Espada") <= 0.2) { return cartaMasAlta("Basto");
        }
        else if (hayPalo("Oro") && porcentajePaloMazo(mazo, "Basto") >= 0.1) { return cartaMasAlta("Oro");
        }
        else if (hayPalo("Espada")) { return cartaMasAlta("Espada");
        }
        else {
            return this.getTresCartas().getFirst();
        }
    }



    // === Métodos utilitarios ===
    /**
     * Devuelve el índice de una carta específica dentro de la mano del bot.
     *
     * Parámetro: carta Carta a buscar.
     * Parámetro: cartas Lista de cartas (mano actual del bot).
     * Retorna: Índice de la carta en la lista o -1 si no se encuentra.
     */
    public int indiceCartaElegida(Carta carta, ArrayList<Carta> cartas) {
        for (int i = 0; i < cartas.size(); i++) {
            if (cartas.get(i) == carta) {
                return i;
            }
        }
        return -1;
    }
}
