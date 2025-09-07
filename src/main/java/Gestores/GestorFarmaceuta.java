package Gestores;

import entidades.Farmaceuta;
import DAO.FarmaceutaDAO;

import java.util.List;

public class GestorFarmaceuta {

    public GestorFarmaceuta(FarmaceutaDAO farmaceutas) {
        // Lista en memoria para los médicos
        this.farmaceutas=farmaceutas;
    }

    // Cantidad total de médicos
    public int cantidadFarmaceutas() {
        return farmaceutas.cantidad();
    }

    public List<Farmaceuta> ObtenerListaFarmaceutas(){
        return farmaceutas.obtenerListaFarmaceutas();
    }

    // Verifica si existe un médico con el ID indicado
    public boolean existeFarmaceuta(String id) {
        return farmaceutas.buscarPorId(id) != null;
    }

    // Busca un médico por ID exacto (o null si no existe)
    public Farmaceuta buscarPorID(String id) {
        return farmaceutas.buscarPorId(id);
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Farmaceuta buscarPorNombre(String nombre) {
        return  farmaceutas.buscarPorNombre(nombre);
    }

    // Agrega un médico si el ID no está repetido
    public Farmaceuta agregar(Farmaceuta farmaceuta) throws IllegalArgumentException { // (antes: aregarFarmaceuta)
        return farmaceutas.agregar(farmaceuta);
    }

    // Reemplaza los datos de un médico existente (match por ID)
    public Farmaceuta actualizar(Farmaceuta farmaceuta) throws IllegalArgumentException { // (antes: pacienteporactualizar)
        return farmaceutas.actualizar(farmaceuta);
    }
    // Elimina un médico por ID
    public Farmaceuta eliminar(String id) throws IllegalArgumentException {
        return  farmaceutas.eliminar(id);
    }
    // Cambia la clave de un farmaceuta existente (match por ID)
    public Farmaceuta cambiarClave(String id, String clave){
        return  farmaceutas.cambiarClave(id,clave);
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
