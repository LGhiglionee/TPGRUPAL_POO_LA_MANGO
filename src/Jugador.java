import Mazo.Carta;

import java.util.ArrayList;

public class Jugador {
    private int salud;
    private int mana;
    private boolean mano;
    private ArrayList<Carta> trescartas;

    public Jugador() {
        this.salud = 100;
        this.mana = 0;
        this.trescartas = new ArrayList<>();
        this.mano = true;
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

    public void setMano(boolean mano) {
        this.mano = mano;
    }

    public ArrayList<Carta> getTresCartas() {
        return trescartas;
    }

    public  void setTresCartas(ArrayList<Carta> trescartas) {
        this.trescartas = trescartas;
    }
}
