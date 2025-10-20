package Modelo.entidades;
import lombok.NoArgsConstructor;
@NoArgsConstructor
public class Administrador extends Usuario {
    public Administrador(String id,String nombre){
        super(id,nombre,"ADMINISTRADOR");
    }
}

