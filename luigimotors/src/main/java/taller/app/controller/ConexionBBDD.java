package taller.app.controller;

import java.sql.*;
import java.io.*;
import java.util.*;
import taller.app.model.Cliente;
import taller.app.model.Reparacion;
import taller.app.model.Cita;

public class ConexionBBDD {

    // driver JDBC
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // dirección de la BBDD MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/luigimotors";
    // usuario y contraseña de acceso a la BD
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    public Connection conectar() {
        Connection conexion = null;

        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión OK");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error en la conexión");
            e.printStackTrace();
        }
        return conexion;
    }

    // método para cerrar la conexión cuando ya no se necesita
    public void cerrarConexion(Connection conection) {
        try {
            // cierre de la conexión
            conection.close();
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al cerrar la conexión");
        }
    }

    // inserta un cliente de prueba en la BD (usado para testing)
    public void insertData() throws SQLException {
        Connection conexion = conectar();
        try {
            // insercción de un cliente de ejemplo
            String consultasInserccion = "INSERT INTO cliente (dni, nombre, telefono, contrasenya, rol) VALUES ('00000000Z', 'Cliente Prueba', '600000000', '1234', 'Cliente');";
            System.out.println(consultasInserccion);
            // creación del Statement para poder realizar la consulta
            Statement consul = conexion.createStatement();
            // ejecución de la consulta
            consul.executeUpdate(consultasInserccion);
            System.out.println("Datos insertados correctamente");
            // cierre del Statement
            consul.close();
        } finally {
            // cierre de la conexión
            cerrarConexion(conexion);
        }
    }

    // obtiene todos los clientes de la BD y los muestra por consola
    public void getData() throws SQLException {
        Connection conexion = conectar();

        if (conexion != null) {
            try {
                // selección de todos los clientes
                String consultasSeleccion = "SELECT * FROM cliente";
                System.out.println(consultasSeleccion);
                Statement consul = conexion.createStatement();
                // ejecución de la consulta
                if (consul.execute(consultasSeleccion)) {
                    ResultSet resultset = consul.getResultSet();
                    // recorre cada fila y crea un objeto Cliente
                    while (resultset.next()) {
                        Cliente cliente = new Cliente(
                                resultset.getInt("id_cliente"),
                                resultset.getString("dni"),
                                resultset.getString("nombre"),
                                resultset.getString("telefono"),
                                resultset.getString("contrasenya"),
                                resultset.getString("rol"));
                        System.out.println(cliente.toString());
                    }

                    System.out.println("Datos recuperados correctamente");
                }
                // cierre del Statement
                consul.close();

            } finally {
                // cierre de la conexión
                cerrarConexion(conexion);
            }
        }
    }

    // inserta una reparación de ejemplo en la BD (usado para testing)
    public void insertDataReparacion() throws SQLException {
        Connection conexion = conectar();
        try {
            // consulta SQL de ejemplo con datos fijos
            String consultasInserccion = "INSERT INTO reparacion (matricula, descripcion, coste, fecha_ingreso, estado, id_cliente) VALUES ('1234ABC', 'Cambio de aceite', 50.0, '2024-05-10', 'Terminado', 2);";
            System.out.println(consultasInserccion);
            // creación del Statement para poder realizar la consulta
            Statement consul = conexion.createStatement();
            // ejecución de la consulta
            consul.executeUpdate(consultasInserccion);
            System.out.println("Datos de reparación insertados correctamente");
            // cierre del Statement
            consul.close();
        } finally {
            // cierre de la conexión
            cerrarConexion(conexion);
        }
    }

    // obtiene todas las reparaciones de la BD y las muestra por consola
    public void getDataReparacion() throws SQLException {
        Connection conexion = conectar();

        if (conexion != null) {
            try {
                // consulta para seleccionar todas las reparaciones
                String consultasSeleccion = "SELECT * FROM reparacion";
                System.out.println(consultasSeleccion);
                Statement consul = conexion.createStatement();
                // ejecución de la consulta
                if (consul.execute(consultasSeleccion)) {
                    ResultSet resultset = consul.getResultSet();
                    // recorre cada fila y crea un objeto Reparacion
                    while (resultset.next()) {
                        Reparacion reparacion = new Reparacion(
                                resultset.getInt("id_reparacion"),
                                resultset.getString("matricula"),
                                resultset.getString("descripcion"),
                                resultset.getDouble("coste"),
                                resultset.getString("fecha_ingreso"),
                                resultset.getString("estado"),
                                resultset.getInt("id_cliente"));
                        System.out.println(reparacion.toString());
                    }

                    System.out.println("Datos de reparación recuperados correctamente");
                }
                // cierre del Statement
                consul.close();

            } finally {
                // cierre de la conexión
                cerrarConexion(conexion);
            }
        }
    }

    // valida el usuario y contraseña y devuelve el rol ("Administrador", "Cliente")
    // o null si no existe
    public String validarLoginCliente(String nombre, String contrasena) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // buscar el usuario que coincida con nombre y contraseña
                String consulta = "SELECT rol FROM cliente WHERE nombre = ? AND contrasenya = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, nombre);
                pstmt.setString(2, contrasena);

                // ejecutar y obtener el rol si el usuario existe
                ResultSet rs = pstmt.executeQuery();
                String rol = null;
                if (rs.next()) {
                    rol = rs.getString("rol"); // leer el campo rol de la fila encontrada
                }

                // cerrar recursos
                rs.close();
                pstmt.close();

                return rol; // devuelve el rol o null si el usuario no existe
            } catch (SQLException e) {
                System.out.println("Error al validar login de cliente");
                e.printStackTrace();
                return null;
            } finally {
                cerrarConexion(conexion);
            }
        }
        return null;
    }

    // registra un nuevo cliente en la BD con rol "Cliente"
    public boolean registrarNuevoCliente(String dni, String nombre, String telefono, String contrasena) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // INSERT con los datos del nuevo cliente, rol siempre "Cliente"
                String consulta = "INSERT INTO cliente (dni, nombre, telefono, contrasenya, rol) VALUES (?, ?, ?, ?, 'Cliente')";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, dni);
                pstmt.setString(2, nombre);
                pstmt.setString(3, telefono);
                pstmt.setString(4, contrasena);

                // ejecutar y ver si se insertó alguna fila
                int filasAfectadas = pstmt.executeUpdate();
                pstmt.close();

                if (filasAfectadas > 0) {
                    // si se guardó en BD, también lo añadimos al archivo .sql
                    guardarEnArchivoSQL(dni, nombre, telefono, contrasena);
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Error al registrar cliente");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // guarda el INSERT del nuevo cliente también en el archivo database.sql
    private void guardarEnArchivoSQL(String dni, String nombre, String telefono, String contrasena) {
        // intentar encontrar el archivo database.sql en varias rutas posibles
        String baseDir = System.getProperty("user.dir");
        File archivoSQL = new File(baseDir, "database.sql");
        if (!archivoSQL.exists()) {
            archivoSQL = new File(baseDir, "luigimotors/database.sql");
        }
        if (!archivoSQL.exists()) {
            // ruta absoluta como último recurso
            archivoSQL = new File(
                    "c:/Users/iLERNA/OneDrive - Ilerna/Programación/Trimestre 3/Actividad 8/PROYECTO-FINAL-PROG/luigimotors/database.sql");
        }

        // abrir el archivo en modo append (true = añadir al final, no sobreescribir)
        try (FileWriter fw = new FileWriter(archivoSQL, true);
                BufferedWriter bw = new BufferedWriter(fw)) {

            // formatear la sentencia INSERT con los datos del cliente
            String insert = String.format(
                    "\nINSERT INTO Cliente (dni, nombre, telefono, contrasenya, rol) VALUES \n('%s', '%s', '%s', '%s', 'Cliente');",
                    dni, nombre, telefono, contrasena);
            bw.write(insert);
            System.out.println("Guardado en database.sql correctamente.");
        } catch (IOException e) {
            System.out.println("No se pudo escribir en el archivo database.sql");
            e.printStackTrace();
        }
    }

    // devuelve el id_cliente buscando por nombre (lo usamos en otras consultas)
    public int obtenerIdCliente(String nombre) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // SELECT para obtener el ID del cliente por su nombre
                String consulta = "SELECT id_cliente FROM cliente WHERE nombre = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, nombre);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // devolver su ID
                    int id = rs.getInt("id_cliente");
                    rs.close();
                    pstmt.close();
                    return id;
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Error al obtener ID del cliente");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return -1; // -1 significa que no se encontró el cliente
    }

    // obtiene un objeto Cliente completo buscando por su nombre
    public Cliente obtenerClientePorNombre(String nombre) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String consulta = "SELECT * FROM cliente WHERE nombre = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, nombre);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Cliente c = new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("contrasenya"),
                            rs.getString("rol"));
                    rs.close();
                    pstmt.close();
                    return c;
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Error al obtener cliente por nombre");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return null;
    }

    // actualiza los datos de un cliente en la BD
    public boolean actualizarDatosCliente(Cliente c, String nombreOriginal) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String consulta = "UPDATE cliente SET dni = ?, nombre = ?, telefono = ?, contrasenya = ? WHERE nombre = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, c.getDni());
                pstmt.setString(2, c.getNombre());
                pstmt.setString(3, c.getTelefono());
                pstmt.setString(4, c.getContrasenya());
                pstmt.setString(5, nombreOriginal);

                int filas = pstmt.executeUpdate();
                pstmt.close();
                return filas > 0;
            } catch (SQLException e) {
                System.out.println("Error al actualizar datos del cliente");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // comprueba si ya hay una cita en esa fecha y hora (para evitar duplicados)
    public boolean citaOcupada(String fecha, String hora) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // COUNT para saber si existe alguna cita con esa fecha y hora
                String consulta = "SELECT COUNT(*) FROM citas WHERE fecha = ? AND hora = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, fecha);
                pstmt.setString(2, hora);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // si el contador es mayor que 0, está ocupada
                    boolean ocupada = rs.getInt(1) > 0;
                    rs.close();
                    pstmt.close();
                    return ocupada;
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Error al comprobar disponibilidad de cita");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // guarda una nueva cita en la BD vinculada al cliente
    public boolean guardarCita(String fecha, String hora, String matricula, String descripcion, String nombreCliente) {
        // primero obtenemos el ID del cliente por su nombre
        int idCliente = obtenerIdCliente(nombreCliente);
        if (idCliente == -1) {
            System.out.println("No se encontró el cliente: " + nombreCliente);
            return false;
        }

        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // INSERT de la cita con todos sus datos
                String consulta = "INSERT INTO citas (fecha, hora, matricula, descripcion, id_cliente) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, fecha);
                pstmt.setString(2, hora);
                pstmt.setString(3, matricula);
                pstmt.setString(4, descripcion);
                pstmt.setInt(5, idCliente);

                int filasAfectadas = pstmt.executeUpdate();
                pstmt.close();
                return filasAfectadas > 0; // True si la cita se guardó bien
            } catch (SQLException e) {
                System.out.println("Error al guardar la cita");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // devuelve una lista con las citas de un cliente
    public List<Cita> obtenerCitasCliente(String nombreCliente) {
        int idCliente = obtenerIdCliente(nombreCliente);
        List<Cita> lista = new ArrayList<>();
        if (idCliente == -1)
            return lista;

        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String consulta = "SELECT * FROM citas WHERE id_cliente = ? ORDER BY fecha DESC, hora DESC";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setInt(1, idCliente);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Cita c = new Cita(
                            rs.getInt("id_cita"),
                            rs.getString("fecha"),
                            rs.getString("hora"),
                            rs.getString("matricula"),
                            rs.getString("descripcion"),
                            rs.getInt("id_cliente"));
                    lista.add(c);
                }

                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Error al obtener citas del cliente");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return lista;
    }

    // obtiene el presupuesto de una cita (si existe)
    public String[] obtenerPresupuesto(int idCita) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String consulta = "SELECT desglose, mano_obra, total FROM presupuestos WHERE id_cita = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setInt(1, idCita);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String[] presupuesto = new String[3];
                    presupuesto[0] = rs.getString("desglose");
                    presupuesto[1] = String.valueOf(rs.getDouble("mano_obra"));
                    presupuesto[2] = String.valueOf(rs.getDouble("total"));
                    rs.close();
                    pstmt.close();
                    return presupuesto;
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Error al obtener presupuesto");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return null;
    }

    // guarda un presupuesto generado para una cita
    public boolean guardarPresupuesto(int idCita, String desglose, double manoObra, double total) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO presupuestos (id_cita, desglose, mano_obra, total) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setInt(1, idCita);
                pstmt.setString(2, desglose);
                pstmt.setDouble(3, manoObra);
                pstmt.setDouble(4, total);

                int filasAfectadas = pstmt.executeUpdate();
                pstmt.close();
                return filasAfectadas > 0;
            } catch (SQLException e) {
                System.out.println("Error al guardar presupuesto");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // busca en el catálogo de reparaciones si alguna coincide con la descripción
    public java.util.List<String[]> obtenerPreciosCatalogo(String descripcion) {
        java.util.List<String[]> resultados = new java.util.ArrayList<>();
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String consulta = "SELECT nombre, precio_total, mano_obra FROM catalogo_reparaciones";
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(consulta);

                String descLower = descripcion.toLowerCase();
                // limpiamos la descripción de caracteres extraños por si acaso
                descLower = descLower.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o")
                        .replace("ú", "u").replace("ñ", "n");

                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String nombreLower = nombre.toLowerCase().replace("á", "a").replace("é", "e").replace("í", "i")
                            .replace("ó", "o").replace("ú", "u").replace("ñ", "n");

                    // dividimos en palabras clave para buscar coincidencias
                    String[] palabrasNombre = nombreLower.split("[\\s+()]");
                    int palabrasEncontradas = 0;

                    for (String palabra : palabrasNombre) {
                        // ignoramos palabras genéricas y muy cortas
                        if (palabra.length() > 3
                                && !palabra.equals("cambio")
                                && !palabra.equals("reparacion")
                                && !palabra.equals("sustitucion")
                                && !palabra.equals("limpieza")) {

                            if (descLower.contains(palabra)) {
                                palabrasEncontradas++;
                            }
                        }
                    }

                    // si hemos encontrado palabras clave específicas (ej: "turbo", "aceite")
                    // o si el nombre coincide exactamente
                    if (palabrasEncontradas > 0 || descLower.equals(nombreLower)) {
                        String[] datos = new String[3];
                        datos[0] = nombre;
                        datos[1] = String.valueOf(rs.getDouble("precio_total"));
                        datos[2] = String.valueOf(rs.getDouble("mano_obra"));
                        resultados.add(datos);

                        // si encontramos una coincidencia fuerte, podemos parar de buscar otras
                        // para evitar que salgan demasiadas cosas en el ticket
                        if (descLower.contains(nombreLower))
                            break;
                    }
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Error al consultar el catálogo");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return resultados;
    }

    // inserta una nueva reparación en la tabla reparacion
    public boolean insertarReparacion(String matricula, String descripcion, double coste,
            String fecha, String estado, int idCliente) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // INSERT con todos los datos de la reparación
                String consulta = "INSERT INTO reparacion (matricula, descripcion, coste, fecha_ingreso, estado, id_cliente) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, matricula);
                pstmt.setString(2, descripcion);
                pstmt.setDouble(3, coste);
                pstmt.setString(4, fecha);
                pstmt.setString(5, estado);
                pstmt.setInt(6, idCliente);

                int filas = pstmt.executeUpdate();
                pstmt.close();
                return filas > 0; // True si se insertó correctamente
            } catch (SQLException e) {
                System.out.println("Error al insertar reparación");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // devuelve un texto con todos los clientes y reparaciones para mostrar en
    // pantalla
    public String obtenerResumenBBDD() {
        Connection conexion = conectar();
        StringBuilder sb = new StringBuilder(); // usamos StringBuilder para ir construyendo el texto

        if (conexion != null) {
            try {
                sb.append("============================\n");
                sb.append("         CLIENTES\n");
                sb.append("============================\n");
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id_cliente, dni, nombre, telefono, rol FROM cliente");
                while (rs.next()) {
                    sb.append(String.format("ID: %d | DNI: %s | Nombre: %s | Tel: %s | Rol: %s\n",
                            rs.getInt("id_cliente"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("rol")));
                }
                rs.close();

                sb.append("\n============================\n");
                sb.append("       REPARACIONES\n");
                sb.append("============================\n");
                rs = stmt.executeQuery(
                        "SELECT id_reparacion, matricula, descripcion, coste, fecha_ingreso, estado, id_cliente FROM reparacion");
                while (rs.next()) {
                    sb.append(String.format(
                            "ID: %d | Matrícula: %s | Desc: %s | Coste: %.2f€ | Fecha: %s | Estado: %s | ClienteID: %d\n",
                            rs.getInt("id_reparacion"),
                            rs.getString("matricula"),
                            rs.getString("descripcion"),
                            rs.getDouble("coste"),
                            rs.getString("fecha_ingreso"),
                            rs.getString("estado"),
                            rs.getInt("id_cliente")));
                }
                rs.close();
                stmt.close();

            } catch (SQLException e) {
                System.out.println("Error al obtener resumen de la BD");
                e.printStackTrace();
                sb.append("\nError al leer la base de datos.");
            } finally {
                cerrarConexion(conexion);
            }
        } else {
            sb.append("No se pudo conectar a la base de datos.");
        }

        return sb.toString();
    }

    // borra un cliente de la BD buscándolo por nombre
    public boolean borrarCliente(String nombre) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // DELETE usando el nombre como filtro
                String consulta = "DELETE FROM cliente WHERE nombre = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, nombre);

                int filas = pstmt.executeUpdate();
                pstmt.close();
                return filas > 0; // true si se borró al menos un cliente
            } catch (SQLException e) {
                System.out.println("Error al borrar el cliente");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // busca y devuelve todas las reparaciones que tiene un cliente por su nombre
    public String buscarReparacionesPorCliente(String nombreCliente) {
        Connection conexion = conectar();
        StringBuilder sb = new StringBuilder();

        if (conexion != null) {
            try {
                // primero obtenemos el ID del cliente para usarlo en el SELECT
                int idCliente = obtenerIdCliente(nombreCliente);

                if (idCliente == -1) {
                    // si no existe el cliente, avisamos
                    return "No se encontró ningún cliente con el nombre: " + nombreCliente;
                }

                // SELECT de todas las reparaciones de ese cliente
                String consulta = "SELECT * FROM reparacion WHERE id_cliente = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setInt(1, idCliente);
                ResultSet rs = pstmt.executeQuery();

                sb.append("Reparaciones del cliente: ").append(nombreCliente).append("\n");
                sb.append("-------------------------------------------\n");

                boolean hayReparaciones = false;
                while (rs.next()) {
                    hayReparaciones = true;
                    sb.append(String.format(
                            "ID: %d | Matrícula: %s\nDescripción: %s\nCoste: %.2f€ | Fecha: %s | Estado: %s\n\n",
                            rs.getInt("id_reparacion"),
                            rs.getString("matricula"),
                            rs.getString("descripcion"),
                            rs.getDouble("coste"),
                            rs.getString("fecha_ingreso"),
                            rs.getString("estado")));
                }

                if (!hayReparaciones) {
                    sb.append("Este cliente no tiene reparaciones registradas.");
                }

                rs.close();
                pstmt.close();

            } catch (SQLException e) {
                System.out.println("Error al buscar reparaciones del cliente");
                e.printStackTrace();
                sb.append("Error al consultar la base de datos.");
            } finally {
                cerrarConexion(conexion);
            }
        } else {
            sb.append("No se pudo conectar a la base de datos.");
        }

        return sb.toString();
    }

    // devuelve una lista con todas las reparaciones de la BD
    public java.util.List<Reparacion> obtenerTodasLasReparaciones() {
        Connection conexion = conectar();
        java.util.List<Reparacion> lista = new java.util.ArrayList<>();

        if (conexion != null) {
            try {
                // SELECT de todas las reparaciones ordenadas por ID
                String consulta = "SELECT id_reparacion, matricula, descripcion, coste, fecha_ingreso, estado, id_cliente FROM reparacion ORDER BY id_reparacion";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // crear un objeto Reparacion por cada fila y añadirlo a la lista
                    Reparacion r = new Reparacion(
                            rs.getInt("id_reparacion"),
                            rs.getString("matricula"),
                            rs.getString("descripcion"),
                            rs.getDouble("coste"),
                            rs.getString("fecha_ingreso"),
                            rs.getString("estado"),
                            rs.getInt("id_cliente"));
                    lista.add(r);
                }

                rs.close();
                pstmt.close();

            } catch (SQLException e) {
                System.out.println("Error al obtener todas las reparaciones");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }

        return lista; // devuelve la lista (puede estar vacía si no hay reparaciones)
    }

    // actualiza el estado de una reparación según su ID (Pendiente <-> Terminado)
    public boolean actualizarEstadoReparacion(int idReparacion, String nuevoEstado) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // UPDATE solo el campo estado de la reparación indicada
                String consulta = "UPDATE reparacion SET estado = ? WHERE id_reparacion = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, nuevoEstado);
                pstmt.setInt(2, idReparacion);

                int filas = pstmt.executeUpdate();
                pstmt.close();

                if (filas > 0) {
                    guardarActualizacionEstadoEnSQL(idReparacion, nuevoEstado);
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Error al actualizar estado de reparación");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // guarda el UPDATE del estado en el archivo database.sql
    private void guardarActualizacionEstadoEnSQL(int idReparacion, String nuevoEstado) {
        // intentar encontrar el archivo database.sql en varias rutas posibles
        String baseDir = System.getProperty("user.dir");
        File archivoSQL = new File(baseDir, "database.sql");
        if (!archivoSQL.exists()) {
            archivoSQL = new File(baseDir, "luigimotors/database.sql");
        }
        if (!archivoSQL.exists()) {
            // ruta absoluta como último recurso
            archivoSQL = new File(
                    "c:/Users/iLERNA/OneDrive - Ilerna/Programación/Trimestre 3/Actividad 8/LuigiMotors/luigimotors/database.sql");
        }

        // abrir el archivo en modo append
        try (FileWriter fw = new FileWriter(archivoSQL, true);
                BufferedWriter bw = new BufferedWriter(fw)) {

            // formatear la sentencia UPDATE con el nuevo estado y el ID de la reparación
            String update = String.format(
                    "\nUPDATE reparacion SET estado = '%s' WHERE id_reparacion = %d;",
                    nuevoEstado, idReparacion);
            bw.write(update);
            System.out.println("Actualización de estado guardada en database.sql correctamente.");
        } catch (IOException e) {
            System.out.println("No se pudo escribir la actualización en el archivo database.sql");
            e.printStackTrace();
        }
    }

    // suma el coste de todas las reparaciones y devuelve el total de ingresos
    public double calcularIngresosTotales() {
        Connection conexion = conectar();
        double total = 0.0;

        if (conexion != null) {
            try {
                // SUM(coste) para sumar todos los costes de la tabla reparacion
                String consulta = "SELECT SUM(coste) AS total_ingresos FROM reparacion";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    total = rs.getDouble("total_ingresos"); // Leer el resultado de la suma
                }

                rs.close();
                pstmt.close();

            } catch (SQLException e) {
                System.out.println("Error al calcular ingresos totales");
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }

        return total;
    }
}
