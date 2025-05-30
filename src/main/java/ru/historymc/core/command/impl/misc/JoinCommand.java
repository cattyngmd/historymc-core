package ru.historymc.core.command.impl.misc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Globals;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;
import ru.historymc.core.player.PlayerExtra;
import ru.historymc.core.player.PlayerStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class JoinCommand extends Command implements Globals {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd'%s' y", Locale.US);

    public JoinCommand(Main plugin) {
        super(plugin, "join");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        Player player = player(args);
        PlayerExtra extra = PlayerStorage.getInstance().get(sender);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(extra.getJoinTime());
        String date = getPrettyDate(calendar);

        sender.sendMessage(formatter.format("%s first joined the server on {red} %s", player.getName(), date));
    }

    private static String getPrettyDate(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = DATE_FORMAT.format(calendar.getTime());
        return String.format(date, getDaySuffix(day));
    }

    private static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}
