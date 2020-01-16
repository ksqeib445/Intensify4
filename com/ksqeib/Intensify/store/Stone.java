package ksqeib.Intensify.store;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Stone {
    public static String NBTID = "CuilianStone";
    public double basePro;
    public double sharpStar;
    public List<Integer> dropLevel = new ArrayList<>();
    public String id;
    public int riseLevel = 0;
    private ItemStack cuiLianStone;

    public Stone(ItemStack cuiLianStone, String id, List<Integer> dropLevel, int riseLevel, double basePro, double sharpStar) {
        this.cuiLianStone = cuiLianStone;
        this.id = id;
        this.dropLevel = dropLevel;
        this.riseLevel = riseLevel;
        this.sharpStar = sharpStar;
    }

    public double getBasePro() {
        return basePro;
    }

    public ItemStack getCuiLianStone() {
        return new ItemStack(cuiLianStone);
    }

    public double getSharpStar() {
        return sharpStar;
    }

    public List<Integer> getDropLevel() {
        return dropLevel;
    }

    public String getId() {
        return id;
    }

    public int getRiseLevel() {
        return riseLevel;
    }
}
