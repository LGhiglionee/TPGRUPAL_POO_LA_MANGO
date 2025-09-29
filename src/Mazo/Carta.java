package Mazo;

public abstract class Carta {
    protected String palo;
    protected int numero;
    protected String habilidad;

    public Carta(String palo, int numero, String habilidad) {
        this.palo = palo;
        this.numero = numero;
        this.habilidad = habilidad;
    }

    public Carta() {}

    public int realizarHabilidad() {
        return 0;
    }
}
