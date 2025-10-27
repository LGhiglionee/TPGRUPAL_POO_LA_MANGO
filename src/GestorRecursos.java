import Excepciones.FuenteNOEncontradaException;
import Excepciones.ImagenNoEncontradaException;

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
        try {
            Font fuentePersonalizada = Font.createFont(Font.TRUETYPE_FONT, new File(rutaFuente));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuentePersonalizada);
            return fuentePersonalizada;
        } catch (IOException | FontFormatException e) {
            throw new FuenteNOEncontradaException("Error al cargar fuente"+rutaFuente);
        }
    }

    //Funcion para cargar imagenes
    public static Image cargarImagen(String rutaImagen) {
        try {
            File miimagen = new File(rutaImagen);
            return ImageIO.read(miimagen);
        } catch (IOException e) {
            throw new ImagenNoEncontradaException("Error al cargar imagen"+rutaImagen);
        }
    }

    public static ImageIcon cargarImagenEscalada(String rutaImagen, int ancho, int alto){
        try {
            Image imagenOriginal = ImageIO.read(new File(rutaImagen));
            if (imagenOriginal == null) {
                throw new  ImagenNoEncontradaException("Error al cargar imagen"+rutaImagen);
            }
            Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);

        } catch (IOException e) {
            throw new ImagenNoEncontradaException("Error al cargar imagen"+rutaImagen);
        }
    }
}
