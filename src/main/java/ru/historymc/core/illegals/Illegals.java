package ru.historymc.core.illegals;

import org.bukkit.Material;

import java.util.Set;

public class Illegals {
    private static final Set<Material> ILLEGAS = Set.of(
            Material.BEDROCK, Material.WATER, Material.STATIONARY_WATER,
            Material.LAVA, Material.STATIONARY_LAVA,
            Material.PISTON_EXTENSION, Material.FIRE,
            Material.MOB_SPAWNER, Material.REDSTONE_WIRE, Material.SOIL,
            Material.BURNING_FURNACE, Material.SIGN_POST,
            Material.WALL_SIGN, Material.IRON_DOOR_BLOCK,
            Material.REDSTONE_TORCH_OFF, Material.PORTAL,
            Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON
    );

    public static boolean isIllegal(Material material) {
        return ILLEGAS.contains(material);
    }

    public static boolean isIllegal(int type) {
        return isIllegal(Material.getMaterial(type));
    }
}
