package Modelo.DAO;

import Modelo.Utils.JsonUtil;
import Modelo.entidades.Medico;
import Modelo.entidades.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import lombok.AllArgsConstructor;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO implements DAOAbstracto<String, Medico> {

    private final Dao<Medico, String> medicoDao; // lo usamos para queries raw
    private final Dao<Usuario, String> usuarioDao;

    public MedicoDAO() throws SQLException {
        // creamos DAOs para poder ejecutar consultas/SQL raw
        this.medicoDao = DaoManager.createDao(ConexionBD.getConexion(), Medico.class);
        this.usuarioDao = DaoManager.createDao(ConexionBD.getConexion(), Usuario.class);
    }
    @Override
    public void add(Medico e) throws SQLException {
        usuarioDao.createOrUpdate(e.getUsuario());
        medicoDao.executeRaw(
                "INSERT INTO medico (usuario_id, especialidad) VALUES (?, ?)",
                    e.getUsuario().getId(),
                    e.getEspecialidad() == null ? "" : e.getEspecialidad()
        );
    }
    @Override
    public Medico findById(String id) throws SQLException {
        Usuario u = usuarioDao.queryForId(id);
        if (u == null) return null;
        GenericRawResults<String[]> raw = medicoDao.queryRaw(
                "SELECT especialidad FROM medico WHERE usuario_id = ?",
                id
        );
        String especialidad = null;
        List<String[]> results = raw.getResults();
        if (!results.isEmpty() && results.get(0).length > 0) {
            especialidad = results.get(0)[0];
            if (especialidad != null && especialidad.isEmpty()) especialidad = null;
        }
        Medico m = new Medico(u, especialidad);
        return m;
    }

    @Override
    public List<Medico> findAll() throws SQLException {
        List<Medico> lista = new ArrayList<>();

        // obtenemos todos los usuario_id que estan en medico
        GenericRawResults<String[]> raw = medicoDao.queryRaw("SELECT usuario_id, especialidad FROM medico");
        List<String[]> rows = raw.getResults();
        for (String[] row : rows) {
            String usuarioId = row[0];
            String especialidad = row.length > 1 ? row[1] : null;
            Usuario u = usuarioDao.queryForId(usuarioId);
            Medico m = new Medico(u, especialidad);
            lista.add(m);
        }
        return lista;
    }

    @Override
    public void update(Medico e) throws SQLException {
        usuarioDao.update(e.getUsuario());

        // luego actualizamos solo la columna especialidad
        medicoDao.executeRaw(
                "UPDATE medico SET especialidad = ? WHERE usuario_id = ?",
                e.getEspecialidad() == null ? "" : e.getEspecialidad(),
                e.getUsuario().getId()
        );
    }

    @Override
    public void delete(String id) throws SQLException {
        // borra la fila de medico; no borra el Usuario (decisión de negocio)
        medicoDao.executeRaw("DELETE FROM medico WHERE usuario_id = ?", id);
    }

    @Override
    public void exportAllToJson(File file) throws SQLException, IOException {
        List<Medico> all = findAll();
        List<MedicoExport> exports = new ArrayList<>();
        for (Medico m : all) {
            String usuarioId = m.getUsuario().getId();
            exports.add(new MedicoExport(usuarioId, m.getEspecialidad()));
        }
        JsonUtil.writeListToFile(exports, file);
    }

    @Override
    public void importAllFromJson(File file) throws SQLException, IOException {
        List<MedicoExport> list = JsonUtil.readListFromFile(file, new TypeReference<List<MedicoExport>>() {});
        for (MedicoExport me : list){
            medicoDao.executeRaw(
                    "INSERT INTO medico (usuario_id, especialidad) VALUES (?, ?)",
                    usuarioDao.queryForId(me.usuarioId).getId(),
                    me.especialidad == null ? "" : me.especialidad
            );
        }
    }

    @AllArgsConstructor
    public class MedicoExport {
        public String usuarioId;
        public String especialidad;
    }
}
