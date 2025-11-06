
package Modelo.Backend;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Iterator;

public class ConnectionRegistry {
    private final Map<String, PrintWriter> clients = new ConcurrentHashMap<>();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void register(String userId, PrintWriter out) {
        if (userId == null || out == null) return;
        clients.put(userId, out);
    }

    public boolean isRegistered(String userId) {
        return userId != null && clients.containsKey(userId);
    }


    public void unregister(String userId) {
        if (userId == null) return;
        clients.remove(userId);
    }

    public void broadcast(ObjectNode node) {
        String json = node.toString();
        Iterator<Map.Entry<String, PrintWriter>> it = clients.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, PrintWriter> e = it.next();
            PrintWriter pw = e.getValue();
            try {
                pw.println(json);
            } catch (Exception ex) {
                it.remove();
            }
        }
    }

    public void broadcastUserLogin(String id, String nombre) {
        ObjectNode n = MAPPER.createObjectNode();
        n.put("op", "userLogin");
        n.put("id", id);
        n.put("nombre", nombre == null ? "" : nombre);
        broadcast(n);
    }

    public void broadcastUserLogout(String id) {
        ObjectNode n = MAPPER.createObjectNode();
        n.put("op", "userLogout");
        n.put("id", id);
        broadcast(n);
    }
}
