package ru.historymc.core.command.impl.social;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;
import ru.historymc.core.command.exception.InvalidUsageException;
import ru.historymc.core.player.PlayerStorage;

public final class ReplyCommand extends Command {
    public ReplyCommand(Main plugin) {
        super(plugin, "reply");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            throw new InvalidUsageException();
        }

        String username = PlayerStorage.getInstance().get(sender).getLastSender();

        if (username == null) {
            throw new CommandException("You haven't received any DMs yet.");
        }

        Player player = Bukkit.getPlayerExact(username);
        if (player == null) {
            throw new CommandException("The recipient is not online.");
        }

        String message = String.join(" ", args);

        plugin.getChat().send(sender, player, message);
    }
}
