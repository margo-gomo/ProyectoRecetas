package Gestores;

import entidades.Farmaceuta;
import DAO.FarmaceutaDAO;

import java.util.List;

public class GestorFarmaceuta {

    public GestorFarmaceuta(FarmaceutaDAO farmaceutas) {
        // Lista en memoria para los médicos
        this.farmaceutas=farmaceutas;
    }

    public int cantidad() {
        return farmaceutas.cantidad();
    }

    public List<Farmaceuta> obtenerListaFarmaceutas(){
        return farmaceutas.obtenerListaFarmaceutas();
    }

    public boolean existeFarmaceuta(String id) {
        return farmaceutas.buscarPorId(id) != null;
    }

    public Farmaceuta buscarPorID(String id) {
        return farmaceutas.buscarPorId(id);
    }

    public Farmaceuta buscarPorNombre(String nombre) {
        return  farmaceutas.buscarPorNombre(nombre);
    }

    public Farmaceuta agregar(Farmaceuta farmaceuta) throws IllegalArgumentException {
        return farmaceutas.agregar(farmaceuta);
    }

    public Farmaceuta actualizar(Farmaceuta farmaceuta) throws IllegalArgumentException {
        return farmaceutas.actualizar(farmaceuta);
    }

    public Farmaceuta eliminar(String id) throws IllegalArgumentException {
        return  farmaceutas.eliminar(id);
    }
    public Farmaceuta cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar){
        return  farmaceutas.cambiarClave(id, claveActual, claveNueva, claveConfirmar);
    }

    // String legible de la lista (debug/impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Farmaceuta farmaceuta : farmaceutas.obtenerListaFarmaceutas()) {
            sb.append(String.format("%n\t%s,", farmaceuta));
        }
        sb.append("\n]");
        return sb.toString();
    }

    private final FarmaceutaDAO farmaceutas;
}
