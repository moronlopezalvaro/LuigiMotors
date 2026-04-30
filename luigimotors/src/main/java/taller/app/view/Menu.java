package taller.app.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Menu extends JPanel {

    private Image imagenFondo;

    public Menu() {
        this.setLayout(null);
        // Cargar la imagen solo una vez
        ImageIcon icon = new ImageIcon(getClass().getResource("/taller/app/resources/FotoInicio.jpg"));
        imagenFondo = icon.getImage();

        // Configurar el botón "Adelante"
        ImageIcon iconOriginal = new ImageIcon(getClass().getResource("/taller/app/resources/Adelante.png"));
        Image imgBtn = iconOriginal.getImage().getScaledInstance(300, 120, Image.SCALE_SMOOTH);
        JButton btnAdelante = new JButton(new ImageIcon(imgBtn));

        // Posicionar el botón en la parte inferior centrada
        btnAdelante.setBounds(70, 570, 300, 120);
        btnAdelante.setContentAreaFilled(false); // Hacer el fondo transparente
        btnAdelante.setBorderPainted(false); // Quitar el borde
        btnAdelante.setFocusPainted(false); // Quitar la línea al seleccionarlo
        btnAdelante.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la ventana principal que contiene este panel
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Menu.this);
                if (frame != null) {
                    // Cambiar a la siguiente pantalla (VentanaLogin)
                    frame.setContentPane(new VentanaLogin());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        this.add(btnAdelante);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}
