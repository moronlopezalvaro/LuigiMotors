package taller.app.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import taller.app.model.Cliente;
import taller.app.model.Reparacion;

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

    public void cerrarConexion(Connection conection) {
        try {
            // Cierre de la conexión
            conection.close();
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al cerrar la conexión");
        }
    }

    public void insertData() throws SQLException {
        Connection conexion = conectar();
        try {
            // Datos a insertar
            String consultasInserccion = "INSERT INTO cliente (dni, nombre, telefono, contrasenya, rol) VALUES ('00000000Z', 'Cliente Prueba', '600000000', '1234', 'Cliente');";
            System.out.println(consultasInserccion);
            // Creación del Statement para poder reqalizar la consulta
            Statement consul = conexion.createStatement();
            // Ejecución de la consulta
            consul.executeUpdate(consultasInserccion);
            System.out.println("Datos insertados correctamente");
            // Cierre del Statement
            consul.close();
        } finally {
            // Cierre de la conexión
            cerrarConexion(conexion);
        }
    }

    public void getData() throws SQLException {
        Connection conexion = conectar();

        if (conexion != null) {
            try {
                // Datos a consultar
                String consultasSeleccion = "SELECT * FROM cliente";
                System.out.println(consultasSeleccion);
                Statement consul = conexion.createStatement();
                // Ejecución de la consulta
                if (consul.execute(consultasSeleccion)) {
                    ResultSet resultset = consul.getResultSet();
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
                // Cierre del Statement
                consul.close();

            } finally {
                // Cierre de la conexión
                cerrarConexion(conexion);
            }
        }
    }
    public void insertDataReparacion() throws SQLException {
        Connection conexion = conectar();
        try {
            // Datos a insertar
            String consultasInserccion = "INSERT INTO reparacion (matricula, descripcion, coste, fecha_ingreso, estado, id_cliente) VALUES ('1234ABC', 'Cambio de aceite', 50.0, '2024-05-10', 'Terminado', 2);";
            System.out.println(consultasInserccion);
            // Creación del Statement para poder realizar la consulta
            Statement consul = conexion.createStatement();
            // Ejecución de la consulta
            consul.executeUpdate(consultasInserccion);
            System.out.println("Datos de reparación insertados correctamente");
            // Cierre del Statement
            consul.close();
        } finally {
            // Cierre de la conexión
            cerrarConexion(conexion);
        }
    }

    public void getDataReparacion() throws SQLException {
        Connection conexion = conectar();

        if (conexion != null) {
            try {
                // Datos a consultar
                String consultasSeleccion = "SELECT * FROM reparacion";
                System.out.println(consultasSeleccion);
                Statement consul = conexion.createStatement();
                // Ejecución de la consulta
                if (consul.execute(consultasSeleccion)) {
                    ResultSet resultset = consul.getResultSet();
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
                // Cierre del Statement
                consul.close();

            } finally {
                // Cierre de la conexión
                cerrarConexion(conexion);
            }
        }
    }

    // Comprobar login
    public boolean validarLoginCliente(String nombre, String contrasena) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // Consulta SELECT
                String consulta = "SELECT * FROM cliente WHERE nombre = ? AND contrasenya = ?";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, nombre);
                pstmt.setString(2, contrasena);
                
                // Ejecutar y ver si existe
                ResultSet rs = pstmt.executeQuery();
                boolean existe = rs.next();
                
                // Cerrar
                rs.close();
                pstmt.close();
                
                return existe;
            } catch (SQLException e) {
                System.out.println("Error al validar login de cliente");
                e.printStackTrace();
                return false;
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }

    // Registrar nuevo usuario
    public boolean registrarNuevoCliente(String dni, String nombre, String telefono, String contrasena) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                // Consulta INSERT
                String consulta = "INSERT INTO cliente (dni, nombre, telefono, contrasenya, rol) VALUES (?, ?, ?, ?, 'Cliente')";
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, dni);
                pstmt.setString(2, nombre);
                pstmt.setString(3, telefono);
                pstmt.setString(4, contrasena);
                
                // Ejecutar
                int filasAfectadas = pstmt.executeUpdate();
                pstmt.close();
                
                if (filasAfectadas > 0) {
                    // Guardar también en archivo .sql
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

    // Escribir en el archivo database.sql
    private void guardarEnArchivoSQL(String dni, String nombre, String telefono, String contrasena) {
        // Buscar el archivo
        String baseDir = System.getProperty("user.dir");
        File archivoSQL = new File(baseDir, "database.sql");
        if (!archivoSQL.exists()) {
            archivoSQL = new File(baseDir, "luigimotors/database.sql");
        }
        if (!archivoSQL.exists()) {
            archivoSQL = new File("c:/Users/iLERNA/OneDrive - Ilerna/Programación/Trimestre 3/Actividad 8/PROYECTO-FINAL-PROG/luigimotors/database.sql");
        }
        
        // Escribir al final del archivo
        try (FileWriter fw = new FileWriter(archivoSQL, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            // Texto a insertar
            String insert = String.format("\nINSERT INTO Cliente (dni, nombre, telefono, contrasenya, rol) VALUES \n('%s', '%s', '%s', '%s', 'Cliente');",
                    dni, nombre, telefono, contrasena);
            bw.write(insert);
            System.out.println("Guardado en database.sql correctamente.");
        } catch (IOException e) {
            System.out.println("No se pudo escribir en el archivo database.sql");
            e.printStackTrace();
        }
    }
}
