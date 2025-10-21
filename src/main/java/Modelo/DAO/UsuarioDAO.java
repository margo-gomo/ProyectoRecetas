package Modelo.DAO;
import Adaptador.XMLUtils;
import Modelo.entidades.Usuario;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
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
    public void guardar (OutputStream out) throws JAXBException, SQLException {
        UsuarioDAOX usuarios=new UsuarioDAOX(findAll());
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(usuarios));
        }
    }
    private final Dao<Usuario, String> dao;
    @XmlRootElement(name = "usuarios")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class UsuarioDAOX{
        public UsuarioDAOX(List<Usuario> lista) {
            this();
            usuarios.addAll(lista);
        }
        public UsuarioDAOX() {
            usuarios = new ArrayList<>();
        }
        @XmlElement
        private List<Usuario> usuarios;
    }
}
