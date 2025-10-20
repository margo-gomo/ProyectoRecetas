package Modelo.entidades;
import lombok.NoArgsConstructor;
@NoArgsConstructor
public class Farmaceuta extends Usuario {
    public Farmaceuta(String id,String nombre){
        super(id,nombre,"FARMACEUTA");
    }
}
