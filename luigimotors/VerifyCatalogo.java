import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// Esta es una clase de prueba (script suelto) para verificar si podemos 
// leer correctamente los datos de nuestro catálogo de la base de datos.
public class VerifyCatalogo {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/luigimotors", "root", "");
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM catalogo_reparaciones");
            System.out.println("Catalogo:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("nombre") + ": " + rs.getDouble("precio_total") + " (MO: " + rs.getDouble("mano_obra") + ")");
            }
            rs.close();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
