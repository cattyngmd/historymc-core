package ru.historymc.core.command.impl.social;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Globals;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;
import ru.historymc.core.command.exception.InvalidUsageException;
import ru.historymc.core.player.PlayerExtra;
import ru.historymc.core.player.PlayerStorage;

import java.util.Locale;

public final class IgnoreCommand extends Command implements Globals {
    public IgnoreCommand(Main plugin) {
        super(plugin, "ignore");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new InvalidUsageException();
        }

        String name = args[0].toLowerCase(Locale.ROOT);
        Player player = player(args);
        PlayerExtra extra = PlayerStorage.getInstance().get(sender);

        if (extra.isIgnoring(player)) {
            extra.getIgnoreList().remove(name);
            send(sender, "You have ignored {red} %s", player.getName());
        } else {
            extra.getIgnoreList().add(name);
            send(sender, "You have unignored {red} %s", player.getName());
        }
    }
}
