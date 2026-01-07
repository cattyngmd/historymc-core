package ru.historymc.core.chat;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import ru.historymc.core.Main;
import ru.historymc.core.utils.events.EventHelper;

public class Chat {
    private final DirectMessages dm = new DirectMessages();
    private final Main main;

    public Chat(Main main) {
        this.main = main;
    }

    public void init() {
        register(new ChatListener(main.getPluginConfig()));
    }

    public void send(CommandSender sender, CommandSender recipient, String message) {
        dm.send(sender, recipient, message);
    }

    private void register(Listener listener) {
        EventHelper.register(main, listener);
    }
}
