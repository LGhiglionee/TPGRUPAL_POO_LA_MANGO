package Modelo.Recursos;

import Excepciones.Recursos.FuenteNoEncontradaException;
import Excepciones.Recursos.ImagenNoEncontradaException;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *  — Centraliza la carga de recursos gráficos del juego.
 */

public class GestorRecursos {

    /**
     * Carga una fuente desde la ruta indicada y la registra en el sistema gráfico.
     */
    public static Font cargarFuente(String rutaFuente) {
        File archivo = new File(rutaFuente);
        if (!archivo.exists()) {
            throw new FuenteNoEncontradaException("No se encontró la fuente: " + rutaFuente);
        }
        try {
            Font fuentePersonalizada = Font.createFont(Font.TRUETYPE_FONT, new File(rutaFuente));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuentePersonalizada);
            return fuentePersonalizada;
        } catch (IOException | FontFormatException e) {
            throw new FuenteNoEncontradaException("Error al cargar fuente "+rutaFuente);
        }
    }

    /**
     * Carga una imagen desde la ruta indicada.
     */
    public static Image cargarImagen(String rutaImagen) {
        File archivo = new File(rutaImagen);
        if (!archivo.exists()) {
            throw new ImagenNoEncontradaException("No se encontró la imagen: " + rutaImagen);
        }
        try {
            File miimagen = new File(rutaImagen);
            return ImageIO.read(miimagen);
        } catch (IOException e) {
            throw new ImagenNoEncontradaException("Error al cargar imagen "+rutaImagen);
        }
    }
    /**
     * Carga una imagen y la escala suavemente a las dimensiones indicadas.
     */
    public static ImageIcon cargarImagenEscalada(String rutaImagen, int ancho, int alto){
        Image imagen = cargarImagen(rutaImagen);
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}