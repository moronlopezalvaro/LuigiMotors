package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;
import taller.app.model.Cliente;

public class DialogoEdicionPerfil extends JDialog {

    private JTextField txtNombre, txtDni, txtTelefono;
    private JPasswordField txtContrasena;
    private Cliente cliente;
    private boolean guardado = false;
    private String nombreOriginal;

    public DialogoEdicionPerfil(JFrame parent, Cliente cliente) {
        super(parent, "Editar Perfil", true);
        this.cliente = cliente;
        this.nombreOriginal = cliente.getNombre();

        setSize(400, 450);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Panel principal con degradado (estilo consistente)
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.decode("#F5F5F5"), 0, getHeight(), Color.decode("#D3DEED"));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Título
        JLabel lblTitulo = new JLabel("Modificar mis datos", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.decode("#1E3A5F"));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(4, 1, 10, 10));
        panelForm.setOpaque(false);

        txtNombre = createStyledTextField(cliente.getNombre(), "Nombre:");
        txtDni = createStyledTextField(cliente.getDni(), "DNI:");
        txtTelefono = createStyledTextField(cliente.getTelefono(), "Teléfono:");
        
        // Contraseña
        JPanel panelPass = new JPanel(new BorderLayout(5, 5));
        panelPass.setOpaque(false);
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial", Font.BOLD, 14));
        txtContrasena = new JPasswordField(cliente.getContrasenya());
        txtContrasena.setPreferredSize(new Dimension(0, 35));
        panelPass.add(lblPass, BorderLayout.NORTH);
        panelPass.add(txtContrasena, BorderLayout.CENTER);

        panelForm.add(createFieldPanel("Nombre:", txtNombre));
        panelForm.add(createFieldPanel("DNI:", txtDni));
        panelForm.add(createFieldPanel("Teléfono:", txtTelefono));
        panelForm.add(panelPass);

        mainPanel.add(panelForm, BorderLayout.CENTER);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);

        JButton btnAceptar = createStyledButton("Aceptar", Color.decode("#FF6B00"));
        JButton btnVolver = createStyledButton("Volver", Color.decode("#2C2C2C"));

        panelBotones.add(btnAceptar);
        panelBotones.add(btnVolver);

        mainPanel.add(panelBotones, BorderLayout.SOUTH);

        btnAceptar.addActionListener(e -> guardarCambios());
        btnVolver.addActionListener(e -> dispose());

        add(mainPanel);
    }

    private JPanel createFieldPanel(String labelText, JTextField textField) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setOpaque(false);
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        p.add(lbl, BorderLayout.NORTH);
        p.add(textField, BorderLayout.CENTER);
        return p;
    }

    private JTextField createStyledTextField(String text, String placeholder) {
        JTextField tf = new JTextField(text);
        tf.setPreferredSize(new Dimension(0, 35));
        tf.setFont(new Font("Arial", Font.PLAIN, 14));
        return tf;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? color.darker() : (getModel().isRollover() ? color.brighter() : color));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void guardarCambios() {
        String nombre = txtNombre.getText().trim();
        String dni = txtDni.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String pass = new String(txtContrasena.getPassword());

        if (nombre.isEmpty() || dni.isEmpty() || telefono.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        cliente.setNombre(nombre);
        cliente.setDni(dni);
        cliente.setTelefono(telefono);
        cliente.setContrasenya(pass);

        ConexionBBDD bd = new ConexionBBDD();
        if (bd.actualizarDatosCliente(cliente, nombreOriginal)) {
            JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            guardado = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar los datos en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isGuardado() {
        return guardado;
    }

    public Cliente getClienteActualizado() {
        return cliente;
    }
}
