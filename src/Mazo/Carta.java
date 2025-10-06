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

        return this.palo;
    }

    public int getNumero() {
        return numero;
    }

    public Carta() {}

    public String getHabilidad(String palo, Carta carta) {
        if (carta.getPalo().equals("Basto")){
            return "Haces el doble de dano si ganas pero recibis el doble si perdes";
        }
        else if (carta.getPalo().equals("Espada")){
            return "Recibis el doble del numero de la carta como escudo";
        }
        else if (carta.getPalo().equals("Copa")){
            return "Regeneras la vida de la carta";
        }
        else if (carta.getPalo().equals("Oro")){}
        return "Ganas mana";
    }
    public void realizarHabilidad() {
    }
}
