package Vistas.Partidas;

import Excepciones.Juego.JugadorSinCartasException;
import Excepciones.Juego.MazoVacioException;
import Excepciones.Juego.PosicionInvalidaException;
import Modelo.Entidades.Carta;
import Modelo.Motor.Turnos;
import Vistas.Pantallas.PantallaCambioTurno;
import Vistas.Pantallas.PantallaResultadosMano;

import javax.swing.*;
import java.util.ArrayList;

/**
 * — Implementa la partida entre dos jugadores humanos del juego "Truco a 2 Lucas".
 *
 * Extiende de PartidaBase y define la lógica específica de la versión PvP.
 * Controla los turnos alternados, la visualización de resultados y las pantallas
 * intermedias entre jugadas.
 *
 * Secuencia típica:
 *   Inicializa los jugadores y reparte las cartas.
 *   Cuando un jugador juega una carta, muestra una pantalla de cambio de turno.
 *   Al completarse la jugada (ambos jugaron), muestra el resultado de la mano.
 */
public class PartidaHumanos extends PartidaBase{
    // === Constructores ===
    /**
     * Constructor principal. Configura el título y fondo del modo "Jugador vs Jugador".
     */
    public PartidaHumanos() {
        super("Truco a 2 Lucas", "src/Recursos/Imagenes/Fondos/FondoJuego.png", 0);
    }



    // === Métodos ===
    /**
     * Inicializa el estado de juego: crea los jugadores, reparte las cartas y prepara el turno.
     */
    protected void inicializarJuego(int nousar) {
        try {
            turno = new Turnos();
            turno.llenarMano(turno.getJugador1());
            turno.llenarMano(turno.getJugador2());
            cartasjugadas = new ArrayList<>();
        } catch (MazoVacioException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al iniciar", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }


    /**
     * Procesa la carta jugada por el jugador actual.
     *
     * Parámetro: indice índice de la carta jugada (0–2)
     */
    protected void procesarCartaJugada(int indice)
            throws MazoVacioException, PosicionInvalidaException, JugadorSinCartasException {
        turno.jugarCarta(indice, cartasjugadas);
        // Si solo jugó un jugador (falta el otro)
        if (!cartasjugadas.isEmpty()) {
            String siguienteTurno = (turno.getJugadorMano() == turno.getJugador1())
                    ? "Turno del Jugador 1" : "Turno del Jugador 2";
            String ruta = "src/Recursos/Imagenes/Fondos/FondoIntermedio.png";
            PantallaCambioTurno cambio = new PantallaCambioTurno(this, ruta, siguienteTurno);
            cambio.setVisible(true);
        }

        if (cartasjugadas.isEmpty()) {
            Carta c1 = turno.getUltimaCartaJugadaJ1();
            Carta c2 = turno.getUltimaCartaJugadaJ2();
            if (c1 != null && c2 != null) {
                new PantallaResultadosMano(this,
                        turno,
                        new ImageIcon(c1.getImagen()).getImage(),
                        new ImageIcon(c2.getImagen()).getImage(),
                        turno.getUltimoResultado(),
                        turno.consumirBannerTruco()
                ).setVisible(true);
            }
        }
    }
}
