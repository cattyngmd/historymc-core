package ru.historymc.core.chat;

import org.bukkit.command.CommandSender;
import ru.historymc.core.Globals;
import ru.historymc.core.player.PlayerExtra;
import ru.historymc.core.player.PlayerStorage;

// idk why do i mention it cuz im the one who made it for mcnr lol but anyway
// credits mcnr utils
public final class DirectMessages implements Globals {
    public void send(CommandSender sender, CommandSender recipient, String message) {
        message = message.trim();
        if (message.isBlank()) {
            sender.sendMessage(formatter.format("{red}You sent an empty message."));
            return;
        }

        PlayerExtra senderExtra = PlayerStorage.getInstance().get(sender);
        PlayerExtra recipientExtra = PlayerStorage.getInstance().get(recipient);

        if (senderExtra.isIgnoring(recipient)) {
            sender.sendMessage(formatter.format("{red}You ignore %s."));
            return;
        }

        if (recipientExtra.isIgnoring(sender)) {
            sender.sendMessage(formatter.format("{red}%s is ignoring you."));
            return;
        }

        String formatted = formatter.format("{red}[%s -> %s]{white} %s", sender.getName(), recipient.getName(), message);

        sender.sendMessage(formatted);
        recipient.sendMessage(formatted);

        recipientExtra.setLastSender(sender.getName());
    }
}
