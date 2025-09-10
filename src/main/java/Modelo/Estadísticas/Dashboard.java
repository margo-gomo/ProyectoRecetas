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
        medicamentosSeleccionados=new ArrayList<>();
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
    public Map<YearMonth, Integer> medicamentosPorMes(
            List<Receta> recetas,
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
        for (Receta receta : recetas) {
            LocalDate fc = receta.getFecha_confeccion();
            if (fc == null) continue;
            if (fc.isBefore(startDate) || fc.isAfter(endDate)) continue;

            YearMonth ym = YearMonth.from(fc);
            int totalReceta = 0;
            for (Indicacion ind : receta.obtenerListaIndicaciones()) {
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


    public Map<String, Long> recetasPorEstado(List<Receta> recetas) {
        if (recetas == null) return Collections.emptyMap();
        return recetas.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Receta::getEstado, Collectors.counting()));
    }
    @Getter
    private List<Medicamento> medicamentosSeleccionados;
}
