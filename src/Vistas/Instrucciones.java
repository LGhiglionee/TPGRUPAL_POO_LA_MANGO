package Vistas;

import Modelo.GestorRecursos;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *  Ventana que muestra las reglas y explicaciones del juego "Truco a 2 Lucas"
 * Lee el contenido del archivo de texto {@code instrucciones.txt} ubicado en la carpeta de recursos.
 */

class Instrucciones extends JFrame {
    /**
     * Constructor que inicializa la ventana y carga el texto de instrucciones.
     */
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
        Font fuenteTexto = fuente.deriveFont(Font.BOLD, 10f);

        // --- Lamina
        PanelConFondo lamina = new PanelConFondo("src/Recursos/Imagenes/FondoMenu.png");
        lamina.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //--- Área de texto con scroll.
        JTextPane texto = new JTextPane();
        texto.setFont(fuenteTexto);
        texto.setEditable(false);
        texto.setOpaque(false);

        JScrollPane scroll = new JScrollPane(texto);
        add(scroll, BorderLayout.CENTER);

        // --- Carga del archivo de instrucciones
        String ruta = "src/Recursos/Instrucciones/instrucciones.txt";
        texto.setText(cargarTextoDesdeArchivo(ruta));

        setVisible(true);
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