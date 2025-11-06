package Modelo.Backend;

import Modelo.entidades.Usuario;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineRegistry {

    private static class Entry {
        final Usuario usuario;
        volatile long lastSeen;
        Entry(Usuario u) { this.usuario = u; this.lastSeen = System.currentTimeMillis(); }
    }

    // userId -> info
    private final ConcurrentHashMap<String, Entry> map = new ConcurrentHashMap<>();
    // cuánto tiempo consideramos “online” sin latidos
    private static final long TTL_MS = 60_000; // 60 s

    public void markOnline(Usuario u) {
        if (u == null || u.getId() == null) return;
        map.put(u.getId(), new Entry(u));
    }

    public void heartbeat(String userId) {
        Entry e = map.get(userId);
        if (e != null) e.lastSeen = System.currentTimeMillis();
    }

    public void logout(String userId) {
        if (userId != null) map.remove(userId);
    }

    public List<Usuario> list() {
        long now = System.currentTimeMillis();
        List<Usuario> out = new ArrayList<>();
        map.forEach((id,e) -> { if (now - e.lastSeen <= TTL_MS) out.add(e.usuario); });
        out.sort(Comparator.comparing(u -> Optional.ofNullable(u.getNombre()).orElse("")));
        return out;
    }
}
