package Mazo;

public class Carta {
    protected String palo;
    protected int numero;
    protected String habilidad;

    public Carta(String palo, int numero) {
        this.palo = palo;
        this.numero = numero;
        this.habilidad = "";
    }
    public Carta(String palo, int numero, String habilidad) {
        this.palo = palo;
        this.numero = numero;
        this.habilidad = habilidad;
    }

    public String getPalo() {
        return palo;
    }

    public int getNumero() {
        return numero;
    }

    public Carta() {}

    public int realizarHabilidad() {
        return 0;
    }
}
