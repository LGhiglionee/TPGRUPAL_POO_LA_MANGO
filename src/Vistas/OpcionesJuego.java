package Vistas;

import Modelo.GestorRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * — Pantalla intermedia del juego "Truco a 2 Lucas".
 *
 * Permite al jugador seleccionar entre los modos disponibles:
 *   <li>Jugador vs Jugador (PvP)</li>
 *   <li>Jugador vs Computadora (PvC, aún sin implementar)</li>
 * También ofrece la opción de volver al menú principal.</p>
 */

class OpcionesJuego extends JFrame implements ActionListener {
    JButton botonOpcionPvP, botonOpcionPvC, botonVolver;
    JLabel titulo;

    /**
     * Constructor que inicializa la ventana de selección de modo de juego.
     */
    public OpcionesJuego() {
        setTitle("Truco a 2 Lucas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // --- Configuración de pantalla.
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        // --- Fuentes personalizadas.
        Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
        Font fuenteTitulo = fuente.deriveFont(Font.BOLD, 45f);
        Font fuenteBoton = fuente.deriveFont(Font.BOLD, 20f);

        // --- Icono de la ventana.
        Image fondo = mipantalla.getImage("src/Recursos/Imagenes/FondoMenu.png");
        setIconImage(fondo);

        // --- Titulo principal.
        titulo = new JLabel("Truco a 2 Lucas");
        titulo.setFont(fuenteTitulo);
        titulo.setHorizontalTextPosition(SwingConstants.CENTER);
        titulo.setVerticalTextPosition(SwingConstants.CENTER);
        titulo.setOpaque(false);
        titulo.setForeground(Color.black);
        titulo.setIcon(GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/FondoTitulo.png", anchura / 2, altura / 4));

        // --- Botones
        ImageIcon imagenBoton = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/FondoBoton.png", anchura / 5, altura / 10);

        botonOpcionPvP = crearBoton("Jugar PvP", fuenteBoton, imagenBoton);
        botonOpcionPvC = crearBoton("Jugar PvC", fuenteBoton, imagenBoton);
        botonVolver = crearBoton("Volver", fuenteBoton, imagenBoton);

        //--- Panel de fondo
        PanelConFondo panelfondo = new PanelConFondo("src/Recursos/Imagenes/FondoMenu.png");
        panelfondo.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // --- Título
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.CENTER;
        panelfondo.add(titulo, gbc);

        // --- Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
        panelBotones.setOpaque(false);
        panelBotones.add(botonOpcionPvP);
        panelBotones.add(botonOpcionPvC);
        panelBotones.add(botonVolver);

        // --- Configuración botones
        gbc.gridy = 1;
        gbc.weighty = 0.6;
        panelfondo.add(panelBotones, gbc);

        // --- Agrega el fondo del panel.
        add(panelfondo);
        setVisible(true);
    }

    /**
     * Crea un botón estilizado con fuente, imagen y acción asignada.
     */
    private JButton crearBoton(String texto, Font fuente, ImageIcon imagen) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setIcon(imagen);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.CENTER);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.addActionListener(this);
        return boton;
    }

    /**
     * Maneja las acciones de los botones de la pantalla.
     */

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonOpcionPvP) {
            new Partida();
            dispose();
        } else if (e.getSource() == botonOpcionPvC) {
            new PartidaBot();
            dispose();
        } else if (e.getSource() == botonVolver) {
            new Inicio();
            dispose();
        }
    }
}
