package taller.app.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel {

    private Image imagenFondo;

    public Menu() {
        this.setLayout(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/taller/app/resources/FotoInicio.jpg"));
        imagenFondo = icon.getImage();

        ImageIcon iconOriginal = new ImageIcon(getClass().getResource("/taller/app/resources/Adelante.png"));
        Image imgBtn = iconOriginal.getImage().getScaledInstance(300, 120, Image.SCALE_SMOOTH);
        JButton btnAdelante = new JButton(new ImageIcon(imgBtn));

        // posicionar el botón
        btnAdelante.setBounds(82, 570, 300, 120);
        btnAdelante.setContentAreaFilled(false);
        btnAdelante.setBorderPainted(false);
        btnAdelante.setFocusPainted(false);
        btnAdelante.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // obtener la ventana principal que contiene este panel
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Menu.this);
                if (frame != null) {
                    // cambiar a la siguiente pantalla
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
        // dibujar la imagen de fondo
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}
