package Modelo.Motor;

import Excepciones.Juego.MazoVacioException;
import Excepciones.Juego.PosicionInvalidaException;
import Modelo.Entidades.Bot;
import Modelo.Entidades.Carta;
import Modelo.Entidades.Jugador;
import Modelo.Entidades.Mazo;

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
    static Mazo mazo; // --- Mazo del juego.
    ArrayList<Carta> doscartas; // --- Lista auxiliar para almacenar las dos cartas jugadas por turno.
    // --- Jugadores.
    static Jugador jugador1;
    static Jugador jugador2;
    private boolean envidodisponible = true;

    public boolean trucodisponible = true;
    private boolean mostrarBannerTruco = false;
    int danoReciente1,danoReciente2;

    private Carta ultimaCartaJugadaJ1;
    private Carta ultimaCartaJugadaJ2;
    private String ultimoResultado = "";

    Bot bot;

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
    }
    public Carta getUltimaCartaJugadaJ1() { return ultimaCartaJugadaJ1; }
    public Carta getUltimaCartaJugadaJ2() { return ultimaCartaJugadaJ2; }
    public String getUltimoResultado() { return ultimoResultado; }

    public boolean envidoDisponible() { return envidodisponible; }
    public void bloquearEnvido() { envidodisponible = false; }
    public void resetearEnvido() { envidodisponible = true; }


    public boolean trucoDisponible() {return trucodisponible;}
    public void bloquearTruco() {trucodisponible = false;}
    public void resetearTruco() {trucodisponible = true;}

    public boolean consumirBannerTruco() {
        boolean x = mostrarBannerTruco;
        mostrarBannerTruco = false;
        return x;
    }

    private void resetDanoReciente() {

        danoReciente1 = 0;

        danoReciente2=0;
    }

    public Turnos(int dificultad) {
        mazo = new Mazo();
        mazo.mezclarMazo();
        doscartas = new ArrayList<Carta>();
        jugador1 = new Jugador();
        bot = new Bot(dificultad);
        jugador2 = bot; //Para no cambiar en partida
        jugador1.setMano(true);
    }

    private void infligirDanioA1(int danodetruco) {
        if (danodetruco <= 0) return;
        jugador1.actualizarSalud(-danodetruco);
    }

    private void infligirDanioA2(int danodetruco) {
        if (danodetruco <= 0) return;
        jugador2.actualizarSalud(-danodetruco);
    }

    // -- Se fija si la carta jugada es de espada o basto (Hecha para el truco)
    public boolean esOfensiva(Carta c) {
        String p = c.getPalo();
        return "Espada".equals(p) || "Basto".equals(p);
    }

    /**
     * Resuelve el enfrentamiento entre dos cartas jugadas en una mano.
     * Aplica efectos según el tipo de palo (curación, mana o daño).
     */
    public void jugarMano(Carta carta1, Carta carta2) {
        // --- Resetea en 0 los daños.
        resetDanoReciente();

        danoReciente1 = jugador1.getSalud();
        danoReciente2 = jugador2.getSalud();

        // --- Cartas de curación y maná.
        this.ultimaCartaJugadaJ1 = carta1;
        this.ultimaCartaJugadaJ2 = carta2;

        StringBuilder descripcion = new StringBuilder();


        if ((carta1.getPalo().equals("Copa") || carta1.getPalo().equals("Oro")) &&
                (carta2.getPalo().equals("Copa") || carta2.getPalo().equals("Oro"))) {

            if (carta1.getPalo().equals("Copa")) {
                jugador1.actualizarSalud(carta1.getNumero());
                jugador1.setDesangrado(false);
                descripcion.append("Jugador 1 recupera ").append(carta1.getNumero()).append(" de vida.\n");
            }
            if (carta1.getPalo().equals("Oro")) {
                jugador1.agregarMana(carta1.getNumero());
                descripcion.append("Jugador 1 gana ").append(carta1.getNumero()).append(" de maná.\n");
            }
            if (carta2.getPalo().equals("Copa")) {
                jugador2.actualizarSalud(carta2.getNumero());
                jugador2.setDesangrado(false);
                descripcion.append("Jugador 2 recupera ").append(carta2.getNumero()).append(" de vida.\n");
            }
            if (carta2.getPalo().equals("Oro")) {
                jugador2.agregarMana(carta2.getNumero());
                descripcion.append("Jugador 2 gana ").append(carta2.getNumero()).append(" de maná.\n");
            }
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
            descripcion.append("Ambos atacan con fuerza, las espadas y bastos chocan.\n");
        }
        // --- Cartas de distinto tipo; se aplica daño y efectos individuales.
        else {
            if (carta1.getPalo().equals("Espada") || carta1.getPalo().equals("Basto")) {
                jugador2.actualizarSalud(-carta1.getNumero());
                descripcion.append("Jugador 1 inflige ").append(carta1.getNumero()).append(" de daño a Jugador 2.\n");

                if (carta1.getPalo().equals("Espada")) {
                    jugador2.setDesangrado(true);
                } else if (carta2.getPalo().equals("Copa")) {
                    jugador2.setDesangrado(false);
                }

                if (carta2.getPalo().equals("Copa")) {
                    jugador2.actualizarSalud(carta2.getNumero());
                    descripcion.append("Jugador 2 recupera ").append(carta2.getNumero()).append(" de vida.\n");
                }
                if (carta2.getPalo().equals("Oro")) {
                    jugador2.agregarMana(carta2.getNumero());
                    descripcion.append("Jugador 2 gana ").append(carta2.getNumero()).append(" de maná.\n");
                }
            } else {
                jugador1.actualizarSalud(-carta2.getNumero());
                descripcion.append("Jugador 2 inflige ").append(carta2.getNumero()).append(" de daño a Jugador 1.\n");

                if (carta2.getPalo().equals("Espada")) {
                    jugador1.setDesangrado(true);
                } else if (carta1.getPalo().equals("Copa")) {
                    jugador1.setDesangrado(false);
                }

                if (carta1.getPalo().equals("Copa")) {
                    jugador1.actualizarSalud(carta1.getNumero());
                    descripcion.append("Jugador 1 recupera ").append(carta1.getNumero()).append(" de vida.\n");
                }
                if (carta1.getPalo().equals("Oro")) {
                    jugador1.agregarMana(carta1.getNumero());
                    descripcion.append("Jugador 1 gana ").append(carta1.getNumero()).append(" de maná.\n");
                }
            }
        }

        if (!trucoDisponible()) { // Truco activo esta mano
            boolean of1 = esOfensiva(carta1);
            boolean of2 = esOfensiva(carta2);

            if (of1 && of2) {
                int n1 = carta1.getNumero();
                int n2 = carta2.getNumero();
                if (n1 < n2) { //-- Ambas son ofensivas
                    infligirDanioA1(15);   // J1 tiro la más baja
                } else if (n2 < n1) {
                    infligirDanioA2(15);   // J2 tiro la más baja
                }
            } else if (of1 && !of2) {
                infligirDanioA2(15);  // J1 atacó, J2 no
            }
            else if (!of1 && of2) {
                infligirDanioA1(15);  // J2 atacó, J1 no
            }
            //-- Si no nadie tiro de ataque

            mostrarBannerTruco = true; // si querés mostrarlo siempre que hubo Truco
            resetearTruco();
        }

        // --- Efecto de sangrado
        if (jugador1.getDesangrado()) {
            jugador1.actualizarSalud(-5);
            descripcion.append("Jugador 1 pierde 5 de vida por desangrarse.\n");
        } else if (jugador2.getDesangrado()) {
            jugador2.actualizarSalud(-5);
            descripcion.append("Jugador 2 pierde 5 de vida por desangrarse.\n");
        }

        // -- Indica si el Truco esta activo
        if (!trucoDisponible()) {
            descripcion.append("El Truco esta ACTIVADO");
        }

        //* --- Resultado final
        descripcion.append("\n⚔️ Resultado intermedio de la mano:\n");
        if (jugador1.getSalud() > 0 & jugador2.getSalud() > 0) {
            descripcion.append("Jugador 1 → Vida: ").append(jugador1.getSalud()).append("\n");
            descripcion.append("Jugador 2 → Vida: ").append(jugador2.getSalud()).append("\n");
        } else if (jugador1.getSalud() <= 0){
            descripcion.append("Jugador 1 → Vida: ").append(0).append("\n");
            descripcion.append("Jugador 2 → Vida: ").append(jugador2.getSalud()).append("\n");
        } else {
            descripcion.append("Jugador 1 → Vida: ").append(jugador1.getSalud()).append("\n");
            descripcion.append("Jugador 2 → Vida: ").append(0).append("\n");

        }
        if (condicionFinalizacion()){
            if (jugador1.getSalud() <= 0) {
                ultimoResultado = descripcion + "\n🏆 Gano el Jugador 2";
            }
            else {
                ultimoResultado = descripcion + "\n🏆 Gano el Jugador 1";
            }
        }else {
            if (jugador1.getSalud() > jugador2.getSalud()) {
                ultimoResultado = descripcion + "\n🏆 La ventaja la tiene el Jugador 1";
            } else if (jugador2.getSalud() > jugador1.getSalud()) {
                ultimoResultado = descripcion + "\n🏆 La ventaja la tiene el Jugador 2";
            } else {
                ultimoResultado = descripcion + "\n🤝 Empate parcial entre ambos jugadores";
            }
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

    public String partidaTerminadaconBot() {

        if (jugador1.getSalud() <= 0 && jugador2.getSalud() <= 0)
            return "Empate: ambos llegaron a 0 de vida";
        if (jugador1.getSalud() <= 0)
            return "Has Perdido!";
        if (jugador2.getSalud() <= 0)
            return "Has Ganado!";
        if (jugador1.getSalud() > jugador2.getSalud()) return "Has Ganado!";
        if (jugador2.getSalud() > jugador1.getSalud()) return "Has Perdido!";
        if (jugador1.getMana() > jugador2.getMana()) return "Ganaste por mana";
        if (jugador2.getMana() > jugador1.getMana()) return "Perdiste por mana";
        return "Empate total";
    }
    /**
     * Evalúa si la partida debe finalizar (por salud, cartas o mazo vacío).
     *
     * @return true si se cumple alguna condición de fin de partida.
     */
    public static boolean condicionFinalizacion() {

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

    public void llenarManoBot(Bot bot) throws MazoVacioException {
        ArrayList<Carta> cartas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cartas.add(mazo.getCarta());
        }
        bot.setTresCartas(cartas);
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

    public void jugarCartaVsBot(int i, ArrayList<Carta> cartasjugadas, boolean esJugador) throws MazoVacioException, PosicionInvalidaException {
         if (esJugador)
            if (i < 0 || i >= getJugador1().getTresCartas().size()) {
                throw new PosicionInvalidaException("Índice de carta inválido: " + i);
            }
         else
            if (i < 0 || i >= getBot().getTresCartas().size()) {
                throw new PosicionInvalidaException("Índice de carta inválido: " + i);
            }

        Carta carta = new Carta();
        if (esJugador)
            carta = getJugadorMano().getTresCartas().get(i);
        else
            carta = getBot().getTresCartas().get(i);

        if (carta == null) {
            throw new PosicionInvalidaException("No hay carta en esa posición.");
        }
        // --- Quita mano jugada.
        if (esJugador)
            getJugador1().setCarta(i, null);
        else
            getBot().setCarta(i, null);

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
            if (esJugador)
                getJugador1().setCarta(i, getMazo().getCarta());
            else
                getBot().setCarta(i, getMazo().getCarta());
        }
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

    public Bot getBot() {
        return bot;
    }
}