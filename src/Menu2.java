import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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

        //Fuente de la letra del titulo
        Font mifuente = new Font("Arial", Font.BOLD, 50);
        g2.setFont(mifuente);
        g2.setColor(Color.BLUE);
        g2.drawString("Truco a dos Lucas", 250, 100);

    }
}

class Partida extends JFrame implements ActionListener {
    JButton botoncarta1;
    JButton botoncarta2;
    JButton botoncarta3;
    Jugador jugador1;
    Jugador jugador2;
    Turnos turno;
    Carta carta1;
    Carta carta2;
    Carta carta3;

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
        jugador1 = new Jugador();
        jugador2 = new Jugador();
        turno =  new Turnos();
        carta1 = new Carta();
        carta2 = new Carta();
        carta3 = new Carta();

        //Botones de cartas
        int anchoboton = anchura/12;
        int  altoboton = altura/6;

        ImageIcon imgcarta1 = new ImageIcon(carta1.getImagen());
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
        add(juego);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoncarta1) {
            System.exit(0);
        }
        else if (e.getSource() == botoncarta2) {
            System.exit(0);
        }
        else if (e.getSource() == botoncarta3) {
            System.exit(0);
        }
    }
}

class Juego extends JPanel {
    private Image imagen;

    public void paintComponent(Graphics g) {
        //Cosas de la funcion
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Imagen de fondo
        File miimagen=new File("Imagenes/MesaJuego.jpg");
        try {
            imagen = ImageIO.read(miimagen);
        }
        catch(IOException e) {
            System.out.println("La imagen no se encuentra");
        }
        Image imagencompleta = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        g2.drawImage(imagencompleta,0,0,null);

        //Fuente de la letra del titulo
        Font mifuente = new Font("Arial", Font.BOLD, 50);
        g2.setFont(mifuente);
        g2.setColor(Color.BLUE);
        g2.drawString("Turno de Jugador 1 ", 250, 100);
    }
}