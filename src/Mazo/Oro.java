package Mazo;

public class Oro extends Carta{
    public Oro(int numero) {
        super("Oro", numero, "Ganas mana");
    }

    public String getHabilidad() {
        return habilidad;
    }

    public int realizarHabilidad() {
        return numero;
    }
}
