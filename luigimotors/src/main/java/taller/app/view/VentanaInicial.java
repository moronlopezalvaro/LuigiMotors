package taller.app.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicial extends JPanel {

    private String nombreCliente;

    public VentanaInicial(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());

        // ===== TÍTULO CON RECUADRO =====
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(true);
        panelTitulo.setBackground(new Color(240, 240, 240));
        panelTitulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 20, 10, 20),   // margen exterior
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2) // recuadro
            ),
            BorderFactory.createEmptyBorder(15, 20, 5, 20)        // padding interior (ajustado en la parte inferior)
        ));

        JLabel lblTitulo = new JLabel("Luigi Motors", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JLabel lblBienvenido = new JLabel("Bienvenido " + nombreCliente);
        lblBienvenido.setFont(new Font("Arial", Font.ITALIC, 14));
        lblBienvenido.setForeground(Color.DARK_GRAY);
        panelTitulo.add(lblBienvenido, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);

        // ===== PANEL DE BOTONES =====
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Botón 1: Pedir cita
        JButton btnPedirCita = new JButton("Pedir cita");
        btnPedirCita.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPedirCita.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPedirCita.setPreferredSize(new Dimension(0, 60));
        btnPedirCita.setFocusPainted(false);
        panelBotones.add(btnPedirCita);

        // Botón 2: Calcular gastos
        JButton btnCalcularGastos = new JButton("Calcular gastos");
        btnCalcularGastos.setFont(new Font("Arial", Font.PLAIN, 16));
        btnCalcularGastos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCalcularGastos.setPreferredSize(new Dimension(0, 60));
        btnCalcularGastos.setFocusPainted(false);
        panelBotones.add(btnCalcularGastos);

        // Botón 3: Salir y cerrar sesión
        JButton btnSalir = new JButton("Salir y cerrar sesión");
        btnSalir.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setPreferredSize(new Dimension(0, 60));
        btnSalir.setFocusPainted(false);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // ===== ACCIONES =====

        // Botón 1: Pedir cita -> abrir VentanaPedirCita
        btnPedirCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaInicial.this);
                if (frame != null) {
                    frame.setContentPane(new VentanaPedirCita(nombreCliente));
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        // Botón 2: Calcular gastos (pendiente de implementar)
        btnCalcularGastos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JOptionPane.showMessageDialog(VentanaInicial.this,
                    "Funcionalidad próximamente.", "Calcular gastos",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Botón 3: Salir y cerrar sesión -> volver al login
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaInicial.this);
                if (frame != null) {
                    frame.setContentPane(new VentanaLogin());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }
}
