package Modelo;

import Excepciones.Juego.MazoVacioException;
import Modelo.Mazo.*;
import java.util.ArrayList;

public class Bot extends Jugador {
    int dificultad;

    public Bot(int dificultad) {
        super();
        this.dificultad = dificultad;
        this.salud = (int) (30 + dificultad * 0.3);
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
        ArrayList<Carta> turista = new ArrayList<>(this.trescartas);

        while (!turista.isEmpty()) {
            if (turista.getFirst().getPalo().equals(palo)) {
                return true;
            }
            turista.removeFirst();
        }
        return false;
    }

    public Carta cartaMasAlta(String palo) {
        ArrayList<Carta> turista = new ArrayList<>(this.trescartas);
        Carta max = new Carta();

        while (!turista.isEmpty()) {
            if (turista.getFirst().getPalo().equals(palo) && max.getNumero() < turista.getFirst().getNumero()) {
                max = turista.getFirst();
            }
            turista.removeFirst();
        }
        return max;
    }

    public float porcentajePaloMazo(Mazo mazo, String palo) throws MazoVacioException {
        Mazo turista = mazo;
        float total = 0;
        float carta = 0;

        while (turista.cartasRestantes() != 0) {
            if (turista.getCarta().getPalo().equals(palo)) {
                carta += 1;
            }
            total += 1;
        }
        return carta / total;
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

    public int indiceCartaElejida(Carta carta, ArrayList<Carta> cartas) {
        for (int i = 0; i < cartas.size(); i++) {
            if (cartas.get(i) == carta) {
                return i;
            }
        }
        return 0;
    }
}
