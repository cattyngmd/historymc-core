package ru.historymc.core.chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import ru.historymc.core.player.PlayerExtra;
import ru.historymc.core.player.PlayerStorage;

// idk why do i mention it cuz im the one who made it for mcnr lol but anyway
// credits mcnr utils
public final class DirectMessages {
    private static final char COLOR_CHAR = '§';
    private static final String MESSAGE_TEMPLATE = "&c[%s -> %s]&f %s";

    public void send(CommandSender sender, CommandSender recipient, String message) {
        message = message.trim();
        if (message.isBlank()) {
            sender.sendMessage(ChatColor.RED + "You sent an empty message.");
            return;
        }

        PlayerExtra senderExtra = PlayerStorage.getInstance().get(sender);
        PlayerExtra recipientExtra = PlayerStorage.getInstance().get(recipient);

        if (senderExtra.isIgnoring(recipient)) {
            sender.sendMessage(ChatColor.RED + recipient.getName() + " ignores you.");
            return;
        }

        if (recipientExtra.isIgnoring(sender)) {
            sender.sendMessage(ChatColor.RED + recipient.getName() + " is ignoring you.");
            return;
        }

        String formatted = format(MESSAGE_TEMPLATE, sender.getName(), recipient.getName(), message);

        sender.sendMessage(formatted);
        recipient.sendMessage(formatted);

        recipientExtra.setLastSender(sender.getName());
    }

    private static String format(String str, Object... objects) {
        return str.replace('&', COLOR_CHAR).formatted(objects);
    }
}
