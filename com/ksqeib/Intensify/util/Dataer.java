package ksqeib.Intensify.util;

import com.ksqeib.ksapi.util.Io;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Dataer {
    //风格定义
    public static String cg, sb;
    public static Random rm = new Random();
    public static HashMap<Integer, Integer> random = new HashMap();
    public static List<String> BlockId = new ArrayList();
    public static List<String> MobId = new ArrayList();
    //强化中的熔炉列表
    public static HashMap<Integer, ItemStack> fuelItem = new HashMap();
    //右键后记录的方块玩家
    public static HashMap<Integer, UUID> player = new HashMap();
    public static HashMap<String, ItemStack> qhitem = new HashMap();
    public static HashMap<String, Integer> qhlevel = new HashMap();
    public static HashMap<String, Integer> qhchance = new HashMap();

    public static HashMap<String, Boolean> safer = new HashMap();

    public static void init(FileConfiguration config) {

        //物品读取
        Io.loaditemlist(qhitem, "items", config);
        //等级读取
        Io.loadintlist(qhlevel, "level", config);
        //风格读取
        cg = config.getString("style.a");
        sb = config.getString("style.b");
        //几率读取
        Io.loadintlist(qhchance, "chance", config);

        Io.loadbooleanlist(safer, "safer", config);

        String[] arr = config.getString("chance.default").split(",");
        for (int i = 0; i < getLevel("maxlevel"); i++) {
            int a = Integer.valueOf(arr[i]);
            random.put(i, a);
        }
        for (String s : config.getStringList("drop.blocks")) {
            BlockId.add(s);
        }
        for (String s : config.getStringList("drop.mobs")) {
            MobId.add(s);
        }
    }

    public static int getLevel(String str) {
        return qhlevel.get(str);
    }

    public static ItemStack getItem(String str) {
        return new ItemStack(qhitem.get(str));
    }

    public static Integer getChance(String str) {
        return qhchance.get(str);
    }

    public static List<String> getBlockId() {
        return BlockId;
    }

    public static List<String> getMobId() {
        return MobId;
    }

    public static Boolean getSafer(String name) {
        return safer.get(name);
    }

    public static String getStoneinName(ItemStack i) {
        for (String key : qhitem.keySet()) {
            ItemStack get = qhitem.get(key);
            if (get.getItemMeta().hasDisplayName() && i.getItemMeta().hasDisplayName())
                if (get.getItemMeta().equals(i.getItemMeta()))
                    return key.toLowerCase();
        }
        return "null";
    }
}
