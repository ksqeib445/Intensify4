package ksqeib.Intensify.util;

import com.ksqeib.ksapi.util.Io;
import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Istone;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Dataer {
    //风格定义

    public static String storepath = "store";
    public static Dataer instance;
    public String cg, sb;
    public Random rm = new Random();
    public int maxlevel;
    public List<String> BlockId = new ArrayList();
    public List<String> MobId = new ArrayList();
    //强化中的熔炉列表
    public HashMap<Integer, String> fuelItem = new HashMap();
    //右键后记录的方块玩家
    public HashMap<Integer, UUID> player = new HashMap();
    public HashMap<String, Istone> istones = new HashMap();
    public HashMap<String, Integer> qhlevel = new HashMap();
    public List<Double> qhchance;


    public void init(FileConfiguration config) {
        instance = this;
        //物品读取
        loadIStones();
        //等级读取
        Io.loadintlist(qhlevel, "level", config);
        //风格读取
        cg = config.getString("style.a");
        sb = config.getString("style.b");
        //几率读取
        qhchance = config.getDoubleList("chance.default");
        maxlevel = qhchance.size();
        for (String s : config.getStringList("drop.blocks")) {
            BlockId.add(s);
        }
        for (String s : config.getStringList("drop.mobs")) {
            MobId.add(s);
        }

    }
    public void loadIStones() {
        FileConfiguration config1 = Intensify.um.getIo().loadYamlFile(storepath + "/iitems.yml", true);

        for (String i : config1.getKeys(false)) {
            String name = config1.getString(i + ".DisplayName");
            List<String> lore = config1.getStringList(i + ".Lore");
            Material itemtype = Material.getMaterial(config1.getString(i + ".Type"));
            Double add = config1.getDouble(i + ".add");
            Boolean issafer = config1.getBoolean(i + ".issafer");
            ItemStack item = new ItemStack(itemtype);
            ItemMeta id = item.getItemMeta();
            id.setDisplayName(name);
            id.setLore(lore);
            item.setItemMeta(id);
            item = Intensify.um.getMulNBT().addNBTdata(item, Istone.NBTID, i);
            Istone s = new Istone(item, i, add, issafer);
            istones.put(i, s);
        }
    }


    public double getChance(int lel) {
        if (qhchance.get(lel) == null) return -1d;
        return qhchance.get(lel);
    }

    public int getLevel(String str) {
        return qhlevel.get(str);
    }

    public ItemStack getItem(String str) {
        return new ItemStack(istones.get(str).stone);
    }

    public List<String> getBlockId() {
        return BlockId;
    }

    public List<String> getMobId() {
        return MobId;
    }

    public Istone getStonebyId(String id) {
        if (istones.get(id) == null) return null;
        return istones.get(id);
    }


    public void clearAll() {
        BlockId.clear();
        MobId.clear();
        qhchance.clear();
    }

    private HashMap<String, ItemStack> getAll(FileConfiguration file) {
        //读取配置
        HashMap<String, ItemStack> hash = new HashMap<>();
        for (String string : file.getValues(false).keySet()) {
            //获取全部样式
            hash.put(string.toLowerCase(), Intensify.um.getItemsr().rep(file.getItemStack(string), null));
        }
        return hash;
    }
}
