package Modelo.Gestores;

import Modelo.DAO.RecetaDAO;
import Modelo.DAO.IndicacionDAO;
import Modelo.entidades.Receta.*;
import Modelo.entidades.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorRecetaIndicacion {
    public GestorRecetaIndicacion()throws SQLException {
        recetas=new RecetaDAO();
        indicaciones=new IndicacionDAO();
        indicacionList=new ArrayList<Indicacion>();
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
    /*public void cargar() throws IOException, JAXBException, SQLException, ParserConfigurationException, SAXException {
        File f=new File(ARCHIVO_DATOS);
        if(f.exists()&&f.length()!=0&&obtenerListaRecetas()==null)
            recetas.cargar(f);
    }*/
    /*public void guardar () throws JAXBException, SQLException, FileNotFoundException {
        recetas.guardar(new FileOutputStream(ARCHIVO_DATOS));
    }*/
    private static final String ARCHIVO_DATOS= "src/datos/recetas.xml";
    private final RecetaDAO recetas;
    private final IndicacionDAO indicaciones;
    private final List<Indicacion> indicacionList;
}
