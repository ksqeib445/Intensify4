package ksqeib.Intensify.store;

import org.bukkit.inventory.ItemStack;

public class Istone {
    public static String NBTID = "IntensifyStone";
    public ItemStack stone;
    public String id;
    public double add;
    public boolean issafe;

    public Istone(ItemStack stone, String id, double add, boolean issafe) {
        this.stone = stone;
        this.id = id;
        this.add = add;
        this.issafe = issafe;
//        Bukkit.getLogger().warning(issafe+"");
    }

    public ItemStack getStone() {
        return stone;
    }

    public String getId() {
        return id;
    }

    public double getAdd() {
        return add;
    }

    public boolean isIssafe() {
        return issafe;
    }
}
