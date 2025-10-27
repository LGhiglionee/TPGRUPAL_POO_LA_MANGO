import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GestorRecursos {

    //Funcion para cargar fuentes
    public static Font cargarFuente(String rutaFuente) {
        Font fuentePersonalizada = null;
        try {
            fuentePersonalizada = Font.createFont(Font.TRUETYPE_FONT, new File(rutaFuente));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuentePersonalizada);
        } catch (IOException | FontFormatException e) {
            System.err.println("Error al cargar la fuente: " + rutaFuente);
            e.printStackTrace();
            fuentePersonalizada = new Font("Arial", Font.PLAIN, 14);
        }
        return fuentePersonalizada;
    }

    //Funcion para cargar imagenes
    public static Image cargarImagen(String rutaImagen) {
        Image imagen = null;
        try {
            File miimagen = new File(rutaImagen);
            imagen = ImageIO.read(miimagen);
        } catch (IOException e) {
            System.err.println("Error al cargar imagen: " + rutaImagen);
            e.printStackTrace();
        }
        return imagen;
    }

    public static ImageIcon cargarImagenEscalada(String rutaImagen, int ancho, int alto) {
        try {
            Image imagenOriginal = ImageIO.read(new File(rutaImagen));
            Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);

        } catch (IOException | NullPointerException e) {
            System.err.println("Error al cargar y escalar imagen: " + rutaImagen);
            e.printStackTrace();
            return null;
        }
    }
}
