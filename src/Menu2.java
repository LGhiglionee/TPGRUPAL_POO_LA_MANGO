import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Mazo.*;

public class Menu2 {
    public static void main(String[] args) {
        Inicio ventana1 = new Inicio();
        ventana1.setVisible(true);
        ventana1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

class Inicio extends JFrame implements ActionListener {
    JButton botonjugar;
    JButton botonsalir;
    JButton botoninstrucciones;
    JLabel titulo;
    public Inicio() {
        setTitle("Truco a 2 Lucas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creacion del marco principal
        Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        //Tamaño de pnatalla
        setBounds(anchura / 4, altura / 4, anchura / 2, altura / 2);

        //Icono del programa
        Image fondo = mipantalla.getImage("Imagenes/Fondojuego.png");
        setIconImage(fondo);

        //lamina
        Lamina lamina = new Lamina();
        lamina.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        //Titulo
        titulo = new  JLabel("Truco a 2 lucas");
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setForeground(Color.BLUE);
        lamina.add(titulo,gbc);

        //Botones de jugar y salir
        gbc.gridy = 1;
        botonjugar = new JButton("Jugar");
        botonjugar.addActionListener(this);
        lamina.add(botonjugar,gbc);

        gbc.gridy = 2;
        botoninstrucciones = new JButton("Instrucciones");
        botoninstrucciones.addActionListener(this);
        lamina.add(botoninstrucciones,gbc);


        gbc.gridy = 3;
        botonsalir = new JButton("Salir");
        botonsalir.addActionListener(this);
        lamina.add(botonsalir,gbc);

        add(lamina);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonsalir) {
            int opcion = JOptionPane.showConfirmDialog (this, "¿Seguro que querés salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            };
        }
        else if (e.getSource() == botonjugar) {
            new Partida();
            dispose();
        }
        else if (e.getSource() == botoninstrucciones) {
            new Instrucciones();
        }
    }
}

class Lamina extends JPanel {
    private Image imagen;

    //Imagen de fondo

    public Lamina() {
        File miimagen = new File("Imagenes/Fondojuego.png");
        try {
            imagen = ImageIO.read(miimagen);
        } catch (IOException e) {
            System.out.println("La imagen no se encuentra");
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        //Cosas de la funcion
        super.paintComponent(g);

        if (imagen != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(imagen, 0, 0, getWidth(),getHeight(),this);
        }


    }
}

class Partida extends JFrame implements ActionListener {
    //Botones de cartas
    JButton botoncarta1;
    JButton botoncarta2;
    JButton botoncarta3;
    JButton envido;
    JButton truco;

    //Información de jugadores
    JProgressBar j1salud;
    JProgressBar j2salud;
    JLabel j1mana;
    JLabel j2mana;
    JLabel j1nombre;
    JLabel j2nombre;
    JLabel jturno;

    //Paneles
    JPanel j1Info;
    JPanel j2Info;
    JPanel manoCartas;
    JPanel infoTurno;
    JPanel infoEnvido;
    JPanel infoTruco;


    Turnos turno;
    ArrayList<Carta> cartasjugadas;

    public Partida() {
        //Creacion del marco principal
        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;
        setBounds(anchura / 4, altura / 4, anchura / 2, altura / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Titulo del programa e icono
        setTitle("Truco a 2 Lucas");
        Image fondo = mipantalla.getImage("Imagenes/Fondojuego.png");
        setIconImage(fondo);

        //inicializar datos
        turno = new Turnos();
        j1salud = new JProgressBar(0, 100);
        j2salud = new JProgressBar(0, 100);
        j1mana = new JLabel("Mana Jugador 1: " + turno.getJugador1().getMana());
        j1mana.setForeground(Color.WHITE);
        j2mana = new JLabel("Mana Jugador 2: " + turno.getJugador2().getMana());
        j2mana.setForeground(Color.WHITE);
        jturno = new JLabel("Turno de Jugador 1");

        botoncarta1 = new JButton("Carta 1");
        botoncarta2 = new JButton("Carta 2");
        botoncarta3 = new JButton("Carta 3");

        //Inicializacion jugadores y turno
        turno.llenarMano(turno.getJugador1().getTresCartas(), turno.getJugador1());
        turno.llenarMano(turno.getJugador2().getTresCartas(), turno.getJugador2());
        cartasjugadas =  new ArrayList<Carta>();

        //Tamanio botones
        int anchoboton = anchura/12;
        int  altoboton = altura/6;

        //Obtener cartas
        Carta carta1 = turno.getJugadorMano().getTresCartas().get(0);
        Carta carta2 = turno.getJugadorMano().getTresCartas().get(1);
        Carta carta3 = turno.getJugadorMano().getTresCartas().get(2);

        //Nombre jugador
        j1nombre = new JLabel();
        j2nombre = new JLabel();

        // Salud Jugador 1
        j1salud = new JProgressBar(0, 100);
        j1salud.setValue(turno.getJugador1().getSalud());
        j1salud.setStringPainted(true);
        j1salud.setForeground(Color.RED);
        j1salud.setString("Vida: " + turno.getJugador1().getSalud());

        // Salud J2
        j2salud = new JProgressBar(0, 100);
        j2salud.setValue(turno.getJugador2().getSalud());
        j2salud.setStringPainted(true);
        j2salud.setForeground(Color.RED);
        j2salud.setString("Vida: " + turno.getJugador2().getSalud());

        //Turno
        Font mifuente = new Font("Arial", Font.BOLD, 45);
        jturno.setFont(mifuente);
        jturno.setForeground(Color.WHITE);

        //Cartas
        ImageIcon imgcarta1 = new ImageIcon(carta1.getImagen());
        Image img1 = imgcarta1.getImage().getScaledInstance(anchoboton, altoboton, Image.SCALE_SMOOTH);
        botoncarta1 = new JButton(new ImageIcon(img1));
        botoncarta1.addActionListener(this);

        ImageIcon imgcarta2 = new ImageIcon(carta2.getImagen());
        Image img2 = imgcarta2.getImage().getScaledInstance(anchoboton, altoboton, Image.SCALE_SMOOTH);
        botoncarta2 = new JButton(new ImageIcon(img2));
        botoncarta2.addActionListener(this);

        ImageIcon imgcarta3 = new ImageIcon(carta3.getImagen());
        Image img3 = imgcarta3.getImage().getScaledInstance(anchoboton, altoboton, Image.SCALE_SMOOTH);
        botoncarta3 = new JButton(new ImageIcon(img3));
        botoncarta3.addActionListener(this);

        //Botones (Envido y Truco)
        envido = new JButton("Envido");
        envido.setPreferredSize(new Dimension(100, 30));
        envido.addActionListener(this);

        truco = new JButton("Truco");
        truco.setPreferredSize(new Dimension(100, 30));
        truco.addActionListener(this);

        //Panel Principal
        Juego juego = new Juego();
        juego.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //Creacion de sub-paneles
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


        //Borde de los sub-paneles
        j1Info.setBorder(BorderFactory.createTitledBorder("Jugador 1"));
        j2Info.setBorder(BorderFactory.createTitledBorder("Jugador 2"));

        //Organiza sub-paneles
        j1Info.setLayout(new GridLayout(3,1));
        j1Info.add(j1nombre);
        j1Info.add(j1salud);
        j1Info.add(j1mana);

        j2Info.setLayout(new GridLayout(3,1));
        j2Info.add(j2nombre);
        j2Info.add(j2salud);
        j2Info.add(j2mana);

        infoTurno.add(jturno);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(1,3));
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

        add(juego);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoncarta1) {
            turno.jugarCarta(0, cartasjugadas);
        }
        else if (e.getSource() == botoncarta2) {
            turno.jugarCarta(1,cartasjugadas);
            }
        else if (e.getSource() == botoncarta3) {
            turno.jugarCarta(2, cartasjugadas);
        }
        else if (e.getSource() == envido) {
            Jugador jugadorActual = turno.getJugadorMano();

            if (jugadorActual.getMana() >= 5) {
                jugadorActual.agregarMana(-5);

                int envidoJ1 = turno.getJugador1().calcularEnvido();
                int envidoJ2 = turno.getJugador2().calcularEnvido();
                JOptionPane.showMessageDialog(this, turno.jugarEnvido (envidoJ1, envidoJ2), "Resultado del Envido", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficiente mana", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource() == truco) {
            if (turno.getJugadorMano().getMana() >= 10){
                //Agregar lo que haria cantar truco
                turno.getJugadorMano().agregarMana(-10);
            } else{
                JOptionPane.showMessageDialog(this, "No tienes suficiente mana", "Aviso",  JOptionPane.WARNING_MESSAGE);
            }
        }
        if (turno.getJugadorMano() == turno.getJugador1()) {
            jturno.setText("Turno de Jugador 1");
        } else {
            jturno.setText("Turno de Jugador 2");
        }

        //Cambio de cartas en botones
        botoncarta1.setIcon(new ImageIcon(turno.getJugadorMano().getTresCartas().get(0).getImagen()));
        botoncarta2.setIcon(new ImageIcon(turno.getJugadorMano().getTresCartas().get(1).getImagen()));
        botoncarta3.setIcon(new ImageIcon(turno.getJugadorMano().getTresCartas().get(2).getImagen()));

        //Cambio de etiquetas

        j1salud.setValue(turno.getJugador1().getSalud());
        j1salud.setString("Vida: " + turno.getJugador1().getSalud());
        j2salud.setValue(turno.getJugador2().getSalud());
        j2salud.setString("Vida: " + turno.getJugador2().getSalud());

        j1mana.setText("Mana Jugador 1: "+turno.getJugador1().getMana());
        j2mana.setText("Mana Jugador 2: "+turno.getJugador2().getMana());


        jturno.repaint();
    }
}

class Instrucciones extends JFrame {

    public Instrucciones() {

        Toolkit mipantalla = Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;
        setBounds(anchura/4,altura/4,anchura/2,altura/2);

        setTitle("Instrucciones del Juego");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);

        JScrollPane scroll = new JScrollPane(texto);
        add(scroll, BorderLayout.CENTER);

        String ruta = "Instrucciones/instrucciones.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            texto.setText(contenido.toString());
        } catch (IOException ex) {
            texto.setText("No se pudo cargar el archivo de instrucciones.");
        }

        setVisible(true);
    }
}

    class Juego extends JPanel {
        private Image imagen;

        public void paintComponent(Graphics g) {
            //Cosas de la funcion
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            //Imagen de fondo
            File miimagen = new File("Imagenes/FondoJuego.png");
            try {
                imagen = ImageIO.read(miimagen);
            } catch (IOException e) {
                System.out.println("La imagen no se encuentra");
            }
            Image imagencompleta = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g2.drawImage(imagencompleta, 0, 0, null);

            //Fuente de la letra del titulo
            Toolkit mipantalla = Toolkit.getDefaultToolkit();
            Dimension tamanio = mipantalla.getScreenSize();
            int altura = tamanio.height;
            int anchura = tamanio.width;
        }
    }