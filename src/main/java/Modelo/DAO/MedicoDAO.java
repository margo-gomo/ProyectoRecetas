package Modelo.DAO;

import Modelo.Utils.JsonUtil;
import Modelo.entidades.Medico;
import Modelo.entidades.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO implements DAOAbstracto<String, Medico> {

    private final Dao<Medico, String> medicoDao;
    private final Dao<Usuario, String> usuarioDao;

    public MedicoDAO() throws SQLException {
        this.medicoDao = DaoManager.createDao(ConexionBD.getConexion(), Medico.class);
        this.usuarioDao = DaoManager.createDao(ConexionBD.getConexion(), Usuario.class);
    }
    @Override
    public void add(Medico e) throws SQLException {
        usuarioDao.createOrUpdate(e.getUsuario());
        e.setUsuarioId(e.getUsuario().getId());
        medicoDao.createOrUpdate(e);
    }
    @Override
    public Medico findById(String id) throws SQLException {
        Medico m = medicoDao.queryForId(id);
        Usuario u = usuarioDao.queryForId(m.getUsuarioId());
        m.setUsuario(u);
        return m;
    }

    @Override
    public List<Medico> findAll() throws SQLException {
        List<Medico> lista = medicoDao.queryForAll();
        for (Medico m : lista) {
            if (m.getUsuarioId() != null) {
                Usuario u = usuarioDao.queryForId(m.getUsuarioId());
                m.setUsuario(u);
            }
        }
        return lista;
    }

    @Override
    public void update(Medico e) throws SQLException {
        usuarioDao.createOrUpdate(e.getUsuario());
        e.setUsuarioId(e.getUsuario().getId());
        medicoDao.update(e);
    }

    @Override
    public void delete(String id) throws SQLException {
        medicoDao.deleteById(id);
        usuarioDao.deleteById(id);
    }

    @Override
    public void exportAllToJson(File file) throws SQLException, IOException {
        List<Medico> all = findAll();
        List<MedicoExport> exports = new ArrayList<>();
        for (Medico m : all) {
            String usuarioId = m.getUsuarioId();
            exports.add(new MedicoExport(usuarioId, m.getEspecialidad()));
        }
        JsonUtil.writeListToFile(exports, file);
    }

    @Override
    public void importAllFromJson(File file) throws SQLException, IOException {
        List<MedicoExport> list = JsonUtil.readListFromFile(file, new TypeReference<List<MedicoExport>>() {});
        for (MedicoExport me : list){
            Usuario u=usuarioDao.queryForId(me.usuario_id);
            Medico med = new Medico(u, me.especialidad);
            try { add(med); }
            catch (SQLException ex) {
                update(med);
            }
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicoExport {
        public String usuario_id;
        public String especialidad;
    }
}
