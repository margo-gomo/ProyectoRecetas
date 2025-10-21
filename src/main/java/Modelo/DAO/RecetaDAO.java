package Modelo.DAO;
import Modelo.entidades.Receta.Receta;
import Modelo.entidades.Usuario;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;
public class RecetaDAO implements DAOAbstracto<String, Receta> {
    public RecetaDAO() throws SQLException {
        this.dao=DaoManager.createDao(ConexionBD.getConexion(), Receta.class);
    }
    @Override
    public void add(Receta e) throws SQLException {
        dao.create(e);
    }

    @Override
    public Receta findById(String id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<Receta> findAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void update(Receta e) throws SQLException {
        dao.update(e);
    }
    public void iniciarProceso(Receta r,Usuario farmaceuta)throws SQLException{
        r.setFarmaceuta_Proceso(farmaceuta);
        r.setEstado("PROCESO");
        dao.update(r);
    }
    public void marcarLista(Receta r,Usuario farmaceuta)throws SQLException{
        r.setFarmaceuta_Lista(farmaceuta);
        r.setEstado("LISTA");
        dao.update(r);
    }
    public void entregar(Receta r,Usuario farmaceuta)throws SQLException{
        r.setFarmaceuta_Entregada(farmaceuta);
        r.setEstado("ENTREGADA");
        dao.update(r);
    }
    @Override
    public void delete(String id) throws SQLException {
        dao.deleteById(id);
    }
    private final Dao<Receta,String> dao;
}
