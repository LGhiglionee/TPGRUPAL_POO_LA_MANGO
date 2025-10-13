import Mazo.*;

import java.util.ArrayList;

public class Turnos {
    Mazo mazo;
    ArrayList<Carta> doscartas;

    public Turnos(){
        this.mazo = new Mazo();
        mazo.mezclarMazo();
        doscartas = new ArrayList<Carta>();    }

    public void jugarMano(Jugador jugador1, Jugador jugador2, Carta carta1, Carta carta2) {
        if (carta1.getPalo().equals("Copa") || carta2.getPalo().equals("Copa")) {
            if (carta1.getPalo().equals("Copa")) {
                jugador1.actualizarSalud(carta1.getNumero());
            } else {
                jugador2.actualizarSalud(carta2.getNumero());
            }
        } else if (carta1.getPalo().equals("Oro") || carta2.getPalo().equals("Oro")) {
            if (carta1.getPalo().equals("Oro")) {
                jugador1.agregarMana(carta1.getNumero());
            } else {
                jugador2.agregarMana(carta2.getNumero());
            }
        } else {
            calcularDanio(jugador1, jugador2, carta1, carta2);
        }
    }

    public void calcularDanio(Jugador jugador1, Jugador jugador2, Carta carta1, Carta carta2) {

        if (carta1.getPalo().equals("Basto") && carta2.getPalo().equals("Espada")) {
            if (carta2.getNumero() > carta1.getNumero()) {
                jugador1.actualizarSalud(carta2.getNumero() - carta1.getNumero());
            }
        }
        if (carta1.getPalo().equals("Espada") && carta2.getPalo().equals("Basto")) {
            if (carta2.getNumero() < carta1.getNumero()) {
                jugador2.actualizarSalud(carta2.getNumero() - carta1.getNumero());

            }

        }
        if (carta1.getPalo().equals("Basto") && carta2.getPalo().equals("Basto")) {
            if (carta2.getNumero() < carta1.getNumero()) {
                jugador2.actualizarSalud(-carta1.getNumero());
            } else {
                jugador1.actualizarSalud(-carta2.getNumero());

            }
        }
    }

    public String resultado(Jugador jugador1, Jugador jugador2, Mazo mazo) {
        if (!mazo.mazoVacio()) {
            if (jugador1.getMana() > jugador2.getMana()) {
                return "Gano jugador 1";
            } else if (jugador1.getMana() == jugador2.getMana()) {
                return "No gano nadie, EMPATE";
            } else {
                return "Gano jugador 2";
            }
        }
        else if (jugador2.getMana() == 0) {
            return "Gano jugador 1";
        }
        else{
            return "Gano jugador 2";
        }
    }

    public Jugador getJugadorMano(Jugador jugador1, Jugador jugador2) {
        if (jugador1.getMano()) {
            return jugador1;
        }
        else {
            return jugador2;
        }
    }

    public void alternarTurno(Jugador jugador1, Jugador jugador2) {
        if (jugador1.getMano()) {
            jugador1.setMano(false);
            jugador2.setMano(true);
        }
        else {
            jugador2.setMano(false);
            jugador1.setMano(true);
        }
    }

    public void llenarMano(ArrayList<Carta> cartas, Jugador jugador) {
        while (cartas.size() != 3) {
            cartas.add(mazo.getCarta());
        }
        jugador.setTresCartas(cartas);
    }
}



