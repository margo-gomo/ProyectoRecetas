package Controlador;
import entidades.Medicamento;
import Modelo.ModeloMedicamento;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class ControladorMedicamento {
    public ControladorMedicamento(ModeloMedicamento modelo){
        this.modelo=modelo;
    }
    public ControladorMedicamento(){
        this(new ModeloMedicamento());
    }
    public void init() {
        try {
            modelo.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
    }
    public Medicamento buscarPorCodigo(int codigo) {
        return modelo.buscarPorCodigo(codigo);
    }

    public Medicamento buscarPorDescripcion(String descripcion) {
        return  modelo.buscarPorDescripcion(descripcion);
    }

    public Medicamento agregar(Medicamento medicamento) throws IllegalArgumentException {
        return modelo.agregar(medicamento);
    }

    public Medicamento actualizar(Medicamento medicamento) throws IllegalArgumentException {
        return modelo.actualizar(medicamento);
    }

    public Medicamento eliminar(int codigo) throws IllegalArgumentException {
        return  modelo.eliminar(codigo);
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
    private ModeloMedicamento modelo;
}
