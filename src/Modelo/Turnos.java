package Modelo;

import Excepciones.Juego.MazoVacioException;
import Excepciones.Juego.PosicionInvalidaException;
import Modelo.Mazo.*;

import java.util.ArrayList;

/**
 *  — Controla la lógica de turnos, jugadas y estado de la partida.
 *
 * <p>Coordina las acciones de los jugadores, el manejo del mazo y la resolución
 * de los efectos de las cartas (daño, curación y energía/mana).</p>
 *
 * <p>Responsabilidades principales:</p>
 *   <li>Gestionar el turno activo entre los jugadores.</li>
 *   <li>Procesar jugadas individuales y enfrentamientos entre cartas.</li>
 *   <li>Evaluar condiciones de finalización de la partida.</li>
 *   <li>Resolver el resultado del "envido" y calcular ganador general.</li>
 */

public class Turnos {
    Mazo mazo; // --- Mazo del juego.
    ArrayList<Carta> doscartas; // --- Lista auxiliar para almacenar las dos cartas jugadas por turno.
    // --- Jugadores.
    Jugador jugador1;
    Jugador jugador2;
    private boolean envidodisponible = true;

    /**
     * Constructor que inicializa los jugadores, el mazo y mezcla las cartas.
     */
    public Turnos() {
        this.mazo = new Mazo();
        mazo.mezclarMazo();
        doscartas = new ArrayList<Carta>();
        jugador1 = new Jugador();
        jugador2 = new Jugador();
        jugador1.setMano(true);
        System.out.println("Cartas disponibles al iniciar: " + mazo.cartasRestantes());
    }

    public boolean envidoDisponible() { return envidodisponible; }
    public void bloquearEnvido() { envidodisponible = false; }
    private void resetearEnvido() { envidodisponible = true; }

    /**
     * Resuelve el enfrentamiento entre dos cartas jugadas en una mano.
     * Aplica efectos según el tipo de palo (curación, mana o daño).
     */
    public void jugarMano(Carta carta1, Carta carta2) {
        // --- Cartas de curación y maná.
        if ((carta1.getPalo().equals("Copa") || carta1.getPalo().equals("Oro")) &&
                (carta2.getPalo().equals("Copa") || carta2.getPalo().equals("Oro"))) {

            if (carta1.getPalo().equals("Copa")) {
                jugador1.actualizarSalud(carta1.getNumero());
                jugador1.setDesangrado(false);
            }
            if (carta1.getPalo().equals("Oro")) jugador1.agregarMana(carta1.getNumero());

            if (carta2.getPalo().equals("Copa")) {
                jugador2.actualizarSalud(carta2.getNumero());
                jugador2.setDesangrado(false);
            }
            if (carta2.getPalo().equals("Oro")) jugador2.agregarMana(carta2.getNumero());
        }
        // --- Cartas ofensivas.
        else if ((carta1.getPalo().equals("Espada") || carta1.getPalo().equals("Basto")) &&
                (carta2.getPalo().equals("Espada") || carta2.getPalo().equals("Basto"))) {

            if (jugador1.getDesangrado()) {
                jugador1.actualizarSalud(-5);
            } else if (jugador2.getDesangrado()) {
                jugador2.actualizarSalud(-5);
            }

            calcularDanio(carta1, carta2);
        }
        // --- Cartas de distinto tipo; se aplica daño y efectos individuales.
        else {
            if (carta1.getPalo().equals("Espada") || carta1.getPalo().equals("Basto")) {
                jugador2.actualizarSalud(-carta1.getNumero());

                if (carta1.getPalo().equals("Espada")) {
                    jugador2.setDesangrado(true);
                } else if (carta2.getPalo().equals("Copa")) {
                    jugador2.setDesangrado(false);
                }

                if (carta2.getPalo().equals("Copa")) jugador2.actualizarSalud(carta2.getNumero());
                if (carta2.getPalo().equals("Oro")) jugador2.agregarMana(carta2.getNumero());
            } else {
                jugador1.actualizarSalud(-carta2.getNumero());

                if (carta2.getPalo().equals("Espada")) {
                    jugador1.setDesangrado(true);
                } else if (carta1.getPalo().equals("Copa")) {
                    jugador1.setDesangrado(false);
                }

                if (carta1.getPalo().equals("Copa")) jugador1.actualizarSalud(carta1.getNumero());
                if (carta1.getPalo().equals("Oro")) jugador1.agregarMana(carta1.getNumero());
            }
        }
        if (jugador1.getDesangrado()) {
            jugador1.actualizarSalud(-5);
        } else if (jugador2.getDesangrado()) {
            jugador2.actualizarSalud(-5);
        }
    }

    /**
     * Calcula el daño entre dos cartas de tipo ofensivo (Basto/Espada).
     */
    public void calcularDanio(Carta carta1, Carta carta2) {
        if ((carta1.getPalo().equals("Basto") || carta1.getPalo().equals("Espada")) && (carta2.getPalo().equals("Basto") || carta2.getPalo().equals("Espada"))) {
            if (carta2.getNumero() > carta1.getNumero()) {
                jugador1.actualizarSalud(-(carta2.getNumero() - carta1.getNumero()));

                if (carta2.getPalo().equals("Espada")) {
                    jugador1.setDesangrado(true);
                }
            }
            if (carta1.getNumero() > carta2.getNumero()) {
                jugador2.actualizarSalud(-(carta1.getNumero() - carta2.getNumero()));

                if (carta1.getPalo().equals("Espada")) {
                    jugador2.setDesangrado(true);
                }
            }
        }
    }

    /**
     * Determina el ganador de la partida al finalizar o si uno de los jugadores se queda sin salud.
     */
    public String partidaTerminada() {

        if (jugador1.getSalud() <= 0 && jugador2.getSalud() <= 0)
            return "Empate: ambos llegaron a 0 de vida";
        if (jugador1.getSalud() <= 0)
            return "Gano jugador 2";
        if (jugador2.getSalud() <= 0)
            return "Gano jugador 1";
        if (jugador1.getSalud() > jugador2.getSalud()) return "Gano jugador 1";
        if (jugador2.getSalud() > jugador1.getSalud()) return "Gano jugador 2";
        if (jugador1.getMana() > jugador2.getMana()) return "Gano jugador 1 (por mana)";
        if (jugador2.getMana() > jugador1.getMana()) return "Gano jugador 2 (por mana)";
        return "Empate total";
    }
    /**
     * Evalúa si la partida debe finalizar (por salud, cartas o mazo vacío).
     *
     * @return true si se cumple alguna condición de fin de partida.
     */
    public boolean condicionFinalizacion() {

        if (jugador1.getSalud() <= 0 || jugador2.getSalud() <= 0)
            return true;

        boolean mazoVacio = (mazo == null || mazo.cartasRestantes() == 0);

        boolean sinCartas1 = true;
        for (Carta c : jugador1.getTresCartas()) {
            if (c != null) sinCartas1 = false;
        }

        boolean sinCartas2 = true;
        for (Carta c : jugador2.getTresCartas()) {
            if (c != null) sinCartas2 = false;
        }

        if (mazoVacio && sinCartas1 && sinCartas2)
            return true;

        return false;
}
    // --- Devuelve el jugador que tiene actualmente la mano (turno activo). */
    public Jugador getJugadorMano() {
        if (jugador1.getMano()) {
            return jugador1;
        } else {
            return jugador2;
        }
    }
    // --- Devuelve el nombre del jugador que tiene la mano actual. */
    public String getStringJugadorMano() {
        if (jugador1.getMano()) {
            return "Jugador 1";
        } else return "Jugador 2";
    }

    // ---  Alterna el turno entre los dos jugadores.
    public void alternarTurno() {
        if (jugador1.getMano()) {
            jugador1.setMano(false);
            jugador2.setMano(true);
        } else {
            jugador2.setMano(false);
            jugador1.setMano(true);
        }
    }
    /**
     * Reparte tres cartas del mazo al jugador indicado.
     */
    public void llenarMano(Jugador jugador) throws MazoVacioException {
        ArrayList<Carta> cartas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cartas.add(mazo.getCarta());
        }
        jugador.setTresCartas(cartas);
    }

    // --- Getters.
    public Jugador getJugador1() {return jugador1;}
    public Jugador getJugador2() {return jugador2;}
    public Mazo getMazo() {return mazo;}

    /**
     * Permite al jugador activo jugar una carta seleccionada por índice.
     * @param i índice de la carta jugada (0-2)
     */
    public void jugarCarta(int i, ArrayList<Carta> cartasjugadas) throws MazoVacioException, PosicionInvalidaException {
        if (i < 0 || i >= getJugadorMano().getTresCartas().size()) {
            throw new PosicionInvalidaException("Índice de carta inválido: " + i);
        }
        Carta carta = getJugadorMano().getTresCartas().get(i);

        if (carta == null) {
            throw new PosicionInvalidaException("No hay carta en esa posición.");
        }
        // --- Quita mano jugada.
        getJugadorMano().setCarta(i, null);

        // --- Si ya hay carta jugada, se resuelve la mano.
        if (!cartasjugadas.isEmpty()) {
            cartasjugadas.add(carta);
            jugarMano(cartasjugadas.get(0), cartasjugadas.get(1));
            cartasjugadas.clear();
            resetearEnvido();
        } else {
            cartasjugadas.add(carta);
        }
        // --- Repone carta del mazo, en caso que haya disponibles.
        if (mazo.cartasRestantes() != 0){
            getJugadorMano().setCarta(i, getMazo().getCarta());
        }
        // --- Cambia de turno.
        alternarTurno();
}

    /**
     * Resuelve el enfrentamiento de "Envido" entre los dos jugadores.
     *
     * @param envidoJ1 puntos de envido del jugador 1
     * @param envidoJ2 puntos de envido del jugador 2
     * @return mensaje con el resultado del envido y cambios de salud aplicados.
     */
    public String jugarEnvido(int envidoJ1, int envidoJ2) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Jugador 1: ").append(envidoJ1).append(" puntos\n")
                .append("Jugador 2: ").append(envidoJ2).append(" puntos\n");

        if (envidoJ1 > envidoJ2) {
            mensaje.append("Gana el envido Jugador 1");
            getJugador2().actualizarSalud(-envidoJ1 / 2);
        } else if (envidoJ2 > envidoJ1) {
            mensaje.append("Gana el envido Jugador 2");
            getJugador1().actualizarSalud(-envidoJ1 / 2);
        } else {
            mensaje.append("Empate en el envido");
        }
        return mensaje.toString();
    }
}