package Modelo.DAO;

import Modelo.Utils.JsonUtil;
import Modelo.entidades.Medicamento;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.AllArgsConstructor;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class IndicacionDAO implements DAOAbstracto<IndicacionDAO.IndicacionKey, Indicacion> {

    public IndicacionDAO() throws SQLException {
        this.dao = DaoManager.createDao(ConexionBD.getConexion(), Indicacion.class);
        this.recetaDao = DaoManager.createDao(ConexionBD.getConexion(), Receta.class);
        this.medicamentoDao = DaoManager.createDao(ConexionBD.getConexion(), Medicamento.class);
    }

    @Override
    public void add(Indicacion e) throws SQLException {
        dao.executeRaw(
                "INSERT INTO indicacion (receta_codigo, medicamento_codigo, cantidad, indicaciones, duracion) VALUES (?, ?, ?, ?, ?)",
                e.getReceta().getCodigo(), e.getMedicamento().getCodigo(), String.valueOf(e.getCantidad()), e.getIndicaciones(),String.valueOf(e.getDuracion())
        );
    }

    @Override
    public Indicacion findById(IndicacionKey id) throws SQLException {
        QueryBuilder<Indicacion, Object> qb = dao.queryBuilder();
        qb.where()
                .eq("receta_codigo", id.getRecetaCodigo())
                .and()
                .eq("medicamento_codigo", id.getMedicamentoCodigo());
        return qb.queryForFirst();
    }

    @Override
    public List<Indicacion> findAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void update(Indicacion e) throws SQLException {
        dao.executeRaw(
                "UPDATE indicacion SET cantidad = ?, indicaiones = ?, duracion = ? WHERE receta_codigo = ? AND medicamento_codigo = ?",
                String.valueOf(e.getCantidad()),
                e.getIndicaciones() == null ? "" : e.getIndicaciones(),
                String.valueOf(e.getDuracion()),
                e.getReceta().getCodigo(),
                e.getMedicamento().getCodigo()
        );
    }

    @Override
    public void delete(IndicacionKey id) throws SQLException {
        DeleteBuilder<Indicacion, Object> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where()
                .eq("receta_codigo", id.getRecetaCodigo())
                .and()
                .eq("medicamento_codigo", id.getMedicamentoCodigo());
        deleteBuilder.delete();
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
            String recetaCodigo = ind.getReceta() != null ? ind.getReceta().getCodigo() : null;
            String medicamentoCodigo = ind.getMedicamento() != null ? ind.getMedicamento().getCodigo() : null;
            exports.add(new IndicacionExport(recetaCodigo, medicamentoCodigo, ind.getCantidad(), ind.getIndicaciones(), ind.getDuracion()));
        }
        JsonUtil.writeListToFile(exports, file);
    }
    @Override
    public void importAllFromJson(File file) throws SQLException, IOException{
        List<IndicacionExport> list = JsonUtil.readListFromFile(file, new TypeReference<List<IndicacionExport>>() {});
        for (IndicacionExport ie : list){
            Receta receta=recetaDao.queryForId(ie.recetaCodigo);
            Medicamento medicamento=medicamentoDao.queryForId(ie.medicamentoCodigo);
            int updated = dao.executeRaw(
                    "UPDATE indicacion SET cantidad = ?, indicaiones = ?, duracion = ? WHERE receta_codigo = ? AND medicamento_codigo = ?",
                    String.valueOf(ie.cantidad),
                    ie.indicaiones == null ? "" : ie.indicaiones,
                    String.valueOf(ie.duracion),
                    receta.getCodigo(),
                    medicamento.getCodigo()
            );
            if (updated == 0){
                dao.executeRaw(
                        "INSERT INTO indicacion (receta_codigo, medicamento_codigo, cantidad, indicaiones, duracion) VALUES (?, ?, ?, ?, ?)",
                        receta.getCodigo(),
                        medicamento.getCodigo(),
                        String.valueOf(ie.cantidad),
                        ie.indicaiones == null ? "" : ie.indicaiones,
                        String.valueOf(ie.duracion)
                );
            }
        }
    }
    @AllArgsConstructor
    public static final class IndicacionKey {
        private final String recetaCodigo;
        private final String medicamentoCodigo;
        public String getRecetaCodigo() {
            return recetaCodigo;
        }

        public String getMedicamentoCodigo() {
            return medicamentoCodigo;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof IndicacionKey)) return false;
            IndicacionKey that = (IndicacionKey) o;
            return Objects.equals(recetaCodigo, that.recetaCodigo) &&
                    Objects.equals(medicamentoCodigo, that.medicamentoCodigo);
        }
        @Override
        public int hashCode() {
            return Objects.hash(recetaCodigo, medicamentoCodigo);
        }
    }
    @AllArgsConstructor
    static final class IndicacionExport{
        public String recetaCodigo;      // solo referencia por clave
        public String medicamentoCodigo; // solo referencia por clave
        public int cantidad;
        public String indicaiones;
        public int duracion;
    }
    private final Dao<Indicacion, Object> dao;
    private final Dao<Receta, String> recetaDao;
    private final Dao<Medicamento, String> medicamentoDao;
}
