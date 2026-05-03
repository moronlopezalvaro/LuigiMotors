package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;

// Pantalla de inicio de sesión y registro del usuario
public class VentanaLogin extends JPanel {

    // Constructor: monta las dos pestañas (login y registro)
    public VentanaLogin() {
        // Layout principal para que el JTabbedPane ocupe toda la ventana
        setLayout(new BorderLayout());

        // Crear el panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña 1: iniciar sesión
        JPanel panelLogin = crearPanelLogin();
        tabbedPane.addTab("Iniciar Sesión", panelLogin);

        // Pestaña 2: registrarse como nuevo usuario
        JPanel panelRegistro = crearPanelRegistro();
        tabbedPane.addTab("Registrarse", panelRegistro);

        // Añadir las pestañas al centro del layout
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Crea el panel de la pestaña "Iniciar Sesión" con fondo de imagen
    private JPanel crearPanelLogin() {
        // Contenedor con imagen de fondo personalizada usando paintComponent
        JPanel contenedor = new JPanel(new GridBagLayout()) {
            private java.awt.Image bgImage;
            {
                try {
                    // Cargar la imagen desde la carpeta de recursos del proyecto
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
                // Dibujar la imagen escalada al tamaño del panel
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Panel del formulario sin fondo (transparente para ver la imagen)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(150, 20, 20, 20));

        // Configuración del GridBagLayout para colocar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(java.awt.Color.WHITE); // Blanco para que se vea sobre la imagen
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtNombre, gbc);

        // Campo Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblContrasena, gbc);

        JPasswordField txtContrasena = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtContrasena, gbc);

        // Botón para entrar
        JButton btnEntrar = new JButton("Entrar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // El botón ocupa las dos columnas
        panel.add(btnEntrar, gbc);

        // Acción al hacer clic en "Entrar"
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Leer lo que el usuario ha escrito
                String nombre = txtNombre.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());

                // Validar que ningún campo esté vacío
                if (nombre.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(contenedor, "Por favor, rellene todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Consultar la BD y obtener el rol del usuario
                ConexionBBDD bd = new ConexionBBDD();
                String rol = bd.validarLoginCliente(nombre, contrasena);

                if (rol != null) {
                    // Si el login fue correcto, redirigir según el rol
                    if ("Administrador".equals(rol)) {
                        // Rol administrador: abrir el panel de administración
                        avanzarPantallaAdmin(nombre);
                    } else {
                        // Rol cliente (u otro): abrir la pantalla normal del cliente
                        avanzarPantallaBienvenida(nombre);
                    }
                } else {
                    // Si el rol es null, las credenciales son incorrectas
                    JOptionPane.showMessageDialog(contenedor, "Nombre o contraseña incorrectos.",
                            "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        contenedor.add(panel);
        return contenedor;
    }

    // Crea el panel de la pestaña "Registrarse" con fondo de imagen
    private JPanel crearPanelRegistro() {
        // Contenedor con imagen de fondo igual que el de login
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

        // Panel del formulario transparente
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(150, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo DNI
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblDni, gbc);

        JTextField txtDni = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtDni, gbc);

        // Campo Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtNombre, gbc);

        // Campo Teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblTelefono, gbc);

        JTextField txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtTelefono, gbc);

        // Campo Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(java.awt.Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblContrasena, gbc);

        JPasswordField txtContrasena = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtContrasena, gbc);

        // Botón para registrar
        JButton btnRegistrar = new JButton("Registrar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa las dos columnas
        panel.add(btnRegistrar, gbc);

        // Acción al hacer clic en "Registrar"
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Leer todos los campos del formulario
                String dni = txtDni.getText().trim();
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());

                // Comprobar que ningún campo esté vacío
                if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(contenedor, "Por favor, rellene todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardar el nuevo cliente en la BD (siempre con rol "Cliente")
                ConexionBBDD bd = new ConexionBBDD();
                boolean exito = bd.registrarNuevoCliente(dni, nombre, telefono, contrasena);

                if (exito) {
                    JOptionPane.showMessageDialog(contenedor, "Usuario registrado correctamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Ir directamente a la pantalla de cliente
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

    // Cambia el contenido del JFrame para mostrar el panel de administración
    private void avanzarPantallaAdmin(String nombreAdmin) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new VentanaAdministrador(nombreAdmin));
            frame.revalidate();
            frame.repaint();
        }
    }

    // Cambia el contenido del JFrame para mostrar la pantalla principal del cliente
    private void avanzarPantallaBienvenida(String nombreCliente) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new VentanaInicial(nombreCliente));
            frame.revalidate();
            frame.repaint();
        }
    }
}
