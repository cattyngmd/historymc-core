package ru.historymc.core.player;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import ru.historymc.core.Config;
import ru.historymc.core.utils.Utils;
import ru.historymc.core.player.source.DataSource;

import java.util.concurrent.ThreadLocalRandom;

public final class PlayerListener implements Listener {
    private final Config config;

    public PlayerListener(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onGameJoin(PlayerJoinEvent event) {
        String player = event.getPlayer().getName();
        PlayerExtra extra = getSource().loadPlayer(player);
        if (extra == null) {
            extra = new PlayerExtra(player);
        }

        PlayerStorage.getInstance().add(extra);
        event.getPlayer().setDisplayName(extra.getColor() + player);
    }

    @EventHandler
    public void onGameLeft(PlayerQuitEvent event) {
        String player = event.getPlayer().getName();
        PlayerExtra extra = PlayerStorage.getInstance().remove(player);

        if (extra != null) {
            getSource().updatePlayer(extra);
        }

        if (event.getPlayer().isOp()) {
            event.getPlayer().setOp(false);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Location location = null;
        while (location == null) {
            location = random(event.getRespawnLocation().getWorld());
        }
        event.setRespawnLocation(location);
    }

    @EventHandler
    public void onWorldBorder(PlayerMoveEvent event) {
        double x = event.getTo().getX();
        double z = event.getTo().getZ();
        int wb = config.getWorldBorder();
        if (x >= wb || x <= -wb || z >= wb || z <= -wb) {
            event.getTo().setX(Utils.clamp((int) x, -wb + 3, wb - 3));
            event.getTo().setZ(Utils.clamp((int) z, -wb + 3, wb - 3));
        }
    }

    private DataSource getSource() {
        return PlayerStorage.getInstance().getSource();
    }

    private Location random(World world) {
        float factorX = ThreadLocalRandom.current().nextFloat();
        float factorZ = ThreadLocalRandom.current().nextFloat();
        int radius = config.getSpawnRadius();
        int x = (int) (factorX * radius * 2 - radius);
        int z = (int) (factorZ * radius * 2 - radius);

        int maxY = world.getHighestBlockYAt(x, z);

        for (int y = maxY; y > 0; y--) {
            if (isSafe(world, x, y, z))
                return new Location(world, x + 0.5f, y, z + 0.5f);
        }
        return null;
    }

    private boolean isSafe(World world, int x, int y, int z) {
        Block legs = world.getBlockAt(x, y, z);
        Block head = world.getBlockAt(x, y + 1, z);
        if (!empty(legs) || !empty(head)) return false;
        Block ground = world.getBlockAt(x, y - 1, z);
        return solid(ground);
    }

    private boolean empty(Block block) {
        return block.isEmpty() || block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER;
    }

    private boolean solid(Block block) {
        return !block.isEmpty() && !block.isLiquid();
    }
}
