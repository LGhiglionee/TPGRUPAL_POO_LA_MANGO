package Vistas.Pantallas;

import Excepciones.Recursos.FuenteNoEncontradaException;
import Excepciones.Recursos.ImagenNoEncontradaException;
import Modelo.Recursos.GestorRecursos;
import Vistas.Configuraciones.ConfigurPanelConFondo;
import Vistas.Configuraciones.ConfigurPantallas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * — Pantalla principal del juego “Truco a 2 Lucas”.
 *
 * Actúa como menú inicial del sistema, presentando el título del juego y
 * tres opciones principales: Jugar, Instrucciones y Salir
 *
 * Responsabilidades principales:
 *     Cargar fuentes e imágenes personalizadas mediante GestorRecursos.
 *     Mostrar un diseño centrado con fondo gráfico e íconos estilizados.
 *     Redirigir a las pantallas correspondientes según la acción del usuario.
 */
public class PantallaInicio extends ConfigurPantallas implements ActionListener {
    // === Componentes principales ===
    JButton botonjugar, botonsalir, botoninstrucciones;
    JLabel titulo;


    /**
     * Constructor que inicializa la ventana de inicio, carga los recursos gráficos y configura
     * los botones de acción (Jugar, PantallaInstrucciones y Salir).
     */
    public PantallaInicio() {
        super("Truco a 2 lucas", "src/Recursos/Imagenes/Fondos/FondoMenu.png");

        // === Configuración general de dimensiones ===
        Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        try {
            // === Fuentes ===
            Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
            Font fuenteTitulo = fuente.deriveFont(Font.BOLD, 70f);
            Font fuenteBoton = fuente.deriveFont(Font.BOLD, 40f);

            // === Imágenes ===
            ImageIcon imagenTitulo = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoTitulo.png", anchura / 2, altura / 4);
            ImageIcon imagenBotonnes = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoBoton.png", anchura / 5, altura / 10);

            // === Título principal ===
            titulo = new JLabel("Truco a 2 lucas");
            titulo.setFont(fuenteTitulo);
            titulo.setHorizontalTextPosition(SwingConstants.CENTER);
            titulo.setVerticalTextPosition(SwingConstants.CENTER);
            titulo.setIcon(imagenTitulo);

            // === Botones principales ===
            botonjugar = crearBoton("Jugar", fuenteBoton, imagenBotonnes);
            botoninstrucciones = crearBoton("Instrucciones", fuenteBoton, imagenBotonnes);
            botonsalir = crearBoton("Salir", fuenteBoton, imagenBotonnes);

            configurarBotonConImagen(botonjugar, imagenBotonnes);
            configurarBotonConImagen(botoninstrucciones, imagenBotonnes);
            configurarBotonConImagen(botonsalir, imagenBotonnes);


            // === Panel principal ===
            ConfigurPanelConFondo lamina = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoMenu.png");
            lamina.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 20, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;

            // --- Título superior ---
            gbc.weighty = 0.4;
            gbc.anchor = GridBagConstraints.CENTER;
            lamina.add(titulo, gbc);

            // --- Panel de botones ---
            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
            panelBotones.setOpaque(false);
            panelBotones.add(botonjugar);
            panelBotones.add(botoninstrucciones);
            panelBotones.add(botonsalir);

            gbc.weighty = 0.6;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            lamina.add(panelBotones, gbc);

            // === Ensamblado final ===
            add(lamina);

        } catch (ImagenNoEncontradaException | FuenteNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de recursos", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Finaliza y sale del programa.
        }
    }


    // === Métodos ===
    /**
     * Crea y configura un botón con una fuente e imagen personalizada.
     */
    private JButton crearBoton(String texto, Font fuente, ImageIcon imagen) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setIcon(imagen);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.CENTER);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.addActionListener(this);
        return boton;
    }
    /**
     * Metodo auxiliar para configurar un botón con una imagen de fondo.
     */
    private void configurarBotonConImagen(JButton boton, ImageIcon imagen) {
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.CENTER);
        boton.setIcon(imagen);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
    }


    /**
     * Maneja las acciones de los botones del menú principal.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonsalir) {
            Object[] opciones = {"Sí", "No"};
            int seleccion = JOptionPane.showOptionDialog(
                    this,
                    "¿Estás seguro que deseas salir?",
                    "Aviso",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion == 0) { // 0 corresponde a la opción "Sí"
                System.exit(0);
            }
        }

        else if (e.getSource() == botonjugar) {
            PantallaOpcionesJuego opciones = new PantallaOpcionesJuego();
            cambiarContenido(opciones.crearContenido());  //
        }

        else if (e.getSource() == botoninstrucciones) {
            PantallaInstrucciones instrucciones = new PantallaInstrucciones();
            cambiarContenido(instrucciones.crearContenido());
        }
    }
}