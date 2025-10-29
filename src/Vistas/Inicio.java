package Vistas;

import Excepciones.Recursos.FuenteNoEncontradaException;
import Excepciones.Recursos.ImagenNoEncontradaException;
import Modelo.GestorRecursos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    /*
     *  Esta clase representa la pantalla principal del juego (“menú inicial”), con:
            Un título grande “Truco a 2 Lucas”.
            Tres botones: Jugar, Instrucciones y Salir.
            Un fondo e iconos cargados mediante GestorRecursos.
        Acción de los botones:
            Jugar → abre la clase OpcionesJuego.
            Instrucciones → abre la clase Instrucciones.
            Salir → cierra el programa.
    */

public class Inicio extends JFrame implements ActionListener {
    // --- Componentes principales de la ventana.
    JButton botonjugar, botonsalir, botoninstrucciones;
    JLabel titulo;

    /**
     * Constructor que inicializa la ventana de inicio, carga los recursos gráficos y configura
     * los botones de acción (Jugar, Instrucciones y Salir).
     */
    public Inicio() {
        setTitle("Truco a 2 Lucas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Dimensiones de la pantalla.
        Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        try {
            // --- Carga de fuente personalizada.
            Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
            Font fuenteTitulo = fuente.deriveFont(Font.BOLD, 45f);
            Font funeteBoton = fuente.deriveFont(Font.BOLD, 20f);

            // --- ICONO del programa.
            Image fondo = mipantalla.getImage("src/Recursos/Imagenes/FondoMenu.png");
            setIconImage(fondo);

            // --- Título principal.
            titulo = new JLabel("Truco a 2 lucas");
            titulo.setFont(fuenteTitulo);
            titulo.setForeground(Color.black);

            // --- Botones principales.
            botonjugar = new JButton("Jugar");
            botonjugar.setFont(funeteBoton);
            botonjugar.addActionListener(this);

            botoninstrucciones = new JButton("Instrucciones");
            botoninstrucciones.setFont(funeteBoton);
            botoninstrucciones.addActionListener(this);

            botonsalir = new JButton("Salir");
            botonsalir.setFont(funeteBoton);
            botonsalir.addActionListener(this);


            // --- Configuración de tamaño completo de la ventana.
            setExtendedState(JFrame.MAXIMIZED_BOTH);

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
            int opcion = JOptionPane.showConfirmDialog (this, "¿Seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            };
        }
        else if (e.getSource() == botonjugar) {
            new OpcionesJuego();
            dispose();
        }
        else if (e.getSource() == botoninstrucciones) {
            new Instrucciones();
        }
    }
}