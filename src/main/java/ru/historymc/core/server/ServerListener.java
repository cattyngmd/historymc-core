package ru.historymc.core.server;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import ru.historymc.core.utils.events.EventHandler;

public final class ServerListener implements Listener {
    @EventHandler(type = Event.Type.SERVER_LIST_PING)
    public void onServerListPing(ServerListPingEvent event) {
        event.setMotd("historymc.ru - 1.0.0");
    }
}
