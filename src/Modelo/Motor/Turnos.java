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
 * Coordina las acciones de los jugadores, el manejo del mazo y la resolución
 * de los efectos de las cartas (daño, curación y energía/mana).
 *
 * Responsabilidades principales:
 *   Gestionar el turno activo entre los jugadores.
 *   Procesar jugadas individuales y enfrentamientos entre cartas.
 *   Evaluar condiciones de finalización de la partida.
 *   Resolver el resultado del "envido" y calcular ganador general.
 */

public class Turnos {
    // === Atributos ===
    static Mazo mazo;
    ArrayList<Carta> doscartas;

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



    // === Constructores ===
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


    /**
     * Constructor alternativo (modo Jugador vs Bot).
     *
     * Parámetro: dificultad Nivel de dificultad del bot (1 = medio, 0 = fácil).
     */
    public Turnos(int dificultad) {
        mazo = new Mazo();
        mazo.mezclarMazo();
        doscartas = new ArrayList<Carta>();
        jugador1 = new Jugador();
        bot = new Bot(dificultad);
        jugador2 = bot; //Para no cambiar en partida
        jugador1.setMano(true);
    }



    // === Getters y Setters ===
    public Carta getUltimaCartaJugadaJ1() { return ultimaCartaJugadaJ1; }
    public Carta getUltimaCartaJugadaJ2() { return ultimaCartaJugadaJ2; }
    public String getUltimoResultado() { return ultimoResultado; }

    public Bot getBot() {return bot;}

    public Jugador getJugador1() {return jugador1;}
    public Jugador getJugador2() {return jugador2;}
    public Mazo getMazo() {return mazo;}

    /**
     * Determina quién tiene actualmente el turno.
     */
    public Jugador getJugadorMano() {
        if (jugador1.getMano()) {
            return jugador1;
        } else {
            return jugador2;
        }
    }

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



    // === Métodos auxiliares internos ===
    private void resetDanoReciente() {
        danoReciente1 = 0;
        danoReciente2=0;
    }


    private void infligirDanioA1(int danodetruco) {
        if (danodetruco <= 0) return;
        jugador1.actualizarSalud(-danodetruco);
    }


    private void infligirDanioA2(int danodetruco) {
        if (danodetruco <= 0) return;
        jugador2.actualizarSalud(-danodetruco);
    }


    /**
     * Determina si una carta es de tipo ofensivo (Espada o Basto).
     */
    public boolean esOfensiva(Carta c) {
        String p = c.getPalo();
        return "Espada".equals(p) || "Basto".equals(p);
    }



    // === Lógica principal ===
    /**
     * Resuelve el enfrentamiento entre dos cartas jugadas en una mano.
     * Aplica efectos de curación, mana o daño según el palo.
     */
    public void jugarMano(Carta carta1, Carta carta2) {
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

        // --- Efectos del truco.
        if (!trucoDisponible()) {
            boolean of1 = esOfensiva(carta1);
            boolean of2 = esOfensiva(carta2);

            if (of1 && of2) {
                int n1 = carta1.getNumero();
                int n2 = carta2.getNumero();
                if (n1 < n2) {
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

            mostrarBannerTruco = true;
            resetearTruco();
        }

        // --- Efecto de sangrado.
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

        // --- Resultado parcial.
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
     * Evalúa si la partida debe finalizar (por salud, cartas o mazo vacío).
     *
     * Retorna true si se cumple alguna condición de fin de partida.
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


    /**
     * Alterna el turno activo entre los jugadores.
     */
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


    /**
     * Ejecuta la lógica de jugada para jugador vs jugador.
     */
    public void jugarCarta(int i, ArrayList<Carta> cartasjugadas) throws MazoVacioException, PosicionInvalidaException {
        if (i < 0 || i >= getJugadorMano().getTresCartas().size()) {
            throw new PosicionInvalidaException("Índice de carta inválido: " + i);
        }
        Carta carta = getJugadorMano().getTresCartas().get(i);

        if (carta == null) {
            throw new PosicionInvalidaException("No hay carta en esa posición.");
        }
        getJugadorMano().setCarta(i, null);

        if (!cartasjugadas.isEmpty()) {
            cartasjugadas.add(carta);
            jugarMano(cartasjugadas.get(0), cartasjugadas.get(1));
            cartasjugadas.clear();
            resetearEnvido();
        } else {
            cartasjugadas.add(carta);
        }
        if (mazo.cartasRestantes() != 0){
            getJugadorMano().setCarta(i, getMazo().getCarta());
        }
        // --- Cambia de turno.
        alternarTurno();
    }


    /**
     * Ejecuta la lógica de jugada para jugador vs bot.
     */
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
        if (esJugador)
            getJugador1().setCarta(i, null);
        else
            getBot().setCarta(i, null);

        if (!cartasjugadas.isEmpty()) {
            cartasjugadas.add(carta);
            jugarMano(cartasjugadas.get(0), cartasjugadas.get(1));
            cartasjugadas.clear();
            resetearEnvido();
        } else {
            cartasjugadas.add(carta);
        }
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
     * Parámetro: envidoJ1 puntos de envido del jugador 1
     * Parámetro: envidoJ2 puntos de envido del jugador 2
     * Retorna: mensaje con el resultado del envido y cambios de salud aplicados.
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
}