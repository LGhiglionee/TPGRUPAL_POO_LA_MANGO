import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Menu() {
        // 🪟 Configuración del frame
        setTitle("Menú Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centra la ventana

        // 🎨 Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 0, 20, 0);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;

        // 🏷️ TÍTULO
        JLabel titulo = new JLabel("MENÚ PRINCIPAL");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(titulo, c);

        // 🎮 BOTÓN JUGAR
        JToggleButton btnJugar = new JToggleButton("JUGAR");
        btnJugar.setPreferredSize(new Dimension(200, 60));
        btnJugar.setFont(new Font("Arial", Font.BOLD, 22));
        btnJugar.setBackground(new Color(70, 130, 180));
        btnJugar.setForeground(Color.WHITE);
        btnJugar.setFocusPainted(false);
        c.gridy = 1;
        panel.add(btnJugar, c);

        // 🚪 BOTÓN SALIR
        JToggleButton btnSalir = new JToggleButton("SALIR");
        btnSalir.setPreferredSize(new Dimension(200, 60));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 22));
        btnSalir.setBackground(new Color(178, 34, 34));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        c.gridy = 2;
        panel.add(btnSalir, c);

        // 🧩 Acciones de los botones
        btnJugar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Iniciando el juego...");
            // acá podrías abrir otra ventana del juego y cerrar esta
            // new Juego().setVisible(true);
            // dispose();
        });

        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this, "¿Seguro que querés salir?",
                    "Salir", JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Agregamos el panel al frame
        add(panel);
    }

    // 🚀 Método principal para probarlo directamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Menu().setVisible(true);
        });
    }
}