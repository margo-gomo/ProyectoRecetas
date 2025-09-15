package Modelo;
import Modelo.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
public class login {
    public login() {
        usuarios = new ArrayList<>();
    }
    public int cantidad(){
        return usuarios.size();
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
    public String devolverId(String id,String clave) throws SecurityException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && u.getClave().equals(clave)) {
                return u.getId();
            }
        }
        throw new SecurityException("Credenciales inválidas");
    }
        public int cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar)throws IllegalArgumentException, SecurityException{
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && u.getClave().equals(claveActual)) {
                if(!claveNueva.equals(claveConfirmar))
                    throw new IllegalArgumentException("La clave por confirmar no coincide con la clave nueva propuesta.");
                if(claveNueva.isEmpty()||claveConfirmar.isEmpty())
                    throw new IllegalArgumentException("Rellene todos los camppos");
                else {
                    u.setClave(claveNueva);
                    return u.getToken();
                }
            }
        }
        throw new SecurityException("No existe un usuario con esas credenciales");
    }
    private List<Usuario> usuarios;
}
