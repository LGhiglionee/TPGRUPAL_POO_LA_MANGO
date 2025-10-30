package Vistas.Pantallas;

import javax.swing.*;
import java.awt.*;
import Vistas.Pantallas.PantallaGanador;

public class PantallaResultadosMano extends JDialog {
    public boolean continuarJuego = false;
    private int jugadorAbandono = 0;   // 0 ninguno, 1 o 2 si alguno abandona
    private final JFrame padre;


    public PantallaResultadosMano(JFrame padre,  Image img1, Image img2, String resultado) {
        super(padre, true); // modal
        this.padre = padre;
        setTitle("Resultado de la Mano");
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension pantalla = tk.getScreenSize();
        setBounds(0, 0, pantalla.width, pantalla.height);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        // Panel principal:
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 25, 25));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        // === Título resultado ===
        JLabel lblResultado = new JLabel("<html>" + resultado.replace("\n", "<br>") + "</html>", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 26));
        lblResultado.setForeground(Color.WHITE);
        lblResultado.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // === Cartas ===
        JPanel panelCartas = new JPanel(new GridLayout(1, 2, 25, 0));
        panelCartas.setOpaque(false);
        panelCartas.add(new JLabel(new ImageIcon(img1)));
        panelCartas.add(new JLabel(new ImageIcon(img2)));



        // === Botones ===
        JButton btnContinuar = new JButton("Continuar jugando");
        JButton btnAbandonar = new JButton("Abandonar partida");
        JButton btnFinalizar = new JButton("Partida finalizada");

        estilizarBoton(btnContinuar, new Color(40, 140, 40));
        estilizarBoton(btnAbandonar, new Color(160, 50, 50));
        estilizarBoton(btnFinalizar, new Color(40, 140, 40));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelBotones.setOpaque(false);
        if (continuarJuego) {
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
        panel.add(lblResultado, BorderLayout.NORTH);
        panel.add(panelCartas, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
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
