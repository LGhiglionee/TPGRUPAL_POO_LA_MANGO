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
        Turnos turnos = new Turnos();

        while (jugador1.getSalud() > 0 && jugador2.getSalud() > 0 && !mazo.mazoVacio()) {
            for  (int i = 0; i < 3; i++) {
                mano1.add(mazo.getCarta());
                mano2.add(mazo.getCarta());
            }
            /*System.out.println(mano1.getFirst().getNumero() + mano1.getFirst().getPalo());
            System.out.println(mano2.getFirst().getNumero() + mano2.getFirst().getPalo());*/
        }
        turnos.resultado(mazo);
        }
    }