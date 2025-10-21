package Modelo.DAO;

import Modelo.entidades.Receta.Indicacion;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * DAO para la entidad Indicacion usando una clave compuesta (recetaCodigo, medicamentoCodigo).
 * Contiene la clase anidada IndicacionKey que representa la clave compuesta.
 */
public class IndicacionDAO implements DAOAbstracto<IndicacionDAO.IndicacionKey, Indicacion> {

    public IndicacionDAO() throws SQLException {
        this.dao = DaoManager.createDao(ConexionBD.getConexion(), Indicacion.class);
    }

    @Override
    public void add(Indicacion e) throws SQLException {
        dao.create(e);
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
        dao.update(e);
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

    /**
     * Clase interna que representa la clave compuesta de Indicacion:
     * (recetaCodigo, medicamentoCodigo)
     */
    public static final class IndicacionKey {
        private final String recetaCodigo;
        private final String medicamentoCodigo;

        public IndicacionKey(String recetaCodigo, String medicamentoCodigo) {
            this.recetaCodigo = recetaCodigo;
            this.medicamentoCodigo = medicamentoCodigo;
        }

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

        @Override
        public String toString() {
            return "IndicacionKey{recetaCodigo='" + recetaCodigo + "', medicamentoCodigo='" + medicamentoCodigo + "'}";
        }
    }
    private final Dao<Indicacion, Object> dao;
}
