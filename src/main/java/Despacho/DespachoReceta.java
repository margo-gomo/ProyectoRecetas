package Despacho;

import Gestores.GestorFarmaceuta;
import Gestores.GestorRecetas;
import Prescripcion.PrescripcionReceta;
import entidades.Farmaceuta;

import java.time.LocalDate;

public class DespachoReceta {
    // Inicia el proceso: requiere "confeccionada" y fecha_retiro dentro de la ventana
    public static PrescripcionReceta iniciarProceso(PrescripcionReceta receta) throws IllegalArgumentException {
        if ("confeccionada".equalsIgnoreCase(receta.getEstado())){
            if (!fechaDentroVentana(receta.getFecha_retiro()))
                receta.setEstado("proceso");
            else
                throw new IllegalArgumentException(receta.getEstado());
        }
        else
            throw new IllegalArgumentException(receta.getFecha_retiro().toString());

        return receta;
    }

    // Marca "lista": requiere que esté en "proceso"
    public static PrescripcionReceta marcarLista(PrescripcionReceta receta) throws IllegalArgumentException {
        if ("proceso".equalsIgnoreCase(receta.getEstado())) receta.setEstado("lista");
        else
            throw new IllegalArgumentException(receta.getEstado());
        return receta;
    }

    // Entrega al paciente: requiere "lista" y validar ventana
    public PrescripcionReceta entregar(PrescripcionReceta receta) throws IllegalArgumentException {
        if ("lista".equalsIgnoreCase(receta.getEstado())){
            if (fechaDentroVentana(receta.getFecha_retiro()))
                receta.setEstado("entregada");
            else
                throw new IllegalArgumentException(receta.getEstado());
        }
        else
            throw new IllegalArgumentException(receta.getFecha_retiro().toString());
        return receta;
    }


    // True si fechaRetiro [HOY-VENTANA_DIAS, HOY+VENTANA_DIAS]
    private static boolean fechaDentroVentana(LocalDate fechaRetiro) {
        if (fechaRetiro == null) return false;
        LocalDate hoy = hoy();
        LocalDate min = restarDias(hoy, VENTANA_DIAS);
        LocalDate max = sumarDias(hoy, VENTANA_DIAS);
        return esEntreInclusivo(fechaRetiro, min, max);
    }

    private static LocalDate hoy() { return LocalDate.now(); }
    private static LocalDate sumarDias(LocalDate fecha, int dias) { return fecha.plusDays(dias); }
    private static LocalDate restarDias(LocalDate fecha, int dias) { return fecha.minusDays(dias); }
    private static boolean esEntreInclusivo(LocalDate f, LocalDate ini, LocalDate fin) {
        return !f.isBefore(ini) && !f.isAfter(fin);

    }
    private static final int VENTANA_DIAS = 3;
}