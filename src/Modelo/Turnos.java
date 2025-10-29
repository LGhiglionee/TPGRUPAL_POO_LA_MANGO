package Modelo;

import Excepciones.Juego.MazoVacioException;
import Excepciones.Juego.PosicionInvalidaException;
import Modelo.Mazo.*;

import java.util.ArrayList;

public class Turnos {
    Mazo mazo;
    ArrayList<Carta> doscartas;
    Jugador jugador1;
    Jugador jugador2;

    public Turnos() {
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
        } else if ((carta1.getPalo().equals("Espada") || carta1.getPalo().equals("Basto")) &&
                (carta2.getPalo().equals("Espada") || carta2.getPalo().equals("Basto"))) {

            calcularDanio(carta1, carta2);
        } else {
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
            }
            if (carta1.getNumero() > carta2.getNumero()) {
                jugador2.actualizarSalud(-(carta1.getNumero() - carta2.getNumero()));
            }
        }
    }

    public String resultado(Mazo mazo) throws MazoVacioException {
        if (mazo == null || mazo.cartasRestantes() == 0) {
            throw new MazoVacioException("No se puede calcular el resultado: el mazo está vacío.");
        }

        int mana1 = jugador1.getMana();
        int mana2 = jugador2.getMana();

        if (mana1 > mana2) {
            return "Gano jugador 1";
        } else if (mana1 == mana2) {
            return "No gano nadie, EMPATE";
        } if (mana1 < mana2){
            return "Gano jugador 2";

        } else if (jugador2.getMana() == 0) {
            return "Gano jugador 1";
        } else {
            return "Gano jugador 2";
        }
    }

    public Jugador getJugadorMano() {
        if (jugador1.getMano()) {
            return jugador1;
        } else {
            return jugador2;
        }
    }

    public String getStringJugadorMano() {
        if (jugador1.getMano()) {
            return "Modelo.Jugador 1";
        } else return "Modelo.Jugador 2";
    }

    public void alternarTurno() {
        if (jugador1.getMano()) {
            jugador1.setMano(false);
            jugador2.setMano(true);
        } else {
            jugador2.setMano(false);
            jugador1.setMano(true);
        }
    }

    public void llenarMano(Jugador jugador) throws MazoVacioException {
        ArrayList<Carta> cartas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
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

    public Mazo getMazo() {
        return mazo;
    }

    public void jugarCarta(int i, ArrayList<Carta> cartasjugadas) throws MazoVacioException, PosicionInvalidaException {
        Carta carta = getJugadorMano().getTresCartas().get(i);

        if (!cartasjugadas.isEmpty()) {
            cartasjugadas.add(carta);
            jugarMano(cartasjugadas.get(0), cartasjugadas.get(1));
            cartasjugadas.clear();
        } else {
            cartasjugadas.add(carta);
        }
        getJugadorMano().setCarta(i, getMazo().getCarta());

        alternarTurno();
    }

    public String jugarEnvido(int envidoJ1, int envidoJ2) {
        String mensaje = "Modelo.Jugador 1: " + envidoJ1 + " puntos\n" +
                "Modelo.Jugador 2: " + envidoJ2 + " puntos\n";

        if (envidoJ1 > envidoJ2) {
            mensaje += "Gana el envido Modelo.Jugador 1";
            getJugador2().actualizarSalud(-envidoJ1 / 2);
        } else if (envidoJ2 > envidoJ1) {
            mensaje += "Gana el envido Modelo.Jugador 2";
            getJugador1().actualizarSalud(-envidoJ1 / 2);
        } else {
            mensaje += "Empate en el envido";
        }
        return mensaje;
    }
}



