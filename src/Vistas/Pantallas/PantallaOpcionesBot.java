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
 * — Pantalla de selección de dificultad del modo "Jugador vs Computadora".
 *
 * Actúa como menú intermedio en el flujo del juego “Truco a 2 Lucas”.
 * Permite elegir entre los niveles de dificultad disponibles o volver al menú principal.
 *
 * Modos disponibles:
 *    Fácil → crea una partida PartidaBot con dificultad 0.
 *    Medio → crea una partida PartidaBot con dificultad 1.
 *    Volver → regresa a PantallaInicio.
 */

public class PantallaOpcionesBot extends ConfigurPantallas implements ActionListener {
    // === Componentes principales ===
    JButton botonOpcionPvP, botonOpcionPvC, botonVolver;
    JLabel titulo;



    // === Constructores ===
    /**
     * Constructor que inicializa la ventana de selección de modo de juego.
     */
    public PantallaOpcionesBot() {
        super ("Truco a 2 Lucas", "src/Recursos/Imagenes/Fondos/FondoMenu.png");

        // === Configuración de dimensiones ===
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        // === Fuentes personalizadas ===
        Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
        Font fuenteTitulo = fuente.deriveFont(Font.BOLD, 70);
        Font fuenteBoton = fuente.deriveFont(Font.BOLD, 40f);

        // === Título principal ===
        titulo = new JLabel("Truco a 2 Lucas");
        titulo.setFont(fuenteTitulo);
        titulo.setHorizontalTextPosition(SwingConstants.CENTER);
        titulo.setVerticalTextPosition(SwingConstants.CENTER);
        titulo.setOpaque(false);
        titulo.setIcon(GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoTitulo.png", anchura / 2, altura / 4));

        // === Imagen base para los botones ===
        ImageIcon imagenBoton = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoBoton.png", anchura / 5, altura / 10);

        // === Creación de botones ===
        botonOpcionPvP = crearBoton("Facil", fuenteBoton, imagenBoton);
        botonOpcionPvC = crearBoton("Medio", fuenteBoton, imagenBoton);
        botonVolver = crearBoton("Volver", fuenteBoton, imagenBoton);

        // === Panel principal con fondo ===
        ConfigurPanelConFondo panelfondo = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
        panelfondo.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // --- Título superior ---
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.CENTER;
        panelfondo.add(titulo, gbc);

        // --- Panel de botones ---
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
        panelBotones.setOpaque(false);
        panelBotones.add(botonOpcionPvP);
        panelBotones.add(botonOpcionPvC);
        panelBotones.add(botonVolver);

        // --- Configuración botones
        gbc.gridy = 1;
        gbc.weighty = 0.6;
        panelfondo.add(panelBotones, gbc);

        // === Ensamblado final ===
        add(panelfondo);
    }



    // === Métodos ===
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
            new PartidaBot(0);
            dispose();
        } else if (e.getSource() == botonOpcionPvC) {
            new PartidaBot(1);
            dispose();
        } else if (e.getSource() == botonVolver) {
            new PantallaInicio();
            dispose();
        }
    }
}