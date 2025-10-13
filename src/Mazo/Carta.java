package Mazo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Carta {
    protected String palo;
    protected int numero;
    protected String habilidad;
    private Image imagen;

    public Carta(String palo, int numero) {
        this.palo = palo;
        this.numero = numero;
        this.habilidad = "";
        setImagen(palo, numero);
    }
    public Carta(String palo, int numero, String habilidad) {
        this.palo = palo;
        this.numero = numero;
        this.habilidad = habilidad;
        setImagen(palo, numero);
    }

    public Carta() {}

    public String getPalo() {

        return this.palo;
    }

    public int getNumero() {
        return numero;
    }


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

    public void setImagen(String palo, int numero) {
        File miimagen=new File(palo + numero + ".png");
        try {
            this.imagen = ImageIO.read(miimagen);
        }
        catch(IOException e) {
            System.out.println("La imagen no se encuentra");
        }
    }

    public Image getImagen() { return imagen; }
}
