package ru.historymc.core.command.impl.server;

import org.bukkit.command.CommandSender;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;

public final class HelpCommand extends Command {
    public HelpCommand(Main plugin) {
        super(plugin, "help");
    }

    String[] cmds = new String[] { "list", "kill", "whisper", "reply", "ignore", "town", "join" };

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        for (String cmd : cmds) {
            sender.sendMessage(getUsage(cmd));
        }
    }

    String getUsage(String cmd) {
        return plugin.getCommand(cmd).getUsage();
    }
}
