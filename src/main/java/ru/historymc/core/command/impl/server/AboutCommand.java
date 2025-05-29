package ru.historymc.core.command.impl.server;

import org.bukkit.command.CommandSender;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;

// TODO
public final class AboutCommand extends Command {
    public AboutCommand(Main plugin) {
        super(plugin, "about");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        throw new UnsupportedOperationException();
    }
}
