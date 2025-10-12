import Mazo.Carta;

import java.util.ArrayList;

public class Jugador {
    private int salud;
    private int mana;
    private boolean mano;

    public Jugador() {
        this.salud = 100;
        this.mana = 0;
    }

    public int getSalud() {
        return salud;
    }

    public void actualizarSalud(int curacion) { this.salud += curacion; }

    public void reducirMana(int mana) {
        this.mana += mana;
    }

    public void agregarMana(int mana) {
        this.mana += mana;
    }

    public int getMana() {
        return mana;
    }

    public boolean getMano() { return mano; }

    public void setMano(boolean mano) { this.mano = mano; }
}
