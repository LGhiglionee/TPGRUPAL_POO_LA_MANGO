package Main;
import Vistas.Pantallas.PantallaInicio;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaInicio ventana = new PantallaInicio();
            ventana.setVisible(true);
        });
    }
}

