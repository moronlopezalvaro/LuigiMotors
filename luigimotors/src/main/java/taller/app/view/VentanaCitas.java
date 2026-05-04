package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import taller.app.controller.ConexionBBDD;
import taller.app.model.Cita;
import taller.app.utils.UIUtils;

public class VentanaCitas extends JPanel {

    private String nombreCliente;
    private JPanel listadoCitasPanel;
    private JButton btnAnteriores, btnProximas;
    private Color colorNaranja = Color.decode("#FF6B00");
    private Color colorAzulOscuro = Color.decode("#1E3A5F");
    private Color colorGrisOscuro = Color.decode("#2C2C2C");

    public VentanaCitas(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());
        setOpaque(false);

        // Cabecera con menú unificada
        add(UIUtils.crearCabeceraConMenu("Luigi Motors", "Mis Citas:", nombreCliente, this), BorderLayout.NORTH);

        // Panel Central
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Botones de filtro
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelFiltros.setOpaque(false);

        btnAnteriores = createFilterButton("Citas anteriores");
        btnProximas = createFilterButton("Próximas citas");

        panelFiltros.add(btnAnteriores);
        panelFiltros.add(btnProximas);
        panelCentral.add(panelFiltros, BorderLayout.NORTH);

        // Listado de citas
        listadoCitasPanel = new JPanel();
        listadoCitasPanel.setLayout(new BoxLayout(listadoCitasPanel, BoxLayout.Y_AXIS));
        listadoCitasPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listadoCitasPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panelCentral.add(scroll, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // Botón Volver
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JButton btnVolver = createRoundedButton("Volver", colorGrisOscuro, Color.WHITE);
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);

        // Acciones
        btnAnteriores.addActionListener(e -> cargarCitas(true));
        btnProximas.addActionListener(e -> cargarCitas(false));
        btnVolver.addActionListener(e -> volver());

        // Cargar próximas citas por defecto
        cargarCitas(false);
    }

    private void cargarCitas(boolean anteriores) {
        // Actualizar estilo botones
        btnAnteriores.setBackground(anteriores ? colorNaranja : Color.WHITE);
        btnAnteriores.setForeground(anteriores ? Color.WHITE : colorAzulOscuro);
        btnProximas.setBackground(!anteriores ? colorNaranja : Color.WHITE);
        btnProximas.setForeground(!anteriores ? Color.WHITE : colorAzulOscuro);

        listadoCitasPanel.removeAll();
        ConexionBBDD bd = new ConexionBBDD();
        List<Cita> citas = anteriores ? bd.obtenerCitasAnteriores(nombreCliente) : bd.obtenerCitasProximas(nombreCliente);

        if (citas.isEmpty()) {
            JLabel lblVacio = new JLabel(anteriores ? "No tienes citas anteriores." : "No tienes próximas citas.");
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 16));
            lblVacio.setForeground(colorAzulOscuro);
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblVacio.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
            listadoCitasPanel.add(lblVacio);
        } else {
            for (Cita cita : citas) {
                listadoCitasPanel.add(crearTarjetaCita(cita, !anteriores));
                listadoCitasPanel.add(Box.createVerticalStrut(15));
            }
        }

        listadoCitasPanel.revalidate();
        listadoCitasPanel.repaint();
    }

    private JPanel crearTarjetaCita(Cita cita, boolean esProxima) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(esProxima ? colorNaranja : Color.decode("#CCCCCC"), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        card.setMaximumSize(new Dimension(800, 120));

        // Icono o Indicador lateral
        JPanel indicador = new JPanel();
        indicador.setPreferredSize(new Dimension(5, 0));
        indicador.setBackground(esProxima ? colorNaranja : Color.LIGHT_GRAY);
        card.add(indicador, BorderLayout.WEST);

        // Información principal
        JPanel info = new JPanel(new GridLayout(2, 1, 0, 5));
        info.setOpaque(false);

        JLabel lblFecha = new JLabel(cita.getFecha() + " - " + cita.getHora());
        lblFecha.setFont(new Font("Arial", Font.BOLD, 16));
        lblFecha.setForeground(colorAzulOscuro);

        JLabel lblVehiculo = new JLabel("Matrícula: " + cita.getMatricula());
        lblVehiculo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        info.add(lblFecha);
        info.add(lblVehiculo);
        card.add(info, BorderLayout.CENTER);

        // Descripción / Servicio
        JLabel lblDesc = new JLabel("<html><i>" + cita.getDescripcion() + "</i></html>");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDesc.setForeground(Color.DARK_GRAY);
        card.add(lblDesc, BorderLayout.SOUTH);

        return card;
    }


    private JButton createFilterButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
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
        UIUtils.pintarFondoDegradado(g, getWidth(), getHeight());
    }

    private void volver() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            ConexionBBDD bd = new ConexionBBDD();
            taller.app.model.Cliente c = bd.obtenerClientePorNombre(nombreCliente);
            
            if (c != null && "Administrador".equals(c.getRol())) {
                frame.setContentPane(new VentanaAdministrador(nombreCliente));
            } else {
                frame.setContentPane(new VentanaInicial(nombreCliente));
            }
            frame.revalidate();
            frame.repaint();
        }
    }
}
