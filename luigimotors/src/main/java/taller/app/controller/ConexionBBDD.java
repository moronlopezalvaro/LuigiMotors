package taller.app.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                                resultset.getString("rol")
                        );
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

}
