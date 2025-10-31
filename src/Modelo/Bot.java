package Modelo;

import Excepciones.Juego.MazoVacioException;
import Modelo.Mazo.*;
import java.util.ArrayList;

public class Bot extends Jugador {
    int dificultad;

    public Bot(int dificultad) {
        super();
        this.dificultad = dificultad;
        this.salud = 30 * dificultad;
        this.mano = false;
        this.esBot = true;
    }

    public Carta decision(Mazo mazo) throws MazoVacioException {
        if (this.dificultad == 0) {
            return analisisFacil();
        }
        return analisisMedio(mazo);
    }

    public boolean hayPalo(String palo) {
        for (Carta c : this.trescartas) {
            if (c != null && palo.equals(c.getPalo())) {
                return true;
            }
        }
        return false;
    }

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

    public int indiceCartaElegida(Carta carta, ArrayList<Carta> cartas) {
        for (int i = 0; i < cartas.size(); i++) {
            if (cartas.get(i) == carta) {
                return i;
            }
        }
        return -1;
    }
}
