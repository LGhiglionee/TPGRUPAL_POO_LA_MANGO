package Vistas.Partidas;

import Excepciones.Juego.JugadorSinCartasException;
import Excepciones.Juego.MazoVacioException;
import Excepciones.Juego.PosicionInvalidaException;
import Modelo.Entidades.Bot;
import Modelo.Entidades.Carta;
import Modelo.Motor.Turnos;
import Vistas.Pantallas.PantallaResultadosMano;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PartidaBot extends PartidaBase {
    public PartidaBot(int dificultad) {
        super("Truco a 2 Lucas (vs Bot)", "src/Recursos/Imagenes/Fondos/FondoJuego.png", dificultad);
    }

    protected void inicializarJuego(int dificultad) {
        try {
            turno = new Turnos(1);
            turno.llenarMano(turno.getJugador1());
            turno.llenarManoBot(turno.getBot());
            cartasjugadas = new ArrayList<>();
        } catch (MazoVacioException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al iniciar", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    protected void procesarCartaJugada(int indice)
            throws MazoVacioException, PosicionInvalidaException, JugadorSinCartasException {
        // Juega humano
        turno.jugarCartaVsBot(indice, cartasjugadas, true);

        // Si el bot debe responder
        if (cartasjugadas.size() == 1 && turno.getJugadorMano() == turno.getBot()) {
            jugarTurnoBot();
        }

        Carta c1 = turno.getUltimaCartaJugadaJ1();
        Carta c2 = turno.getUltimaCartaJugadaJ2();
        if (c1 != null && c2 != null) {
            Image img1 = new ImageIcon(c1.getImagen()).getImage();
            Image img2 = new ImageIcon(c2.getImagen()).getImage();
            String resultado = turno.getUltimoResultado();
            boolean mostrarTruco = turno.consumirBannerTruco();
            PantallaResultadosMano p = new PantallaResultadosMano(this, img1, img2, resultado, mostrarTruco);
            p.setVisible(true);
        }
    }


    private void jugarTurnoBot() throws MazoVacioException, PosicionInvalidaException, JugadorSinCartasException {
        Bot bot = turno.getBot();
        Carta eleccion = bot.decision(turno.getMazo());
        int indice = bot.indiceCartaElegida(eleccion, bot.getTresCartas());

        //Si te da mal, busca cualquiera de la mano (llega null o -1)
        if (indice < 0 || indice >= bot.getTresCartas().size() || bot.getTresCartas().get(indice) == null) {
            indice = 0;
            for (int i = 0; i < bot.getTresCartas().size(); i++) {
                if (bot.getTresCartas().get(i) != null) {
                    indice = i;
                    break;
                }
            }
        }

        if (eleccion.getNumero() >= 10 && turno.trucoDisponible() && turno.getJugador2().getMana() >= 10 && turno.esOfensiva(eleccion)) {
            turno.getJugadorMano().agregarMana(-10);
            turno.bloquearTruco();
            JOptionPane.showMessageDialog(this, "¡Truco cantado!");
        } else if (turno.envidoDisponible() && turno.getJugador2().getMana() >= 5 && turno.getBot().calcularEnvido() >= 27) {
            turno.getJugador2().agregarMana(-5);
            JOptionPane.showMessageDialog(this,
                    turno.jugarEnvido(turno.getJugador1().calcularEnvido(), turno.getJugador2().calcularEnvido()),
                    "Resultado del Envido", JOptionPane.INFORMATION_MESSAGE);
            turno.bloquearEnvido();
        }

        if (indice != -1) {
            //Juega bot
            turno.jugarCartaVsBot(indice, cartasjugadas, false);
        }
    }
}



