package Mazo;

public class Espada extends Carta {

    public Espada(int numero) {
        super("Espada", numero, "Recibis el doble del numero de la carta como escudo");
    }

    public Espada() {}

    public String getHabilidad() {
        return habilidad;
    }

    @Override
    public int realizarHabilidad() {
        int escudo = this.numero * 2;
        return escudo;
    }
}
