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
 */

public class Carta {
    // === Atributos ===
    protected String palo;
    protected int numero;
    private Image imagen;



    // === Constructores ===
    /**
     * Constructor que crea una carta con su palo, número e imagen correspondiente.
     */
    public Carta(String palo, int numero) {
        this.palo = palo;
        this.numero = numero;
        this.imagen = cargarImagenDesdeRecurso();
    }

    /**
     * Constructor vacío.
     */
    public Carta() {
    }



    // === Getters ===
    public String getPalo() {
        return this.palo;
    }
    public int getNumero() {
        return this.numero;
    }
    public Image getImagen() {
        return this.imagen;
    }



    // === Métodos carga de imagen ===
    /**
     * Carga la imagen asociada a la carta desde los recursos.
     */
    private Image cargarImagenDesdeRecurso() {
        String ruta = "/Recursos/Imagenes/Cartas/" + numero + palo + ".PNG";
        try {
            return ImageIO.read(getClass().getResourceAsStream(ruta));
        } catch (IOException | NullPointerException e) {
            throw new ImagenNoEncontradaException("No se pudo leer la imagen " + ruta);
        }
    }
}

