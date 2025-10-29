package Modelo;

import Excepciones.Juego.JugadorSinCartasException;
import Excepciones.Juego.PosicionInvalidaException;
import Modelo.Mazo.Carta;
import java.util.ArrayList;

public class Jugador {
    protected int salud;
    protected int mana;
    protected boolean mano;
    protected ArrayList<Carta> trescartas;
    protected boolean desangrado;

    public Jugador() {
        this.salud = 100;
        this.mana = 0;
        this.trescartas = new ArrayList<>(3);
        this.mano = true;
        this.desangrado = false;
    }

    public int getSalud() {
        return salud;
    }

    public void actualizarSalud(int salud) {
        if (this.salud + salud > 100){
            this.salud = 50;
        } else {
            this.salud += salud;
        }
    }

    public boolean getDesangrado() {
        return desangrado;
    }

    public void setDesangrado(boolean desangrado) {
        this.desangrado = desangrado;
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

    public void setCarta(int indice, Carta carta) throws PosicionInvalidaException {
        if (indice < 0 || indice >= this.trescartas.size()) {
            throw new PosicionInvalidaException("Índice fuera de rango al intentar asignar carta: " + indice);
        }
        this.trescartas.set(indice, carta);
    }

    public int calcularEnvido() throws JugadorSinCartasException {
        if (trescartas == null || trescartas.isEmpty()) {
            throw new JugadorSinCartasException("El jugador no tiene cartas para calcular el envido.");
        }

        ArrayList<Carta> cartas = this.getTresCartas();
        int maxEnvido = 0;

        for (int i = 0; i < cartas.size(); i++) {
            for (int j = i + 1; j < cartas.size(); j++) {
                Carta c1 = cartas.get(i);
                Carta c2 = cartas.get(j);

                if (c1.getPalo().equals(c2.getPalo())) {
                    int total = valorEnvido(c1) + valorEnvido(c2) + 20;
                    if (total > maxEnvido)
                        maxEnvido = total;
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


