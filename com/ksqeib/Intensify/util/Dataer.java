package ksqeib.Intensify.util;

import com.ksqeib.ksapi.util.Io;
import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Istone;
import ksqeib.Intensify.store.Stone;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
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


    //淬炼


    //FurnaceManager
    public HashMap<Location, String> furnaceUsingMap = new HashMap<>();
    public HashMap<Location, ItemStack> furnaceFuelMap = new HashMap<>();
    //Manager
    public List<Stone> customCuilianStoneList = new ArrayList<>();
    public HashMap<String, Stone> customCuilianStoneMap = new HashMap<>();
    public List<Material> itemList = new ArrayList<>();
    //    Main
    public List<Material> arms = new ArrayList<>();
    public List<Material> helmet = new ArrayList<>();
    public List<Material> chestplate = new ArrayList<>();
    public List<Material> leggings = new ArrayList<>();
    public List<Material> boots = new ArrayList<>();
    public List<String> powerArms = new ArrayList<>();
    public List<String> powerHelmet = new ArrayList<>();
    public List<String> powerChestplate = new ArrayList<>();
    public List<String> powerLeggings = new ArrayList<>();
    public List<String> powerBoots = new ArrayList<>();
    public List<String> localArms = new ArrayList<>();
    public List<String> localHelmet = new ArrayList<>();
    public List<String> localChestplate = new ArrayList<>();
    public List<String> localLeggings = new ArrayList<>();
    public List<String> localBoots = new ArrayList<>();
    public Boolean usingDefaultPower;
    public int cuilianmax = 0;
    public int cuiliannotice = 5;
    public int csuitEffectlevel = 5;
    public List<Double> clchance;
    public int moveLevelUse = 1;
    public String r1, r5;

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
        BlockId.addAll(config.getStringList("drop.blocks"));
        MobId.addAll(config.getStringList("drop.mobs"));
//        淬炼
        //1
        for (String str : config.getStringList("items.arms")) {
            arms.add(Material.getMaterial(str));
        }
        for (String str : config.getStringList("items.helmet")) {
            helmet.add(Material.getMaterial(str));
        }
        for (String str : config.getStringList("items.chestplate")) {
            chestplate.add(Material.getMaterial(str));
        }
        for (String str : config.getStringList("items.leggings")) {
            leggings.add(Material.getMaterial(str));
        }
        for (String str : config.getStringList("items.boots")) {
            boots.add(Material.getMaterial(str));
        }
        itemList.addAll(arms);
        itemList.addAll(helmet);
        itemList.addAll(chestplate);
        itemList.addAll(leggings);
        itemList.addAll(boots);
//        2

        powerArms = config.getStringList("power.arms");
        powerHelmet = config.getStringList("power.helmet");
        powerChestplate = config.getStringList("power.chestplate");
        powerLeggings = config.getStringList("power.leggings");
        powerBoots = config.getStringList("power.boots");
//        3

        localArms = config.getStringList("local.arms");
        localHelmet = config.getStringList("local.helmet");
        localChestplate = config.getStringList("local.chestplate");
        localLeggings = config.getStringList("local.leggings");
        localBoots = config.getStringList("local.boots");

        loadStones();

        cuiliannotice = config.getInt("cuilian.noticelevel");
        csuitEffectlevel = config.getInt("cuilian.suitEffectlevel");
        moveLevelUse = config.getInt("cuilian.moveLevelUse");

        r1 = config.getString("cuilian.style.a");
        r5 = config.getString("cuilian.style.b");
        usingDefaultPower = config.getBoolean("cuilian.UsingDefaultPower");

        clchance = config.getDoubleList("cuilian.levelandchance");
        cuilianmax = clchance.size() + 1;

    }

    public void loadStones() {
        FileConfiguration config1 = Intensify.um.getIo().loadYamlFile(storepath + "/cuilianStone.yml", true);

        for (String i : config1.getKeys(false)) {
            String name = config1.getString(i + ".DisplayName");
            List<String> lore = config1.getStringList(i + ".Lore");
            int riseLevel = config1.getInt(i + ".riseLevel");
            Material itemtype = Material.getMaterial(config1.getString(i + ".Type"));
            List<Integer> dropLevel = config1.getIntegerList(i + ".dropLevel");
            double sharpStar = config1.getDouble(i + ".sharpStar");
            double basePro = config1.getDouble(i + ".basePro");
            ItemStack item = new ItemStack(itemtype);
            ItemMeta id = item.getItemMeta();
            id.addEnchant(Enchantment.OXYGEN, 1, true);
            id.setDisplayName(name);
            id.setLore(lore);
            item.setItemMeta(id);
            item = Intensify.um.getMulNBT().addNBTdata(item, Stone.NBTID, i);
            Stone s = new Stone(item, i, dropLevel, riseLevel, basePro, sharpStar);
            customCuilianStoneMap.put(i, s);
            customCuilianStoneList.add(s);
        }
    }

    public void loadIStones() {
        FileConfiguration config1 = Intensify.um.getIo().loadYamlFile(storepath + "/iitems.yml", true);

        for (String i : config1.getKeys(false)) {
            String name = config1.getString(i + ".DisplayName");
            List<String> lore = config1.getStringList(i + ".Lore");
            Material itemtype = Material.getMaterial(config1.getString(i + ".Type"));
            double add = config1.getDouble(i + ".add");
            boolean issafer = config1.getBoolean(i + ".issafer");
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

    public Stone getCuilianStone(String id) {
        return customCuilianStoneMap.getOrDefault(id, null);
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

        itemList.clear();
        arms.clear();
        helmet.clear();
        chestplate.clear();
        leggings.clear();
        boots.clear();

        powerArms.clear();
        powerBoots.clear();
        powerChestplate.clear();
        powerHelmet.clear();
        powerLeggings.clear();

        localArms.clear();
        localHelmet.clear();
        localChestplate.clear();
        localLeggings.clear();
        localBoots.clear();

        customCuilianStoneList.clear();
    }
}
