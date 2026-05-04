package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;
import taller.app.utils.ValidationUtils;

public class VentanaLogin extends JPanel {

    public VentanaLogin() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // iniciar sesión
        JPanel panelLogin = crearPanelLogin();
        tabbedPane.addTab("Iniciar Sesión", panelLogin);

        // registrarse como nuevo usuario
        JPanel panelRegistro = crearPanelRegistro();
        tabbedPane.addTab("Registrarse", panelRegistro);

        // añadir las pestañas al centro del layout
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelLogin() {

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
                // dibujar la imagen escalada al tamaño del panel
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // panel del formulario
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(150, 20, 20, 20));

        // configuración del GridBagLayout para colocar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // campo nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtNombre, gbc);

        // campo contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblContrasena, gbc);

        JPasswordField txtContrasena = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtContrasena, gbc);

        // botón para entrar
        JButton btnEntrar = new JButton("Entrar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnEntrar, gbc);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // leer lo que el usuario ha escrito
                String nombre = txtNombre.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());

                // validar que ningún campo esté vacío
                if (nombre.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(contenedor, "Por favor, rellene todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // consultar la BD y obtener el rol del usuario
                ConexionBBDD bd = new ConexionBBDD();
                String rol = bd.validarLoginCliente(nombre, contrasena);

                if (rol != null) {
                    // si el login fue correcto, redirigir según el rol
                    if ("Administrador".equals(rol)) {
                        // rol administrador: abrir el panel de administración
                        avanzarPantallaAdmin(nombre);
                    } else {
                        // rol cliente: abrir la pantalla normal del cliente
                        avanzarPantallaBienvenida(nombre);
                    }
                } else {
                    // si el rol es null, las credenciales son incorrectas
                    JOptionPane.showMessageDialog(contenedor, "Nombre o contraseña incorrectos.",
                            "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contenedor.add(panel);
        return contenedor;
    }

    // panel de la pestaña "registrarse"
    private JPanel crearPanelRegistro() {
        // contenedor con imagen de fondo igual que el de login
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

        // panel del formulario
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(150, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // campo DNI
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblDni, gbc);

        JTextField txtDni = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtDni, gbc);

        // campo Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtNombre, gbc);

        // campo Teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblTelefono, gbc);

        JTextField txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtTelefono, gbc);

        // campo Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblContrasena, gbc);

        JPasswordField txtContrasena = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtContrasena, gbc);

        // botón para registrar
        JButton btnRegistrar = new JButton("Registrar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // leer todos los campos del formulario
                String dni = txtDni.getText().trim();
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());

                // comprobar que ningún campo esté vacío
                if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(contenedor, "Por favor, rellene todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // VALIDACIONES EXTRAS
                if (!ValidationUtils.esDniValido(dni)) {
                    JOptionPane.showMessageDialog(contenedor, "El DNI no tiene un formato válido (8 números y 1 letra).", "Formato Incorrecto", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!ValidationUtils.esTelefonoValido(telefono)) {
                    JOptionPane.showMessageDialog(contenedor, "El teléfono debe tener exactamente 9 dígitos.", "Formato Incorrecto", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (contrasena.length() < 4) {
                    JOptionPane.showMessageDialog(contenedor, "La contraseña debe tener al menos 4 caracteres.", "Seguridad", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // guardar el nuevo cliente en la BD siempre con rol "Cliente"
                ConexionBBDD bd = new ConexionBBDD();
                boolean exito = bd.registrarNuevoCliente(dni, nombre, telefono, contrasena);

                if (exito) {
                    JOptionPane.showMessageDialog(contenedor, "Usuario registrado correctamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    // ir directamente a la pantalla de cliente
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

    // cambia el contenido del JFrame para mostrar el panel de administración
    private void avanzarPantallaAdmin(String nombreAdmin) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new VentanaAdministrador(nombreAdmin));
            frame.revalidate();
            frame.repaint();
        }
    }

    // cambia el contenido del JFrame para mostrar la pantalla principal del cliente
    private void avanzarPantallaBienvenida(String nombreCliente) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new VentanaInicial(nombreCliente));
            frame.revalidate();
            frame.repaint();
        }
    }
}
