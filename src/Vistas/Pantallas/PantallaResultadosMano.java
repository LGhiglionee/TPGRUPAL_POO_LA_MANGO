package Vistas.Pantallas;

import javax.swing.*;
import java.awt.*;

import Modelo.Motor.Turnos;
import Modelo.Recursos.GestorRecursos;
import Vistas.Configuraciones.ConfigurPanelConFondo;

public class PantallaResultadosMano extends JDialog {
    public boolean continuarJuego = false;
    private int jugadorAbandono = 0;   // 0 ninguno, 1 o 2 si alguno abandona
    private final JFrame padre;


    public PantallaResultadosMano(JFrame padre,  Image img1, Image img2, String resultado, boolean mostrarTruco) {
        super(padre, true); // modal
        this.padre = padre;
        setTitle("Resultado de la Mano");
        setUndecorated(true);
        padre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // Imagen cartas
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;
        int altoCartaGrande = altura/6;
        int anchoCartaGrande = anchura/12;

        Image imgEscalada1 = img1.getScaledInstance(anchoCartaGrande, altoCartaGrande, Image.SCALE_SMOOTH);
        Image imgEscalada2 = img2.getScaledInstance(anchoCartaGrande, altoCartaGrande, Image.SCALE_SMOOTH);

        // Funete
        Font fuente = GestorRecursos.cargarFuente("src/Recursos/Fuentes/ka1.ttf");
        Font fuenteTexto = fuente.deriveFont(Font.BOLD, 35f);

        // Panel principal:
        ConfigurPanelConFondo panel = new ConfigurPanelConFondo("src/Recursos/Imagenes/Fondos/FondoIntermedio.png");
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        // === Título resultado ===
        JLabel lblResultado = new JLabel("<html>" + resultado.replace("\n", "<br>") + "</html>", SwingConstants.CENTER);
        lblResultado.setFont(fuenteTexto);
        lblResultado.setForeground(Color.WHITE);
        lblResultado.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // === Cartas ===
        JPanel panelCartas = new JPanel(new GridLayout(1, 2, 25, 0));
        panelCartas.setOpaque(false);
        panelCartas.add(new JLabel(new ImageIcon(imgEscalada1)));
        panelCartas.add(new JLabel(new ImageIcon(imgEscalada2)));



        // === Botones ===
        JButton btnContinuar = new JButton("Continuar jugando");
        JButton btnAbandonar = new JButton("Abandonar partida");
        JButton btnFinalizar = new JButton("Partida finalizada");

        estilizarBoton(btnContinuar, new Color(40, 140, 40));
        estilizarBoton(btnAbandonar, new Color(160, 50, 50));
        estilizarBoton(btnFinalizar, new Color(40, 140, 40));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelBotones.setOpaque(false);

        if (!Turnos.condicionFinalizacion()) {
            panelBotones.add(btnContinuar);
            panelBotones.add(btnAbandonar);
        } else {
            panelBotones.add(btnFinalizar);
        }

        // === Acciones ===
        btnContinuar.addActionListener(e -> {
            continuarJuego = true;
            dispose();
        });

        btnFinalizar.addActionListener(e -> {
            continuarJuego = false;
            dispose();
        });

        btnAbandonar.addActionListener(e -> {
            continuarJuego = false;

            Object[] opciones = {"Jugador 1", "Jugador 2", "Cancelar"};
            int seleccion = JOptionPane.showOptionDialog(
                    this,
                    "¿Qué jugador desea abandonar la partida?",
                    "Confirmar abandono",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion == 0) {
                jugadorAbandono = 1;
                mostrarPantallaGanador("Jugador 2 gana por abandono del Jugador 1");
            } else if (seleccion == 1) {
                jugadorAbandono = 2;
                mostrarPantallaGanador("Jugador 1 gana por abandono del Jugador 2");
            } else {
                // Canceló la acción → no cerrar
                continuarJuego = true;
            }

            dispose();
        });

        // === Ensamble ===
        panel.add(panelCartas, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        // === Header con CardLayout para TRUCO/RESULTADO ===
        JPanel header = new JPanel(new CardLayout());
        header.setOpaque(false);

        // Resultado (card "resultado")
        lblResultado = new JLabel("<html>" + resultado.replace("\n", "<br>") + "</html>", SwingConstants.CENTER);
        lblResultado.setFont(fuenteTexto);
        lblResultado.setForeground(Color.WHITE);
        lblResultado.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        header.add(lblResultado, "resultado");

        //-- Si se jugo el truco, muestra el card de TRUCO
        if (mostrarTruco) {
            JLabel lblTruco = new JLabel("¡EL TRUCO FUE ACTIVADO!", SwingConstants.CENTER);
            lblTruco.setFont(new Font("Arial", Font.BOLD, 38));
            lblTruco.setForeground(new Color(25, 25, 25));
            lblTruco.setBackground(new Color(255, 250, 0));
            lblTruco.setOpaque(true);
            lblTruco.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
            lblTruco.setPreferredSize(new Dimension(400, 20));
            header.add(lblTruco, "truco");

            // -- Muestra primero el cartel de truco
            ((CardLayout) header.getLayout()).show(header, "truco");

            // --NO muestra el resto
            panelCartas.setVisible(false);
            panelBotones.setVisible(false);

            Timer t = new Timer(3000, e -> {
                ((CardLayout) header.getLayout()).show(header, "resultado"); // cambiar al resultado
                panelCartas.setVisible(true);
                panelBotones.setVisible(true);
                panel.revalidate();
                panel.repaint();
            });
            t.setRepeats(false);
            t.start();
        } else {
            ((CardLayout) header.getLayout()).show(header, "resultado");
        }

        panel.add(header, BorderLayout.NORTH);
        add(panel);
    }

    private void estilizarBoton(JButton btn, Color colorFondo) {
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(220, 40));
    }

    private void mostrarPantallaGanador(String mensaje) {
        new PantallaGanador(mensaje);
        if (padre != null) {
            padre.dispose(); // cerrar la ventana de partida
        }
    }

    public boolean continuarJuego() {
        return continuarJuego;
    }

    public int getJugadorAbandono() {
        return jugadorAbandono;
    }
}
