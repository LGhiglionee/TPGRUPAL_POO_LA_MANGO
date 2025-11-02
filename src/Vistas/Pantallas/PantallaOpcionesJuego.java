package Vistas.Pantallas;

import Modelo.Recursos.GestorRecursos;
import Vistas.Configuraciones.ConfigurPanelConFondo;
import Vistas.Configuraciones.ConfigurPantallas;
import Vistas.Partidas.PartidaBot;
import Vistas.Partidas.PartidaHumanos;

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

public class PantallaOpcionesJuego extends ConfigurPantallas implements ActionListener {
    JButton botonOpcionPvP, botonOpcionPvC, botonVolver;
    JLabel titulo;

    private JPanel panelPrincipal;

    /**
     * Constructor que inicializa la ventana de selección de modo de juego.
     */
    public PantallaOpcionesJuego() {
        super ("Truco a 2 Lucas", "src/Recursos/Imagenes/Fondos/FondoMenu.png");

        // --- Configuración de pantalla.
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        // --- Fuentes personalizadas.
        Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
        Font fuenteTitulo = fuente.deriveFont(Font.BOLD, 70);
        Font fuenteBoton = fuente.deriveFont(Font.BOLD, 40f);

        // --- Titulo principal.
        titulo = new JLabel("Truco a 2 Lucas");
        titulo.setFont(fuenteTitulo);
        titulo.setHorizontalTextPosition(SwingConstants.CENTER);
        titulo.setVerticalTextPosition(SwingConstants.CENTER);
        titulo.setOpaque(false);
        titulo.setIcon(GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoTitulo.png", anchura / 2, altura / 4));

        // --- Botones
        ImageIcon imagenBoton = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoBoton.png", anchura / 5, altura / 10);

        botonOpcionPvP = crearBoton("Jugar PvP", fuenteBoton, imagenBoton);
        botonOpcionPvC = crearBoton("Jugar PvC", fuenteBoton, imagenBoton);
        botonVolver = crearBoton("Volver", fuenteBoton, imagenBoton);

        //--- Panel de fondo
        ConfigurPanelConFondo panelfondo = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
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
            new PartidaHumanos();
            dispose();
        } else if (e.getSource() == botonOpcionPvC) {
            new PantallaOpcionesBot();
            dispose();
        } else if (e.getSource() == botonVolver) {
            new PantallaInicio();
            dispose();
        }
    }
    public JPanel crearContenido() {
        ConfigurPanelConFondo panel = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
        // ... agregar componentes ...
        return panel;
    }
}
