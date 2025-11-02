package Vistas.Pantallas;

import Modelo.Recursos.GestorRecursos;
import Vistas.Configuraciones.ConfigurPantallas;

import javax.swing.*;
import java.awt.*;

/**
 * — Pantalla intermedia que se muestra brevemente al cambiar el turno entre jugadores.
 *
 * Su función principal es pausar la partida durante unos segundos, mostrando un mensaje
 * central con fondo gráfico y una tipografía personalizada, antes de continuar con el flujo del juego.
 *
 * Características principales:
 *     Hereda la configuración general de ConfigurPantallas (pantalla completa, sin bordes, centrada).
 *     Muestra una imagen de fondo escalada dinámicamente.
 *     Presenta un texto grande y centrado con fuente personalizada.
 *     Se cierra automáticamente tras un breve intervalo (3 segundos).
 */
public class PantallaCambioTurno extends ConfigurPantallas {
    // --- Imagen de fondo de la pantalla.
    private Image imagenFondo;

    /**
     * Constructor que inicializa la pantalla de cambio de turno.
     *
     * Parámetro: padre      Referencia a la ventana que invoca esta pantalla (no se usa directamente, pero mantiene coherencia jerárquica).
     * Parámetro: rutaImagen Ruta del archivo de imagen a utilizar como fondo.
     * Parámetro: mensaje    Texto que se mostrará centrado en pantalla (por ejemplo: “Turno del Jugador 1”).
     */
    public PantallaCambioTurno(JFrame padre, String rutaImagen, String mensaje) {
        super("Cambio de turno", rutaImagen);

        // === Carga de recursos ===
        imagenFondo = new ImageIcon(rutaImagen).getImage();
        Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
        Font fuenteTexto = fuente.deriveFont(Font.BOLD, 70f);

        // === Panel de fondo personalizado ===
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout());

        // === Texto centrado ===
        JLabel label = new JLabel(mensaje, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(fuenteTexto);
        label.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.add(label, BorderLayout.CENTER);

        // --- Ensamblado final.
        add(panel);

        // === Timer de cierre automático (3 segundos) ===
        new Timer(3000, e -> dispose()).start();
    }
}