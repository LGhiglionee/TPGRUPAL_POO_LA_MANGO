package Modelo;

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

public class GestorRecursos {

    //Funcion para cargar fuentes
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

    //Funcion para cargar imagenes; retorna Image.
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

    public static ImageIcon cargarImagenEscalada(String rutaImagen, int ancho, int alto){
        Image imagen = cargarImagen(rutaImagen);
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}
