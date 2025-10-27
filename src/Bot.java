import Mazo.Carta;

import java.util.ArrayList;

public class Bot extends Jugador {
    int dificultad;

    public Bot(int dificultad) {
        super();
        this.dificultad = dificultad;
        this.salud = (int) (30 + dificultad * 0.3);
        this.mano = false;
    }

    public boolean hayPalo(String palo) {
        ArrayList<Carta> turista = this.trescartas;

    }

    public Carta analisis() {
        if (this.salud < 10) {
            return this.trescartas.getFirst();
        }
        else
            return null;
    }
}
