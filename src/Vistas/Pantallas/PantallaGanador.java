package Vistas.Pantallas;

import Modelo.GestorRecursos;
import Vistas.Inicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaGanador extends JFrame implements ActionListener {

    // --- Imagen.
    private Image imagen;

    // --- Botones principales de la pantalla.
    private JButton btnMenu;
    private JButton btnSalir;


    /**
     * Constructor que crea la ventana de resultado con un mensaje central.
     */
    public PantallaGanador(String mensaje) {
        // --- Configuración general de la ventana.
        setTitle("Resultado del Juego");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // --- Fuente personalizada.
        Font fuente;
        try {
            fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf").deriveFont(Font.BOLD, 28f);
        } catch (Exception ex) {
            fuente = new Font("Arial", Font.BOLD, 28);
        }

        // --- Mensaje principal
        JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
        lbl.setFont(fuente);
        lbl.setForeground(Color.BLACK);
        lbl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(lbl, BorderLayout.CENTER);

        // --- Botones inferiores
        btnMenu = new JButton("Volver al menú");
        btnMenu.addActionListener(this);


        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(this);

        // --- Panel de botones con separación uniforme.
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBtns.add(btnMenu);
        panelBtns.add(btnSalir);
        add(panelBtns, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Maneja las acciones de los botones "Volver al menú" y "Salir".
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMenu) {
            new Inicio();
            dispose();
        }
        if (e.getSource() == btnSalir) {
            System.exit(0);
        }
    }
}

