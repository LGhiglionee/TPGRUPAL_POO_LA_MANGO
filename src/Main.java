import Mazo.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Mazo mazo = new Mazo();
        mazo.mezclarMazo();
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        List<Carta> mano1 = new ArrayList<>();
        List<Carta> mano2 = new ArrayList<>();

        while (jugador1.getSalud() > 0 && jugador2.getSalud() > 0 && !mazo.mazoVacio()) {
            for  (int i = 0; i < 3; i++) {
                mano1.add(mazo.getCarta());
                mano2.add(mazo.getCarta());
            }

            /*System.out.println(mano1.getFirst().getNumero() + mano1.getFirst().getPalo());
            System.out.println(mano2.getFirst().getNumero() + mano2.getFirst().getPalo());*/

            jugador1.reducirSalud(100);
        }




/*        int MAX_CARTAS = 52;

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
 */
    }
}