package taller.app.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import taller.app.controller.ConexionBBDD;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class VentanaLogin extends JPanel {
    // Constructor
    public VentanaLogin() {
        // Layout principal
        setLayout(new BorderLayout());

        // Título
        JLabel lblTitulo = new JLabel("Bienvenido a Luigi Motors");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña Iniciar Sesión
        JPanel panelLogin = crearPanelLogin();
        tabbedPane.addTab("Iniciar Sesión", panelLogin);

        // Pestaña Registrarse
        JPanel panelRegistro = crearPanelRegistro();
        tabbedPane.addTab("Registrarse", panelRegistro);

        // Añadir pestañas
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Panel iniciar sesión
    private JPanel crearPanelLogin() {
        // Layout en cuadrícula
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtNombre, gbc);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblContrasena, gbc);

        JPasswordField txtContrasena = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtContrasena, gbc);

        // Botón
        JButton btnEntrar = new JButton("Entrar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnEntrar, gbc);

        // Acción del botón
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Leer campos
                String nombre = txtNombre.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());

                // Comprobar vacíos
                if (nombre.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Por favor, rellene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Conectar a BD
                ConexionBBDD bd = new ConexionBBDD();
                boolean exito = bd.validarLoginCliente(nombre, contrasena);

                if (exito) {
                    avanzarPantallaBienvenida();
                } else {
                    JOptionPane.showMessageDialog(panel, "Nombre o contraseña incorrectos.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    // Panel registro
    private JPanel crearPanelRegistro() {
        // Layout en cuadrícula
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // DNI
        JLabel lblDni = new JLabel("DNI:");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblDni, gbc);

        JTextField txtDni = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtDni, gbc);

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtNombre, gbc);

        // Teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblTelefono, gbc);

        JTextField txtTelefono = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtTelefono, gbc);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblContrasena, gbc);

        JPasswordField txtContrasena = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(txtContrasena, gbc);

        // Botón
        JButton btnRegistrar = new JButton("Registrar");
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);

        // Acción del botón
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Leer datos
                String dni = txtDni.getText().trim();
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());

                // Comprobar vacíos
                if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Por favor, rellene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardar en BD
                ConexionBBDD bd = new ConexionBBDD();
                boolean exito = bd.registrarNuevoCliente(dni, nombre, telefono, contrasena);

                if (exito) {
                    JOptionPane.showMessageDialog(panel, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    avanzarPantallaBienvenida();
                } else {
                    JOptionPane.showMessageDialog(panel, "Error al registrar el usuario en la BD.\nAsegúrate de que el DNI no esté duplicado.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    // Cambiar a pantalla bienvenida
    private void avanzarPantallaBienvenida() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
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
    }
}
