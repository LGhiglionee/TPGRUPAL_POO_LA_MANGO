package Vistas;

import Modelo.GestorRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PantallaGanador extends JFrame implements ActionListener {

    private Image imagen;
    private JButton btnMenu;
    private JButton btnSalir;

    public PantallaGanador(String mensaje) {
        setTitle("Resultado del Vistas.Juego");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 350);
        setLocationRelativeTo(null); // centrar

        Font fuente;
        try {
            fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf").deriveFont(Font.BOLD, 28f);
        } catch (Exception ex) {
            fuente = new Font("Arial", Font.BOLD, 28);
        }

        // Mensaje principal
        JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
        lbl.setFont(fuente);
        lbl.setForeground(Color.BLACK);
        lbl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(lbl, BorderLayout.CENTER);

        // Botones inferiores
        btnMenu = new JButton("Volver al menú");
        btnMenu.addActionListener(this);
        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(this);

        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBtns.add(btnMenu);
        panelBtns.add(btnSalir);
        add(panelBtns, BorderLayout.SOUTH);

        setVisible(true);
    }

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
