package taller.app.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Dimension;

public class VentanaInicial extends JPanel {

    public VentanaInicial() {
        setLayout(new BorderLayout());

        // ===== TÍTULO SUPERIOR =====
        JLabel lblTitulo = new JLabel("Luigi Motors", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        // Margen superior, izquierdo, inferior, derecho
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        add(lblTitulo, BorderLayout.NORTH);

        // ===== PANEL DE BOTONES =====
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 0, 15));
        // Margen alrededor del panel de botones (arriba, izq, abajo, der)
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 40, 50));

        // Crear 5 botones
        for (int i = 1; i <= 5; i++) {
            JButton btn = new JButton("Botón " + i);
            btn.setFont(new Font("Arial", Font.PLAIN, 16));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(0, 60));
            btn.setFocusPainted(false);
            panelBotones.add(btn);
        }

        add(panelBotones, BorderLayout.CENTER);
    }
}
