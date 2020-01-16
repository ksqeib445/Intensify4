package ksqeib.Intensify.enums;

import ksqeib.Intensify.main.Intensify;
import org.bukkit.Material;

public enum LocType {
    arms, helmet, chestplate, leggings, boots;

    public static LocType getType(Material itemid) {
        if (Intensify.dataer.arms.contains(itemid)) {
            return arms;
        }
        if (Intensify.dataer.boots.contains(itemid)) {
            return boots;
        }
        if (Intensify.dataer.chestplate.contains(itemid)) {
            return chestplate;
        }
        if (Intensify.dataer.leggings.contains(itemid)) {
            return leggings;
        }
        if (Intensify.dataer.helmet.contains(itemid)) {
            return helmet;
        }
        return null;
    }
}
