package Main;
import Vistas.Inicio;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inicio ventana = new Inicio();
            ventana.setVisible(true);
        });
    }
}

