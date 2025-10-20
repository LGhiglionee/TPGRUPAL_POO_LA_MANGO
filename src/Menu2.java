import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    public Inicio() {
        //Creacion del marco principal
        Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;
        setBounds(anchura/4,altura/4 ,anchura/2 ,altura/2 );

        //Titulo del programa e icono
        setTitle("Truco a 2 Lucas");
        Image fondo=mipantalla.getImage("Imagenes/download.jpeg");
        setIconImage(fondo);

        //Botones de jugar y salir
        int anchoboton = anchura/10;
        int  altoboton = altura/16;


        botonjugar = new JButton("Jugar");
        botonjugar.setBounds(anchura/4 - anchoboton/2,altura/4,anchoboton,altoboton);
        botonjugar.addActionListener(this);
        add(botonjugar);

        botonsalir = new JButton("Salir");
        botonsalir.setBounds(anchura/4 - anchoboton/2,altura/4 + 100,anchoboton,altoboton);
        botonsalir.addActionListener(this);
        add(botonsalir);

        //Lamina
        Lamina lamina = new Lamina();
        add(lamina);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonsalir) {
            System.exit(0);
        }
        else if (e.getSource() == botonjugar) {
            new Partida();
            dispose();
        }
    }
}

class Lamina extends JPanel {
    private Image imagen;

    public void paintComponent(Graphics g) {
        //Cosas de la funcion
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Imagen de fondo
        File miimagen=new File("Imagenes/BarMenuPrincipal.jpeg");
        try {
            imagen = ImageIO.read(miimagen);
        }
        catch(IOException e) {
            System.out.println("La imagen no se encuentra");
        }
        Image imagencompleta = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        g2.drawImage(imagencompleta,0,0,null);

        //Fuente de la letra del titulo y tamaño
        Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        Font mifuente = new Font("Arial", Font.BOLD, 50);
        g2.setFont(mifuente);
        g2.setColor(Color.BLUE);
        g2.drawString("Truco a dos Lucas", anchura/9, altura/8);

    }
}

class Partida extends JFrame implements ActionListener {
    JButton botoncarta1;
    JButton botoncarta2;
    JButton botoncarta3;
    JProgressBar j1salud;
    JProgressBar j2salud;
    JLabel j1mana;
    JLabel j2mana;
    JLabel jturno;
    Turnos turno;
    ArrayList<Carta> cartasjugadas;

    public Partida() {
        //Creacion del marco principal
        Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;
        setBounds(anchura/4,altura/4 ,anchura/2 ,altura/2 );

        //Titulo del programa e icono
        setTitle("Truco a 2 Lucas");
        Image fondo=mipantalla.getImage("Imagenes/download.jpeg");
        setIconImage(fondo);

        //Inicializacion jugadores y turno
        turno =  new Turnos();
        turno.llenarMano(turno.getJugador1().getTresCartas(), turno.getJugador1());
        turno.llenarMano(turno.getJugador2().getTresCartas(), turno.getJugador2());
        cartasjugadas =  new ArrayList<Carta>();

        //Tamanio botones
        int anchoboton = anchura/12;
        int  altoboton = altura/6;

        //Barra salud

        j1salud = new JProgressBar(0,100);
        j1salud.setBounds(anchura/4 - anchoboton*2, altura/50,115,20);
        j1salud.setValue(turno.getJugador1().getSalud());
        j1salud.setStringPainted(true);
        j1salud.setForeground(Color.RED);
        j1salud.setString("Vida: " + turno.getJugador1().getSalud());

        j2salud = new JProgressBar(0,100);
        j2salud.setBounds(anchura/4 + anchoboton,altura/50,115,20);
        j2salud.setValue(turno.getJugador2().getSalud());
        j2salud.setStringPainted(true);
        j2salud.setForeground(Color.RED);
        j2salud.setString("Vida: " + turno.getJugador2().getSalud());


        //Etiquetas mana
        j1mana = new JLabel("Mana Jugador 1: " + turno.getJugador1().getMana());
        j1mana.setBounds(anchura/4 - anchoboton*2, altura/50,anchura/6,altura/10);

        j2mana = new JLabel("Mana Jugador 2: " + turno.getJugador2().getMana());
        j2mana.setBounds(anchura/4 + anchoboton,altura/50,anchura/6,altura/10);

        //Etiqueta turno
        jturno = new JLabel("Turno de Jugador 1");
        jturno.setBounds(anchura/9, altura/75, anchura/2,altura/4);
        Font mifuente = new Font("Arial", Font.BOLD, 45);
        jturno.setFont(mifuente);
        jturno.setForeground(Color.BLUE);

        //Botones de cartas

        Carta carta1 = new Carta();
        Carta carta2 = new Carta();
        Carta carta3 = new Carta();

        carta1 = turno.getJugadorMano().getTresCartas().get(0);
        carta2 = turno.getJugadorMano().getTresCartas().get(1);
        carta3 = turno.getJugadorMano().getTresCartas().get(2);

        ImageIcon imgcarta1 = new ImageIcon(carta1.getImagen());
        //Image imagencompleta1 = imgcarta1.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        botoncarta1 = new JButton(imgcarta1);
        botoncarta1.setBounds(anchura/4 - anchoboton*2,altura/4,anchoboton,altoboton);
        botoncarta1.addActionListener(this);
        add(botoncarta1);

        ImageIcon imgcarta2 = new ImageIcon(carta2.getImagen());
        botoncarta2 = new JButton(imgcarta2);
        botoncarta2.setBounds(anchura/4 - anchoboton/2,altura/4,anchoboton,altoboton);
        botoncarta2.addActionListener(this);
        add(botoncarta2);

        ImageIcon imgcarta3 = new ImageIcon(carta3.getImagen());
        botoncarta3 = new JButton(imgcarta3);
        botoncarta3.setBounds(anchura/4 + anchoboton,altura/4,anchoboton,altoboton);
        botoncarta3.addActionListener(this);
        add(botoncarta3);

        //Lamina
        Juego juego = new Juego();
        juego.setBounds(0,0,anchura,altura);

        JLayeredPane vista = new JLayeredPane();
        vista.setBounds(0,0,getWidth(),getHeight());
        vista.add(juego, JLayeredPane.DEFAULT_LAYER);
        vista.add(jturno, JLayeredPane.PALETTE_LAYER);
        vista.add(j1salud,  JLayeredPane.PALETTE_LAYER);
        vista.add(j2salud,  JLayeredPane.PALETTE_LAYER);
        vista.add(j1mana,   JLayeredPane.PALETTE_LAYER);
        vista.add(j2mana,   JLayeredPane.PALETTE_LAYER);
        vista.add(botoncarta1,  JLayeredPane.PALETTE_LAYER);
        vista.add(botoncarta2,  JLayeredPane.PALETTE_LAYER);
        vista.add(botoncarta3,  JLayeredPane.PALETTE_LAYER);

        add(vista);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoncarta1) {
            if (!cartasjugadas.isEmpty()) {
                cartasjugadas.add(turno.getJugadorMano().getTresCartas().getFirst());
                turno.jugarMano(cartasjugadas.get(0),  cartasjugadas.get(1));
                cartasjugadas.clear();
            }
            else {
                cartasjugadas.add(turno.getJugadorMano().getTresCartas().getFirst());
            }
            turno.getJugadorMano().setCarta(turno.getJugadorMano().getTresCartas(),0, turno.getMazo().getCarta());
        }
        else if (e.getSource() == botoncarta2) {
            if (!cartasjugadas.isEmpty()) {
                cartasjugadas.add(turno.getJugadorMano().getTresCartas().get(1));
                turno.jugarMano(cartasjugadas.get(0),  cartasjugadas.get(1));
                cartasjugadas.clear();
            }
            else {
                cartasjugadas.add(turno.getJugadorMano().getTresCartas().get(1));
            }
            turno.getJugadorMano().setCarta(turno.getJugadorMano().getTresCartas(),1, turno.getMazo().getCarta());
        }
        else if (e.getSource() == botoncarta3) {
            if (!cartasjugadas.isEmpty()) {
                cartasjugadas.add(turno.getJugadorMano().getTresCartas().get(2));
                turno.jugarMano(cartasjugadas.get(0),  cartasjugadas.get(1));
                cartasjugadas.clear();
            }
            else {
                cartasjugadas.add(turno.getJugadorMano().getTresCartas().get(2));
            }
            turno.getJugadorMano().setCarta(turno.getJugadorMano().getTresCartas(),2, turno.getMazo().getCarta());
        }
        turno.alternarTurno();
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

class Juego extends JPanel {
    private Image imagen;

    private String turnoActual = "Jugador 1";

    public void setTurnoActual(String turnoActual) {
        this.turnoActual = turnoActual;
    }

    public void paintComponent(Graphics g) {
        //Cosas de la funcion
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Imagen de fondo
        File miimagen=new File("Imagenes/CartasFondo.jpg");
        try {
            imagen = ImageIO.read(miimagen);
        }
        catch(IOException e) {
            System.out.println("La imagen no se encuentra");
        }
        Image imagencompleta = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        g2.drawImage(imagencompleta,0,0,null);

        //Fuente de la letra del titulo
        /*Toolkit mipantalla =  Toolkit.getDefaultToolkit();
        Dimension tamanio = mipantalla.getScreenSize();
        int altura = tamanio.height;
        int anchura = tamanio.width;

        Font mifuente = new Font("Arial", Font.BOLD, 50);
        g2.setFont(mifuente);
        g2.setColor(Color.BLUE);
        g2.drawString("Turno de " + turnoActual, anchura/9, altura/5);*/
    }
}