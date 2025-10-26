package Modelo.DAO;
import Modelo.Utils.JsonUtil;
import Modelo.entidades.Medico;
import Modelo.entidades.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO implements DAOAbstracto<String,Medico> {
    public MedicoDAO() throws SQLException {
        this.dao = DaoManager.createDao(ConexionBD.getConexion(), Medico.class);
    }
    @Override
    public void add(Medico e) throws SQLException {
        dao.createOrUpdate(e);
    }

    @Override
    public Medico findById(String id) throws SQLException {
        return dao.queryForId(id);
    }
    
    @Override
    public List<Medico> findAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void update(Medico e) throws SQLException {
        dao.update(e);
    }

    @Override
    public void delete(String id) throws SQLException {
        dao.deleteById(id);
    }

    @Override
    public void exportAllToJson(File file) throws SQLException, IOException {
        List<Medico> all = findAll();
        List<MedicoExport> exports = new ArrayList<>();
        for (Medico m : all) {
            String usuarioId = null;
            if (m.getUsuario() != null) usuarioId = m.getUsuario().getId();
            exports.add(new MedicoExport(usuarioId, m.getEspecialidad()));
        }
        JsonUtil.writeListToFile(exports, file);
    }

    @Override
    public void importAllFromJson(File file) throws SQLException, IOException {

    }
    public void importFromJson(File file, List<Usuario>usuarios) throws SQLException, IOException{
        List<MedicoExport> list = JsonUtil.readListFromFile(file, new TypeReference<List<MedicoExport>>() {});
        for (MedicoExport me : list) {
            Usuario usuario = new Usuario();
            for(Usuario u : usuarios){
                if(u.getId().equals(me.usuarioId)){
                    usuario = u;
                    break;
                }
            }
            // armar objeto Medico y persistir
            Medico med = new Medico(usuario, me.especialidad);
            add(med);
        }
    }
    @AllArgsConstructor
    public class MedicoExport {
        public String usuarioId;
        public String especialidad;
    }
    private final Dao<Medico, String> dao;
}
