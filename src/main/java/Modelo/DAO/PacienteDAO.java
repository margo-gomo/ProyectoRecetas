package Modelo.DAO;
import Modelo.Utils.JsonUtil;
import Modelo.entidades.Paciente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public class PacienteDAO implements DAOAbstracto<Integer, Paciente>{
    public PacienteDAO() throws SQLException {
        this.dao=DaoManager.createDao(ConexionBD.getConexion(), Paciente.class);
    }
    @Override
    public void add(Paciente e) throws SQLException {
        dao.create(e);
    }

    @Override
    public Paciente findById(Integer id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<Paciente> findAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void update(Paciente e) throws SQLException {
        dao.update(e);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        dao.deleteById(id);
    }

    @Override
    public void exportAllToJson(File file) throws SQLException, IOException {
        List<Paciente> all = findAll();
        JsonUtil.writeListToFile(all, file);
    }

    @Override
    public void importAllFromJson(File file) throws SQLException, IOException {
        List<Paciente> list = JsonUtil.readListFromFile(file, new TypeReference<List<Paciente>>() {});
        for (Paciente e : list)
            dao.createOrUpdate(e);
    }

    private final Dao<Paciente, Integer> dao;
}
