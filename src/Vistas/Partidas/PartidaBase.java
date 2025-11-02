package Vistas.Partidas;

import Excepciones.Juego.JugadorSinCartasException;
import Excepciones.Juego.MazoVacioException;
import Excepciones.Juego.PosicionInvalidaException;
import Modelo.Entidades.Jugador;
import Modelo.Entidades.Carta;
import Modelo.Motor.Turnos;
import Modelo.Recursos.GestorRecursos;
import Vistas.Configuraciones.ConfigurPanelConFondo;
import Vistas.Configuraciones.ConfigurPantallas;
import Vistas.Pantallas.PantallaGanador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class PartidaBase extends ConfigurPantallas implements ActionListener {
    // --- Botones de cartas y acciones ---
    private JButton botoncarta1, botoncarta2, botoncarta3, envido, truco;

    // --- Información de jugadores ---
    private JProgressBar j1salud, j2salud;
    private JLabel j1mana, j2mana, j1nombre, j2nombre, jturno;

    // --- Paneles principales ---
    private JPanel j1Info, j2Info, manoCartas, infoTurno, infoEnvido, infoTruco;

    // --- Fuentes ---
    private Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
    private Font fuenteTitulo = fuente.deriveFont(Font.BOLD, 70f);
    private Font fuenteTexto = fuente.deriveFont(Font.BOLD, 20f);

    // --- Parámetros generales ---
    private int altoBotonAccion,anchoBotonAccion, anchura,anchocarta, altocarta, anchobarra,altobarra;

    // --- Lógica de juego ---
    protected Turnos turno;
    protected ArrayList<Carta> cartasjugadas;

    public PartidaBase(String titulo, String fondo, int dificultad) {
        super(titulo, fondo);
        inicializarJuego(dificultad); // lo define cada subclase
        inicializarComponentesGraficos();
        configurarPanelPrincipal();
    }

    protected void inicializarJuego(int i) {

    }


    private void inicializarComponentesGraficos () {
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        anchocarta = anchura / 12;
        altocarta = altura / 6;

        anchobarra = anchura / 10;
        altobarra = altura / 30;

        anchoBotonAccion = anchura / 10;
        altoBotonAccion = altura / 18;
        // --- Crear nombre jugador
        j1nombre = crearNombreJugador("Jugador 1", turno.getJugador1());
        j2nombre = crearNombreJugador("Jugador 2", turno.getJugador2());

        // --- BARRAS DE SALUD ---
        j1salud = crearBarraSalud(turno.getJugador1());
        j2salud = crearBarraSalud(turno.getJugador2());

        // --- Maná
        j1mana = crearEtiquetaMana("Mana Jugador 1: " + turno.getJugador1().getMana());
        j2mana = crearEtiquetaMana("Mana Jugador 2: ##");

        // --- Turno
        jturno = new JLabel("Turno de Jugador 1", SwingConstants.CENTER);
        jturno.setFont(fuenteTitulo);
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


    private JLabel crearNombreJugador(String nombreJugador, Jugador jugador) {
        JLabel nombre = new JLabel(nombreJugador);
        nombre.setFont(fuenteTexto);
        nombre.setForeground(Color.WHITE);
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        return nombre;
    }
    private JProgressBar crearBarraSalud(Jugador jugador) {
        Dimension tamanoBarra = new Dimension(anchobarra, altobarra);
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(jugador.getSalud());
        barra.setString("Vida: " + jugador.getSalud());
        barra.setStringPainted(true);
        barra.setForeground(Color.RED);
        barra.setPreferredSize(tamanoBarra);
        barra.setMaximumSize(tamanoBarra);
        barra.setMinimumSize(tamanoBarra); ;
        return barra;
    }

    private JLabel crearEtiquetaMana(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(fuenteTexto);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        ImageIcon imgFondo = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoBotonJuego.png", anchoBotonAccion, altoBotonAccion);

        JButton boton = new JButton(texto);
        boton.setIcon(imgFondo);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.CENTER);

        boton.setFont(fuenteTexto);
        boton.setForeground(Color.WHITE);

        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setMargin(new Insets(0, 0, 0, 0));

        boton.addActionListener(this);
        return boton;
    }

    private void configurarPanelPrincipal() {

        // ===== 1️⃣ Panel de fondo general =====
        ConfigurPanelConFondo juego = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoJuego.png");
        juego.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //Creacion de sub-paneles
        crearSubPaneles ();

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
    private void ubicarPanelesEnLayout(ConfigurPanelConFondo juego, JPanel panelInferior, GridBagConstraints gbc){
        //Panel jugador 1 (Arriba - izquierda)
        gbc.insets = new Insets(50, 50, 50, 50);
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
        ImageIcon fondo = GestorRecursos.cargarImagenEscalada("src/Recursos/Imagenes/Fondos/FondoBotonJuego.png",100,100);
        //infoEnvido.setIcon(fondo);
        infoEnvido.add(envido);

        infoTruco.setLayout(new FlowLayout(FlowLayout.RIGHT));
        infoTruco.add(truco);

        panelInferior.add(infoEnvido);
        panelInferior.add(manoCartas);
        panelInferior.add(infoTruco);

        return panelInferior;
    }

    private void configurarPanelJugadores() {
        j1Info.setLayout(new BoxLayout(j1Info, BoxLayout.Y_AXIS));
        j1Info.add(Box.createRigidArea(new Dimension(0, 35)));
        j1Info.add(j1nombre);
        j1Info.add(Box.createRigidArea(new Dimension(0, 10)));
        j1Info.add(j1salud);
        j1Info.add(Box.createRigidArea(new Dimension(0, 10)));
        j1Info.add(j1mana);
        j1Info.add(Box.createRigidArea(new Dimension(0, 20)));

        j2Info.setLayout(new BoxLayout(j2Info, BoxLayout.Y_AXIS));
        j2Info.add(Box.createRigidArea(new Dimension(0, 35)));
        j2Info.add(j2nombre);
        j2Info.add(Box.createRigidArea(new Dimension(0, 10)));
        j2Info.add(j2salud);
        j2Info.add(Box.createRigidArea(new Dimension(0, 10)));
        j2Info.add(j2mana);
        j2Info.add(Box.createRigidArea(new Dimension(0, 20)));

    }

    private void crearSubPaneles() {
        j1Info = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoEstadisticas.png");
        j1Info.setPreferredSize(new Dimension(340,190));
        j1Info.setOpaque(false);
        j2Info = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoEstadisticas.png");
        j2Info.setPreferredSize(new Dimension(340,190));
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

    private void actualizarInterfaz() throws MazoVacioException {
        jturno.setText(turno.getJugadorMano() == turno.getJugador1() ? "Turno de Jugador 1" : "Turno de Jugador 2");

        var mano = turno.getJugadorMano().getTresCartas();
        actualizarBotonCarta(botoncarta1, mano.get(0), anchocarta, altocarta);
        actualizarBotonCarta(botoncarta2, mano.get(1), anchocarta, altocarta);
        actualizarBotonCarta(botoncarta3, mano.get(2), anchocarta, altocarta);

        j1salud.setValue(turno.getJugador1().getSalud()); j1salud.setString("Vida: " + turno.getJugador1().getSalud());
        j2salud.setValue(turno.getJugador2().getSalud()); j2salud.setString("Vida: " + turno.getJugador2().getSalud());

        if (turno.getJugadorMano() == turno.getJugador1()) {
            j1mana.setText("Mana Jugador 1: " + turno.getJugador1().getMana());
            j2mana.setText("Mana Jugador 2: ##");
        } else {
            j1mana.setText("Mana Jugador 1: ##");
            j2mana.setText("Mana Jugador 2: " + turno.getJugador2().getMana());
        }

        envido.setVisible(turno.envidoDisponible());
        truco.setVisible(turno.trucoDisponible());
    }

    private void actualizarBotonCarta(JButton boton, Carta carta, int ancho, int alto) {
        if (carta == null) {
            boton.setIcon(null);
            boton.setEnabled(false);
            boton.setVisible(false);
            return;
        }

        boton.setVisible(true);
        boton.setEnabled(true);

        ImageIcon icon = new ImageIcon(carta.getImagen());
        Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(img));
    }

    /**
     * Método abstracto — se redefine según cómo se juega el turno
     */
    protected void procesarCartaJugada(int indice)
            throws MazoVacioException, PosicionInvalidaException, JugadorSinCartasException {

    }

    // --- Acción general ---
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == botoncarta1 || e.getSource() == botoncarta2 || e.getSource() == botoncarta3) {
                int indice = (e.getSource() == botoncarta1) ? 0 : (e.getSource() == botoncarta2) ? 1 : 2;
                procesarCartaJugada(indice);
                actualizarInterfaz();
            }

            else if (e.getSource() == envido) {
                Jugador j = turno.getJugadorMano();
                if (j.getMana() >= 5) {
                    j.agregarMana(-5);
                    JOptionPane.showMessageDialog(this,
                            turno.jugarEnvido(turno.getJugador1().calcularEnvido(), turno.getJugador2().calcularEnvido()),
                            "Resultado del Envido", JOptionPane.INFORMATION_MESSAGE);
                    turno.bloquearEnvido();
                    envido.setVisible(false);
                    actualizarInterfaz();
                } else JOptionPane.showMessageDialog(this, "No tienes suficiente mana", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            else if (e.getSource() == truco) {
                if (turno.getJugadorMano().getMana() < 10) {
                    JOptionPane.showMessageDialog(this, "No tienes suficiente mana", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    turno.getJugadorMano().agregarMana(-10);
                    turno.bloquearTruco();
                    JOptionPane.showMessageDialog(this, "¡Truco cantado!");
                    truco.setVisible(false);
                }
            }
        if (turno.condicionFinalizacion()) {
            dispose();
            new PantallaGanador(turno.partidaTerminada());
        }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



