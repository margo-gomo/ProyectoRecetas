package Modelo.Estadísticas;
import Modelo.entidades.Receta.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Historicos {
    public Historicos() {
        receta=new Receta();
    }
    public Receta buscarPorCodigo(Receta receta){
        this.receta=receta;
        if(receta!=null)
            return receta;
        else
            return null;
    }
    public Receta buscarPorPacienteFecha(Receta receta){
        this.receta=receta;
        if(receta!=null)
            return receta;
        else
            return null;
    }
    public List<Indicacion>mostrarIndicaciones(){
        if(receta!=null)
            return receta.obtenerListaIndicaciones();
        else
            return null;
    }
    private Receta receta;
}
