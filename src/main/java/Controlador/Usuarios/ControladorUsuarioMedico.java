package Controlador.Usuarios;
import Prescripcion.PrescripcionReceta;
import Estadísticas.Dashboard;
import entidades.Medicamento;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;
public class ControladorUsuarioMedico{
    public ControladorUsuarioMedico(ModeloRecetas modelo) {
        this.modelo = modelo;
    }
    public ControladorUsuarioMedico() {
        this(new ModeloRecetas());
    }
    public void init() {
        try {
            modelo.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
    }
    public PrescripcionReceta buscarReceta(int idPaciente, LocalDate fechaConfeccion) {
        return modelo.buscarReceta(idPaciente, fechaConfeccion);
    }

    public PrescripcionReceta agregar(PrescripcionReceta receta) throws IllegalArgumentException {
        return  modelo.agregar(receta);
    }

    public PrescripcionReceta actualizar(PrescripcionReceta recetaPorActualizar) throws IllegalArgumentException {
        return modelo.actualizar(recetaPorActualizar);
    }

    public PrescripcionReceta eliminar(int idPaciente, LocalDate fechaConfeccion) throws IllegalArgumentException {
        return modelo.eliminar(idPaciente, fechaConfeccion);
    }
    public void cerrarAplicacion() {
        try {
            modelo.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }

        System.out.println("Aplicación finalizada..");
        System.exit(0);
    }
    public Map<YearMonth,Integer> medicamentoPorMes(List<Medicamento> medicamentosSeleccionados,
                                                    LocalDate startDate, LocalDate endDate){
        Dashboard dashboard = new Dashboard();
        return dashboard.medicamentosPorMes(modelo.obtenerListaRecetas(),medicamentosSeleccionados,startDate,endDate);
    }
    public Map<String, Long>recetasPorEstado(){
        Dashboard dashboard = new Dashboard();
        return dashboard.recetasPorEstado(modelo.obtenerListaRecetas());
    }
    private ModeloRecetas modelo;
}
