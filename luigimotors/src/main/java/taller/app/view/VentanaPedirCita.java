package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;
import taller.app.utils.ValidationUtils;

// Esta pantalla muestra un formulario para que el cliente pueda pedir una cita.
public class VentanaPedirCita extends JPanel {

    private String nombreCliente;

    public VentanaPedirCita(String nombreCliente) {
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

        JLabel lblTitulo = new JLabel("Pedir Cita", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        add(panelTitulo, BorderLayout.NORTH);

        // formulario para pedir cita
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(true);
        panelFormulario.setBackground(Color.decode("#F5F5F5"));
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#2C2C2C"), 2),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // fecha en formato americano
        JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelFormulario.add(lblFecha, gbc);

        JTextField txtFecha = new JTextField(15);
        txtFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelFormulario.add(txtFecha, gbc);

        // hora
        JLabel lblHora = new JLabel("Hora:");
        lblHora.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(lblHora, gbc);

        String[] horas = { "09:00", "10:00", "11:00", "12:00", "13:00",
                "16:00", "17:00", "18:00" };
        JComboBox<String> comboHora = new JComboBox<>(horas);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelFormulario.add(comboHora, gbc);

        // matrícula
        JLabel lblMatricula = new JLabel("Matrícula del vehículo:");
        lblMatricula.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(lblMatricula, gbc);

        JTextField txtMatricula = new JTextField(15);
        txtMatricula.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelFormulario.add(txtMatricula, gbc);

        // descripción
        JLabel lblDescripcion = new JLabel("Descripción del problema:");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelFormulario.add(lblDescripcion, gbc);

        JTextArea txtDescripcion = new JTextArea(6, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelFormulario.add(scrollDescripcion, gbc);

        JPanel wrapperFormulario = new JPanel(new GridBagLayout());
        wrapperFormulario.setOpaque(false);
        wrapperFormulario.add(panelFormulario);
        add(wrapperFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JButton btnGuardar = createRoundedButton("Confirmar Cita", Color.decode("#FF6B00"), Color.WHITE);
        JButton btnVolver = createRoundedButton("Volver", Color.decode("#2C2C2C"), Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // confirmar cita
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // leer datos del formulario
                String fecha = txtFecha.getText().trim();
                String hora = (String) comboHora.getSelectedItem();
                String matricula = txtMatricula.getText().trim();
                String descripcion = txtDescripcion.getText().trim();

                // validar campos no vacíos
                if (fecha.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "Por favor, introduce la fecha en formato YYYY-MM-DD.",
                            "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // validar formato de fecha (YYYY-MM-DD)
                if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "El formato de la fecha debe ser YYYY-MM-DD.\nEjemplo: 2026-05-15",
                            "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // VALIDACIÓN DE FECHA PASADA
                if (!ValidationUtils.esFechaFutura(fecha)) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "No puedes pedir una cita para una fecha que ya ha pasado.",
                            "Fecha inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (matricula.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "Por favor, introduce la matrícula de tu vehículo.",
                            "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // VALIDACIÓN DE MATRÍCULA
                if (!ValidationUtils.esMatriculaValida(matricula)) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "El formato de la matrícula no es válido.\nEjemplo: 1234ABC",
                            "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "Por favor, describe el problema de tu vehículo.",
                            "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // comprobar disponibilidad
                ConexionBBDD bd = new ConexionBBDD();
                if (bd.citaOcupada(fecha, hora)) {
                    // día/hora ocupado -> mostrar JDialog
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "Lo sentimos, el día " + fecha + " a las " + hora +
                                    " ya está ocupado por otro cliente.\nPor favor, elige otra fecha u hora.",
                            "Cita no disponible", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // guardar la cita
                boolean exito = bd.guardarCita(fecha, hora, matricula, descripcion, nombreCliente);
                if (exito) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "¡Cita confirmada!\nFecha: " + fecha + "\nHora: " + hora +
                                    "\nMatrícula: " + matricula,
                            "Cita guardada", JOptionPane.INFORMATION_MESSAGE);
                    // volver a VentanaInicial
                    volverAInicio();
                } else {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                            "Error al guardar la cita. Inténtalo de nuevo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // volver a VentanaInicial
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAInicio();
            }
        });
    }

    // volver a la pantalla principal
    private void volverAInicio() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new VentanaInicial(nombreCliente));
            frame.revalidate();
            frame.repaint();
        }
    }

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
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));
        return btn;
    }
}
