import Mazo.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int MAX_CARTAS = 52;

        Espada[] espada = new Espada[MAX_CARTAS/4];
        Oro[] oro = new Oro[MAX_CARTAS/4];
        Copa[] copa = new Copa[MAX_CARTAS/4];
        Basto[] basto = new Basto[MAX_CARTAS/4];

        for (int i = 0; i < MAX_CARTAS/4; i++) {
            espada[i] = new Espada(i + 1);
            oro[i] = new Oro(i + 1);
            copa[i] = new Copa(i + 1);
            basto[i] = new Basto(i + 1);
        }

        Mazo mazo = new Mazo(espada, oro, basto, copa);


        System.out.println("Habilidad de Cada una: " + mazo.getEspadas()[0].getHabilidad());
    }
}