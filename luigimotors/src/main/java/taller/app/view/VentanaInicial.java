package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;
import taller.app.model.Cita;
import java.util.List;

// Pantalla principal que ve el cliente después de iniciar sesión
public class VentanaInicial extends JPanel {

    private String nombreCliente; // Guardamos el nombre para mostrarlo y pasarlo a otras ventanas

    public VentanaInicial(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());
        setOpaque(false); // Para que se dibuje el fondo degradado personalizado

        // ===== ENCABEZADO CON EL TÍTULO =====
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(true);
        panelTitulo.setBackground(Color.decode("#1E3A5F")); // Azul oscuro corporativo
        panelTitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(15, 20, 10, 20), // margen exterior
                        BorderFactory.createLineBorder(Color.decode("#2C2C2C"), 2) // borde gris oscuro
                ),
                BorderFactory.createEmptyBorder(15, 20, 15, 20) // padding interior
        ));

        // Título de la aplicación
        JLabel lblTitulo = new JLabel("Luigi Motors", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        // Mensaje de bienvenida personalizado con el nombre del cliente
        JLabel lblBienvenido = new JLabel("Bienvenido " + nombreCliente);
        lblBienvenido.setFont(new Font("Arial", Font.ITALIC, 14));
        lblBienvenido.setForeground(Color.decode("#F5F5F5")); // Gris claro
        lblBienvenido.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelTitulo.add(lblBienvenido, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);

        // ===== PANEL DE BOTONES =====
        // GridLayout de 3 filas, 1 columna, con 20px entre filas
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        panelBotones.setOpaque(false); // Transparente para ver el fondo degradado

        // Colores de la paleta del proyecto
        Color colorNaranja = Color.decode("#FF6B00"); // Naranja: acción principal
        Color colorAzulOscuro = Color.decode("#1E3A5F"); // Azul: secundario
        Color colorGrisOscuro = Color.decode("#2C2C2C"); // Gris: salir

        // Botón 1: Pedir cita (color naranja, destacado)
        JButton btnPedirCita = createRoundedButton("Pedir cita", colorNaranja, Color.WHITE);
        panelBotones.add(btnPedirCita);

        // Botón 2: Calcular gastos (color azul)
        JButton btnCalcularGastos = createRoundedButton("Calcular gastos", colorAzulOscuro, Color.WHITE);
        panelBotones.add(btnCalcularGastos);

        // Botón 3: Salir y cerrar sesión (color gris)
        JButton btnSalir = createRoundedButton("Salir y cerrar sesión", colorGrisOscuro, Color.WHITE);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // ===== ACCIONES DE LOS BOTONES =====

        // Botón 1: abre la pantalla para pedir cita
        btnPedirCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaInicial.this);
                if (frame != null) {
                    // Cambiar la pantalla a VentanaPedirCita pasando el nombre del cliente
                    frame.setContentPane(new VentanaPedirCita(nombreCliente));
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        // Botón 2: Calcular gastos
        btnCalcularGastos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexionBBDD bd = new ConexionBBDD();
                java.util.List<taller.app.model.Cita> citas = bd.obtenerCitasCliente(nombreCliente);

                if (citas.isEmpty()) {
                    // Si no tiene citas, mostramos el mensaje original "próximamente" o similar
                    javax.swing.JOptionPane.showMessageDialog(VentanaInicial.this,
                            "Actualmente no tienes citas registradas.\nFuncionalidad próximamente para nuevas reparaciones.", 
                            "Calcular gastos",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Si tiene citas, abrimos la pantalla de gastos
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaInicial.this);
                    if (frame != null) {
                        frame.setContentPane(new VentanaGastos(nombreCliente));
                        frame.revalidate();
                        frame.repaint();
                    }
                }
            }
        });

        // Botón 3: volver a la pantalla de login (cerrar sesión)
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

    // Dibuja el fondo degradado de la ventana (de gris claro a azul suave)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = Color.decode("#F5F5F5"); // Gris claro arriba
        Color color2 = Color.decode("#D3DEED"); // Azul muy claro abajo
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
    }

    // Crea un botón con esquinas redondeadas y efecto hover (cambia de color al
    // pasar el ratón)
    private JButton createRoundedButton(String text, Color bgColor, Color fgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Color cambia según el estado del botón
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker()); // Al hacer clic: más oscuro
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter()); // Al pasar el ratón: más claro
                } else {
                    g2.setColor(bgColor); // Estado normal
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Bordes redondeados
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false); // Desactivar el fondo por defecto de Swing
        btn.setFocusPainted(false); // Sin borde de foco al usar el teclado
        btn.setBorderPainted(false); // Sin borde estándar
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mano
        btn.setPreferredSize(new Dimension(0, 60));
        return btn;
    }
}
