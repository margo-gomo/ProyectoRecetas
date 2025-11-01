package Modelo.DAO;

import Modelo.Utils.JsonUtil;
import Modelo.entidades.Medicamento;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class IndicacionDAO implements DAOAbstracto<Integer, Indicacion> {

    public IndicacionDAO() throws SQLException {
        this.dao = DaoManager.createDao(ConexionBD.getConexion(), Indicacion.class);
        this.recetaDao = DaoManager.createDao(ConexionBD.getConexion(), Receta.class);
        this.medicamentoDao=DaoManager.createDao(ConexionBD.getConexion(), Medicamento.class);
    }

    @Override
    public void add(Indicacion e) throws SQLException {
        dao.create(e);
    }
    @Override
    public Indicacion findById(Integer id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<Indicacion> findAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void update(Indicacion e) throws SQLException {
        dao.update(e);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        dao.deleteById(id);
    }

    public List<Indicacion> findByRecetaCodigo(String recetaCodigo) throws SQLException {
        QueryBuilder<Indicacion, Object> qb = dao.queryBuilder();
        qb.where().eq("receta_codigo", recetaCodigo);
        return qb.query();
    }
    public List<Indicacion> findByMedicamentoCodigo(String medicamentoCodigo) throws SQLException {
        QueryBuilder<Indicacion, Object> qb = dao.queryBuilder();
        qb.where().eq("medicamento_codigo", medicamentoCodigo);
        return qb.query();
    }
    @Override
    public void exportAllToJson(File file) throws SQLException, IOException {
        List<Indicacion> all = findAll();
        List<IndicacionExport> exports = new ArrayList<>();
        for (Indicacion ind : all) {
            String recetaCodigo = ind.getReceta().getCodigo();
            String medicamentoCodigo = ind.getMedicamento().getCodigo();
            exports.add(new IndicacionExport(
                    ind.getId(),
                    recetaCodigo,
                    medicamentoCodigo,
                    ind.getCantidad(),
                    ind.getIndicaciones(),
                    ind.getDuracion()
            ));
        }
        JsonUtil.writeListToFile(exports, file);
    }

    @Override
    public void importAllFromJson(File file) throws SQLException, IOException {
        List<IndicacionExport> list = JsonUtil.readListFromFile(file, new TypeReference<List<IndicacionExport>>() {});
        for (IndicacionExport ie : list) {
            Receta      receta      = recetaDao.queryForId(ie.receta_codigo);
            Medicamento medicamento = medicamentoDao.queryForId(ie.medicamento_codigo);
            Indicacion  indicacion = new Indicacion(ie.id,receta,medicamento,
                    ie.cantidad,ie.indicaciones,ie.duracion_dias);
            try {
                add(indicacion);
            }catch (SQLException ex){
                update(indicacion);
            }
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IndicacionExport {
        public String id;
        public String receta_codigo;
        public String medicamento_codigo;
        public int    cantidad;
        public String indicaciones;
        public int    duracion_dias;
    }
    private final Dao<Indicacion, Object> dao;
    private final Dao<Receta, String> recetaDao;
    private final Dao<Medicamento, String> medicamentoDao;
}
