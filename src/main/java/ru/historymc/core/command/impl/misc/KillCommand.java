package ru.historymc.core.command.impl.misc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;

public final class KillCommand extends Command {
    public KillCommand(Main plugin) {
        super(plugin, "kill");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        if (sender instanceof Player) {
            ((Player) sender).setHealth(0);
        }
    }
}
