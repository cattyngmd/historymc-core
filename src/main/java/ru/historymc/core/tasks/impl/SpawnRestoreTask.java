package ru.historymc.core.tasks.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import ru.historymc.core.Main;
import ru.historymc.core.tasks.AbstractTask;
import ru.historymc.core.utils.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class SpawnRestoreTask extends AbstractTask {
    public SpawnRestoreTask(Main main) {
        super(main, 10, 20);
    }

    @Override
    public void run() {
        World world = Bukkit.getWorld("world");

        BlockPos pos = main.getPluginConfig().getSpawn();
        for (BlockPos bp : getSpawnPositions(pos)) {
            Location loc = bp.down().toLoc(world);
            Block block = world.getBlockAt(loc);
            if (block.getType() != Material.BEDROCK) {
                block.setType(Material.BEDROCK);
            }
            block = world.getBlockAt(bp.toLoc(world));
            if (block.getType() != Material.AIR) {
                block.setType(Material.AIR);
            }
        }
    }

    private static Iterable<BlockPos> getSpawnPositions(BlockPos root) {
        Set<BlockPos> spawn = new HashSet<>();
        spawn.add(rel(root, 0.5, 0, 0.5));
        spawn.add(rel(root, -0.5, 0, 0.5));
        spawn.add(rel(root, 0.5, 0, -0.5));
        spawn.add(rel(root, -0.5, 0, -0.5));
        return spawn;
    }

    private static BlockPos rel(BlockPos pos, double relX, double relY, double relZ) {
        return BlockPos.ofFloored(pos.x() + relX, pos.y() + relY, pos.z() + relZ);
    }
}
