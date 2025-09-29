package Mazo;

public class Basto extends Carta {
    public Basto(int numero) {
        super("Basto", numero, "Haces el doble de dano si ganas pero recibis el doble si perdes");
    }

    public Basto() {}

    public String getHabilidad() {
        return habilidad;
    }

    public int realizarHabilidad() {
        return numero;
    }
}
