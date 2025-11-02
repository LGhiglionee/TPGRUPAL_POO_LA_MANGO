package Vistas.Configuraciones;

import Modelo.Recursos.GestorRecursos;
import javax.swing.*;
import java.awt.*;

/**
 *— Panel genérico que muestra una imagen de fondo.
 *
 * El fondo se ajusta automáticamente al tamaño actual del panel, evitando
 * la distorsión o los bordes en blanco.
 *
 * Ejemplo de uso:
 *     ConfigurPanelConFondo menu = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
 *     ConfigurPanelConFondo juego = new ConfigurPanelConFondo("src/Recursos/Imagenes/FondoJuego.png");
 */

public class ConfigurPanelConFondo extends JPanel {
    // --- Imagen de fondo del panel.
    private Image imagen;

    /**
     * Constructor que carga la imagen de fondo desde los recursos indicados.
     */
    public ConfigurPanelConFondo(String rutaImagen) {
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
