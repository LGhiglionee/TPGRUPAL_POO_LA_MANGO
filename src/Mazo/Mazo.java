package Mazo;

public class Mazo {
    Espada[] espadas;
    Oro[] oros;
    Basto[] bastos;
    Copa[] copas;

    public Mazo() {}

    public Mazo(Espada[] espadas, Oro[] oros, Basto[] bastos, Copa[] copas) {
        this.espadas = espadas;
        this.oros = oros;
        this.bastos = bastos;
        this.copas = copas;
    }

    public Espada[] getEspadas() {
        return espadas;
    }

    public Oro[] getOros() {
        return oros;
    }

    public Basto[] getBastos() {
        return bastos;
    }

    public Copa[] getCopas() {
        return copas;
    }
}
