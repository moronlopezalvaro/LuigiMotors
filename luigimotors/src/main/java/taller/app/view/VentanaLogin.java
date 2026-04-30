package taller.app.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import taller.app.controller.ConexionBBDD;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class VentanaLogin extends JPanel {
    public VentanaLogin() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Pantalla de Login / Registro");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        JLabel lblCorreo = new JLabel("Correo electrónico:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblCorreo, gbc);

        JTextField txtCorreo = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtCorreo, gbc);

        JLabel lblContrasena = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblContrasena, gbc);

        JPasswordField txtContrasena = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtContrasena, gbc);

        JButton btnGuardar = new JButton("Guardar / Entrar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = txtCorreo.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());

                if (correo.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaLogin.this, "Por favor, rellene todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ConexionBBDD bd = new ConexionBBDD();
                boolean exito = bd.registrarUsuario(correo, contrasena);

                if (exito) {
                    JOptionPane.showMessageDialog(VentanaLogin.this, "Usuario guardado correctamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Avanzar a la siguiente pantalla (pantalla temporal de bienvenida)
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaLogin.this);
                    if (frame != null) {
                        JPanel panelPrincipal = new JPanel();
                        panelPrincipal.setLayout(new GridBagLayout());
                        JLabel lblBienvenido = new JLabel("¡Bienvenido a Luigi Motors!");
                        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 24));
                        panelPrincipal.add(lblBienvenido);

                        frame.setContentPane(panelPrincipal);
                        frame.revalidate();
                        frame.repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaLogin.this,
                            "Error al guardar el usuario en la BD.\nAsegúrate de que la tabla 'usuarios' existe y tienes la BD encendida.",
                            "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
