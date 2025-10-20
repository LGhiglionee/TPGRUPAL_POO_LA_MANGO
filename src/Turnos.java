import Mazo.*;

import java.util.ArrayList;

public class Turnos {
    Mazo mazo;
    ArrayList<Carta> doscartas;
    Jugador jugador1;
    Jugador jugador2;

    public Turnos(){
        this.mazo = new Mazo();
        mazo.mezclarMazo();
        doscartas = new ArrayList<Carta>();
        jugador1 = new Jugador();
        jugador2 = new Jugador();
    }

    public void jugarMano(Carta carta1, Carta carta2) {

        if ((carta1.getPalo().equals("Copa") || carta1.getPalo().equals("Oro")) &&
                (carta2.getPalo().equals("Copa") || carta2.getPalo().equals("Oro"))) {

            if (carta1.getPalo().equals("Copa")) jugador1.actualizarSalud(carta1.getNumero());
            if (carta1.getPalo().equals("Oro")) jugador1.agregarMana(carta1.getNumero());

            if (carta2.getPalo().equals("Copa")) jugador2.actualizarSalud(carta2.getNumero());
            if (carta2.getPalo().equals("Oro")) jugador2.agregarMana(carta2.getNumero());
        }

        else if ((carta1.getPalo().equals("Espada") || carta1.getPalo().equals("Basto")) &&
                (carta2.getPalo().equals("Espada") || carta2.getPalo().equals("Basto"))) {

            calcularDanio(carta1, carta2);
        }

        else {
            if (carta1.getPalo().equals("Espada") || carta1.getPalo().equals("Basto")) {
                jugador2.actualizarSalud(-carta1.getNumero());

                if (carta2.getPalo().equals("Copa")) jugador2.actualizarSalud(carta2.getNumero());
                if (carta2.getPalo().equals("Oro")) jugador2.agregarMana(carta2.getNumero());
            } else {
                jugador1.actualizarSalud(-carta2.getNumero());

                if (carta1.getPalo().equals("Copa")) jugador1.actualizarSalud(carta1.getNumero());
                if (carta1.getPalo().equals("Oro")) jugador1.agregarMana(carta1.getNumero());
            }
        }
    }

    public void calcularDanio(Carta carta1, Carta carta2) {
        if ((carta1.getPalo().equals("Basto") || carta1.getPalo().equals("Espada")) && (carta2.getPalo().equals("Basto") || carta2.getPalo().equals("Espada"))) {
           if (carta2.getNumero() > carta1.getNumero()) {
                jugador1.actualizarSalud(-(carta2.getNumero() - carta1.getNumero()));
            } if (carta1.getNumero() > carta2.getNumero()) {
                jugador2.actualizarSalud(-(carta1.getNumero() - carta2.getNumero()));
            }
        }
    }

    public String resultado(Mazo mazo) {
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

    public Jugador getJugadorMano() {
        if (jugador1.getMano()) {
            return jugador1;
        }
        else {
            return jugador2;
        }
    }

    public String getStringJugadorMano() {
        if (jugador1.getMano()) {
            return "Jugador 1";
        }
        else return "Jugador 2";
    }

    public void alternarTurno() {
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

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Mazo getMazo(){
        return mazo;
    }
}



