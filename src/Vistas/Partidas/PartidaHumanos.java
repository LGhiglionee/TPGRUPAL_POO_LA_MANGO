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

public class PartidaHumanos extends PartidaBase{

    public PartidaHumanos() {
        super("Truco a 2 Lucas", "src/Recursos/Imagenes/Fondos/FondoJuego.png", 0);
    }

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
