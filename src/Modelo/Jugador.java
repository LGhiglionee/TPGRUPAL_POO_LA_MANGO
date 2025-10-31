package Modelo;

import Excepciones.Juego.JugadorSinCartasException;
import Excepciones.Juego.PosicionInvalidaException;
import Modelo.Mazo.Carta;
import java.util.ArrayList;

/**
 *  — Representa a un jugador dentro del juego.
 * <p>Almacena los atributos principales del jugador (salud, maná, cartas, turno)
 * y provee métodos para manipularlos, incluyendo acciones como recuperar salud,
 * agregar maná, asignar cartas y calcular el envido.</p>
 */

public class Jugador {
    protected int salud;
    protected int mana;
    protected boolean mano;
    protected ArrayList<Carta> trescartas;
    protected boolean desangrado;
    protected boolean esBot;

    /**
     * Constructor por defecto.
     * Inicializa la salud en 100, el maná en 0, tres cartas vacías y asigna la mano al jugador (por defecto true).
     */
    public Jugador() {
        this.salud = 100;
        this.mana = 999999999;
        this.trescartas = new ArrayList<>(3);
        this.mano = true;
        this.desangrado = false;
        this.esBot = false;
    }

    // --- Getters y Setters.
    public int getSalud() {return salud;}
    public int getMana() {return mana;}
    public boolean getMano() { return mano; }
    public void setMano(boolean mano) {this.mano = mano;}
    public ArrayList<Carta> getTresCartas() {return trescartas;}
    public  void setTresCartas(ArrayList<Carta> trescartas) {this.trescartas = trescartas;}

    /**
     * Modifica la salud del jugador, sin exceder el máximo de 100.
     */
    public void actualizarSalud(int salud) {
        if (this.salud + salud > 100){
            this.salud = 100;
        } else {
            this.salud += salud;
        }
    }

    /**
     * Incrementa el maná del jugador en la cantidad indicada.
     */
    public void agregarMana(int mana) {this.mana += mana;}

    public boolean getDesangrado() {
        return desangrado;
    }

    public void setDesangrado(boolean desangrado) {
        this.desangrado = desangrado;
    }

    /**
     * Asigna una carta a una posición específica dentro de las tres disponibles.
     */
    public void setCarta(int indice, Carta carta) throws PosicionInvalidaException {
        if (indice < 0 || indice >= this.trescartas.size()) {
            throw new PosicionInvalidaException("Índice fuera de rango al intentar asignar carta: " + indice);
        }
        this.trescartas.set(indice, carta);
    }

    /**
     * Calcula el puntaje de envido del jugador según sus tres cartas.
     *
     * <p>El cálculo sigue la lógica tradicional del truco:
     * si hay dos cartas del mismo palo, se suman sus valores (sin contar 10, 11, 12)
     * y se agrega un bono de 20 puntos.</p>
     */
    public int calcularEnvido() throws JugadorSinCartasException {
        if (trescartas == null || trescartas.isEmpty()) {
            throw new JugadorSinCartasException("El jugador no tiene cartas para calcular el envido.");
        }

        ArrayList<Carta> cartas = this.getTresCartas();
        int maxEnvido = 0;

        // --- Combinaciones de cartas del mismo palo.
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
        // --- En caso que no haya cartas del mismo palo.
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
    /**
     * Retorna el valor de una carta para el cálculo del envido.
     * <p>Las cartas del 10 en adelante (10, 11, 12) valen 0.</p>
     */
    private int valorEnvido(Carta c) {
        int numero = c.getNumero();
        if (numero >= 10) return 0;
        return numero;
    }
}