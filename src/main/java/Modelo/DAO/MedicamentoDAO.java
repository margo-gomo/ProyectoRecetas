package Modelo.DAO;
import Modelo.Utils.JsonUtil;
import Modelo.entidades.Medicamento;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MedicamentoDAO implements DAOAbstracto<String, Medicamento> {
    public MedicamentoDAO() throws SQLException {
        this.dao=DaoManager.createDao(ConexionBD.getConexion(), Medicamento.class);
    }
    @Override
    public void add(Medicamento e) throws SQLException {
        dao.create(e);
    }

    @Override
    public Medicamento findById(String id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<Medicamento> findAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void update(Medicamento e) throws SQLException {
        dao.update(e);
    }

    @Override
    public void delete(String id) throws SQLException {
        dao.deleteById(id);
    }

    @Override
    public void exportAllToJson(File file) throws SQLException, IOException {
        List<Medicamento> all = findAll();
        JsonUtil.writeListToFile(all, file);
    }

    @Override
    public void importAllFromJson(File file) throws SQLException, IOException {
        List<Medicamento> list = JsonUtil.readListFromFile(file, new TypeReference<List<Medicamento>>() {});
        for (Medicamento e : list)
            dao.createOrUpdate(e);
    }

    private final Dao<Medicamento, String> dao;
}
