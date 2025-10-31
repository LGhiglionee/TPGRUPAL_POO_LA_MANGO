package Vistas.Pantallas;

import Modelo.Recursos.GestorRecursos;
import Vistas.Configuraciones.ConfigurPanelConFondo;
import Vistas.Configuraciones.ConfigurPantallas;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *  Ventana que muestra las reglas y explicaciones del juego "Truco a 2 Lucas"
 * Lee el contenido del archivo de texto {@code instrucciones.txt} ubicado en la carpeta de recursos.
 */

public class PantallaInstrucciones extends ConfigurPantallas implements  ActionListener {
    /**
     * Constructor que inicializa la ventana y carga el texto de instrucciones.
     */
    JButton botonVolver;
    public PantallaInstrucciones() {
        super("PantallaInstrucciones del juego", "src/Recursos/Imagenes/Fondos/FondoInstrucciones.png");

        // --- Configuración general de la pantalla.
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        // --- Configuración de tamaño completo de la ventana.
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Instrucciones del Juego");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- Carga de fuente personalizada.
        Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
        Font fuenteTexto = fuente.deriveFont(Font.BOLD, 13f);

        // --- Lamina Principal
        ConfigurPanelConFondo lamina = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
        lamina.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- Lamina Pergamino
        ConfigurPanelConFondo laminaPergamino = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoInstrucciones.png");
        laminaPergamino.setLayout(new BorderLayout());
        laminaPergamino.setOpaque(false);
        laminaPergamino.setBorder(BorderFactory.createEmptyBorder(200, 350, 50, 350));

        //--- Área de texto con scroll.
        JTextPane texto = new JTextPane();
        texto.setFont(fuenteTexto);
        texto.setEditable(false);
        texto.setOpaque(false);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        texto.setParagraphAttributes(attribs, false);
        texto.setText(cargarTextoDesdeArchivo("src/Recursos/Instrucciones/instrucciones.txt"));

        JScrollPane scroll = new JScrollPane(texto);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        laminaPergamino.add(scroll, BorderLayout.CENTER);

        // --- Boton volver
        botonVolver = new JButton("Volver");
        botonVolver.addActionListener( this);
        ImageIcon imagenBoton = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoBoton.png", anchura / 7, altura / 12);
        botonVolver.setHorizontalTextPosition(SwingConstants.CENTER);
        botonVolver.setVerticalTextPosition(SwingConstants.CENTER);
        botonVolver.setIcon(imagenBoton);
        botonVolver.setBorderPainted(false);
        botonVolver.setContentAreaFilled(false);
        botonVolver.setFont(fuenteTexto);


        // --- Logica gbc - Lamina
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.9;
        gbc.insets = new Insets(50, 200, 0, 200);
        gbc.fill = GridBagConstraints.BOTH;
        lamina.add(laminaPergamino,gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 250, 10, 250);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        lamina.add(botonVolver,gbc);

        add(lamina);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonVolver) {
            new PantallaInicio();
            dispose();
        }
    }
    /**
     * Lee el contenido de un archivo de texto y lo devuelve como cadena.
     */
    private String cargarTextoDesdeArchivo(String rutaArchivo) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException ex) {
            return "No se pudo cargar el archivo de instrucciones.";
        }
        return contenido.toString();
    }
    public JPanel crearContenido() {
        ConfigurPanelConFondo panel = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
        // ... agregar componentes ...
        return panel;
    }
}