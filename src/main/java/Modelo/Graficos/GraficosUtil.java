package Modelo.Graficos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartUtils;

import java.awt.BasicStroke;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Utilidades para crear gráficos JFreeChart a partir de
 * los Maps que produce GestorRecetaIndicacion.
 */
public class GraficosUtil {

    /**
     * Crea un ChartPanel con un gráfico de líneas.
     *
     * @param seriesPorMes Map<serieNombre, Map<"YYYY-M", cantidad>>
     * @return ChartPanel listo para añadir a un JFrame/JPanel
     */
    public ChartPanel crearGraficoLineas(Map<String, Map<String, Integer>> seriesPorMes) {
        // 1) Determinar conjunto ordenado de meses (usar YearMonth para ordenar cronológicamente)
        final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M");
        SortedSet<YearMonth> mesesOrdenados = new TreeSet<>();

        for (Map<String, Integer> serie : seriesPorMes.values()) {
            for (String mesLabel : serie.keySet()) {
                try {
                    YearMonth ym = YearMonth.parse(mesLabel, fmt);
                    mesesOrdenados.add(ym);
                } catch (DateTimeParseException ex) {
                    // intentar con yyyy-MM (por si acaso)
                    try {
                        YearMonth ym = YearMonth.parse(mesLabel, DateTimeFormatter.ofPattern("yyyy-MM"));
                        mesesOrdenados.add(ym);
                    } catch (DateTimeParseException ex2) {
                        // ignorar etiquetas inválidas
                    }
                }
            }
        }

        // Si no hay meses (map vacío), salir con dataset vacío
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (!mesesOrdenados.isEmpty()) {
            // convertir YearMonth a etiquetas en formato consistente "YYYY-M" (o "YYYY-MM" si prefieres)
            List<String> mesesEtiquetas = new ArrayList<>();
            for (YearMonth ym : mesesOrdenados) {
                // aquí usaremos "YYYY-M" (sin padding). Cambia si prefieres "YYYY-MM":
                mesesEtiquetas.add(ym.getYear() + "-" + (ym.getMonthValue()));
            }

            // 2) Rellenar dataset: para cada serie (medicamento) completar todos los meses (0 si no hay)
            for (Map.Entry<String, Map<String, Integer>> serieEntry : seriesPorMes.entrySet()) {
                String nombreSerie = serieEntry.getKey();
                Map<String, Integer> datosSerie = serieEntry.getValue();

                for (String mes : mesesEtiquetas) {
                    Integer val = datosSerie.get(mes);
                    if (val == null) val = 0;
                    dataset.addValue(val, nombreSerie, mes);
                }
            }
        }

        // 3) Crear gráfico
        JFreeChart chart = ChartFactory.createLineChart(
                "Medicamentos",
                "Mes",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true,   // legend
                true,   // tooltips
                false   // urls
        );

        // 4) Estética: adaptar fuente, stroke y mostrar puntos
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(java.awt.Color.LIGHT_GRAY);
        plot.setDomainGridlinesVisible(false);
        plot.setInsets(new RectangleInsets(5, 5, 5, 5));

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setDefaultShapesVisible(true);
        renderer.setDefaultShapesFilled(true);
        renderer.setDefaultStroke(new BasicStroke(2.0f));
        renderer.setDefaultItemLabelFont(new Font("Dialog", Font.PLAIN, 10));
        plot.setRenderer(renderer);

        chart.getTitle().setFont(new Font("Dialog", Font.BOLD, 16));
        chart.getLegend().setItemFont(new Font("Dialog", Font.PLAIN, 12));

        // 5) Devolver panel
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new java.awt.Dimension(600, 400));
        return panel;
    }

    /**
     * Crea un ChartPanel con un gráfico de pastel (pie) a partir de un mapa estado->cantidad.
     *
     * @param conteoPorEstado Map estado -> cantidad
     *
     * @return ChartPanel listo para insertar en la UI
     */
    public ChartPanel crearGraficoPastel(Map<String, Integer> conteoPorEstado) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> e : conteoPorEstado.entrySet()) {
            dataset.setValue(e.getKey(), e.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Recetas por estado",
                dataset,
                true,   // legend
                true,   // tooltips
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));
        plot.setCircular(true);
        plot.setSimpleLabels(false);
        plot.setLabelFont(new Font("Dialog", Font.PLAIN, 11));
        chart.getTitle().setFont(new Font("Dialog", Font.BOLD, 16));
        chart.getLegend().setItemFont(new Font("Dialog", Font.PLAIN, 12));

        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new java.awt.Dimension(450, 350));
        return panel;
    }

    /**
     * Guarda un JFreeChart en PNG.
     * @param chart gráfico a guardar
     * @param file archivo destino (.png)
     * @param width anchura
     * @param height altura
     * @throws IOException si ocurre error IO
     */
    public void guardarChartPNG(JFreeChart chart, File file, int width, int height) throws IOException {
        ChartUtils.saveChartAsPNG(file, chart, width, height);
    }
}
