package ksqeib.Intensify.util;

import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Istone;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class Isqh {
    //学猫叫
    public static Sound getAnvilSound() {
        Sound[] soundlist = Sound.values();
        for (int i = 0; i < soundlist.length; i++) {
            if (soundlist[i].name().equalsIgnoreCase("BLOCK_ANVIL_USE")) {
                return Sound.valueOf("BLOCK_ANVIL_USE");
            }
            if (soundlist[i].name().equalsIgnoreCase("ANVIL_USE")) {
                return Sound.valueOf("ANVIL_USE");
            }
        }
        return null;
    }

    //判断强化石方法
    public static boolean isqhStone(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (!(item.hasItemMeta())) {
            return false;
        }
        String id = getNBTID(item);
        if (id == null) {
            return false;
        }
        for (String str : Intensify.dataer.istones.keySet()) {
            if (str.equals(id))
                return true;
        }
        return false;
    }

    public static String getNBTID(ItemStack item) {
        return Intensify.um.getMulNBT().getNBTdataStr(item, Istone.NBTID);
    }

    //是不是强化武器
    @SuppressWarnings({"rawtypes", "deprecation"})
    public static boolean isCanQhWeapon(ItemStack item) {
        if (item == null) {
            //不是假东西
            return false;
        }
        Iterator it = Intensify.um.getIo().getaConfig("config").getConfigurationSection("id.items").getKeys(false).iterator();
        //遍历
        while (it.hasNext()) {
            String i = (String) it.next();
            int a = Integer.parseInt(i);
//	      Bukkit.getLogger().warning(item.getTypeId()+" "+a+" "+item.getAmount());

            if ((a == item.getTypeId()) && (item.getAmount() == 1)) {
                //如果ID相同，并且只有一个
                return true;
            }
        }
        return false;
    }

}
