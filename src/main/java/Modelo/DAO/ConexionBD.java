package Modelo.DAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import java.sql.SQLException;
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/proyectobd";
    private static final String USUARIO = "root";
    private static final String CLAVE = "root";

    private static ConnectionSource conexion;

    public static synchronized ConnectionSource getConexion() throws SQLException {
        if (conexion == null) {
            conexion = new JdbcConnectionSource(URL, USUARIO, CLAVE);
        }
        return conexion;
    }
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion = null;
            }
        }
    }
}