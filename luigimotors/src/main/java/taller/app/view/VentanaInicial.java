package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;
import taller.app.model.Cita;
import java.util.List;

public class VentanaInicial extends JPanel {

    private String nombreCliente;

    public VentanaInicial(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(true);
        panelTitulo.setBackground(Color.decode("#1E3A5F"));
        panelTitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(15, 20, 10, 20),
                        BorderFactory.createLineBorder(Color.decode("#2C2C2C"), 2)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        JLabel lblTitulo = new JLabel("Luigi Motors", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        // mensaje de bienvenida personalizado con el nombre del cliente
        JLabel lblBienvenido = new JLabel("Bienvenido " + nombreCliente);
        lblBienvenido.setFont(new Font("Arial", Font.ITALIC, 14));
        lblBienvenido.setForeground(Color.decode("#F5F5F5")); // Gris claro
        lblBienvenido.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelTitulo.add(lblBienvenido, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);

        // panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        panelBotones.setOpaque(false);

        // colores de la paleta del proyecto
        Color colorNaranja = Color.decode("#FF6B00");
        Color colorAzulOscuro = Color.decode("#1E3A5F");
        Color colorGrisOscuro = Color.decode("#2C2C2C");

        // boton 1: pedir cita (color naranja, destacado)
        JButton btnPedirCita = createRoundedButton("Pedir cita", colorNaranja, Color.WHITE);
        panelBotones.add(btnPedirCita);

        // boton 2: Calcular gastos (color azul)
        JButton btnCalcularGastos = createRoundedButton("Calcular gastos", colorAzulOscuro, Color.WHITE);
        panelBotones.add(btnCalcularGastos);

        // boton 3: Salir y cerrar sesión (color gris)
        JButton btnSalir = createRoundedButton("Salir y cerrar sesión", colorGrisOscuro, Color.WHITE);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // boton 1: abre la pantalla para pedir cita
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

        // boton 2: calcular gastos
        btnCalcularGastos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexionBBDD bd = new ConexionBBDD();
                java.util.List<taller.app.model.Cita> citas = bd.obtenerCitasCliente(nombreCliente);

                if (citas.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(VentanaInicial.this,
                            "Actualmente no tienes citas registradas.\nFuncionalidad próximamente para nuevas reparaciones.",
                            "Calcular gastos",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaInicial.this);
                    if (frame != null) {
                        frame.setContentPane(new VentanaGastos(nombreCliente));
                        frame.revalidate();
                        frame.repaint();
                    }
                }
            }
        });

        // boton 3: cerrar sesion
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

    // dibuja el fondo degradado de la ventana
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = Color.decode("#F5F5F5");
        Color color2 = Color.decode("#D3DEED");
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
