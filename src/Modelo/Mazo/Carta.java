package Modelo.Mazo;

import Excepciones.Recursos.ImagenNoEncontradaException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 *  — Representa una carta individual del mazo español.
 *
 * <p>Cada carta tiene un número, un palo y una imagen asociada.
 * Se utiliza para las acciones del juego como ataques, curaciones o suma de maná.</p>
 *
 * <p>Las imágenes deben estar ubicadas en la carpeta:
 * {@code src/Recursos/Imagenes/Cartas/} y tener formato:
 * <b>{numero}{palo}.PNG</b> — por ejemplo:
 * <code>1Espada.PNG</code> o <code>7Copa.PNG</code>.</p>
 */

public class Carta {
    // --- Atributos cartas.
    protected String palo;
    protected int numero;
    private Image imagen;

    /**
     * Constructor que crea una carta con su palo, número e imagen correspondiente.
     */
    public Carta(String palo, int numero) {
        this.palo = palo;
        this.numero = numero;
        setImagen(palo, numero);
    }

    public Carta() {}

    // --- Getters
    public String getPalo() {return this.palo;}
    public int getNumero() {return numero;}

    /**
     * Carga la imagen asociada a la carta desde los recursos.
     */
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

    /**
     * Devuelve la imagen actual de la carta cargada desde disco.
     * <p>Vuelve a leer el archivo para asegurar que la imagen esté disponible
     * incluso si fue modificada o regenerada externamente.</p>
     *
     */
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
