package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;

// Ventana del panel de administración con todas las opciones de gestión del taller
public class VentanaAdministrador extends JPanel {

    private String nombreAdmin; // Nombre del administrador que ha iniciado sesión

    public VentanaAdministrador(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
        setLayout(new BorderLayout());
        setOpaque(false); // Para que el fondo degradado se dibuje correctamente

        // ===== ENCABEZADO SUPERIOR =====
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(true);
        panelTitulo.setBackground(Color.decode("#1E3A5F")); // Color azul oscuro corporativo
        panelTitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(15, 20, 10, 20),
                        BorderFactory.createLineBorder(Color.decode("#2C2C2C"), 2)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        // Título principal centrado
        JLabel lblTitulo = new JLabel("Luigi Motors · Panel de Administración", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        // Texto con el nombre del administrador debajo del título
        JLabel lblBienvenido = new JLabel("Administrador: " + nombreAdmin);
        lblBienvenido.setFont(new Font("Arial", Font.ITALIC, 13));
        lblBienvenido.setForeground(Color.decode("#F5F5F5"));
        lblBienvenido.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelTitulo.add(lblBienvenido, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);

        // ===== PANEL DE BOTONES DEL MENÚ =====
        // GridLayout de 8 filas, 1 columna, con 15px de separación entre filas
        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 0, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        panelBotones.setOpaque(false); // Transparente para ver el fondo degradado

        // Colores de la paleta del proyecto
        Color colorNaranja = Color.decode("#FF6B00"); // Naranja: acento principal
        Color colorAzul = Color.decode("#1E3A5F"); // Azul: corporativo
        Color colorVerde = Color.decode("#2E7D32"); // Verde: para añadir
        Color colorRojo = Color.decode("#B71C1C"); // Rojo: para borrar
        Color colorMorado = Color.decode("#4A148C"); // Morado: para búsqueda
        Color colorCian = Color.decode("#00695C"); // Verde azulado: para editar reparación
        Color colorGrisOscuro = Color.decode("#2C2C2C"); // Gris: para salir

        // Crear los 8 botones del menú con sus colores
        JButton btnAnadirCliente = createRoundedButton("Añadir cliente", colorVerde, Color.WHITE);
        JButton btnAnadirReparacion = createRoundedButton("Añadir reparación", colorNaranja, Color.WHITE);
        JButton btnEditarReparacion = createRoundedButton("Editar reparación", colorCian, Color.WHITE);
        JButton btnVerBBDD = createRoundedButton("Ver Base de Datos", colorAzul, Color.WHITE);
        JButton btnBorrarCliente = createRoundedButton("Borrar cliente", colorRojo, Color.WHITE);
        JButton btnBuscarReparacion = createRoundedButton("Buscar reparación por cliente", colorMorado, Color.WHITE);
        JButton btnCalcularIngresos = createRoundedButton("Calcular ingresos", colorAzul, Color.WHITE);
        JButton btnSalir = createRoundedButton("Salir / Cerrar Sesión", colorGrisOscuro, Color.WHITE);

        // Añadir los botones al panel en orden
        panelBotones.add(btnAnadirCliente);
        panelBotones.add(btnAnadirReparacion);
        panelBotones.add(btnEditarReparacion);
        panelBotones.add(btnVerBBDD);
        panelBotones.add(btnBorrarCliente);
        panelBotones.add(btnBuscarReparacion);
        panelBotones.add(btnCalcularIngresos);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // ===== ACCIONES DE CADA BOTÓN =====

        // --- Botón 1: Añadir cliente ---
        btnAnadirCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Campos del formulario para el nuevo cliente
                JTextField txtDni = new JTextField();
                JTextField txtNombre = new JTextField();
                JTextField txtTelefono = new JTextField();
                JPasswordField txtClave = new JPasswordField();

                // Array con etiquetas y campos que mostrará el JOptionPane
                Object[] campos = {
                        "DNI:", txtDni,
                        "Nombre:", txtNombre,
                        "Teléfono:", txtTelefono,
                        "Contraseña:", txtClave
                };

                // Mostrar el diálogo con el formulario
                int opcion = JOptionPane.showConfirmDialog(
                        VentanaAdministrador.this, campos,
                        "Añadir nuevo cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (opcion == JOptionPane.OK_OPTION) {
                    // Leer lo que el admin ha escrito en cada campo
                    String dni = txtDni.getText().trim();
                    String nombre = txtNombre.getText().trim();
                    String telefono = txtTelefono.getText().trim();
                    String contrasena = new String(txtClave.getPassword());

                    // Comprobar que no haya ningún campo vacío
                    if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                        JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                "Por favor, rellena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Guardar el cliente en la BD usando el método de ConexionBBDD
                    ConexionBBDD bd = new ConexionBBDD();
                    boolean exito = bd.registrarNuevoCliente(dni, nombre, telefono, contrasena);

                    if (exito) {
                        JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                "Cliente '" + nombre + "' añadido correctamente.", "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                "Error al añadir el cliente.\nComprueba que el DNI no está duplicado.",
                                "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // --- Botón 2: Añadir reparación ---
        btnAnadirReparacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Campos del formulario para la nueva reparación
                JTextField txtMatricula = new JTextField();
                JTextField txtDescripcion = new JTextField();
                JTextField txtCoste = new JTextField();
                JTextField txtFecha = new JTextField();
                JTextField txtEstado = new JTextField("Pendiente"); // Valor por defecto
                JTextField txtIdCliente = new JTextField();

                Object[] campos = {
                        "Matrícula:", txtMatricula,
                        "Descripción:", txtDescripcion,
                        "Coste (€):", txtCoste,
                        "Fecha (YYYY-MM-DD):", txtFecha,
                        "Estado:", txtEstado,
                        "ID Cliente:", txtIdCliente
                };

                int opcion = JOptionPane.showConfirmDialog(
                        VentanaAdministrador.this, campos,
                        "Añadir reparación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (opcion == JOptionPane.OK_OPTION) {
                    // Leer los datos del formulario
                    String matricula = txtMatricula.getText().trim();
                    String descripcion = txtDescripcion.getText().trim();
                    String costeStr = txtCoste.getText().trim();
                    String fecha = txtFecha.getText().trim();
                    String estado = txtEstado.getText().trim();
                    String idClienteStr = txtIdCliente.getText().trim();

                    // Validar que no haya campos vacíos
                    if (matricula.isEmpty() || descripcion.isEmpty() || costeStr.isEmpty()
                            || fecha.isEmpty() || idClienteStr.isEmpty()) {
                        JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                "Por favor, rellena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        // Convertir el coste y el ID de String a número
                        double coste = Double.parseDouble(costeStr);
                        int idCliente = Integer.parseInt(idClienteStr);

                        // Guardar la reparación en la BD
                        ConexionBBDD bd = new ConexionBBDD();
                        boolean exito = bd.insertarReparacion(matricula, descripcion, coste, fecha, estado, idCliente);

                        if (exito) {
                            JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                    "Reparación añadida correctamente.", "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                    "Error al añadir la reparación.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        // Si el coste o el ID no son números, mostramos error de formato
                        JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                "El coste debe ser un número válido (ej: 45.50) y el ID cliente un número entero.",
                                "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // --- Botón 3: Editar reparación ---
        btnEditarReparacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener todas las reparaciones de la BD
                ConexionBBDD bd = new ConexionBBDD();
                java.util.List<taller.app.model.Reparacion> reparaciones = bd.obtenerTodasLasReparaciones();

                if (reparaciones.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaAdministrador.this,
                            "No hay reparaciones registradas en la base de datos.",
                            "Editar reparación", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Columnas de la tabla
                String[] columnas = {"ID", "Matrícula", "Descripción", "Coste (€)", "Fecha", "Estado", "ID Cliente"};

                // Rellenar los datos de la tabla con cada reparación
                Object[][] datos = new Object[reparaciones.size()][7];
                for (int i = 0; i < reparaciones.size(); i++) {
                    taller.app.model.Reparacion r = reparaciones.get(i);
                    datos[i][0] = r.getId_reparacion();
                    datos[i][1] = r.getMatricula();
                    datos[i][2] = r.getDescripcion();
                    datos[i][3] = String.format("%.2f", r.getCoste());
                    datos[i][4] = r.getFecha_ingreso();
                    datos[i][5] = r.getEstado();
                    datos[i][6] = r.getId_cliente();
                }

                // Crear la tabla con los datos (no editable directamente)
                javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(datos, columnas) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false; // Las celdas no se editan a mano
                    }
                };
                JTable tabla = new JTable(modelo);
                tabla.setFont(new Font("Arial", Font.PLAIN, 13));
                tabla.setRowHeight(28);
                tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
                tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                // Colorear las filas según el estado
                tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                    @Override
                    public java.awt.Component getTableCellRendererComponent(
                            JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                        super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                        String estado = (String) t.getValueAt(row, 5);
                        if (!isSelected) {
                            if ("Terminado".equalsIgnoreCase(estado)) {
                                setBackground(new Color(200, 240, 200)); // Verde claro = terminado
                            } else {
                                setBackground(new Color(255, 235, 200)); // Naranja claro = pendiente
                            }
                        } else {
                            setBackground(new Color(100, 149, 237)); // Azul al seleccionar
                            setForeground(Color.WHITE);
                        }
                        if (!isSelected) setForeground(Color.BLACK);
                        return this;
                    }
                });

                JScrollPane scroll = new JScrollPane(tabla);
                scroll.setPreferredSize(new Dimension(750, 300));

                // Botón para cambiar el estado de la reparación seleccionada
                JButton btnCambiarEstado = new JButton("🔄  Cambiar estado (Pendiente ↔ Terminado)");
                btnCambiarEstado.setFont(new Font("Arial", Font.BOLD, 13));
                btnCambiarEstado.setBackground(Color.decode("#00695C"));
                btnCambiarEstado.setForeground(Color.WHITE);
                btnCambiarEstado.setFocusPainted(false);
                btnCambiarEstado.setCursor(new Cursor(Cursor.HAND_CURSOR));

                btnCambiarEstado.addActionListener(ev -> {
                    int filaSeleccionada = tabla.getSelectedRow();
                    if (filaSeleccionada == -1) {
                        JOptionPane.showMessageDialog(null,
                                "Selecciona una reparación de la tabla primero.",
                                "Sin selección", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Obtener el ID y el estado actual de la fila seleccionada
                    int idRep = (int) modelo.getValueAt(filaSeleccionada, 0);
                    String estadoActual = (String) modelo.getValueAt(filaSeleccionada, 5);

                    // Alternar el estado entre Pendiente y Terminado
                    String nuevoEstado = "Terminado".equalsIgnoreCase(estadoActual) ? "Pendiente" : "Terminado";

                    // Actualizar en la BD
                    ConexionBBDD bdActualizar = new ConexionBBDD();
                    boolean exito = bdActualizar.actualizarEstadoReparacion(idRep, nuevoEstado);

                    if (exito) {
                        // Actualizar también el dato en la tabla para reflejar el cambio visualmente
                        modelo.setValueAt(nuevoEstado, filaSeleccionada, 5);
                        tabla.repaint(); // Forzar repintado para actualizar el color de fila
                        JOptionPane.showMessageDialog(null,
                                "Estado de la reparación ID " + idRep + " cambiado a: " + nuevoEstado,
                                "Estado actualizado", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Error al actualizar el estado en la base de datos.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                // Panel inferior con leyenda y botón
                JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
                JLabel leyenda = new JLabel("🟠 Pendiente   🟢 Terminado   — Selecciona una fila y pulsa el botón para cambiar su estado");
                leyenda.setFont(new Font("Arial", Font.ITALIC, 12));
                panelInferior.add(leyenda);
                panelInferior.add(btnCambiarEstado);

                // Panel principal del diálogo
                JPanel panelDialogo = new JPanel(new BorderLayout(0, 10));
                panelDialogo.add(scroll, BorderLayout.CENTER);
                panelDialogo.add(panelInferior, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(VentanaAdministrador.this, panelDialogo,
                        "Editar reparaciones — Cambiar estado", JOptionPane.PLAIN_MESSAGE);
            }
        });

        // --- Botón 4: Ver Base de Datos ---
        btnVerBBDD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el resumen de clientes y reparaciones de la BD
                ConexionBBDD bd = new ConexionBBDD();
                String contenido = bd.obtenerResumenBBDD();

                // Mostrarlo en un área de texto con scroll para poder desplazarse
                JTextArea areaTexto = new JTextArea(contenido);
                areaTexto.setEditable(false); // Solo lectura
                areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JScrollPane scroll = new JScrollPane(areaTexto);
                scroll.setPreferredSize(new Dimension(600, 400));

                JOptionPane.showMessageDialog(VentanaAdministrador.this, scroll,
                        "Base de Datos - Clientes y Reparaciones", JOptionPane.PLAIN_MESSAGE);
            }
        });

        // --- Botón 4: Borrar cliente ---
        btnBorrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pedir el nombre del cliente que se quiere borrar
                String nombreBorrar = JOptionPane.showInputDialog(
                        VentanaAdministrador.this,
                        "Introduce el nombre del cliente a borrar:",
                        "Borrar cliente", JOptionPane.WARNING_MESSAGE);

                if (nombreBorrar != null && !nombreBorrar.trim().isEmpty()) {
                    // Pedir confirmación antes de borrar (es una acción irreversible)
                    int confirm = JOptionPane.showConfirmDialog(
                            VentanaAdministrador.this,
                            "¿Seguro que deseas borrar al cliente '" + nombreBorrar.trim()
                                    + "'?\nEsta acción no se puede deshacer.",
                            "Confirmar borrado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Llamar al método que borra el cliente en la BD
                        ConexionBBDD bd = new ConexionBBDD();
                        boolean exito = bd.borrarCliente(nombreBorrar.trim());

                        if (exito) {
                            JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                    "Cliente '" + nombreBorrar.trim() + "' borrado correctamente.", "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(VentanaAdministrador.this,
                                    "No se encontró el cliente o ocurrió un error al borrar.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // --- Botón 5: Buscar reparación por cliente ---
        btnBuscarReparacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pedir el nombre del cliente para buscar sus reparaciones
                String nombreBuscar = JOptionPane.showInputDialog(
                        VentanaAdministrador.this,
                        "Introduce el nombre del cliente para buscar sus reparaciones:",
                        "Buscar reparación por cliente", JOptionPane.QUESTION_MESSAGE);

                if (nombreBuscar != null && !nombreBuscar.trim().isEmpty()) {
                    // Buscar en la BD las reparaciones de ese cliente
                    ConexionBBDD bd = new ConexionBBDD();
                    String resultado = bd.buscarReparacionesPorCliente(nombreBuscar.trim());

                    // Mostrar el resultado en un área de texto con scroll
                    JTextArea areaTexto = new JTextArea(resultado);
                    areaTexto.setEditable(false);
                    areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    JScrollPane scroll = new JScrollPane(areaTexto);
                    scroll.setPreferredSize(new Dimension(550, 300));

                    JOptionPane.showMessageDialog(VentanaAdministrador.this, scroll,
                            "Reparaciones de: " + nombreBuscar.trim(), JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        // --- Botón 6: Calcular ingresos ---
        btnCalcularIngresos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al método que suma todos los costes de reparaciones
                ConexionBBDD bd = new ConexionBBDD();
                double total = bd.calcularIngresosTotales();

                // Mostrar el total formateado con 2 decimales
                JOptionPane.showMessageDialog(VentanaAdministrador.this,
                        String.format("💰 Ingresos totales del taller:\n\n   %.2f €", total),
                        "Calcular ingresos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // --- Botón 7: Salir / Cerrar Sesión ---
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Volver a la pantalla de login cerrando la sesión
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaAdministrador.this);
                if (frame != null) {
                    frame.setContentPane(new VentanaLogin());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }

    // Dibuja el fondo degradado de la ventana (de gris claro a azul muy suave)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = Color.decode("#F5F5F5"); // Gris claro arriba
        Color color2 = Color.decode("#D3DEED"); // Azul muy suave abajo
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
    }

    // Crea un botón con esquinas redondeadas y efecto hover (cambia de color al
    // pasar el ratón)
    private JButton createRoundedButton(String text, Color bgColor, Color fgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Cambiar el color según el estado del botón
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker()); // Más oscuro al hacer clic
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter()); // Más claro al pasar el ratón
                } else {
                    g2.setColor(bgColor); // Color normal
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Bordes redondeados
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false); // No pintar el fondo por defecto de Swing
        btn.setFocusPainted(false); // Sin borde de foco al seleccionar con teclado
        btn.setBorderPainted(false); // Sin borde estándar
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mano al pasar por encima
        btn.setPreferredSize(new Dimension(0, 50));
        return btn;
    }
}
