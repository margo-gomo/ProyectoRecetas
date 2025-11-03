package Modelo.DAO;

import Modelo.entidades.Mensaje;
import Modelo.entidades.Usuario;
import Modelo.Proxy.dto.MensajeDTO;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MensajeDAO {

    private final Dao<Mensaje, Long> mensajeDao;
    private final Dao<Usuario, String> usuarioDao;

    public MensajeDAO() throws SQLException {
        ConnectionSource cs = ConexionBD.getConexion();
        this.mensajeDao = DaoManager.createDao(cs, Mensaje.class);
        this.usuarioDao = DaoManager.createDao(cs, Usuario.class);
    }

    /** Devuelve nombre si id/clave son válidos; null si no. */
    public String validarUsuario(String id, String clave) throws SQLException {
        QueryBuilder<Usuario, String> qb = usuarioDao.queryBuilder();
        qb.where().eq("id", id).and().eq("clave", clave);
        Usuario u = usuarioDao.queryForFirst(qb.prepare());
        return (u != null) ? u.getNombre() : null;
    }

    /** Inserta un mensaje y devuelve DTO. */
    public MensajeDTO enviar(String remitente, String destinatario, String texto) throws SQLException {
        Mensaje m = new Mensaje(remitente, destinatario, texto, new Date());
        mensajeDao.create(m);
        return toDTO(m);
    }

    /** Lista mensajes donde el usuario participa, ordenados por fecha desc. */
    public List<MensajeDTO> listarConversacionesDe(String userId) throws SQLException {
        QueryBuilder<Mensaje, Long> qb = mensajeDao.queryBuilder();
        qb.where().eq("remitente", userId).or().eq("destinatario", userId);
        qb.orderBy("fecha_envio", false);
        List<Mensaje> lista = mensajeDao.query(qb.prepare());
        return lista.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private MensajeDTO toDTO(Mensaje m) {
        MensajeDTO dto = new MensajeDTO();
        dto.setId(m.getId());
        dto.setRemitente(m.getRemitente());
        dto.setDestinatario(m.getDestinatario());
        dto.setTexto(m.getTexto());

        dto.setFechaEnvio(m.getFechaEnvio().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime());
        return dto;
    }
}
