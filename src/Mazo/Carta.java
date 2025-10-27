package Mazo;

import Excepciones.ImagenNoEncontradaException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
        String ruta = "Imagenes/Cartas/" + numero + palo + ".PNG";
        try {
            File archivo = new File(ruta);
            imagen = ImageIO.read(archivo);
            if (imagen == null) {
                throw new ImagenNoEncontradaException("No se pudo leer la imagen " + archivo.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new ImagenNoEncontradaException("La imagen " + numero + palo + ".PNG no se encuentra.");
        }
    }

    public Image getImagen(){
        File imagenCarta = new File("Imagenes/Cartas/"+ this.numero + this.palo + ".PNG");
        try {
            imagen = ImageIO.read(imagenCarta);
        }
        catch(IOException e) {
            throw new ImagenNoEncontradaException("La imagen " + this.numero + this.palo + ".png no se encuentra.");
        }
        return imagen;
    }
}
