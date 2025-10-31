package Vistas;

import Modelo.GestorRecursos;

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

class Instrucciones extends JFrame implements  ActionListener {
    /**
     * Constructor que inicializa la ventana y carga el texto de instrucciones.
     */
    JButton botonVolver;
    public Instrucciones() {
        // --- Configuración general de la pantalla.
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;
        //setBounds(anchura / 4, altura / 4, anchura / 2, altura / 2);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Instrucciones del Juego");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- Carga de fuente personalizada.
        Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
        Font fuenteTexto = fuente.deriveFont(Font.BOLD, 13f);

        // --- Lamina
        PanelConFondo lamina = new PanelConFondo("src/Recursos/Imagenes/FondoInstrucciones.png");
        lamina.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //--- Área de texto con scroll.
        JTextPane texto = new JTextPane();
        texto.setFont(fuenteTexto);
        texto.setEditable(false);
        texto.setOpaque(false);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        texto.setParagraphAttributes(attribs, false);

        JScrollPane scroll = new JScrollPane(texto);
        add(scroll, BorderLayout.CENTER);

        // --- Boton volver
        botonVolver = new JButton("Volver");
        botonVolver.addActionListener( this);
        ImageIcon imagenBoton = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/FondoBoton.png", anchura / 7, altura / 12);
        botonVolver.setHorizontalTextPosition(SwingConstants.CENTER);
        botonVolver.setVerticalTextPosition(SwingConstants.CENTER);
        botonVolver.setIcon(imagenBoton);
        botonVolver.setBorderPainted(false);
        botonVolver.setContentAreaFilled(false);
        botonVolver.setFont(fuenteTexto);

        // --- Carga del archivo de instrucciones
        String ruta = "src/Recursos/Instrucciones/instrucciones.txt";
        texto.setText(cargarTextoDesdeArchivo(ruta));

        // --- Logica gbc - Lamina
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(100, 500, 10, 450);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        lamina.add(texto,gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 200, 10, 200);
        gbc.anchor = GridBagConstraints.SOUTH;
        lamina.add(botonVolver,gbc);

        add(lamina);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonVolver) {
            new Inicio();
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
}