package Modelo.DAO;
import Modelo.Utils.JsonUtil;
import Modelo.entidades.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
public class UsuarioDAO implements DAOAbstracto<String,Usuario> {
    public UsuarioDAO() throws SQLException {
        this.dao=DaoManager.createDao(ConexionBD.getConexion(), Usuario.class);
    }
    @Override
    public void add(Usuario e) throws SQLException {
        dao.create(e);
    }

    @Override
    public Usuario findById(String id) throws SQLException {
        return dao.queryForId(id);
    }
    public Usuario findByIdAndClave(String id,String clave) throws SQLException {
        QueryBuilder<Usuario, String> builder = dao.queryBuilder();
        builder.where()
                .eq("id", id)
                .and()
                .eq("contraseña", clave);

        return builder.queryForFirst();
    }

    @Override
    public List<Usuario> findAll() throws SQLException {
        return dao.queryForAll();
    }
    public List<Usuario> findAllAdministradores() throws SQLException {
        QueryBuilder<Usuario, String> qb = dao.queryBuilder();
        qb.where().eq("tipo","ADMINISTRADOR");
        return qb.query();
    }
    public List<Usuario> findAllFarmaceutas() throws SQLException {
        QueryBuilder<Usuario, String> qb = dao.queryBuilder();
        qb.where().eq("tipo","FARMACEUTA");
        return qb.query();
    }
    public List<Usuario> findAllMedicos() throws SQLException {
        QueryBuilder<Usuario, String> qb = dao.queryBuilder();
        qb.where().eq("tipo","MEDICO");
        return qb.query();
    }
    @Override
    public void update(Usuario e) throws SQLException {
        dao.update(e);
    }
    public void cambiarClave(Usuario u, String claveNueva) throws SQLException{
        u.setClave(claveNueva);
        dao.update(u);
    }
    @Override
    public void delete(String id) throws SQLException {
        dao.deleteById(id);
    }

    @Override
    public void exportAllToJson(File file) throws SQLException, IOException {
        List<Usuario> all = findAll();
        JsonUtil.writeListToFile(all, file);
    }

    @Override
    public void importAllFromJson(File file) throws SQLException, IOException {
        List<Usuario> list = JsonUtil.readListFromFile(file, new TypeReference<List<Usuario>>() {});
        for (Usuario e : list)
            add(e);
    }
    private final Dao<Usuario, String> dao;
}
