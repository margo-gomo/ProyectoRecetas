package Modelo.Estadísticas;

import Modelo.entidades.Receta.Receta;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Medicamento;
import lombok.Getter;

import java.util.*;
import java.time.YearMonth;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Dashboard {

    public Dashboard(){
        medicamentosSeleccionados = new ArrayList<>();
    }

    public int cantidad(){
        return medicamentosSeleccionados.size();
    }

    public void limpiar(){
        medicamentosSeleccionados.clear();
    }

    public void agregarMedicameno(Medicamento medicamento){
        medicamentosSeleccionados.add(medicamento);
    }

    public Map<YearMonth, Integer> medicamentosPorMesUnidades(
            List<Receta> recetas,
            LocalDate startDate, LocalDate endDate,
            String nombreMedicamento) {

        Map<YearMonth, Integer> estadisticas = new TreeMap<>();
        if (recetas == null || startDate == null || endDate == null) {
            return estadisticas;
        }

        for (Receta receta : recetas) {
            LocalDate fc = receta.getFecha_confeccion();
            if (fc == null) continue;
            if (fc.isBefore(startDate) || fc.isAfter(endDate)) continue;

            boolean contiene = (nombreMedicamento == null || nombreMedicamento.isEmpty());
            int totalReceta = 0;

            for (Indicacion ind : receta.obtenerListaIndicaciones()) {
                if (ind == null || ind.getMedicamento() == null) continue;
                String nombre = ind.getMedicamento().getNombre();
                if (contiene || (nombre != null && nombre.equalsIgnoreCase(nombreMedicamento))) {
                    totalReceta += ind.getCantidad();
                }
            }

            if (totalReceta > 0) {
                YearMonth ym = YearMonth.from(fc);
                estadisticas.put(ym, estadisticas.getOrDefault(ym, 0) + totalReceta);
            }
        }

        return estadisticas;
    }

    public Map<YearMonth, Integer> medicamentosPorMesRecetas(
            List<Receta> recetas,
            LocalDate startDate, LocalDate endDate,
            String nombreMedicamento) {

        Map<YearMonth, Integer> estadisticas = new TreeMap<>();
        if (recetas == null || startDate == null || endDate == null) {
            return estadisticas;
        }

        for (Receta receta : recetas) {
            LocalDate fc = receta.getFecha_confeccion();
            if (fc == null) continue;
            if (fc.isBefore(startDate) || fc.isAfter(endDate)) continue;

            boolean contiene = (nombreMedicamento == null || nombreMedicamento.isEmpty());
            boolean match = false;

            for (Indicacion ind : receta.obtenerListaIndicaciones()) {
                if (ind == null || ind.getMedicamento() == null) continue;
                String nombre = ind.getMedicamento().getNombre();
                if (contiene || (nombre != null && nombre.equalsIgnoreCase(nombreMedicamento))) {
                    match = true;
                    break;
                }
            }

            if (match) {
                YearMonth ym = YearMonth.from(fc);
                estadisticas.put(ym, estadisticas.getOrDefault(ym, 0) + 1);
            }
        }

        YearMonth startYM = YearMonth.from(startDate);
        YearMonth endYM = YearMonth.from(endDate);
        YearMonth cur = startYM;
        while (!cur.isAfter(endYM)) {
            estadisticas.putIfAbsent(cur, 0);
            cur = cur.plusMonths(1);
        }

        return estadisticas;
    }

    public Map<String, Long> recetasPorEstado(
            List<Receta> recetas,
            LocalDate startDate,
            LocalDate endDate,
            String nombreMedicamento) {

        if (recetas == null || startDate == null || endDate == null) {
            return Collections.emptyMap();
        }

        return recetas.stream()
                .filter(r -> r != null && r.getFecha_confeccion() != null)
                .filter(r -> !r.getFecha_confeccion().isBefore(startDate) &&
                        !r.getFecha_confeccion().isAfter(endDate))
                .filter(r -> {
                    if (nombreMedicamento == null || nombreMedicamento.isEmpty()) {
                        return true;
                    }
                    return r.obtenerListaIndicaciones().stream()
                            .anyMatch(ind -> ind != null && ind.getMedicamento() != null &&
                                    nombreMedicamento.equalsIgnoreCase(ind.getMedicamento().getNombre()));
                })
                .collect(Collectors.groupingBy(Receta::getEstado, Collectors.counting()));
    }

    @Getter
    private List<Medicamento> medicamentosSeleccionados;
}
