package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;
import taller.app.controller.ConexionBBDD;
import taller.app.model.Cita;

public class VentanaGastos extends JPanel {

    private String nombreCliente;
    private DefaultListModel<Cita> listModel;
    private JList<Cita> listCitas;

    public VentanaGastos(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(true);
        panelTitulo.setBackground(Color.decode("#1E3A5F"));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Mis Presupuestos", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        add(panelTitulo, BorderLayout.NORTH);

        // lista de citas
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        listModel = new DefaultListModel<>();
        cargarCitas();

        listCitas = new JList<>(listModel);
        listCitas.setFont(new Font("Arial", Font.PLAIN, 16));
        listCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCitas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                if (value instanceof Cita) {
                    Cita cita = (Cita) value;
                    label.setText("Cita: " + cita.getFecha() + " - " + cita.getMatricula());
                    label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                }
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(listCitas);
        panelCentral.add(new JLabel("Selecciona una cita para ver el presupuesto:"), BorderLayout.NORTH);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        // botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JButton btnVerTicket = createRoundedButton("Ver Presupuesto", Color.decode("#FF6B00"), Color.WHITE);
        JButton btnVolver = createRoundedButton("Volver", Color.decode("#2C2C2C"), Color.WHITE);

        panelBotones.add(btnVerTicket);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // ver ticket
        btnVerTicket.addActionListener(e -> {
            Cita seleccionada = listCitas.getSelectedValue();
            if (seleccionada == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona una cita de la lista.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            mostrarTicket(seleccionada);
        });

        // volver
        btnVolver.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                frame.setContentPane(new VentanaInicial(nombreCliente));
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    private void cargarCitas() {
        ConexionBBDD bd = new ConexionBBDD();
        List<Cita> citas = bd.obtenerCitasCliente(nombreCliente);
        for (Cita c : citas) {
            listModel.addElement(c);
        }
    }

    private void mostrarTicket(Cita cita) {
        ConexionBBDD bd = new ConexionBBDD();
        String[] datosPresupuesto = bd.obtenerPresupuesto(cita.getIdCita());

        if (datosPresupuesto == null) {
            // generar presupuesto usando el catálogo real
            List<String[]> matches = bd.obtenerPreciosCatalogo(cita.getDescripcion());

            String desglose;
            double manoObra;
            double total;

            if (!matches.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                double subtotalProductos = 0;
                double maxManoObra = 0;

                // extraer cantidad de la descripción
                int cantidad = 1;
                java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\b([1-9])\\b");
                java.util.regex.Matcher m = p.matcher(cita.getDescripcion());
                if (m.find()) {
                    cantidad = Integer.parseInt(m.group(1));
                }

                for (String[] match : matches) {
                    double pTotal = Double.parseDouble(match[1]);
                    double mObraBase = Double.parseDouble(match[2]);
                    double precioProductoUnitario = pTotal - mObraBase;

                    // si la descripción menciona una cantidad, multiplicamos
                    double precioProductoTotal = precioProductoUnitario * cantidad;
                    double manoObraTotal = mObraBase * cantidad;

                    String nombreConcepto = match[0];
                    if (cantidad > 1) {
                        nombreConcepto += " (x" + cantidad + ")";
                    }

                    sb.append(nombreConcepto).append(": ").append(String.format("%.2f", precioProductoTotal))
                            .append("€\n");
                    subtotalProductos += precioProductoTotal;

                    // mano de obra proporcional a la cantidad
                    maxManoObra += manoObraTotal;
                }

                desglose = sb.toString();
                manoObra = maxManoObra;
                total = subtotalProductos + manoObra;
            } else {
                // Fallback si no hay coincidencias exactas
                desglose = "Revisión y diagnóstico general: 40.00€\n";
                manoObra = 40.0;
                total = 80.0;
            }

            bd.guardarPresupuesto(cita.getIdCita(), desglose, manoObra, total);
            datosPresupuesto = new String[] { desglose, String.valueOf(manoObra), String.valueOf(total) };
        }

        // mostrar el JDialog tipo ticket
        DialogoTicket ticket = new DialogoTicket((JFrame) SwingUtilities.getWindowAncestor(this), cita,
                datosPresupuesto);
        ticket.setVisible(true);
    }

    private JButton createRoundedButton(String text, Color bgColor, Color fgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed())
                    g2.setColor(bgColor.darker());
                else if (getModel().isRollover())
                    g2.setColor(bgColor.brighter());
                else
                    g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(fgColor);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 45));
        return btn;
    }

    // clase interna para el diálogo del ticket
    class DialogoTicket extends JDialog {
        public DialogoTicket(Frame owner, Cita cita, String[] presupuesto) {
            super(owner, "Presupuesto Estimado", true);
            setLayout(new BorderLayout());
            setSize(350, 500);
            setLocationRelativeTo(owner);

            JPanel panelTicket = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            panelTicket.setLayout(new BoxLayout(panelTicket, BoxLayout.Y_AXIS));
            panelTicket.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

            Font ticketFont = new Font("Monospaced", Font.PLAIN, 12);
            Font titleFont = new Font("Monospaced", Font.BOLD, 16);

            JLabel lblTaller = new JLabel("LUIGI MOTORS S.L.");
            lblTaller.setFont(titleFont);
            lblTaller.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblSeparador1 = new JLabel("--------------------------");
            lblSeparador1.setAlignmentX(Component.CENTER_ALIGNMENT);

            JTextArea txtInfo = new JTextArea();
            txtInfo.setFont(ticketFont);
            txtInfo.setEditable(false);
            txtInfo.setOpaque(false);
            txtInfo.setText(
                    "FECHA: " + cita.getFecha() + "\n" +
                            "MATRICULA: " + cita.getMatricula() + "\n" +
                            "CLIENTE: " + nombreCliente + "\n\n" +
                            "DESCRIPCION:\n" + cita.getDescripcion() + "\n" +
                            "--------------------------\n" +
                            "CONCEPTOS:\n" + presupuesto[0] +
                            "MANO DE OBRA: " + String.format("%.2f", Double.parseDouble(presupuesto[1])) + "€\n" +
                            "--------------------------\n\n" +
                            "TOTAL ESTIMADO: " + String.format("%.2f", Double.parseDouble(presupuesto[2])) + "€");

            panelTicket.add(lblTaller);
            panelTicket.add(Box.createVerticalStrut(10));
            panelTicket.add(lblSeparador1);
            panelTicket.add(Box.createVerticalStrut(10));
            panelTicket.add(txtInfo);

            add(new JScrollPane(panelTicket), BorderLayout.CENTER);

            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> dispose());
            add(btnCerrar, BorderLayout.SOUTH);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth(), h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#F5F5F5"), 0, h, Color.decode("#D3DEED"));
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
    }
}
