package ru.historymc.core.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import ru.historymc.core.Config;
import ru.historymc.core.player.PlayerExtra;
import ru.historymc.core.player.PlayerStorage;
import ru.historymc.core.utils.events.EventHandler;

public class ChatListener implements Listener {
    private final Config config;

    public ChatListener(Config config) {
        this.config = config;
    }

    @EventHandler(type = Event.Type.PLAYER_CHAT)
    public void onChat(PlayerChatEvent event) {
        onColoredText(event);
        onTown(event);
        removeIgnored(event);
    }

    private void onColoredText(PlayerChatEvent event) {
        ChatColor color = config.getColor(event.getMessage().trim());

        if (color != null) {
            event.setMessage(color + event.getMessage());
        }
    }

    private void onTown(PlayerChatEvent event) {
        if (event.getMessage().isEmpty()) return;
        PlayerExtra extra = PlayerStorage.getInstance().get(event.getPlayer());
        if (extra.getTown() != null) {
            event.setMessage(event.getMessage().trim() + " | " + extra.getTown());
        }
    }

    public void removeIgnored(PlayerChatEvent event) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerExtra extra = PlayerStorage.getInstance().get(p);
            if (extra.isIgnoring(event.getPlayer())) {
                event.getRecipients().remove(p);
            }
        }
    }
}
