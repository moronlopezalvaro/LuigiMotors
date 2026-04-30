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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicial extends JPanel {

    private String nombreCliente;

    public VentanaInicial(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());
        setOpaque(false); // Para que se dibuje el fondo personalizado

        // ===== TÍTULO CON RECUADRO =====
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(true);
        panelTitulo.setBackground(Color.decode("#1E3A5F")); // Azul oscuro (principal)
        panelTitulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 20, 10, 20),   // margen exterior
                BorderFactory.createLineBorder(Color.decode("#2C2C2C"), 2) // recuadro gris oscuro
            ),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)        // padding interior
        ));

        JLabel lblTitulo = new JLabel("Luigi Motors", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE); // Texto blanco sobre header azul
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JLabel lblBienvenido = new JLabel("Bienvenido " + nombreCliente);
        lblBienvenido.setFont(new Font("Arial", Font.ITALIC, 14));
        lblBienvenido.setForeground(Color.decode("#F5F5F5")); // Blanco/gris claro
        lblBienvenido.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Más separado del título
        panelTitulo.add(lblBienvenido, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);

        // ===== PANEL DE BOTONES =====
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        panelBotones.setOpaque(false); // Transparente para ver el fondo

        // Colores de la paleta
        Color colorNaranja = Color.decode("#FF6B00");
        Color colorAzulOscuro = Color.decode("#1E3A5F");
        Color colorGrisOscuro = Color.decode("#2C2C2C");

        // Botón 1: Pedir cita (Naranja - Acento principal)
        JButton btnPedirCita = createRoundedButton("Pedir cita", colorNaranja, Color.WHITE);
        panelBotones.add(btnPedirCita);

        // Botón 2: Calcular gastos (Azul oscuro - Secundario)
        JButton btnCalcularGastos = createRoundedButton("Calcular gastos", colorAzulOscuro, Color.WHITE);
        panelBotones.add(btnCalcularGastos);

        // Botón 3: Salir y cerrar sesión (Gris oscuro - Acción de salida)
        JButton btnSalir = createRoundedButton("Salir y cerrar sesión", colorGrisOscuro, Color.WHITE);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        // Fondo degradado claro acorde a la paleta
        Color color1 = Color.decode("#F5F5F5"); // Blanco / gris claro
        Color color2 = Color.decode("#D3DEED"); // Azul muy claro
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
    }

    private JButton createRoundedButton(String text, Color bgColor, Color fgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 60));
        return btn;
    }
}
