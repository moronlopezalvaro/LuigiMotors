package taller.app.main;

import java.sql.Connection;
import java.sql.SQLException;
import taller.app.controller.ConexionBBDD;
import taller.app.view.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {

        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);

        ConexionBBDD conexion = new ConexionBBDD();
        Connection cn = null;
        try {
            cn = conexion.conectar();
            conexion.insertData();
            conexion.getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
