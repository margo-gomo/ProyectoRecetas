package Modelo.Gestores;

import Modelo.DAO.RecetaDAO;
import Modelo.DAO.IndicacionDAO;
import Modelo.entidades.Medicamento;
import Modelo.entidades.Receta.*;
import Modelo.entidades.Usuario;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

public class GestorRecetaIndicacion {
    public GestorRecetaIndicacion()throws SQLException {
        recetas=new RecetaDAO();
        indicaciones=new IndicacionDAO();
        indicacionList=new ArrayList<Indicacion>();
        medicamentoCodigos=new ArrayList<String>();
    }
    public void iniciarProceso(String codigo,Usuario farmaceuta) throws IllegalArgumentException,SecurityException,SQLException {
        Receta receta=buscarReceta(codigo);
        if(!("FARMACEUTA".equals(farmaceuta.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        if(!receta.fechaDentroVentana())
            throw new IllegalArgumentException("La receta está fuera de la ventana de 3 días");
        if(!("CONFECCIONADA".equals(receta.getEstado())))
            throw new IllegalArgumentException("La receta no está solamente confeccionada");
        recetas.iniciarProceso(receta,farmaceuta);
    }
    public void marcarLista(String codigo,Usuario farmaceuta) throws IllegalArgumentException,SecurityException,SQLException {
        Receta receta=buscarReceta(codigo);
        if(!("FARMACEUTA".equals(farmaceuta.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        if(!("PROCESO".equals(receta.getEstado())))
            throw new IllegalArgumentException("La receta no está en proceso");
        if(receta.getFarmaceuta_Proceso().getId().equals(farmaceuta.getId()))
            throw new SecurityException("No puedes marcar lista una receta a la que le iniciaste el proceso");
        recetas.marcarLista(receta,farmaceuta);
    }
    public void entregar(String codigo,Usuario farmaceuta) throws IllegalArgumentException,SecurityException,SQLException {
        Receta receta=buscarReceta(codigo);
        if(!("FARMACEUTA".equals(farmaceuta.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        if(!("LISTA".equals(receta.getEstado())))
            throw new IllegalArgumentException("La receta no está en proceso");
        if(receta.getFarmaceuta_Proceso().getId().equals(farmaceuta.getId())&&receta.getFarmaceuta_Lista().getId().equals(farmaceuta.getId()))
            throw new SecurityException("No puedes entregar una receta a la que marcaste lista o pusiste en proceso");
        recetas.entregar(receta,farmaceuta);
    }

    public List<Receta> obtenerListaRecetas()throws SQLException{
        return recetas.findAll();
    }

    public Receta buscarReceta(String codigo)throws SQLException {
        Receta receta= recetas.findById(codigo);
        if(receta==null)
            throw new SQLException("No existe un usuario con esas credenciales");
        return receta;
    }
    public void agregarReceta(Receta receta, Usuario usuario) throws SecurityException,SQLException {
        if(!("MEDICO".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            recetas.add(receta);
        }catch(SQLException e){
            throw new SQLException("Ya existe un receta con ese codigo");
        }
        agregarIndicacion(receta,usuario);
    }
    public void actualizarReceta(Receta receta, Usuario usuario) throws SecurityException,SQLException {
        if(!("MEDICO".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            recetas.update(receta);
        }catch(SQLException e){
            throw new SQLException("No existe un receta con ese codigo");
        }
    }

    public void eliminarReceta(String codigo, Usuario usuario) throws SecurityException,SQLException {
        if(!("MEDICO".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            recetas.delete(codigo);
        }catch(SQLException e){
            throw new SQLException("No existe un receta con ese codigo");
        }
    }
    public Indicacion buscarIndicacionLista(String codigo){
        for(Indicacion indicacion:indicacionList){
            if(indicacion.getMedicamento().getCodigo().equals(codigo))
                return indicacion;
        }
        return null;
    }
    public int buscarPosicion(String codigo){
        for(int i=0;i<indicacionList.size();i++){
            if(indicacionList.get(i).getMedicamento().getCodigo().equals(codigo))
                return i;
        }
        return -1;
    }
    public void agregarIndicacionLista(Indicacion indicacion, Usuario usuario) throws SecurityException{
        if(!("MEDICO".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        if(buscarIndicacionLista(indicacion.getMedicamento().getCodigo())!=null)
            throw new IllegalArgumentException("Ya existe un medicamento con ese codigo en la receta");
        indicacionList.add(indicacion);
    }
    public void actualizarIndicacionLista(Indicacion indicacion, Usuario usuario) throws SecurityException,IllegalArgumentException{
        if(!("MEDICO".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        int i=buscarPosicion(indicacion.getMedicamento().getCodigo());
        if(i==-1)
            throw new IllegalArgumentException("No existe una indicacion con ese codigo");
        indicacionList.set(i,indicacion);
    }
    public void eliminarIndicacionLista(String codigo, Usuario usuario) throws SecurityException{
        if(!("MEDICO".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        int i=buscarPosicion(codigo);
        if(i==-1)
            throw new IllegalArgumentException("No existe una indicacion con ese codigo");
        indicacionList.remove(i);
    }
    public void agregarIndicacion(Receta receta, Usuario usuario) throws SecurityException,SQLException{
        if(!("MEDICO".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        for(Indicacion indicacion:indicacionList){
            indicacion.setReceta(receta);
            indicaciones.add(indicacion);
        }
        indicacionList.clear();
    }
    public List<Indicacion> obtenerListaIndicaciones() throws SQLException {
        return indicaciones.findAll();
    }
    public Indicacion buscarIndicacion(String recetaCodigo, String medicamentoCodigo) throws SQLException {
        IndicacionDAO.IndicacionKey key = new IndicacionDAO.IndicacionKey(recetaCodigo, medicamentoCodigo);
        Indicacion ind = indicaciones.findById(key);
        if (ind == null)
            throw new SQLException("No existe una indicación con esa clave");
        return ind;
    }
    public List<Indicacion> indiccionesReceta(String recetaCodigo) throws SQLException{
        return indicaciones.findByRecetaCodigo(recetaCodigo);
    }
    public List<Indicacion> indicacionesbyMedicamento(String medicamentoCodigo) throws SQLException{
        return indicaciones.findByMedicamentoCodigo(medicamentoCodigo);
    }

    public Map<String, Integer> recetasPorEstado() throws SQLException {
        List<Receta> lista = recetas.findAll();
        Map<String, Integer> conteo = new HashMap<>();
        for (Receta receta : lista) {
            String estado = receta.getEstado();
            conteo.put(estado, conteo.getOrDefault(estado, 0) + 1);
        }
        return conteo;
    }
    public void agregarMedicamentoCodigo(String codigo)throws IllegalArgumentException{
        for(String m:medicamentoCodigos){
            if(m.equals(codigo))
                throw new IllegalArgumentException("No se admiten repetidos");
        }
        medicamentoCodigos.add(codigo);
    }
    public void eliminarMedicamentoCodigo(String codigo){
        medicamentoCodigos.remove(codigo);
    }
    public void limpiarMedicamentoCodigo(){
        medicamentoCodigos.clear();
    }
    public Map<String, Map<String, Integer>> estadisticaMedicamentosPorMes(Date desde, Date hasta) throws IllegalArgumentException,SQLException {
        if (desde == null || hasta == null)
            throw new IllegalArgumentException("Fechas inválidas");

        if (desde.after(hasta))
            throw new IllegalArgumentException("La fecha 'desde' no puede ser posterior a 'hasta'");

        // 1) Generar lista de etiquetas de meses entre desde y hasta (inclusive) en formato "YYYY-M"
        List<String> meses = generarEtiquetasMeses(desde, hasta);

        // 2) Preparar el mapa resultado: para cada medicamento, inicializar mapa de meses a 0
        Map<String, Map<String, Integer>> resultado = new LinkedHashMap<>();
        for (String codigoMed : medicamentoCodigos) {
            Map<String, Integer> serie = new LinkedHashMap<>();
            for (String m : meses) serie.put(m, 0);
            // inicialmente usamos el código como clave; intentaremos obtener el nombre más adelante
            resultado.put(codigoMed, serie);
        }

        // 3) Para cada medicamento, obtener indicaciones y acumular por mes si la receta está en el rango
        for (String codigoMed : medicamentoCodigos) {
            List<Indicacion> lista = indicaciones.findByMedicamentoCodigo(codigoMed);

            // intentar obtener nombre legible del medicamento (si hay al menos una indicación)
            String etiquetaMedicamento = codigoMed;
            if (!lista.isEmpty() && lista.get(0).getMedicamento() != null
                    && lista.get(0).getMedicamento().getNombre() != null
                    && !lista.get(0).getMedicamento().getNombre().isEmpty()) {
                etiquetaMedicamento = lista.get(0).getMedicamento().getNombre();
                // renombrar la clave en el mapa resultado (mantener orden), moviendo el inner map
                Map<String, Integer> serie = resultado.remove(codigoMed);
                resultado.put(etiquetaMedicamento, serie);
            }

            // si la lista está vacía simplemente queda la serie con ceros (de todos modos la dejamos)
            List<Indicacion> indicacionesMedicamento = lista;
            for (Indicacion ind : indicacionesMedicamento) {
                // obtener la receta asociada y su fecha de confección
                Receta r = ind.getReceta();
                Date fecha = null;
                if (r != null && r.getFecha_confeccion() != null) {
                    fecha = r.getFecha_confeccion();
                } else {
                    // si por alguna razón la receta no está fully cargada, intentar buscar por id
                    if (r != null && r.getCodigo() != null) {
                        Receta recetaBD = recetas.findById(r.getCodigo());
                        if (recetaBD != null) fecha = recetaBD.getFecha_confeccion();
                    }
                }

                if (fecha == null) continue; // no hay fecha, no sumamos

                // verificar que la fecha esté dentro del rango (inclusive)
                if (fecha.before(desde) || fecha.after(hasta)) continue;

                // calcular la etiqueta del mes de la receta
                String mesLabel = etiquetaMesDesdeFecha(fecha); // "YYYY-M"

                // sumar la cantidad
                // la clave outer puede ser nombre o código; buscamos la entrada correspondiente
                String finalEtiquetaMedicamento = etiquetaMedicamento;
                Map.Entry<String, Map<String, Integer>> entry = resultado.entrySet()
                        .stream()
                        .filter(e -> e.getKey().equals(finalEtiquetaMedicamento) || e.getKey().equals(codigoMed))
                        .findFirst()
                        .orElse(null);

                if (entry == null) continue; // defensivo, no debería pasar

                Map<String, Integer> serie = entry.getValue();
                // si por alguna razón mesLabel no está (no debería), ignorar
                if (!serie.containsKey(mesLabel)) continue;

                int actual = serie.get(mesLabel);
                serie.put(mesLabel, actual + ind.getCantidad());
            }
        }

        return resultado;
    }
    public void cargarRecetas()throws SQLException, IOException {
        File f=new File(ARCHIVO_DATOS_RECETA);
        if(obtenerListaRecetas().isEmpty()&&f.exists())
            recetas.importAllFromJson(f);
    }
    public void guardarRecetas() throws SQLException, IOException {
        recetas.exportAllToJson(new File(ARCHIVO_DATOS_RECETA));
    }
    public void cargarIndicaciones() throws SQLException, IOException {
        File f=new File(ARCHIVO_DATOS_INDICACION);
        if(obtenerListaRecetas().isEmpty()&&f.exists())
            indicaciones.importAllFromJson(f);
    }
    public void guardarIndicaciones()throws SQLException, IOException {
        indicaciones.exportAllToJson(new File(ARCHIVO_DATOS_INDICACION));
    }
    /* -------------------- helpers -------------------- */

    private static List<String> generarEtiquetasMeses(Date desde, Date hasta) {
        Calendar ini = Calendar.getInstance();
        ini.setTime(desde);
        ini.set(Calendar.DAY_OF_MONTH, 1);
        Calendar fin = Calendar.getInstance();
        fin.setTime(hasta);
        fin.set(Calendar.DAY_OF_MONTH, 1);

        List<String> meses = new ArrayList<>();
        while (!ini.after(fin)) {
            meses.add(ini.get(Calendar.YEAR) + "-" + (ini.get(Calendar.MONTH) + 1)); // formato YYYY-M
            ini.add(Calendar.MONTH, 1);
        }
        return meses;
    }

    private static String etiquetaMesDesdeFecha(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1);
    }
    private static final String ARCHIVO_DATOS_RECETA= "src/main/datos/recetas.json";
    private static final String ARCHIVO_DATOS_INDICACION= "src/main/datos/indicaciones.json";
    private final RecetaDAO recetas;
    private final IndicacionDAO indicaciones;
    private final List<Indicacion> indicacionList;
    List<String> medicamentoCodigos;
}
