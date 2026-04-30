package taller.app.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public boolean registrarUsuario(String correo, String contrasena) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO usuarios (correo_electronico, contrasenya_usuario) VALUES (?, ?)";
                java.sql.PreparedStatement pstmt = conexion.prepareStatement(consulta);
                pstmt.setString(1, correo);
                pstmt.setString(2, contrasena);
                
                int filasAfectadas = pstmt.executeUpdate();
                pstmt.close();
                
                return filasAfectadas > 0;
            } catch (SQLException e) {
                System.out.println("Error al registrar usuario");
                e.printStackTrace();
                return false;
            } finally {
                cerrarConexion(conexion);
            }
        }
        return false;
    }
}
