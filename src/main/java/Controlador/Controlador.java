package Controlador;
import Modelo.entidades.*;
import Modelo.Gestores.*;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import Modelo.Graficos.*;
import org.jfree.chart.ChartPanel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Controlador {
    public Controlador(GestorUsuario modeloUsuario, GestorMedico modeloMedico,
                       GestorMedicamento modeloMedicamento, GestorPaciente modeloPaciente, GestorRecetaIndicacion modeloRecetasIndicacion,Usuario usuario_login, GraficosUtil graficosUtil) {
        this.modeloUsuarios = modeloUsuario;
        this.modeloMedico = modeloMedico;
        this.modeloPaciente = modeloPaciente;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetasIndicacion = modeloRecetasIndicacion;
        this.usuario_login=usuario_login;
        this.graficosUtil=graficosUtil;
    }
    public Controlador() throws SQLException {
        this(new GestorUsuario(),new GestorMedico(),new GestorMedicamento(),new GestorPaciente(),new GestorRecetaIndicacion(),new Usuario(),new GraficosUtil());
    }
    public void usuarioLogin(String id,String clave) throws SQLException {
        usuario_login=modeloUsuarios.buscarIdClave(id,clave);
    }
    public void cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar) throws IllegalArgumentException, SQLException {
        modeloUsuarios.cambiarClave(id, claveActual, claveNueva, claveConfirmar);
    }
    /*public void init() {
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
            modeloRecetasIndicacion.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
    }*/
    public List<Medico> obtenerListaMedicos() throws SQLException {
        return modeloMedico.obtenerListaMedicos();
    }
    public Medico buscarMedico(String id) throws SQLException {
        return modeloMedico.buscar(id);
    }
    public void agregarMedico(String id,String nom,String esp) throws SecurityException, SQLException {
        Usuario usuario=new Usuario(id,nom,"MEDICO");
        Medico medico=new Medico(usuario,esp);
        modeloMedico.agregar(medico,usuario_login);
    }
    public void actualizarMedico(String id,String nombre,String especialidad) throws SecurityException, SQLException {
        Medico medico = buscarMedico(id);
        medico.setNombre(nombre);
        medico.setEspecialidad(especialidad);
        modeloMedico.actualizar(medico,usuario_login);
    }
    public void eliminarMedico(String id) throws SQLException {
        modeloMedico.eliminar(id,usuario_login);
    }
    public List<Usuario> obtenerListaAdministradores() throws SQLException {
        return modeloUsuarios.obtenerListaAdministradores();
    }
    public List<Usuario> obtenerListaFarmaceutas() throws SQLException {
        return modeloUsuarios.obtenerListaFarmaceutas();
    }
    public List<Usuario> obtenerListaMedicosU() throws SQLException {
        return modeloUsuarios.obtenerListaMedicos();
    }
    public Usuario buscarUsuario(String id) throws SQLException {
        return modeloUsuarios.buscar(id);
    }
    public void agregarAdministrador(String id,String nombre)  throws SQLException{
        Administrador administrador = new Administrador(id,nombre);
        modeloUsuarios.agregar(administrador,usuario_login);
    }
    public void agregarFarmaceuta(String id,String nombre)  throws SQLException{
        Farmaceuta farmaceuta = new Farmaceuta(id,nombre);
        modeloUsuarios.agregar(farmaceuta,usuario_login);
    }
    public void actualizarUsuario(String id,String nombre) throws SecurityException, SQLException {
        Usuario usuario=buscarUsuario(id);
        usuario.setClave(nombre);
        usuario.setNombre(nombre);
        modeloUsuarios.actualizar(usuario,usuario_login);
    }
    public void eliminarUsuario(String id) throws SQLException{
        modeloUsuarios.eliminar(id,usuario_login);;
    }
    public List<Paciente> obtenerListaPacientes() throws SQLException {
        return modeloPaciente.obtenerListaPacientes();
    }
    public Paciente buscarPacientePorId(int id) throws SQLException {
        return modeloPaciente.buscar(id);
    }
    public void agregarPaciente(int id, String nombre, int telefono, Date fecha) throws SecurityException, SQLException {
        Paciente paciente = new Paciente(id,nombre,fecha,telefono);
        modeloPaciente.agregar(paciente,usuario_login);
    }
    public void actualizarPaciente(int id, String nombre, int telefono,Date fecha) throws SQLException {
        Paciente paciente = buscarPacientePorId(id);
        paciente.setNombre(nombre);
        paciente.setTelefono(telefono);
        paciente.setFecha_nacimiento(fecha);
        modeloPaciente.actualizar(paciente,usuario_login);
    }
    public void eliminarPaciente(int id) throws SecurityException, SQLException {
         modeloPaciente.eliminar(id,usuario_login);
    }
    public List<Medicamento> obtenerListaMedicamentos() throws SQLException {
        return modeloMedicamento.obtenerListaMedicamentos();
    }
    public Medicamento buscarMedicamento(String codigo) throws SQLException {
        return modeloMedicamento.buscar(codigo);
    }
    public void agregarMedicamento(String codigo,String nombre,String presentacion,String descripcion) throws SecurityException, SQLException {
        Medicamento medicamento = new Medicamento(codigo,nombre,presentacion,descripcion);
        modeloMedicamento.agregar(medicamento,usuario_login);
    }
    public void actualizarMedicamento(String codigo,String nombre,String presentacion,String descripcion) throws SecurityException, SQLException {
        Medicamento medicamento = buscarMedicamento(codigo);
        medicamento.setNombre(nombre);
        medicamento.setDescripcion(descripcion);
        modeloMedicamento.actualizar(medicamento,usuario_login);
    }
    public void eliminarMedicamento(String codigo) throws SecurityException, SQLException {
        modeloMedicamento.eliminar(codigo,usuario_login);
    }
    public List<Receta> obtenerListaRecetas() throws SQLException {
        return modeloRecetasIndicacion.obtenerListaRecetas();
    }
    public  Receta buscarReceta(String codigo) throws SQLException {
        return modeloRecetasIndicacion.buscarReceta(codigo);
    }
    public void agregarReceta(String codigo,Paciente paciente,Date fecha_retiro,Date fecha_confeccion,Usuario farmaceuta_Proceso,Usuario farmaceuta_Lista,Usuario farmaceuta_Entregada) throws IllegalArgumentException, SecurityException, SQLException {
        Receta receta = new Receta(codigo, paciente, fecha_retiro, fecha_confeccion, farmaceuta_Proceso, farmaceuta_Lista, farmaceuta_Entregada);
        modeloRecetasIndicacion.agregarReceta(receta,usuario_login);
    }
    public void iniciarProceso(String codigo) throws IllegalArgumentException, SQLException,SecurityException {
        modeloRecetasIndicacion.iniciarProceso(codigo,usuario_login);
    }
    public void marcarLista(String codigo) throws IllegalArgumentException, SQLException,SecurityException {
        modeloRecetasIndicacion.marcarLista(codigo,usuario_login);
    }
    public void entregar(String codigo) throws IllegalArgumentException, SQLException, SecurityException {
        modeloRecetasIndicacion.entregar(codigo,usuario_login);
    }
    public List<Indicacion> obtenerListaIndicaciones() throws SQLException {
        return modeloRecetasIndicacion.obtenerListaIndicaciones();
    }
    public Indicacion buscarIndicacion(String medicamentoCodigo){
        return modeloRecetasIndicacion.buscarIndicacionLista(medicamentoCodigo);
    }
    public void agregarIndicacion(Medicamento medicamento,int cantidad,String indicaiones,int duracion) throws IllegalArgumentException, SecurityException{
        Indicacion indicacion=new Indicacion(medicamento,cantidad,indicaiones,duracion);
        modeloRecetasIndicacion.agregarIndicacionLista(indicacion,usuario_login);
    }
    public void actualizarIndicacion(Medicamento medicamento,int cantidad,String indicaiones,int duracion)throws IllegalArgumentException, SecurityException{
        Indicacion indicacion=buscarIndicacion(medicamento.getCodigo());
        indicacion.setCantidad(cantidad);
        indicacion.setIndicaiones(indicaiones);
        indicacion.setDuracion(duracion);
        modeloRecetasIndicacion.actualizarIndicacionLista(indicacion,usuario_login);
    }
    public void eliminarIndicacion(String codigo) throws SQLException, SecurityException{
        modeloRecetasIndicacion.eliminarIndicacionLista(codigo,usuario_login);
    }

    public DateTimeFormatter getFormatoFecha() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
    /*public void cerrarAplicacion() {
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
            modeloRecetasIndicacion.guardar();
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
    }*/
    // ---------------- MÉTODOS DASHBOARD ----------------
    public void agregarMedicamentoCodigo(String codigo)throws IllegalArgumentException{
        modeloRecetasIndicacion.agregarMedicamentoCodigo(codigo);
    }
    public void eliminarMedicamentoCodigo(String codigo){
        modeloRecetasIndicacion.eliminarMedicamentoCodigo(codigo);
    }
    public void limpiarMedicamentoCodigo(){
        modeloRecetasIndicacion.limpiarMedicamentoCodigo();
    }
    public ChartPanel crearGraficoLineas(Date desde, Date hasta)throws IllegalArgumentException, SQLException{
        return graficosUtil.crearGraficoLineas(modeloRecetasIndicacion.estadisticaMedicamentosPorMes(desde,hasta));
    }
    public ChartPanel crearGraficoPastel()throws SQLException{
        return graficosUtil.crearGraficoPastel(modeloRecetasIndicacion.recetasPorEstado());
    }

    public List<Indicacion>mostrarIndicaciones(String codigo) throws SQLException {
        return modeloRecetasIndicacion.indiccionesReceta(codigo);
    }

    /*// ---------------- EXPORTACIÓN A PDF DESDE CONTROLADOR ----------------

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

        for (Usuario f : obtenerListaFarmaceutas()) {
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
*/
    private GestorMedico modeloMedico;
    private GestorPaciente modeloPaciente;
    private GestorMedicamento modeloMedicamento;
    private GestorRecetaIndicacion modeloRecetasIndicacion;
    private GestorUsuario modeloUsuarios;
    private Usuario usuario_login;
    private GraficosUtil graficosUtil;
}
