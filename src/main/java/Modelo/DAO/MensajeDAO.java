package Modelo.DAO;

import Modelo.Utils.JsonUtil;
import Modelo.entidades.Mensaje;
import Modelo.entidades.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MensajeDAO {

    private final Dao<Mensaje, String> mensajeDao;
    private final Dao<Usuario, String> usuarioDao;

    public MensajeDAO() throws SQLException {
        this.mensajeDao = DaoManager.createDao(ConexionBD.getConexion(), Mensaje.class);
        this.usuarioDao = DaoManager.createDao(ConexionBD.getConexion(), Usuario.class);
    }

    /* ---------------- CRUD básicos ---------------- */

    public void add(Mensaje e) throws SQLException {
        mensajeDao.create(e);
    }

    public void update(Mensaje e) throws SQLException{
        mensajeDao.update(e);
    }

    public List<Mensaje> findAll() throws SQLException {
        return mensajeDao.queryForAll();
    }


    public List<Mensaje> recibirMensaje(String remitenteId, String destinatarioId) throws SQLException {
        QueryBuilder<Mensaje, String> qAB = mensajeDao.queryBuilder();
        qAB.where().eq("remitente", remitenteId).and().eq("destinatario", destinatarioId);
        qAB.orderBy("id", true);
        List<Mensaje> ab = qAB.query();

        QueryBuilder<Mensaje, String> qBA = mensajeDao.queryBuilder();
        qBA.where().eq("remitente", destinatarioId).and().eq("destinatario", remitenteId);
        qBA.orderBy("id", true); // idem nota de fecha_envio
        List<Mensaje> ba = qBA.query();

        List<Mensaje> todos = new ArrayList<>(ab.size() + ba.size());
        todos.addAll(ab);
        todos.addAll(ba);
        todos.sort(Comparator.comparing(Mensaje::getId));

        for (Mensaje m : todos) {
            Usuario dst = m.getDestinatario();
            if (dst != null && remitenteId.equals(dst.getId()) && m.getLeido() == 0) {
                m.setLeido(1);
                mensajeDao.update(m);
            }
        }
        return todos;
    }

    public void marcarLeido(List<Mensaje> mensajes)throws SQLException{
        for(Mensaje e:mensajes){
            e.setLeido(1);
            mensajeDao.update(e);
        }
    }

    public MensajeDTO mensajedto(String id) throws SQLException{
        Mensaje e = mensajeDao.queryForId(id);
        return new MensajeDTO(
                e.getId(),
                e.getRemitente().getId(),
                e.getDestinatario().getId(),
                e.getTexto(),
                e.getLeido()
        );
    }

    /* ---------------- Soporte para backend (ClientHandler) ---------------- */

    public String validarUsuario(String id, String clave) throws SQLException {
        Usuario u = usuarioDao.queryForId(id);
        if (u == null) return null;
        if (u.getClave() == null || !Objects.equals(u.getClave(), clave)) return null;
        return (u.getNombre() == null || u.getNombre().isBlank()) ? id : u.getNombre();
    }

    public MensajeDTO enviar(String remitenteId, String destinatarioId, String texto) throws SQLException {
        Usuario rem = usuarioDao.queryForId(remitenteId);
        Usuario dst = usuarioDao.queryForId(destinatarioId);
        if (rem == null) throw new SQLException("Remitente no existe: " + remitenteId);
        if (dst == null) throw new SQLException("Destinatario no existe: " + destinatarioId);

        String id = "Mensaje-"+String.valueOf(findAll().size()+1);
        Mensaje m = new Mensaje(id, rem, dst, texto, 0);
        mensajeDao.create(m);

        return new MensajeDTO(id, rem.getId(), dst.getId(), texto, 0);
    }

    public List<MensajeDTO> listarConversacionesDe(String userId) throws SQLException {
        QueryBuilder<Mensaje, String> qb = mensajeDao.queryBuilder();
        Where<Mensaje, String> w = qb.where();
        w.eq("remitente", userId).or().eq("destinatario", userId);

        List<Mensaje> list = qb.query();
        return list.stream()
                .map(e -> new MensajeDTO(
                        e.getId(),
                        e.getRemitente().getId(),
                        e.getDestinatario().getId(),
                        e.getTexto(),
                        e.getLeido()))
                .collect(Collectors.toList());
    }

    /** Helper para convertir listas de entidades a DTOs (útil en backend). */
    public List<MensajeDTO> toDTOs(List<Mensaje> lista) {
        if (lista == null) return Collections.emptyList();
        List<MensajeDTO> res = new ArrayList<>(lista.size());
        for (Mensaje e : lista) {
            if (e == null) continue;
            String rem = (e.getRemitente() != null) ? e.getRemitente().getId() : null;
            String des = (e.getDestinatario() != null) ? e.getDestinatario().getId() : null;
            res.add(new MensajeDTO(e.getId(), rem, des, e.getTexto(), e.getLeido()));
        }
        return res;
    }

    /* ---------------- Export/Import JSON para persistencia offline ---------------- */

    public void exportAllToJson(File file) throws SQLException, IOException {
        List<Mensaje> mensajes = findAll();
        List<MensajeDTO> exports= new ArrayList<>();
        for(Mensaje e:mensajes){
            String remitente=e.getRemitente().getId();
            String destinatario=e.getDestinatario().getId();
            exports.add(new MensajeDTO(e.getId(),remitente,destinatario,e.getTexto(),e.getLeido()));
        }
        JsonUtil.writeListToFile(exports, file);
    }

    public void importAllFromJson(File file) throws SQLException, IOException {
        List<MensajeDTO> list= JsonUtil.readListFromFile(file, new TypeReference<List<MensajeDTO>>() {});
        for(MensajeDTO m:list){
            Usuario remitente=usuarioDao.queryForId(m.getRemitente());
            Usuario destinatario=usuarioDao.queryForId(m.getDestinatario());
            if (remitente == null || destinatario == null) continue;
            Mensaje mensaje=new Mensaje(m.getId(), remitente, destinatario, m.getTexto(), m.getLeido());
            try{
                add(mensaje);
            }catch(SQLException e){
                update(mensaje);
            }
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MensajeDTO {
        private String id;
        private String remitente;
        private String destinatario;
        private String texto;
        private int leido;
    }
}
