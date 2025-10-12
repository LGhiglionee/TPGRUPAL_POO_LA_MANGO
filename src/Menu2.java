import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
    JButton carta1;
    JButton carta2;
    JButton carta3;
    Jugador jugador1;
    Jugador jugador2;
    Turnos turno;

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
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        Turnos turno =  new Turnos();

        //Botones de cartas
        int anchoboton = anchura/12;
        int  altoboton = altura/6;


        carta1 = new JButton("Carta 1");
        carta1.setBounds(anchura/4 - anchoboton*2,altura/4,anchoboton,altoboton);
        carta1.addActionListener(this);
        add(carta1);

        carta2 = new JButton("Carta 2");
        carta2.setBounds(anchura/4 - anchoboton/2,altura/4,anchoboton,altoboton);
        carta2.addActionListener(this);
        add(carta2);

        carta3 = new JButton("Carta 3");
        carta3.setBounds(anchura/4 + anchoboton,altura/4,anchoboton,altoboton);
        carta3.addActionListener(this);
        add(carta3);

        //Lamina
        Juego juego = new Juego();
        add(juego);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == carta1) {
            System.exit(0);
        }
        else if (e.getSource() == carta2) {
            System.exit(0);
        }
        else if (e.getSource() == carta3) {
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