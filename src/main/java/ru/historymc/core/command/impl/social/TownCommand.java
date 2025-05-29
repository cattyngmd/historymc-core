package ru.historymc.core.command.impl.social;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;
import ru.historymc.core.command.exception.InvalidUsageException;
import ru.historymc.core.player.PlayerStorage;

public final class TownCommand extends Command {
    public TownCommand(Main plugin) {
        super(plugin, "town");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof Player)) {
            throw new InvalidUsageException();
        }

        if (args.length == 0) {
            PlayerStorage.getInstance().get(sender).setTown(null);
            return;
        }

        String town = String.join(" ", args);
        PlayerStorage.getInstance().get(sender).setTown(town);
    }
}
