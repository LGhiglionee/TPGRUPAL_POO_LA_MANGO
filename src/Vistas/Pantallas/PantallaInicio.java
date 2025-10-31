package Vistas.Pantallas;

import Excepciones.Recursos.FuenteNoEncontradaException;
import Excepciones.Recursos.ImagenNoEncontradaException;
import Modelo.GestorRecursos;
import Vistas.PanelConFondo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    /*
     *  Esta clase representa la pantalla principal del juego (“menú inicial”), con:
            Un título grande “Truco a 2 Lucas”.
            Tres botones: Jugar, PantallaInstrucciones y Salir.
            Un fondo e iconos cargados mediante GestorRecursos.
        Acción de los botones:
            Jugar → abre la clase PantallaOpcionesJuego.
            PantallaInstrucciones → abre la clase PantallaInstrucciones.
            Salir → cierra el programa.
    */

public class PantallaInicio extends ConfigurPantallas implements ActionListener {
    // --- Componentes principales de la ventana.
    JButton botonjugar, botonsalir, botoninstrucciones;
    JLabel titulo;

    /**
     * Constructor que inicializa la ventana de inicio, carga los recursos gráficos y configura
     * los botones de acción (Jugar, PantallaInstrucciones y Salir).
     */
    public PantallaInicio() {
        super("Truco a 2 lucas", "src/Recursos/Imagenes/FondoMenu.png");

        // --- Dimensiones de la pantalla.
        Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        try {
            // --- Carga de fuente personalizada.
            Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
            Font fuenteTitulo = fuente.deriveFont(Font.BOLD, 45f);
            Font fuenteBoton = fuente.deriveFont(Font.BOLD, 20f);

            // --- Título principal.
            titulo = new JLabel("Truco a 2 lucas");
            titulo.setFont(fuenteTitulo);
            titulo.setForeground(Color.black);

            // --- Botones principales.
            botonjugar = new JButton("Jugar");
            botonjugar.setFont(fuenteBoton);
            botonjugar.addActionListener(this);

            botoninstrucciones = new JButton("Instrucciones");
            botoninstrucciones.setFont(fuenteBoton);
            botoninstrucciones.addActionListener(this);

            botonsalir = new JButton("Salir");
            botonsalir.setFont(fuenteBoton);
            botonsalir.addActionListener(this);

            // --- Imagen titulo
            ImageIcon imagenTitulo = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/FondoTitulo.png", anchura / 2, altura / 4);
            titulo.setHorizontalTextPosition(SwingConstants.CENTER);
            titulo.setVerticalTextPosition(SwingConstants.CENTER);
            titulo.setIcon(imagenTitulo);

            // --- Imagenes botones
            ImageIcon imagenBotonnes = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/FondoBoton.png", anchura / 5, altura / 10);
            configurarBotonConImagen(botonjugar, imagenBotonnes);
            configurarBotonConImagen(botoninstrucciones, imagenBotonnes);
            configurarBotonConImagen(botonsalir, imagenBotonnes);

            //--- Lámina
            PanelConFondo lamina = new PanelConFondo("src/Recursos/Imagenes/FondoMenu.png");
            lamina.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 20, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;

            // --- Titulo de lámina.
            gbc.weighty = 0.4;
            gbc.anchor = GridBagConstraints.CENTER;
            lamina.add(titulo, gbc);

            // --- Panel de botones
            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
            panelBotones.setOpaque(false);
            panelBotones.add(botonjugar);
            panelBotones.add(botoninstrucciones);
            panelBotones.add(botonsalir);

            gbc.weighty = 0.6;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            lamina.add(panelBotones, gbc);

            // --- Finaliza configuración.
            add(lamina);
            setVisible(true);

        } catch (ImagenNoEncontradaException | FuenteNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de recursos", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Finaliza y sale del programa.
        }
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
            new PantallaOpcionesJuego();
            dispose();
        }
        else if (e.getSource() == botoninstrucciones) {
            new PantallaInstrucciones();
            dispose();
        }
    }
}