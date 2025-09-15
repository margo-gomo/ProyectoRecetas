package Modelo.entidades;

public interface Usuario {
    String getId();
    String getClave();
    void setClave(String clave);
    int getToken();
    String getNombre();
}
