package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;
import taller.app.model.Cliente;

public class VentanaPerfil extends JPanel {

    private String nombreCliente;
    private Cliente clienteActual;
    private JLabel lblValNombre, lblValDni, lblValTelefono;

    public VentanaPerfil(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());
        setOpaque(false);

        // Cabecera (estilo consistente con VentanaInicial)
        JPanel panelTitulo = crearCabecera();
        add(panelTitulo, BorderLayout.NORTH);

        // Contenedor Central
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Cargar datos
        cargarDatos();

        // Panel de información (estilo tarjeta)
        JPanel card = new JPanel(new GridLayout(3, 2, 10, 20));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#CCCCCC"), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));

        card.add(createLabel("Nombre:"));
        lblValNombre = createValueLabel(clienteActual.getNombre());
        card.add(lblValNombre);

        card.add(createLabel("DNI:"));
        lblValDni = createValueLabel(clienteActual.getDni());
        card.add(lblValDni);

        card.add(createLabel("Teléfono:"));
        lblValTelefono = createValueLabel(clienteActual.getTelefono());
        card.add(lblValTelefono);

        panelCentral.add(card);
        add(panelCentral, BorderLayout.CENTER);

        // Panel de botones inferior
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelBotones.setOpaque(false);

        JButton btnCambiar = createRoundedButton("Cambiar datos", Color.decode("#FF6B00"), Color.WHITE);
        JButton btnVolver = createRoundedButton("Volver", Color.decode("#2C2C2C"), Color.WHITE);

        panelBotones.add(btnCambiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnVolver.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                frame.setContentPane(new VentanaInicial(this.nombreCliente));
                frame.revalidate();
                frame.repaint();
            }
        });

        btnCambiar.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            DialogoEdicionPerfil dialogo = new DialogoEdicionPerfil(frame, clienteActual);
            dialogo.setVisible(true);

            if (dialogo.isGuardado()) {
                clienteActual = dialogo.getClienteActualizado();
                this.nombreCliente = clienteActual.getNombre();
                actualizarUI();
            }
        });
    }

    private void cargarDatos() {
        ConexionBBDD bd = new ConexionBBDD();
        clienteActual = bd.obtenerClientePorNombre(nombreCliente);
        if (clienteActual == null) {
            // Fallback por si algo falla
            clienteActual = new Cliente(0, "???", nombreCliente, "???", "", "Cliente");
        }
    }

    private void actualizarUI() {
        lblValNombre.setText(clienteActual.getNombre());
        lblValDni.setText(clienteActual.getDni());
        lblValTelefono.setText(clienteActual.getTelefono());
        
        // Actualizar el mensaje de bienvenida en la cabecera
        // Tendríamos que refrescar toda la pantalla para que la cabecera cambie el nombre si el usuario lo editó
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new VentanaPerfil(this.nombreCliente));
            frame.revalidate();
            frame.repaint();
        }
    }

    private JPanel crearCabecera() {
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

        JLabel lblSub = new JLabel("Mi Perfil - Gestión de Datos", JLabel.CENTER);
        lblSub.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSub.setForeground(Color.decode("#F5F5F5"));
        lblSub.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelTitulo.add(lblSub, BorderLayout.SOUTH);

        return panelTitulo;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setForeground(Color.decode("#1E3A5F"));
        return lbl;
    }

    private JLabel createValueLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        lbl.setForeground(Color.BLACK);
        return lbl;
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
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 50));
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#F5F5F5"), 0, getHeight(), Color.decode("#D3DEED"));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
