package Vistas.Configuraciones;

import javax.swing.*;
import java.awt.*;

/**
 * — Clase base abstracta para todas las ventanas principales del juego "Truco a 2 Lucas"
 *
 * Centraliza la configuración visual y de comportamiento de cada pantalla:
 * tamaño completo, sin bordes, centrado automático, ícono personalizado y control
 * de visibilidad. Su objetivo es unificar la apariencia y facilitar la herencia
 * en las clases de tipo pantalla (Inicio, Opciones, Juego, Instrucciones, etc.)
 *
 * Responsabilidades principales:
 *     Establecer modo pantalla completa y comportamiento sin bordes.
 *     Definir ícono e información general de la ventana.
 *     Ofrecer método utilitario para cambiar dinámicamente el contenido del frame.
 *
 *
 * Las clases hijas deben invocar el constructor e implementar sus propios paneles internos.
 */


public abstract class ConfigurPantallas extends JFrame {
    /**
     * Constructor base que configura la apariencia y comportamiento estándar de todas las ventanas.
     *
     * Parámetro: titulo     Título de la ventana mostrado en la barra superior (si está habilitada).
     * Parámetro: rutaIcono  Ruta del ícono del juego (por ejemplo: "src/Recursos/Imagenes/IconoJuego.png").
     */
    public ConfigurPantallas(String titulo, String rutaIcono) {
        // --- Configuración general de todas las ventanas.
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // --- Carga del ícono.
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
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    /**
     * Reemplaza el contenido actual del frame por un nuevo panel sin crear una nueva ventana.
     * Permite transiciones fluidas entre pantallas (por ejemplo, del menú principal al juego).</p>
     *
     * Parámetro: nuevoPanel Panel que reemplazará al contenido actual.
     */    protected void cambiarContenido(JPanel nuevoPanel) {
        getContentPane().removeAll();
        add(nuevoPanel);
        revalidate();
        repaint();
    }
}
