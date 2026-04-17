package taller.app.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class VentanaLogin extends JPanel {
    public VentanaLogin() {
        setLayout(new BorderLayout());
        add(new JLabel("Pantalla de Login", JLabel.CENTER), BorderLayout.CENTER);
    }
}
