package Modelo.DAO;

import Modelo.Utils.JsonUtil;
import Modelo.entidades.Mensaje;
import Modelo.entidades.Usuario;
import Modelo.Proxy.dto.MensajeDTO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MensajeDAO {

    private final Dao<Mensaje, String> mensajeDao;
    private final Dao<Usuario, String> usuarioDao;

    public MensajeDAO() throws SQLException {
        this.mensajeDao = DaoManager.createDao(ConexionBD.getConexion(), Mensaje.class);
        this.usuarioDao = DaoManager.createDao(ConexionBD.getConexion(), Usuario.class);
    }

    public void add(Mensaje e) throws SQLException {
        mensajeDao.create(e);
    }
    public void update(Mensaje e) throws SQLException{
        mensajeDao.update(e);
    }

    public List<Mensaje> findByRemitente(String usuarioId) throws SQLException{
        QueryBuilder<Mensaje, String> qb = mensajeDao.queryBuilder();
        qb.where().eq("remitente", usuarioId).and().eq("leido", 0).query();
        marcarLeido(qb.query());
        return qb.query();
    }
    public void marcarLeido(List<Mensaje> mensajes)throws SQLException{
        for(Mensaje e:mensajes){
            e.setLeido(1);
            mensajeDao.update(e);
        }
    }
    public MensajeDTO mensajedto(Mensaje e) throws SQLException{
        return new MensajeDTO(e.getId(),e.getRemitente().getId(),e.getDestinatario().getId(),e.getTexto(),e.getLeido());
    }
    public void exportAllToJson(File file) throws SQLException, IOException {

    }

    public void importAllFromJson(File file) throws SQLException, IOException {
        List<MensajeDTO> list= JsonUtil.readListFromFile(file, new TypeReference<List<MensajeDTO>>() {});
        for(MensajeDTO m:list){
            Usuario remitente=usuarioDao.queryForId(m.remitente);
            Usuario destinatario=usuarioDao.queryForId(m.destinatario);
            Mensaje mensaje=new Mensaje(m.id, remitente,destinatario,m.texto,m.leido);
            try{
                add(mensaje);
            }catch(SQLException e){
                update(mensaje);
            }
        }
    }
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MensajeDTO {
        private String id;
        private String remitente;
        private String destinatario;
        private String texto;
        private int leido;
    }
}
    /*
    Devuelve nombre si id/clave son válidos; null si no.
    public String validarUsuario(String id, String clave) throws SQLException {
        QueryBuilder<Usuario, String> qb = usuarioDao.queryBuilder();
        qb.where().eq("id", id).and().eq("clave", clave);
        Usuario u = usuarioDao.queryForFirst(qb.prepare());
        return (u != null) ? u.getNombre() : null;
    }

     Inserta un mensaje y devuelve DTO.
    public MensajeDTO enviar(String remitente, String destinatario, String texto) throws SQLException {
        Mensaje m = new Mensaje(remitente, destinatario, texto, new Date());
        mensajeDao.create(m);
        return toDTO(m);
    }

    Lista mensajes donde el usuario participa, ordenados por fecha desc.
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
    }*/