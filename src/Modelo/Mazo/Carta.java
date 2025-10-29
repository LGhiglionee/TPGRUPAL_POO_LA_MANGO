package Modelo.Mazo;

import Excepciones.Recursos.ImagenNoEncontradaException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Carta {
    protected String palo;
    protected int numero;
    private Image imagen;

    public Carta(String palo, int numero) {
        this.palo = palo;
        this.numero = numero;
        setImagen(palo, numero);
    }

    public Carta() {}

    public String getPalo() {return this.palo;}

    public int getNumero() {return numero;}

    public void setImagen(String palo, int numero) {
        String ruta = "src/Recursos/Imagenes/Cartas/" + numero + palo + ".PNG";
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
        File imagenCarta = new File("src/Recursos/Imagenes/Cartas/"+ this.numero + this.palo + ".PNG");
        try {
            imagen = ImageIO.read(imagenCarta);
        }
        catch(IOException e) {
            throw new ImagenNoEncontradaException("La imagen " + this.numero + this.palo + ".png no se encuentra.");
        }
        return imagen;
    }
}
