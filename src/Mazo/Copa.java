package Mazo;

public class Copa extends Carta{
    public Copa(int numero) {
        super("Copa", numero, "Regeneras la vida de la carta");
    }

    public Copa() {}

    public String getHabilidad() {
        return habilidad;
    }

    public int realizarHabilidad() {
        return numero;
    }
}
