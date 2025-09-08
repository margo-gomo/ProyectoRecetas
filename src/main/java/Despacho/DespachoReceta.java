package Despacho;

import Gestores.GestorFarmaceuta;
import Gestores.GestorRecetas;
import Prescripcion.PrescripcionReceta;
import entidades.Farmaceuta;

import java.time.LocalDate;

public class DespachoReceta {
/*
    // Inicia el proceso: requiere "confeccionada" y fecha_retiro dentro de la ventana
    public boolean iniciarProceso(GestorFarmaceuta gestorFarmas, Farmaceuta usuario,
                                  PrescripcionReceta receta, GestorRecetas gestorRecetas) {
        if (!esFarmaceutaValido(gestorFarmas, usuario) || receta == null || gestorRecetas == null) return false;
        if (!"confeccionada".equalsIgnoreCase(receta.getEstado())) return false;
        if (!fechaDentroVentana(receta.getFecha_retiro())) return false;

        receta.setEstado("proceso");
        return upsertYGuardar(gestorRecetas, receta);
    }

    // Marca "lista": requiere que esté en "proceso"
    public boolean marcarLista(GestorFarmaceuta gestorFarmas, Farmaceuta usuario,
                               PrescripcionReceta receta, GestorRecetas gestorRecetas) {
        if (!esFarmaceutaValido(gestorFarmas, usuario) || receta == null || gestorRecetas == null) return false;
        if (!"proceso".equalsIgnoreCase(receta.getEstado())) return false;

        receta.setEstado("lista");
        return upsertYGuardar(gestorRecetas, receta);
    }

    // Entrega al paciente: requiere "lista" y validar ventana
    public boolean entregar(GestorFarmaceuta gestorFarmas, Farmaceuta usuario,
                            PrescripcionReceta receta, GestorRecetas gestorRecetas) {
        if (!esFarmaceutaValido(gestorFarmas, usuario) || receta == null || gestorRecetas == null) return false;
        if (!"lista".equalsIgnoreCase(receta.getEstado())) return false;
        if (!fechaDentroVentana(receta.getFecha_retiro())) return false;

        receta.setEstado("entregada");
        return upsertYGuardar(gestorRecetas, receta);
    }

    private boolean upsertYGuardar(GestorRecetas gestorRecetas, PrescripcionReceta receta) {
//        try { gestorRecetas.cargarXML("recetas"); } catch (Exception ignored) {}
//        gestorRecetas.upsertReceta(receta);
//        try { gestorRecetas.guardarXML("recetas"); return true; } catch (Exception e) { return false; }
//    }
        try {
            gestorRecetas.cargarXML(rutaArchivo);
        } catch (Exception e) {
            System.err.println("Advertencia: no se pudo cargar el archivo XML. Se continuará con los datos actuales.");
        }
        try {
            gestorRecetas.upsertReceta(receta);
            gestorRecetas.guardarXML(rutaArchivo);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar la receta: " + e.getMessage());
            return false;
        }
    }


    private boolean esFarmaceutaValido(GestorFarmaceuta gestor, Farmaceuta usuario) {
        if (gestor == null || usuario == null) return false;
        boolean registrado = gestor.existeFarmaceuta(usuario.getId());
        boolean esFarma = true;
        try { esFarma = usuario.getToken() == 2; } catch (Throwable ignored) {}
        return registrado && esFarma;
    }

    // True si fechaRetiro [HOY-VENTANA_DIAS, HOY+VENTANA_DIAS]
    private boolean fechaDentroVentana(LocalDate fechaRetiro) {
        if (fechaRetiro == null) return false;
        LocalDate hoy = hoy();
        LocalDate min = restarDias(hoy, VENTANA_DIAS);
        LocalDate max = sumarDias(hoy, VENTANA_DIAS);
        return esEntreInclusivo(fechaRetiro, min, max);
    }

    private LocalDate hoy() { return LocalDate.now(); }
    private LocalDate sumarDias(LocalDate fecha, int dias) { return fecha.plusDays(dias); }
    private LocalDate restarDias(LocalDate fecha, int dias) { return fecha.minusDays(dias); }
    private boolean esEntreInclusivo(LocalDate f, LocalDate ini, LocalDate fin) {
        return !f.isBefore(ini) && !f.isAfter(fin);

    }
    private static final int VENTANA_DIAS = 3;
    NO TOCAR*/
}
