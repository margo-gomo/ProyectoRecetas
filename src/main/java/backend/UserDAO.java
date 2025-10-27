/*package backend;

import java.sql.*;

public class UserDAO {
    private String url = "jdbc:mysql://localhost:3306/recetasdb?serverTimezone=UTC";
    private String user = "root";
    private String pass = "tu_contraseña";

    public boolean validarUsuario(String id, String clave) {
        String sql = "SELECT clave FROM usuario WHERE id = ?";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("clave").equals(clave);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}*/

