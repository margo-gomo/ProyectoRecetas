package Modelo;
import Modelo.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
public class login {
    public login() {
        usuarios = new ArrayList<>();
    }
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
    public void actualizarUsuario(Usuario usuario) {
        usuarios.removeIf(usuario::equals);
        usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }
    public void cargarUsuarios(List<? extends Usuario> usuarios) {
        this.usuarios.addAll(usuarios);
    }
    public void limpiar() {
        usuarios.clear();
    }
    public int devolverToken(String id, String clave) throws SecurityException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && u.getClave().equals(clave)) {
                return u.getToken();
            }
        }
        throw new SecurityException("Credenciales inválidas");
    }
    private List<Usuario> usuarios;
}
