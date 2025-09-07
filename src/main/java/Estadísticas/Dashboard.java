package Estadísticas;
import Prescripcion.PrescripcionReceta;
import Prescripcion.Indicaciones;
import Gestores.GestorRecetas;
import entidades.Medicamento;
import java.util.Map;
import java.util.TreeMap;
import java.time.Month;
import java.util.stream.Collectors;

public class Dashboard {
    public Dashboard(GestorRecetas gestorRecetas) {
        recetas=gestorRecetas;
    }
    public Map<Month,Integer>medicamentoPorMes(Medicamento medicamento, int mesInicio, int mesFin){
        Map<Month, Integer> estadisticas = new TreeMap<>();
        for (PrescripcionReceta receta : recetas.obtenerListaRecetas()){
            if(receta.getFecha_confeccion()!=null){
                Month mes=receta.getFecha_confeccion().getMonth();
                if(mes.getValue()>=mesInicio&&mes.getValue()<=mesFin){
                    int total=0;
                    for (Indicaciones indicacion : receta.obtenerListaIndicaciones()){
                        if(indicacion.getMedicamento().getCodigo()==medicamento.getCodigo()){
                            total+= indicacion.getCantidad();
                        }
                    }
                    estadisticas.put(mes,estadisticas.getOrDefault(mes,0)+total);
                }
            }
        }
        return estadisticas;
    }
    public Map<String, Long>recetasPorEstado(){
        return recetas.obtenerListaRecetas().stream().collect(Collectors.groupingBy(PrescripcionReceta::getEstado,Collectors.counting()));
    }
    private GestorRecetas recetas;
}