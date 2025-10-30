package Vistas;

import Excepciones.Juego.JugadorSinCartasException;
import Excepciones.Juego.MazoVacioException;
import Excepciones.Juego.PosicionInvalidaException;
import Excepciones.Recursos.ImagenNoEncontradaException;
import Modelo.GestorRecursos;
import Modelo.Jugador;
import Modelo.Mazo.Carta;
import Modelo.Turnos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Partida extends JFrame implements ActionListener {
    // --- Botones de cartas y acciones ---
    private JButton botoncarta1, botoncarta2, botoncarta3, envido, truco;

    // --- Información de jugadores ---
    private JProgressBar j1salud, j2salud;
    private JLabel j1mana, j2mana, j1nombre, j2nombre, jturno;

    // --- Paneles principales ---
    private JPanel j1Info, j2Info, manoCartas, infoTurno, infoEnvido, infoTruco;

    // --- Parámetros generales ---
    private int anchocarta, altocarta;

    // --- Lógica de juego ---
    private Turnos turno;
    private ArrayList<Carta> cartasjugadas;

    public Partida () {
        configurarVentana();
        inicializarJuego();
        inicializarComponentesGraficos();
        configurarPanelPrincipal();

    }
    private void configurarPanelPrincipal() {

        // ===== 1️⃣ Panel de fondo general =====
        PanelConFondo juego = new PanelConFondo("src/Recursos/Imagenes/FondoJuego.png");
        juego.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //Creacion de sub-paneles
        crearSubPaneles ();


        //Borde de los sub-paneles
        j1Info.setBorder(BorderFactory.createTitledBorder("Jugador 1"));
        j2Info.setBorder(BorderFactory.createTitledBorder("Jugador 2"));

        //Organizar contenido de sub-paneles
        configurarPanelJugadores ();
        infoTurno.add(jturno);

        // --- Panel inferior (ENVIDO + CARTAS + TRUCO)
        JPanel panelInferior = crearPanelInferior();

        // --- Ubicar paneles en el layout principal.
        ubicarPanelesEnLayout(juego, panelInferior, gbc);

        add(juego);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // Ubica todos los subpaneles en el GridBagLayout
    private void ubicarPanelesEnLayout(PanelConFondo juego, JPanel panelInferior, GridBagConstraints gbc){
        //Panel jugador 1 (Arriba - izquierda)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        juego.add(j1Info, gbc);

        //Panel turno (Arriba - Centro)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.NORTH;
        juego.add(infoTurno, gbc);

        //Panel jugador 2 (Arriba - derecha)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        juego.add(j2Info, gbc);

        //Panel inferior
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        juego.add(panelInferior, gbc);
    }

    // Crea el panel inferior con cartas + botones
    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new GridLayout(1, 3));
        panelInferior.setOpaque(false);

        manoCartas.setLayout(new GridLayout(1, 3)); // 1 fila, 3 columnas, 10px entre cartas
        manoCartas.add(botoncarta1);
        manoCartas.add(botoncarta2);
        manoCartas.add(botoncarta3);

        infoEnvido.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoEnvido.add(envido);

        infoTruco.setLayout(new FlowLayout(FlowLayout.RIGHT));
        infoTruco.add(truco);

        panelInferior.add(infoEnvido);
        panelInferior.add(manoCartas);
        panelInferior.add(infoTruco);

        return panelInferior;
    }

    private void configurarPanelJugadores() {
        j1Info.setLayout(new GridLayout(3, 1));
        j1Info.add(j1nombre);
        j1Info.add(j1salud);
        j1Info.add(j1mana);

        j2Info.setLayout(new GridLayout(3, 1));
        j2Info.add(j2nombre);
        j2Info.add(j2salud);
        j2Info.add(j2mana);
    }

    private void crearSubPaneles() {
        j1Info = new JPanel();
        j1Info.setOpaque(false);
        j2Info = new JPanel();
        j2Info.setOpaque(false);
        infoTurno = new JPanel();
        infoTurno.setOpaque(false);
        manoCartas = new JPanel();
        manoCartas.setOpaque(false);
        infoEnvido = new JPanel();
        infoEnvido.setOpaque(false);
        infoTruco = new JPanel();
        infoTruco.setOpaque(false);
    }

    private void configurarVentana() {
        setTitle("Truco a 2 Lucas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        //Cargar icono.
        try {
            Image fondo = mipantalla.getImage("src/Recursos/Imagenes/Fondojuego.png");
            setIconImage(fondo);
        } catch (ImagenNoEncontradaException e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el ícono del juego.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void inicializarJuego() {
        try {
            turno = new Turnos();
            turno.llenarMano(turno.getJugador1());
            turno.llenarMano(turno.getJugador2());
            cartasjugadas = new ArrayList<>();
        } catch (MazoVacioException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al iniciar la partida", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
    }
    private void inicializarComponentesGraficos () {
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        anchocarta = anchura / 12;
        altocarta = altura / 6;
        //Nombre jugador
        j1nombre = new JLabel();
        j2nombre = new JLabel();

        // --- BARRAS DE SALUD ---
        j1salud = crearBarraSalud(turno.getJugador1());
        j2salud = crearBarraSalud(turno.getJugador2());

        // --- Maná
        j1mana = crearEtiquetaMana("Mana Jugador 1: ", turno.getJugador1());
        j2mana = crearEtiquetaMana("Mana Jugador 2: ", turno.getJugador2());

        // --- Turno
        jturno = new JLabel("Turno de Jugador 1", SwingConstants.CENTER);
        jturno.setFont(new Font("Arial", Font.BOLD, 45));
        jturno.setForeground(Color.WHITE);

        //Obtener cartas
        Carta carta1 = turno.getJugadorMano().getTresCartas().get(0);
        Carta carta2 = turno.getJugadorMano().getTresCartas().get(1);
        Carta carta3 = turno.getJugadorMano().getTresCartas().get(2);

        botoncarta1 = crearBotonCarta(carta1);
        botoncarta2 = crearBotonCarta(carta2);
        botoncarta3 = crearBotonCarta(carta3);

        botoncarta1.addActionListener(this);
        botoncarta2.addActionListener(this);
        botoncarta3.addActionListener(this);

        // --- Botones de acción
        envido = crearBotonAccion("Envido");
        truco = crearBotonAccion("Truco");
    }

    private JProgressBar crearBarraSalud(Jugador jugador) {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(jugador.getSalud());
        barra.setString("Vida: " + jugador.getSalud());
        barra.setStringPainted(true);
        barra.setForeground(Color.RED);
        return barra;
    }

    private JLabel crearEtiquetaMana(String texto, Jugador jugador) {
        JLabel lbl = new JLabel(texto + jugador.getMana());
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private JButton crearBotonCarta(Carta carta) {
        JButton boton = new JButton();
        if (carta != null) {
            ImageIcon icon = new ImageIcon(carta.getImagen());
            Image img = icon.getImage().getScaledInstance(anchocarta, altocarta, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        }
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        return boton;
    }

    private JButton crearBotonAccion(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(100, 30));
        boton.addActionListener(this);
        return boton;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoncarta1) {
            try {
                turno.jugarCarta(0, cartasjugadas);
                String siguienteTurno = (turno.getJugadorMano() == turno.getJugador1()) ?
                        "Turno del Jugador 1" : "Turno del Jugador 2";

// 🔹 Mostramos la imagen de transición personalizada
                String ruta = "src/Recursos/Imagenes/transicion_turno.png"; // 🔸 tu imagen elegida
                PantallaCambioTurno pantalla = new PantallaCambioTurno(this, ruta, siguienteTurno);
                pantalla.setVisible(true);

            } catch (MazoVacioException | PosicionInvalidaException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == botoncarta2) {
            try {
                turno.jugarCarta(1, cartasjugadas);
                String siguienteTurno = (turno.getJugadorMano() == turno.getJugador1()) ?
                        "Turno del Jugador 1" : "Turno del Jugador 2";

// 🔹 Mostramos la imagen de transición personalizada
                String ruta = "src/Recursos/Imagenes/transicion_turno.png"; // 🔸 tu imagen elegida
                PantallaCambioTurno pantalla = new PantallaCambioTurno(this, ruta, siguienteTurno);
                pantalla.setVisible(true);

            } catch (MazoVacioException | PosicionInvalidaException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == botoncarta3) {
            try {
                turno.jugarCarta(2, cartasjugadas);
                String siguienteTurno = (turno.getJugadorMano() == turno.getJugador1()) ?
                        "Turno del Jugador 1" : "Turno del Jugador 2";

// 🔹 Mostramos la imagen de transición personalizada
                String ruta = "src/Recursos/Imagenes/transicion_turno.png"; // 🔸 tu imagen elegida
                PantallaCambioTurno pantalla = new PantallaCambioTurno(this, ruta, siguienteTurno);
                pantalla.setVisible(true);

            } catch (MazoVacioException | PosicionInvalidaException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == envido) {
            Jugador jugadorActual = turno.getJugadorMano();

            if (jugadorActual.getMana() >= 5) {
                jugadorActual.agregarMana(-5);

                int envidoJ1 = 0;
                try {
                    envidoJ1 = turno.getJugador1().calcularEnvido();
                } catch (JugadorSinCartasException ex) {
                    throw new RuntimeException(ex);
                }
                int envidoJ2 = 0;
                try {
                    envidoJ2 = turno.getJugador2().calcularEnvido();
                } catch (JugadorSinCartasException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, turno.jugarEnvido(envidoJ1, envidoJ2), "Resultado del Envido", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficiente mana", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == truco) {
            if (turno.getJugadorMano().getMana() >= 10) {
                //Agregar lo que haria cantar truco
                turno.getJugadorMano().agregarMana(-10);
            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficiente mana", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (turno.getJugadorMano() == turno.getJugador1()) {
            jturno.setText("Turno de Jugador 1");
        } else {
            jturno.setText("Turno de Jugador 2");
        }

        //Cambio de cartas en botones
        Carta carta1 = turno.getJugadorMano().getTresCartas().get(0);
        Carta carta2 = turno.getJugadorMano().getTresCartas().get(1);
        Carta carta3 = turno.getJugadorMano().getTresCartas().get(2);

        actualizarBotonCarta(botoncarta1, carta1, anchocarta, altocarta);
        actualizarBotonCarta(botoncarta2, carta2, anchocarta, altocarta);
        actualizarBotonCarta(botoncarta3, carta3, anchocarta, altocarta);

        //Cambio de etiquetas

        j1salud.setValue(turno.getJugador1().getSalud());
        j1salud.setString("Vida: " + turno.getJugador1().getSalud());
        j2salud.setValue(turno.getJugador2().getSalud());
        j2salud.setString("Vida: " + turno.getJugador2().getSalud());

        j1mana.setText("Mana Jugador 1: " + turno.getJugador1().getMana());
        j2mana.setText("Mana Jugador 2: " + turno.getJugador2().getMana());

        if (turno.condicionFinalizacion()) {
            new PantallaGanador(turno.partidaTerminada());
        }

        jturno.repaint();
    }


    static class PantallaCambioTurno extends JDialog {
        private Image imagenFondo;

        public PantallaCambioTurno(JFrame padre, String rutaImagen, String mensaje) {
            super(padre, false); // modal
            setUndecorated(true); // sin bordes
            setAlwaysOnTop(true);

            // --- Dimensiones ---
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension pantalla = tk.getScreenSize();
            setSize(pantalla);
            setLocationRelativeTo(null);

            // --- Carga de imagen ---
            imagenFondo = new ImageIcon(rutaImagen).getImage();

            // --- Panel personalizado ---
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            };
            panel.setLayout(new BorderLayout());

            // --- Texto centrado ---
            JLabel label = new JLabel(mensaje, SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("src/Recursos/Fuentes/ka1.ttf", Font.BOLD, 60));
            label.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
            panel.add(label, BorderLayout.CENTER);

            add(panel);

            // --- Timer de cierre automático (3 segundos) ---
             new Timer(3000, e -> dispose()).start();
        }
    }

    class PantallaGanador extends JFrame implements ActionListener {

        // --- Imagen.
        private Image imagen;

        // --- Botones principales de la pantalla.
        private JButton btnMenu;
        private JButton btnSalir;


        /**
         * Constructor que crea la ventana de resultado con un mensaje central.
         */
        public PantallaGanador(String mensaje) {
            // --- Configuración general de la ventana.
            setTitle("Resultado del Juego");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setSize(600, 350);
            setLocationRelativeTo(null); // --- Centra ventana en la pantalla.

            // --- Fuente personalizada.
            Font fuente;
            try {
                fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf").deriveFont(Font.BOLD, 28f);
            } catch (Exception ex) {
                fuente = new Font("Arial", Font.BOLD, 28);
            }

            // --- Mensaje principal
            JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
            lbl.setFont(fuente);
            lbl.setForeground(Color.BLACK);
            lbl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            add(lbl, BorderLayout.CENTER);

            // --- Botones inferiores
            btnMenu = new JButton("Volver al menú");
            btnMenu.addActionListener(this);


            btnSalir = new JButton("Salir");
            btnSalir.addActionListener(this);

            // --- Panel de botones con separación uniforme.
            JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            panelBtns.add(btnMenu);
            panelBtns.add(btnSalir);
            add(panelBtns, BorderLayout.SOUTH);

            setVisible(true);
        }

        /**
         * Maneja las acciones de los botones "Volver al menú" y "Salir".
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnMenu){
                new Inicio();
                dispose();
            }
            if (e.getSource() == btnSalir){
                System.exit(0);
            }
        }
    }

    private void actualizarBotonCarta(JButton boton, Carta carta, int ancho, int alto) {
        if (carta == null) {
            boton.setIcon(null);
            boton.setEnabled(false);
            boton.setVisible(false);  // o true si preferís mantener el espacio
            return;
        }

        boton.setVisible(true);
        boton.setEnabled(true);

        ImageIcon icon = new ImageIcon(carta.getImagen());
        Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(img));
    }

}
