package taller.app.view;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Menu extends JPanel {

    private Image imagenFondo;

    public Menu() {
        this.setLayout(null);
        // Cargar la imagen solo una vez
        ImageIcon icon = new ImageIcon(getClass().getResource("/taller/app/resources/FotoInicio.jpg"));
        imagenFondo = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}
