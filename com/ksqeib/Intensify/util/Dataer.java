package ksqeib.Intensify.util;

import com.ksqeib.ksapi.util.Io;
import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Istone;
import ksqeib.Intensify.store.Stone;
import ksqeib.Intensify.store.SuitEffect;
import ksqeib.Intensify.store.Wings;
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
    public List<Material> itemList = new ArrayList<>();
    public SuitEffect NULLSuitEffect;
    public Wings NULLWings;
    public Stone NULLStone;
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
    public HashMap<UUID, Integer> playerSuitEffectList = new HashMap<>();
    public HashMap<UUID, Double> playerSuitEffectHealthList = new HashMap<>();
    public Boolean usingDefaultPower;
    public int cuilianmax = 0;
    public int cuiliannotice = 5;
    public int csuitEffectlevel = 5;
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
        for (String s : config.getStringList("drop.blocks")) {
            BlockId.add(s);
        }
        for (String s : config.getStringList("drop.mobs")) {
            MobId.add(s);
        }
//        淬炼

        NULLWings = new Wings(null, 0, 0, 0);
        NULLStone = new Stone(new ItemStack(Material.AIR), "", new ArrayList<>(), 0, 0d, 0d);
        NULLSuitEffect = new SuitEffect(new ArrayList<>(), 0);
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
    }

    public void loadStones() {
        FileConfiguration config1 = Intensify.um.getIo().loadYamlFile(storepath + "/cuilianStone.yml", true);

        for (String i : config1.getKeys(false)) {
            String name = config1.getString(i + ".DisplayName");
            List<String> lore = config1.getStringList(i + ".Lore");
            int riseLevel = config1.getInt(i + ".riseLevel");
            Material itemtype = Material.getMaterial(config1.getString(i + ".Type"));
            List<Integer> dropLevel = config1.getIntegerList(i + ".dropLevel");
            Double sharpStar = config1.getDouble(i + ".sharpStar");
            Double basePro = config1.getDouble(i + ".basePro");
            ItemStack item = new ItemStack(itemtype);
            ItemMeta id = item.getItemMeta();
            id.addEnchant(Enchantment.OXYGEN, 1, true);
            id.setDisplayName(name);
            id.setLore(lore);
            item.setItemMeta(id);
            Stone s = new Stone(item, i, dropLevel, riseLevel, basePro, sharpStar);
            customCuilianStoneList.add(s);
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

    public Istone getStonebyItem(ItemStack i) {

        for (String key : istones.keySet()) {
            ItemStack get = istones.get(key).stone;
            if (get.getItemMeta().hasDisplayName() && i.getItemMeta().hasDisplayName())
                if (get.getItemMeta().equals(i.getItemMeta()))
                    return istones.get(key);
        }
        return null;
    }

    public Istone getStonebyId(String id) {
        if (istones.get(id) == null) return null;
        return istones.get(id);
    }

    public Istone getStonebyNBT(ItemStack item) {
        String id = Intensify.um.getMulNBT().getNBTdataStr(item, Istone.NBTID);
//        Bukkit.getLogger().warning("f3");
        if (id == null) return null;
//        Bukkit.getLogger().warning("f2");
        if (istones.get(id) == null) return null;
//        Bukkit.getLogger().warning("f1");
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
