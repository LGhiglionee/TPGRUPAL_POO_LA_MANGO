package Modelo.Entidades;

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
    private String rutaImagen;
    private Image imagen;

    /**
     * Constructor que crea una carta con su palo, número e imagen correspondiente.
     */
    public Carta(String palo, int numero) {
        this.palo = palo;
        this.numero = numero;
        this.rutaImagen = "src/Recursos/Imagenes/Cartas/" + numero + palo + ".PNG";
        this.imagen = cargarImagen();
    }

    public Carta() {}

    // --- Getters
    public String getPalo() {return this.palo;}
    public int getNumero() {return numero;}
    public Image getImagen(){return  imagen;}

    /**
     * Carga la imagen asociada a la carta desde los recursos.
     */
    private Image cargarImagen () {
        try {
            File archivo = new File (rutaImagen);
            Image img = ImageIO.read(archivo);
            if (img == null) {
                throw new ImagenNoEncontradaException("No se pudo leer la imagen " + rutaImagen);
            }
            return img;
        } catch (IOException ex) {
            throw new ImagenNoEncontradaException("No se encontró la imagen " + rutaImagen);
        }
    }
}
