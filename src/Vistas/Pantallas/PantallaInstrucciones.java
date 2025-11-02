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
 * — Pantalla de instrucciones del juego "Truco a 2 Lucas".
 *
 * Muestra las reglas básicas y explicaciones del juego,
 * leyendo el contenido desde un archivo de texto externo ubicado en
 * src/Recursos/Instrucciones/instrucciones.txt.
 *
 * Características principales:
 *     Diseño en modo pantalla completa con fondo temático.
 *     Texto centrado y legible dentro de un "pergamino".
 *     Botón “Volver” para regresar al menú principal.
 */
public class PantallaInstrucciones extends ConfigurPantallas implements  ActionListener {
    // --- Botón que permite volver al menú principal.
    JButton botonVolver;

    /**
     * Constructor que inicializa la ventana y carga el texto de instrucciones.
     */
    public PantallaInstrucciones() {
        super("PantallaInstrucciones del juego", "src/Recursos/Imagenes/Fondos/FondoInstrucciones.png");

        // === Configuración de pantalla ===
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        // === Fuente personalizada ===
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

        // === Panel principal ===
        ConfigurPanelConFondo lamina = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
        lamina.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // === Subpanel: “pergamino” para las reglas ===
        ConfigurPanelConFondo laminaPergamino = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoInstrucciones.png");
        laminaPergamino.setLayout(new BorderLayout());
        laminaPergamino.setOpaque(false);
        laminaPergamino.setBorder(BorderFactory.createEmptyBorder(100, 350, 100, 350));

        // === Área de texto ===
        JTextPane texto = new JTextPane();
        texto.setFont(fuenteTexto);
        texto.setEditable(false);
        texto.setOpaque(false);
        texto.setText(cargarTextoDesdeArchivo("src/Recursos/Instrucciones/instrucciones.txt"));

        // === Centrado del texto ===
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        texto.setParagraphAttributes(attribs, false);

        // === Scroll sin barras visibles ===
        JScrollPane scroll = new JScrollPane(texto);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        laminaPergamino.add(scroll, BorderLayout.CENTER);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // === Botón “Volver” ===
        botonVolver = new JButton("Volver");
        botonVolver.addActionListener( this);
        ImageIcon imagenBoton = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoBoton.png", anchura / 7, altura / 12);
        botonVolver.setHorizontalTextPosition(SwingConstants.CENTER);
        botonVolver.setVerticalTextPosition(SwingConstants.CENTER);
        botonVolver.setIcon(imagenBoton);
        botonVolver.setBorderPainted(false);
        botonVolver.setContentAreaFilled(false);
        botonVolver.setFont(fuenteTexto);

        // === Layout general con GridBag ===
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

        // === Ensamblado final ===
        add(lamina);
    }



    // === Métodos ===
    /**
     * Acción del botón "Volver" → regresa al menú principal.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonVolver) {
            new PantallaInicio();
            dispose();
        }
    }

    /**
     * Lee el contenido de un archivo de texto línea por línea.
     *
     * Parámetro: rutaArchivo Ruta del archivo de texto a leer.
     * Retorna: Texto completo del archivo, o mensaje de error si no se encuentra.
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


    /**
     * Método opcional: crea un panel contenedor reutilizable
     * si se desea insertar esta pantalla dentro de otra vista.
     */
    public JPanel crearContenido() {
        ConfigurPanelConFondo panel = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
        return panel;
    }
}