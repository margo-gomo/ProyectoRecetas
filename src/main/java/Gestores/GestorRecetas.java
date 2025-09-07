package Gestores;

import Prescripcion.PrescripcionReceta;
import DAO.RecetaDAO;
import java.time.LocalDate;
import java.util.List;

public class GestorRecetas {

    public GestorRecetas(RecetaDAO recetas) {
        this.recetas = recetas;
    }

    public int cantidadRecetas() {
        return recetas.cantidad();
    }
    public List<PrescripcionReceta> obtenerListaRecetas() {
        return recetas.obtenerListaPrescripcionRecetas();
    }

    public boolean existeReceta(int idPaciente, LocalDate fechaConfeccion) {
        return recetas.buscarReceta(idPaciente, fechaConfeccion)!=null;
    }

    public PrescripcionReceta buscarReceta(int idPaciente, LocalDate fechaConfeccion) {
        return recetas.buscarReceta(idPaciente, fechaConfeccion);
    }

    public PrescripcionReceta agregar(PrescripcionReceta receta) throws IllegalArgumentException {
        return  recetas.agregar(receta);
    }

    public PrescripcionReceta actualizar(PrescripcionReceta recetaPorActualizar) throws IllegalArgumentException {
        return recetas.actualizar(recetaPorActualizar);
    }

    public PrescripcionReceta eliminar(int idPaciente, LocalDate fechaConfeccion) throws IllegalArgumentException {
        return recetas.eliminar(idPaciente, fechaConfeccion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (PrescripcionReceta receta : recetas.obtenerListaPrescripcionRecetas()) {
            sb.append(String.format("%n\t%s,", receta));
        }
        sb.append("\n]");
        return sb.toString();
    }

    private boolean fechasIguales(LocalDate a, LocalDate b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }

    private final RecetaDAO recetas;
}
