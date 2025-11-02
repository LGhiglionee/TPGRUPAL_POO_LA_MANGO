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
 * — Centraliza la carga de recursos gráficos del juego "Truco a 2 Lucas".
 *
 * Se encarga de gestionar imágenes, íconos y fuentes tipográficas,
 * lanzando excepciones personalizadas ante cualquier problema de acceso o lectura.
 *
 * Principales responsabilidades:
 *     Cargar fuentes personalizadas (.ttf, .otf) y registrarlas en el entorno gráfico.
 *     Cargar imágenes desde el sistema de archivos o recursos empaquetados.
 *     Escalar imágenes manteniendo la suavidad y proporciones visuales.
 */

public class GestorRecursos {
    // === Fuentes ===
    /**
     * Carga una fuente desde la ruta indicada y la registra en el sistema gráfico.
     *
     * Parámetro: rutaFuente Ruta absoluta o relativa al archivo de fuente (.ttf / .otf).
     * Retorna: Objeto  Font ya registrado en el sistema gráfico.
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



    // === Imágenes ===
    /**
     * Carga una imagen desde la ruta indicada.
     *
     * Parámetro: rutaImagen Ruta absoluta o relativa del archivo de imagen.
     * Retorna: Objeto  Image cargado correctamente.
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
     * Carga una imagen desde disco y la escala suavemente a las dimensiones indicadas.
     *
     * Parámetro: rutaImagen Ruta del archivo de imagen.
     * Parámetro: ancho      Ancho deseado en píxeles.
     * Parámetro: alto       Alto deseado en píxeles.
     * Retorna: Objeto ImageIcon escalado con suavizado.
     */
    public static ImageIcon cargarImagenEscalada(String rutaImagen, int ancho, int alto){
        Image imagen = cargarImagen(rutaImagen);
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}