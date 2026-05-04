package taller.app.view;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.net.URL;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("Luigi Motors");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // icono de la aplicación
        URL iconURL = getClass().getResource("/taller/app/resources/Logo.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        } else {
            System.err.println("No se pudo encontrar el icono: /taller/app/resources/Logo.png");
        }

        Menu menu = new Menu();
        setContentPane(menu);
    }
}