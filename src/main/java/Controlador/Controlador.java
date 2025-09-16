package Controlador;
import Modelo.entidades.*;
import Modelo.Gestores.*;
import Modelo.DAO.*;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import Modelo.login;
import Modelo.Estadísticas.*;
import jakarta.xml.bind.JAXBException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import lombok.Setter;
public class Controlador {
    public Controlador(GestorAdministrador modeloAdministrador,GestorMedico modeloMedico, GestorFarmaceuta modeloFarmaceuta,
                       GestorMedicamento modeloMedicamento,GestorPaciente modeloPaciente,GestorRecetas modeloRecetas,GestorIndicacion modeloIndicacion) {
        this.modeloAdministrador = modeloAdministrador;
        this.modeloMedico = modeloMedico;
        this.modeloFarmaceuta = modeloFarmaceuta;
        this.modeloPaciente = modeloPaciente;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetas = modeloRecetas;
        this.modeloIndicacion = modeloIndicacion;
        usuarios=new login();
        dashboard=new Dashboard();
        historial=new Historicos();
    }
    public Controlador() {
        this(new GestorAdministrador(),new GestorMedico(),new GestorFarmaceuta(),new GestorMedicamento(),new GestorPaciente(),new GestorRecetas(),new GestorIndicacion());
        usuarios=new login();
        dashboard=new Dashboard();
        historial=new Historicos();
    }
    public int devolverToken(String id, String clave) throws SecurityException{
        token= usuarios.devolverToken(id, clave);
        return token;
    }
    public String devolverId(String id, String clave) throws SecurityException{
        idUsuario=usuarios.devolverId(id, clave);
        return idUsuario;
    }
    public void cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar)throws IllegalArgumentException, SecurityException{
        int tipo = usuarios.cambiarClave(id, claveActual, claveNueva, claveConfirmar);

        if (tipo == 1) {
            Medico medico = buscarMedicoPorId(id);
            medico.setClave(claveConfirmar);
            actualizarUsuarioMedico(medico);
        } else if (tipo == 2) {
            Farmaceuta farmaceuta=buscarFarmaceutaPorId(id);
            farmaceuta.setClave(claveConfirmar);
            actualizarUsuarioFarmaceuta(farmaceuta);
        }
    }
    public void init() {
        try{
            modeloAdministrador.cargar();
        }catch(JAXBException | FileNotFoundException ex){
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloMedico.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloPaciente.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloFarmaceuta.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloMedicamento.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloRecetas.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        usuarios.cargarUsuarios(modeloAdministrador.obtenerListaAdministradores());
        usuarios.cargarUsuarios(modeloMedico.obtenerListaMedicos());
        usuarios.cargarUsuarios(modeloFarmaceuta.obtenerListaFarmaceutas());
    }
    public List<Medico> obtenerListaMedicos(){
        return modeloMedico.obtenerListaMedicos();
    }
    public Medico buscarMedicoPorId(String id) {
        return modeloMedico.buscarPorId(id);
    }
    public Medico buscarMedicoPorNombre(String nombre){
        return modeloMedico.buscarPorNombre(nombre);
    }
    public Medico agregarMedico(String id,String nom,String esp) throws IllegalArgumentException, SecurityException{
        Medico medico = new Medico();
        medico.setId(id);
        medico.setClave(id);
        medico.setNombre(nom);
        medico.setEspecialidad(esp);
        return modeloMedico.agregar(medico,token);
    }
    public Medico actualizarMedico(String id,String nombre,String especialidad) throws IllegalArgumentException, SecurityException{
        Medico medico = buscarMedicoPorId(id);
        if(medico==null){
            throw new IllegalArgumentException("No existe un médico con ID: "+id);
        }
        medico.setNombre(nombre);
        medico.setEspecialidad(especialidad);
        return modeloMedico.actualizar(medico,token);
    }
    public Medico actualizarUsuarioMedico(Medico medico)throws IllegalArgumentException, SecurityException{
        if(medico==null){
            throw new IllegalArgumentException("No existe un médico con ese ID");
        }
        return modeloMedico.actualizar(medico,0);
    }
    public Medico eliminarMedico(String id) throws IllegalArgumentException, SecurityException{
        return modeloMedico.eliminar(id,token);
    }
    public List<Farmaceuta> obtenerListaFarmaceutas(){
        return modeloFarmaceuta.obtenerListaFarmaceutas();
    }
    public Farmaceuta buscarFarmaceutaPorId(String id) {
        return modeloFarmaceuta.buscarPorId(id);
    }
    public Farmaceuta buscarFarmaceutaPorNombre(String nombre) {
        return modeloFarmaceuta.buscarPorNombre(nombre);
    }
    public Farmaceuta agregarFarmaceuta(String id,String nombre)  throws IllegalArgumentException, SecurityException{
        Farmaceuta farmaceuta = new Farmaceuta();
        farmaceuta.setId(id);
        farmaceuta.setClave(id);
        farmaceuta.setNombre(nombre);
        return modeloFarmaceuta.agregar(farmaceuta,token);
    }
    public Farmaceuta actualizarFarmaceuta(String id,String nombre) throws IllegalArgumentException, SecurityException{
        Farmaceuta farmaceuta = buscarFarmaceutaPorId(id);
        if(farmaceuta==null){
            throw new IllegalArgumentException("No existe un farmaceuta con ID: "+id);
        }
        if (nombre.isEmpty())
            throw new IllegalArgumentException("Rellene todos los campos");
        farmaceuta.setNombre(nombre);
        return modeloFarmaceuta.actualizar(farmaceuta,token);
    }
    public Farmaceuta actualizarUsuarioFarmaceuta(Farmaceuta farmaceuta)throws IllegalArgumentException, SecurityException{
        if(farmaceuta==null){
            throw new IllegalArgumentException("No existe un farmaceuta con ese ID");
        }
        return modeloFarmaceuta.actualizar(farmaceuta,0);
    }
    public Farmaceuta eliminarFarmaceuta(String id) throws IllegalArgumentException, SecurityException{
        return modeloFarmaceuta.eliminar(id,token);
    }
    public List<Paciente> obtenerListaPacientes(){
        return modeloPaciente.obtenerListaPacientes();
    }
    public Paciente buscarPacientePorId(int id){
        return modeloPaciente.buscarPorId(id);
    }
    public Paciente buscarPacientePorNombre(String nombre){
        return modeloPaciente.buscarPorNombre(nombre);
    }
    public Paciente agregarPaciente(int id, String nombre, int telefono,LocalDate fecha) throws IllegalArgumentException, SecurityException{
        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNombre(nombre);
        paciente.setTelefono(telefono);
        paciente.setFecha_nacimiento(fecha);
        return modeloPaciente.agregar(paciente,token);
    }
    public Paciente actualizarPaciente(int id, String nombre, int telefono,LocalDate fecha) throws IllegalArgumentException, SecurityException{
        Paciente paciente = buscarPacientePorId(id);
        if(paciente==null)
            throw new IllegalArgumentException("No existe un paciente con ID: "+id);
        paciente.setNombre(nombre);
        paciente.setTelefono(telefono);
        paciente.setFecha_nacimiento(fecha);
        return modeloPaciente.actualizar(paciente,token);
    }
    public Paciente eliminarPaciente(int id) throws IllegalArgumentException, SecurityException{
        return modeloPaciente.eliminar(id,token);
    }
    public List<Medicamento> obtenerListaMedicamentos(){
        return modeloMedicamento.obtenerListaMedicamentos();
    }
    public Medicamento buscarMedicamentoPorCodigo(int codigo){
        return modeloMedicamento.buscarPorCodigo(codigo);
    }
    public Medicamento buscarMedicamentoPorDescripcion(String descripcion){
        return modeloMedicamento.buscarPorDescripcion(descripcion);
    }
    public Medicamento agregarMedicamento(int codigo,String nombre,String descripcion) throws IllegalArgumentException, SecurityException{
        Medicamento medicamento = new Medicamento();
        medicamento.setCodigo(codigo);
        medicamento.setNombre(nombre);
        medicamento.setDescripcion(descripcion);
        return modeloMedicamento.agregar(medicamento,token);
    }
    public Medicamento actualizarMedicamento(int codigo,String nombre,String descripcion) throws IllegalArgumentException, SecurityException{
        Medicamento medicamento = buscarMedicamentoPorCodigo(codigo);
        if(medicamento==null)
            throw new IllegalArgumentException("No existe un medicamento con ID: "+codigo);
        if (nombre.isEmpty()||descripcion.isEmpty())
            throw new IllegalArgumentException("Rellene todos los campos");
        medicamento.setNombre(nombre);
        medicamento.setDescripcion(descripcion);
        return modeloMedicamento.actualizar(medicamento,token);
    }
    public Medicamento eliminarMedicamento(int codigo) throws IllegalArgumentException, SecurityException{
        return modeloMedicamento.eliminar(codigo,token);
    }
    public List<Receta> obtenerListaRecetas(){
        return modeloRecetas.obtenerListaRecetas();
    }
    public  Receta buscarRecetaPorCodigo(String codigo){
        return modeloRecetas.buscarRecetaPorCodigo(codigo);
    }
    public Receta buscarReceta(int idPaciente, LocalDate fechaConfeccion){
        return modeloRecetas.buscarReceta(idPaciente, fechaConfeccion);
    }
    public Receta agregarReceta(String codigo,List<Indicacion>indicaciones,int id,LocalDate fecha_confeccion,LocalDate fecha_retiro) throws IllegalArgumentException, SecurityException{
        Receta receta = new Receta();
        receta.setCodigo(codigo);
        receta.agregarIndicaciones(indicaciones);
        receta.setPaciente(buscarPacientePorId(id));
        receta.setFecha_confeccion(fecha_confeccion);
        receta.setFecha_retiro(fecha_retiro);
        return modeloRecetas.agregar(receta,token);
    }
    public Receta actualizarReceta(String codigo,List<Indicacion>indicaciones,int id,LocalDate fecha_confeccion,LocalDate fecha_retiro) throws IllegalArgumentException, SecurityException{
        Receta receta = buscarRecetaPorCodigo(codigo);
        if(receta==null)
            throw new IllegalArgumentException("No existe un receta con código: "+codigo);
        receta.actualizarIndicaciones(indicaciones);
        receta.setPaciente(buscarPacientePorId(id));
        receta.setFecha_confeccion(fecha_confeccion);
        receta.setFecha_retiro(fecha_retiro);
        return modeloRecetas.actualizar(receta,token);
    }
    public List<Indicacion> obtenerListaIndicaciones(){
        return modeloIndicacion.obtenerListaIndicaciones();
    }
    public Indicacion buscarIndicacion(int codigo){
        return modeloIndicacion.buscarIndicacion(codigo);
    }
    public Indicacion agregarIndicacion(Medicamento medicamento,int cantidad,String ind, int duracion) throws IllegalArgumentException, SecurityException{
        Indicacion indicacion=new Indicacion();
        indicacion.setMedicamento(medicamento);
        indicacion.setCantidad(cantidad);
        indicacion.setDescripcion(ind);
        indicacion.setDuracion(duracion);
        return modeloIndicacion.agregar(indicacion,token);
    }
    public Indicacion actualizarIndicacion(Medicamento medicamento,int cantidad,String ind, int duracion)throws IllegalArgumentException, SecurityException{
        Indicacion indicacion=buscarIndicacion(medicamento.getCodigo());
        if(medicamento==null)
            throw new IllegalArgumentException("No existe un medicamento con codigo: "+medicamento.getCodigo()+" en la receta");
        indicacion.setCantidad(cantidad);
        indicacion.setDescripcion(ind);
        indicacion.setDuracion(duracion);
        return modeloIndicacion.actualizar(indicacion,token);
    }
    public Indicacion eliminarIndicacion(int codigo) throws IllegalArgumentException, SecurityException{
        return modeloIndicacion.eliminar(codigo,token);
    }
    public void iniciarProceso(String codigo) throws IllegalArgumentException{
        Receta receta = buscarRecetaPorCodigo(codigo);
        if(receta==null)
            throw new IllegalArgumentException("No existe un receta con codigo: "+codigo);
        modeloRecetas.iniciarProceso(receta,token,idUsuario);
    }
    public void marcarLista(String codigo) throws IllegalArgumentException{
        Receta receta = buscarRecetaPorCodigo(codigo);
        if(receta==null)
            throw new IllegalArgumentException("No existe un receta con codigo: "+codigo);
        modeloRecetas.marcarLista(receta,token,idUsuario);
    }
    public void entregar(String codigo) throws IllegalArgumentException{
        Receta receta = buscarRecetaPorCodigo(codigo);
        if(receta==null)
            throw new IllegalArgumentException("No existe un receta con codigo: "+codigo);

        modeloRecetas.entregar(receta,token,idUsuario);
    }
    public DateTimeFormatter getFormatoFecha() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
    public void cerrarAplicacion() {
        try{
            modeloAdministrador.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloMedico.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloPaciente.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloFarmaceuta.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloMedicamento.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloRecetas.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        if(usuarios.cantidad()!=0)
            usuarios.limpiar();
        if(dashboard.cantidad()!=0)
            dashboard.limpiar();
        System.out.println("Aplicación finalizada..");
        System.exit(0);
    }
    // ---------------- MÉTODOS DASHBOARD ----------------

    public Map<YearMonth, Integer> DashboardMedicamentosPorMes(LocalDate startDate, LocalDate endDate, String nombreMedicamento){
        return dashboard.medicamentosPorMes(modeloRecetas.obtenerListaRecetas(), startDate, endDate, nombreMedicamento);
    }
    public Map<String, Long> DashboardRecetasPorEstado(LocalDate desde, LocalDate hasta, String nombreMedicamento) {
        return dashboard.recetasPorEstado(modeloRecetas.obtenerListaRecetas(), desde, hasta, nombreMedicamento);
    }

    public Receta buscarRecetaHistorial(String codigo){
        return historial.buscarPorCodigo(buscarRecetaPorCodigo(codigo));
    }
    public Receta buscarRecetaHistorial(int idPaciente, LocalDate fechaConfeccion){
        return historial.buscarPorPacienteFecha(buscarReceta(idPaciente, fechaConfeccion));
    }
    public List<Indicacion>mostrarIndicaciones(String codigo){
        Receta receta=buscarRecetaHistorial(codigo);
        return historial.mostrarIndicaciones(receta);
    }

    // ---------------- EXPORTACIÓN A PDF DESDE CONTROLADOR ----------------

    public void exportarHistorico(java.util.List<Receta> recetas) throws Exception {
        String[] cabeceras = {"Código", "ID Paciente", "Paciente", "Medicamentos", "Fecha Confección", "Fecha Retiro", "Estado"};
        java.util.List<String[]> filas = new java.util.ArrayList<>();

        for (Receta r : recetas) {
            String medicamentos = r.obtenerListaIndicaciones().stream()
                    .map(in -> in.getMedicamento() != null ? in.getMedicamento().getNombre() : "")
                    .reduce((a, b) -> a + ", " + b).orElse("");

            filas.add(new String[]{
                    r.getCodigo(),
                    (r.getPaciente() != null ? String.valueOf(r.getPaciente().getId()) : ""),
                    (r.getPaciente() != null ? r.getPaciente().getNombre() : ""),
                    medicamentos,
                    (r.getFecha_confeccion() != null ? r.getFecha_confeccion().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""),
                    (r.getFecha_retiro() != null ? r.getFecha_retiro().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""),
                    (r.getEstado() != null ? r.getEstado().toString() : "")
            });
        }

        exportarTablaPDF("Histórico de Recetas", cabeceras, filas, "historico_recetas.pdf");
    }

    public void exportarMedicos() throws Exception {
        String[] cabeceras = {"ID", "Nombre", "Especialidad"};
        java.util.List<String[]> filas = new java.util.ArrayList<>();

        for (Medico m : obtenerListaMedicos()) {
            filas.add(new String[]{ m.getId(), m.getNombre(), m.getEspecialidad() });
        }

        exportarTablaPDF("Listado de Médicos", cabeceras, filas, "medicos.pdf");
    }

    public void exportarFarmaceutas() throws Exception {
        String[] cabeceras = {"ID", "Nombre"};
        java.util.List<String[]> filas = new java.util.ArrayList<>();

        for (Farmaceuta f : obtenerListaFarmaceutas()) {
            filas.add(new String[]{ f.getId(), f.getNombre() });
        }

        exportarTablaPDF("Listado de Farmacéutas", cabeceras, filas, "farmaceutas.pdf");
    }

    public void exportarPacientes() throws Exception {
        String[] cabeceras = {"ID", "Nombre", "Fecha Nacimiento", "Teléfono"};
        java.util.List<String[]> filas = new java.util.ArrayList<>();

        for (Paciente p : obtenerListaPacientes()) {
            String fecha = (p.getFecha_nacimiento() != null) ? p.getFecha_nacimiento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
            filas.add(new String[]{ String.valueOf(p.getId()), p.getNombre(), fecha, String.valueOf(p.getTelefono()) });
        }

        exportarTablaPDF("Listado de Pacientes", cabeceras, filas, "pacientes.pdf");
    }

    public void exportarMedicamentos() throws Exception {
        String[] cabeceras = {"Código", "Nombre", "Descripción"};
        java.util.List<String[]> filas = new java.util.ArrayList<>();

        for (Medicamento m : obtenerListaMedicamentos()) {
            filas.add(new String[]{ String.valueOf(m.getCodigo()), m.getNombre(), m.getDescripcion() });
        }

        exportarTablaPDF("Listado de Medicamentos", cabeceras, filas, "medicamentos.pdf");
    }

    private void exportarTablaPDF(String titulo, String[] cabeceras, java.util.List<String[]> filas, String nombreSugerido) throws Exception {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart - 40;

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.beginText();
        contentStream.newLineAtOffset(margin, yStart);
        contentStream.showText(titulo);
        contentStream.endText();

        float[] proporciones = new float[cabeceras.length];
        for (int i = 0; i < cabeceras.length; i++) {
            proporciones[i] = 1f;
        }
        if (cabeceras.length == 7) {
            proporciones = new float[]{1f, 1f, 1.5f, 3f, 1.5f, 1.5f, 1f};
        }

        float totalProporciones = 0;
        for (float p : proporciones) totalProporciones += p;

        float[] colWidths = new float[cabeceras.length];
        for (int i = 0; i < cabeceras.length; i++) {
            colWidths[i] = (proporciones[i] / totalProporciones) * tableWidth;
        }

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        float textY = yPosition;
        float x = margin;

        for (int i = 0; i < cabeceras.length; i++) {
            escribirTextoEnCelda(contentStream, cabeceras[i], x, textY, colWidths[i], 10, PDType1Font.HELVETICA_BOLD);
            x += colWidths[i];
        }

        contentStream.setFont(PDType1Font.HELVETICA, 9);
        textY -= 20;

        for (String[] fila : filas) {
            x = margin;
            float alturaFila = calcularAlturaFila(fila, colWidths, 9, PDType1Font.HELVETICA);


            if (textY - alturaFila < margin) {
                contentStream.close();
                page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                textY = yStart;
            }

            for (int i = 0; i < fila.length; i++) {
                escribirTextoEnCelda(contentStream, fila[i], x, textY, colWidths[i], 9, PDType1Font.HELVETICA);
                x += colWidths[i];
            }

            textY -= alturaFila + 5;
        }

        contentStream.close();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte como...");
        fileChooser.setSelectedFile(new File(nombreSugerido));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            document.save(fileToSave.getAbsolutePath());
        }

        document.close();
    }

    private void escribirTextoEnCelda(PDPageContentStream contentStream, String texto,
                                      float x, float y, float colWidth,
                                      int fontSize, PDType1Font font) throws IOException {
        if (texto == null) texto = "";
        float leading = 1.2f * fontSize;
        java.util.List<String> lineas = dividirTextoPorAncho(texto, colWidth, font, fontSize);

        float textY = y;
        for (String linea : lineas) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x + 2, textY);
            contentStream.showText(linea);
            contentStream.endText();
            textY -= leading;
        }
    }

    private float calcularAlturaFila(String[] fila, float[] colWidths,
                                     int fontSize, PDType1Font font) throws IOException {
        float maxAltura = 0;
        for (int i = 0; i < fila.length; i++) {
            String texto = fila[i] != null ? fila[i] : "";
            java.util.List<String> lineas = dividirTextoPorAncho(texto, colWidths[i], font, fontSize);
            float altura = lineas.size() * (1.2f * fontSize);
            if (altura > maxAltura) maxAltura = altura;
        }
        return maxAltura;
    }

    private java.util.List<String> dividirTextoPorAncho(String texto, float colWidth,
                                                        PDType1Font font, int fontSize) throws IOException {
        java.util.List<String> lineas = new java.util.ArrayList<>();
        if (texto == null) return lineas;

        String[] palabras = texto.split(" ");
        StringBuilder linea = new StringBuilder();

        for (String palabra : palabras) {
            String tmp = linea.length() == 0 ? palabra : linea + " " + palabra;
            float anchoTmp = font.getStringWidth(tmp) / 1000 * fontSize;
            if (anchoTmp > colWidth - 4) {
                if (linea.length() > 0) {
                    lineas.add(linea.toString());
                    linea = new StringBuilder(palabra);
                } else {
                    lineas.add(palabra);
                    linea = new StringBuilder();
                }
            } else {
                linea = new StringBuilder(tmp);
            }
        }

        if (linea.length() > 0) lineas.add(linea.toString());
        return lineas;
    }

    private GestorAdministrador modeloAdministrador;
    private GestorMedico modeloMedico;
    private GestorFarmaceuta modeloFarmaceuta;
    private GestorPaciente modeloPaciente;
    private GestorMedicamento modeloMedicamento;
    private GestorRecetas modeloRecetas;
    private GestorIndicacion modeloIndicacion;
    private login usuarios;
    @Setter
    private int token;
    private String idUsuario;
    private Dashboard  dashboard;
    private Historicos historial;
}
