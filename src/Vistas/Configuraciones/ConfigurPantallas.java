package Vistas.Configuraciones;

import javax.swing.*;
import java.awt.*;

/**
 * Clase base para todas las ventanas del juego "Truco a 2 Lucas".
 * Centraliza la configuración general: pantalla completa, sin bordes,
 * posición, ícono y tamaño.
 */


public abstract class ConfigurPantallas extends JFrame {
    public ConfigurPantallas(String titulo, String rutaIcono) {
        // --- Configuración general de todas las ventanas ---
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        if (rutaIcono != null && !rutaIcono.isEmpty()) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            try {
                Image icono = toolkit.getImage(rutaIcono);
                setIconImage(icono);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "No se pudo cargar el ícono del juego.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
        // 🧩 Se muestra solo después de construirse completamente
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    /** Reemplaza el contenido actual del frame sin crear una nueva ventana */
    protected void cambiarContenido(JPanel nuevoPanel) {
        getContentPane().removeAll();
        add(nuevoPanel);
        revalidate();
        repaint();
    }
}
