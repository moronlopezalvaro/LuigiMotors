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
        // Contenedor principal con imagen de fondo
        JPanel contenedor = new JPanel(new GridBagLayout()) {
            private java.awt.Image bgImage;
            {
                try {
                    java.net.URL imgUrl = getClass().getResource("/taller/app/resources/FotoLogIn.jpg");
                    if (imgUrl != null) {
                        bgImage = new javax.swing.ImageIcon(imgUrl).getImage();
                    }
                } catch (Exception ex) {
                    System.err.println("No se pudo cargar la imagen de login");
                }
            }

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Layout en cuadrícula para el formulario (fondo transparente)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(150, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtNombre, gbc);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(java.awt.Color.WHITE);
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
                    JOptionPane.showMessageDialog(contenedor, "Por favor, rellene todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Conectar a BD
                ConexionBBDD bd = new ConexionBBDD();
                boolean exito = bd.validarLoginCliente(nombre, contrasena);

                if (exito) {
                    avanzarPantallaBienvenida(nombre);
                } else {
                    JOptionPane.showMessageDialog(contenedor, "Nombre o contraseña incorrectos.",
                            "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        contenedor.add(panel);
        return contenedor;
    }

    // Panel registro
    private JPanel crearPanelRegistro() {
        // Contenedor principal con imagen de fondo
        JPanel contenedor = new JPanel(new GridBagLayout()) {
            private java.awt.Image bgImage;
            {
                try {
                    java.net.URL imgUrl = getClass().getResource("/taller/app/resources/FotoLogIn.jpg");
                    if (imgUrl != null) {
                        bgImage = new javax.swing.ImageIcon(imgUrl).getImage();
                    }
                } catch (Exception ex) {
                    System.err.println("No se pudo cargar la imagen de registro");
                }
            }

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Layout en cuadrícula para el formulario (fondo transparente)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(150, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // DNI
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblDni, gbc);

        JTextField txtDni = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtDni, gbc);

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtNombre, gbc);

        // Teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblTelefono, gbc);

        JTextField txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtTelefono, gbc);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblContrasena, gbc);

        JPasswordField txtContrasena = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtContrasena, gbc);

        // Botón
        JButton btnRegistrar = new JButton("Registrar");
        gbc.gridx = 0;
        gbc.gridy = 4;
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
                    JOptionPane.showMessageDialog(contenedor, "Por favor, rellene todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardar en BD
                ConexionBBDD bd = new ConexionBBDD();
                boolean exito = bd.registrarNuevoCliente(dni, nombre, telefono, contrasena);

                if (exito) {
                    JOptionPane.showMessageDialog(contenedor, "Usuario registrado correctamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    avanzarPantallaBienvenida(nombre);
                } else {
                    JOptionPane.showMessageDialog(contenedor,
                            "Error al registrar el usuario en la BD.\nAsegúrate de que el DNI no esté duplicado.",
                            "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        contenedor.add(panel);
        return contenedor;
    }

    // Cambiar a pantalla principal (VentanaInicial)
    private void avanzarPantallaBienvenida(String nombreCliente) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new VentanaInicial(nombreCliente));
            frame.revalidate();
            frame.repaint();
        }
    }
}
