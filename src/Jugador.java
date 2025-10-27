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

    public void actualizarSalud(int curacion) {
        if (this.salud + curacion > 100){
            this.salud = 100;
        } else {
            this.salud += curacion;
        }
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

    public void setCarta(ArrayList<Carta> trescartas, int indice, Carta carta) {
        this.trescartas.set(indice, carta);
    }

    public int calcularEnvido(){
        ArrayList<Carta> cartas = this.getTresCartas();
        int maxEnvido = 0;

        for (int i = 0; i < cartas.size(); i++) {
            for (int j = i + 1; j < cartas.size(); j++) {
                Carta c1 = cartas.get(i);
                Carta c2 = cartas.get(j);

                if (c1.getPalo().equals(c2.getPalo())) {
                    int valor1 = valorEnvido(c1);
                    int valor2 = valorEnvido(c2);
                    int total = valor1 + valor2 + 20;
                    if (total > maxEnvido) maxEnvido = total;
                }
            }
        }
        if (maxEnvido == 0) {
            for (int i = 0; i < cartas.size(); i++) {
                Carta c = cartas.get(i);
                int valor = valorEnvido(c);
                if (valor > maxEnvido) {
                    maxEnvido = valor;
                }
            }
        }
        return maxEnvido;
    }

    private int valorEnvido(Carta c) {
        int numero = c.getNumero();
        if (numero >= 10) return 0;
        return numero;
    }
}


