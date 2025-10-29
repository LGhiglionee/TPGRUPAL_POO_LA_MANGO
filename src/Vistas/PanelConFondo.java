package Vistas;

import Modelo.GestorRecursos;
import javax.swing.*;
import java.awt.*;

/**
 *— Panel genérico que muestra una imagen de fondo.
 *
 * El fondo se ajusta automáticamente al tamaño actual del panel, evitando
 * la distorsión o los bordes en blanco.</p>
 *
 * Ejemplo de uso:</p>
 *     PanelConFondo menu = new PanelConFondo("src/Recursos/Imagenes/FondoMenu.png");
 *     PanelConFondo juego = new PanelConFondo("src/Recursos/Imagenes/FondoJuego.png");
 */

public class PanelConFondo extends JPanel {
    // --- Imagen de fondo del panel.
    private Image imagen;

    /**
     * Constructor que carga la imagen de fondo desde los recursos indicados.
     */
    public PanelConFondo(String rutaImagen) {
        imagen = GestorRecursos.cargarImagen(rutaImagen);
    }

    /**
     * Dibuja la imagen de fondo escalada al tamaño actual del panel.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
