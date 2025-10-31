package Vistas.Pantallas;

import Vistas.Configuraciones.ConfigurPantallas;

import javax.swing.*;
import java.awt.*;

public class PantallaCambioTurno extends ConfigurPantallas {
    private Image imagenFondo;

    public PantallaCambioTurno(JFrame padre, String rutaImagen, String mensaje) {
        super("Cambio de turno", rutaImagen);

        // --- Carga de imagen ---
        imagenFondo = new ImageIcon(rutaImagen).getImage();

        // --- Panel personalizado ---
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout());

        // --- Texto centrado ---
        JLabel label = new JLabel(mensaje, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("src/Recursos/Fuentes/ka1.ttf", Font.BOLD, 60));
        label.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.add(label, BorderLayout.CENTER);

        add(panel);

        // --- Timer de cierre automático (3 segundos) ---
        new Timer(3000, e -> dispose()).start();
    }
}
