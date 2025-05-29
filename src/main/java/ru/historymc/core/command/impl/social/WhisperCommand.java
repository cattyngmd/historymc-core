package ru.historymc.core.command.impl.social;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;
import ru.historymc.core.command.exception.InvalidUsageException;

import java.util.Arrays;

public final class WhisperCommand extends Command {
    public WhisperCommand(Main plugin) {
        super(plugin, "whisper");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            throw new InvalidUsageException();
        }

        String username = args[0];
        Player player = Bukkit.getPlayerExact(username);
        if (player == null) {
            throw new InvalidUsageException();
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        plugin.getChat().send(sender, player, message);
    }
}
