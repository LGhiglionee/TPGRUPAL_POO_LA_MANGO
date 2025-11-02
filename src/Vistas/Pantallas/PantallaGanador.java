package Vistas.Pantallas;

import Modelo.Recursos.GestorRecursos;
import Vistas.Configuraciones.ConfigurPanelConFondo;
import Vistas.Configuraciones.ConfigurPantallas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaGanador extends ConfigurPantallas implements ActionListener {

    // --- Botones principales de la pantalla.
    private JButton btnMenu;
    private JButton btnSalir;


    /**
     * Constructor que crea la ventana de resultado con un mensaje central.
     */
    public PantallaGanador(String mensaje) {
        super("Resultado del Juego", "src/Recursos/Imagenes/Fondos/FondoIntermedio.png");

        // --- Imagen
        Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;
        ImageIcon imagenBotonnes = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoBoton.png", anchura / 7, altura / 12);

        // --- Panel principal
        ConfigurPanelConFondo panelPrincipal = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoIntermedio.png");
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- Fuente personalizada.
        Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
        Font fuenteTitulo = fuente.deriveFont(Font.BOLD, 70f);
        Font fuenteBoton = fuente.deriveFont(Font.BOLD, 30f);


        // --- Mensaje principal
        JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
        lbl.setFont(fuenteTitulo);
        lbl.setOpaque(false);
        lbl.setForeground(Color.WHITE);

        // --- Botones inferiores
        btnMenu = new JButton("Volver al menu");
        configurarBotonConImagen(btnMenu, imagenBotonnes);
        btnMenu.setFont(fuenteBoton);
        btnMenu.addActionListener(this);


        btnSalir = new JButton("Salir");
        configurarBotonConImagen(btnSalir, imagenBotonnes);
        btnSalir.setFont(fuenteBoton);
        btnSalir.addActionListener(this);

        // --- Panel de botones con separación uniforme.
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBtns.add(btnMenu);
        panelBtns.add(btnSalir);
        panelBtns.setOpaque(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.9;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lbl, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        panelPrincipal.add(panelBtns, gbc);

        add(panelPrincipal);
        setVisible(true);
    }

    private void configurarBotonConImagen(JButton boton, ImageIcon imagen) {
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.CENTER);
        boton.setIcon(imagen);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
    }
    /**
     * Maneja las acciones de los botones "Volver al menú" y "Salir".
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMenu) {
            new PantallaInicio();
            dispose();
        }
        if (e.getSource() == btnSalir) {
            System.exit(0);
        }
    }
}

