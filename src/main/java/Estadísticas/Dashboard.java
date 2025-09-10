/*package Estadísticas;
import Prescripcion.PrescripcionReceta;
import Prescripcion.Indicaciones;
import entidades.Medicamento;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.Collections;
import java.time.YearMonth;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Dashboard {
    public Map<YearMonth, Integer> medicamentosPorMes(
            List<PrescripcionReceta> recetas,
            List<Medicamento> medicamentosSeleccionados,
            LocalDate startDate, LocalDate endDate) {

        Map<YearMonth, Integer> estadisticas = new TreeMap<>();
        if (recetas == null || medicamentosSeleccionados == null || startDate == null || endDate == null) {
            return estadisticas;
        }

        // Prepara un set de códigos para lookup O(1)
        Set<Integer> codigos = new HashSet<>();
        for (Medicamento m : medicamentosSeleccionados) {
            if (m != null) codigos.add(m.getCodigo());
        }

        // Itera recetas
        for (PrescripcionReceta receta : recetas) {
            LocalDate fc = receta.getFecha_confeccion();
            if (fc == null) continue;
            if (fc.isBefore(startDate) || fc.isAfter(endDate)) continue;

            YearMonth ym = YearMonth.from(fc);
            int totalReceta = 0;
            for (Indicaciones ind : receta.obtenerListaIndicaciones()) {
                if (ind == null || ind.getMedicamento() == null) continue;
                if (codigos.contains(ind.getMedicamento().getCodigo())) {
                    totalReceta += ind.getCantidad();
                }
            }
            estadisticas.put(ym, estadisticas.getOrDefault(ym, 0) + totalReceta);
        }

        // Asegurar que todos los YearMonth del rango existan (llenar con 0)
        YearMonth startYM = YearMonth.from(startDate);
        YearMonth endYM = YearMonth.from(endDate);
        YearMonth cur = startYM;
        while (!cur.isAfter(endYM)) {
            estadisticas.putIfAbsent(cur, 0);
            cur = cur.plusMonths(1);
        }

        return estadisticas;
    }


    public Map<String, Long> recetasPorEstado(List<PrescripcionReceta> recetas) {
        if (recetas == null) return Collections.emptyMap();
        return recetas.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(PrescripcionReceta::getEstado, Collectors.counting()));
    }
}*/
