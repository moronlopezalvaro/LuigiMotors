package taller.app.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import taller.app.controller.ConexionBBDD;

public class VentanaPedirCita extends JPanel {

    private String nombreCliente;

    public VentanaPedirCita(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());

        // ===== TÍTULO =====
        JLabel lblTitulo = new JLabel("Pedir Cita", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY),
            BorderFactory.createEmptyBorder(20, 20, 15, 20)
        ));
        add(lblTitulo, BorderLayout.NORTH);

        // ===== FORMULARIO CENTRAL =====
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Fecha (formato americano: YYYY-MM-DD) ---
        JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelFormulario.add(lblFecha, gbc);

        JTextField txtFecha = new JTextField(15);
        txtFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        panelFormulario.add(txtFecha, gbc);

        // --- Hora ---
        JLabel lblHora = new JLabel("Hora:");
        lblHora.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(lblHora, gbc);

        String[] horas = {"09:00", "10:00", "11:00", "12:00", "13:00",
                          "16:00", "17:00", "18:00"};
        JComboBox<String> comboHora = new JComboBox<>(horas);
        gbc.gridx = 1; gbc.gridy = 1;
        panelFormulario.add(comboHora, gbc);

        // --- Matrícula ---
        JLabel lblMatricula = new JLabel("Matrícula del vehículo:");
        lblMatricula.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(lblMatricula, gbc);

        JTextField txtMatricula = new JTextField(15);
        txtMatricula.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 2;
        panelFormulario.add(txtMatricula, gbc);

        // --- Descripción ---
        JLabel lblDescripcion = new JLabel("Descripción del problema:");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelFormulario.add(lblDescripcion, gbc);

        JTextArea txtDescripcion = new JTextArea(6, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelFormulario.add(scrollDescripcion, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // ===== BOTONES INFERIORES =====
        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JButton btnGuardar = new JButton("Confirmar Cita");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setFocusPainted(false);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // ===== ACCIÓN: CONFIRMAR CITA =====
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Leer datos del formulario
                String fecha = txtFecha.getText().trim();
                String hora = (String) comboHora.getSelectedItem();
                String matricula = txtMatricula.getText().trim();
                String descripcion = txtDescripcion.getText().trim();

                // Validar campos no vacíos
                if (fecha.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                        "Por favor, introduce la fecha en formato YYYY-MM-DD.",
                        "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validar formato de fecha (YYYY-MM-DD)
                if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                        "El formato de la fecha debe ser YYYY-MM-DD.\nEjemplo: 2026-05-15",
                        "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (matricula.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                        "Por favor, introduce la matrícula de tu vehículo.",
                        "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                        "Por favor, describe el problema de tu vehículo.",
                        "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Comprobar disponibilidad
                ConexionBBDD bd = new ConexionBBDD();
                if (bd.citaOcupada(fecha, hora)) {
                    // Día/hora ocupado -> mostrar JDialog
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                        "Lo sentimos, el día " + fecha + " a las " + hora +
                        " ya está ocupado por otro cliente.\nPor favor, elige otra fecha u hora.",
                        "Cita no disponible", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardar la cita
                boolean exito = bd.guardarCita(fecha, hora, matricula, descripcion, nombreCliente);
                if (exito) {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                        "¡Cita confirmada!\nFecha: " + fecha + "\nHora: " + hora +
                        "\nMatrícula: " + matricula,
                        "Cita guardada", JOptionPane.INFORMATION_MESSAGE);
                    // Volver a VentanaInicial
                    volverAInicio();
                } else {
                    JOptionPane.showMessageDialog(VentanaPedirCita.this,
                        "Error al guardar la cita. Inténtalo de nuevo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ===== ACCIÓN: VOLVER =====
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAInicio();
            }
        });
    }

    // Volver a la pantalla principal
    private void volverAInicio() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new VentanaInicial(nombreCliente));
            frame.revalidate();
            frame.repaint();
        }
    }
}
