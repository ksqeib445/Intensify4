package ksqeib.Intensify.main;

import com.ksqeib.ksapi.KsAPI;
import com.ksqeib.ksapi.util.MulNBT;
import com.ksqeib.ksapi.util.Tip;
import ksqeib.Intensify.enums.LocType;
import ksqeib.Intensify.enums.Sectype;
import ksqeib.Intensify.store.Stone;
import ksqeib.Intensify.util.LevelCalc;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

import static com.ksqeib.ksapi.util.Io.getRandom;
import static ksqeib.Intensify.main.Intensify.um;

public class NewAPI {

    public static MulNBT mulNBT = Intensify.um.getMulNBT();
    public static LevelCalc levelCalc = Intensify.levelCalc;
    public static Tip tip = Intensify.um.getTip();
    public static String lorestart = "cuilianlorestart";
    Plugin plugin;

    public NewAPI(Plugin p) {
        plugin = p;
    }

    public static int getMinLevel(List<ItemStack> list) {
        if (list.size() < 5 && (Intensify.ServerVersion == 2 || Intensify.ServerVersion == 1)) {
            return -1;
        }
        if (list.size() < 6 && (Intensify.ServerVersion == 3)) {
            return -1;
        }
        boolean flag = true;
        int ret = 0;
        for (ItemStack item : list) {
            int l = getLelByNBT(item);
            if (l == -1) {
                return -1;
            }
            if (flag) {
                ret = l;
                flag = false;
            } else {
                if (l < ret) {
                    ret = l;
                }
            }
        }
        return ret;
    }


    public static ItemStack cuilian(ItemStack cls, ItemStack item, Player p) {
        if (cls != null && item != null && p != null && Intensify.dataer.itemList.contains(item.getType())) {
            int level = getLelByNBT(item);
            Stone stone = getStoneByNBT(cls);
            int dx = getRandom(stone.dropLevel.get(0), stone.dropLevel.get(1)), sx = stone.riseLevel;
            boolean flag = Intensify.um.getIo().rand(levelCalc.calcStoneProvavility(stone, level), 100);
            if (flag) {
                level += sx;
                String afteradd = levelCalc.makeLelStr(level).get(0);
                item = setItemCuiLian(item, level);
                tip.getDnS(p, "CAN_CUILIAN_PROMPT", new String[]{afteradd});
                if (level >= 5) {
                    tip.broadcastMessage(tip.getMessage("ALL_SERVER_PROMPT"), new String[]{p.getDisplayName(), cls.getItemMeta().getDisplayName(), afteradd});
                }
            } else {
                level -= dx;
                if (level >= 0) {
                    item = setItemCuiLian(item, level);
                    String afteradd = levelCalc.makeLelStr(level).get(0);
                    tip.getDnS(p, "CUILIAN_OVER", new String[]{afteradd, String.valueOf(dx)});
                } else {
                    item = setItemCuiLian(item, -1);
                    tip.getDnS(p, "CUILIAN_OVER_ZERO", new String[]{String.valueOf(dx)});
                }

            }
        }
        return item;
    }

    public static ItemStack setItemCuiLian(ItemStack item, int level) {
        item = new ItemStack(addlorenbt(item));
        String nbt = mulNBT.getNBTdataStr(item, lorestart);
        int start = Integer.valueOf(nbt);
        List<String> lore = getLore(item);
        if (Intensify.dataer.itemList.contains(item.getType())
                && level != -1) {
//            List<String> lore = NewAPI.cleanCuiLian(item);

            ArrayList<String> willadd = new ArrayList<>();
            willadd.addAll(tip.getMessageList("UNDER_LINE"));
            willadd.addAll(levelCalc.makeLelStr(level));
            willadd.addAll(getLore(getListStringByType(item.getType()), level, item.getType()));


            if (lore == null) lore = willadd;
            if (lore.size() < start + willadd.size()) {
                lore.addAll(willadd);
            } else {
                for (int i = 0; i < willadd.size(); i++) {
                    lore.set(start + i, willadd.get(i));
                }
            }

            ItemMeta meta = item.getItemMeta();
            meta.setLore(lore);
            item.setItemMeta(meta);
            item = mulNBT.addNBTdata(item, LevelCalc.cln, String.valueOf(level));

        } else if (level == -1) {
            lore = NewAPI.cleanCuiLian(item);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(lore);
            item.setItemMeta(meta);
            item = mulNBT.addNBTdata(item, LevelCalc.cln, String.valueOf(0));
        }
        return item;
    }

    public static ItemStack addlorenbt(ItemStack itemStack) {
        int will = 0;
        String nbt = mulNBT.getNBTdataStr(itemStack, lorestart);
        if (nbt == null) {
            if (getLore(itemStack) != null) {
                will = getLore(itemStack).size();
            }
            return um.getMulNBT().addNBTdata(itemStack, lorestart, String.valueOf(will));
        }
        return itemStack;
    }

    public static List<String> getLore(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        return lore;
    }

    public static List<String> cleanCuiLian(ItemStack item) {
        List<String> lore = new ArrayList<>();
        if (item.getItemMeta().hasLore()) {
            lore = item.getItemMeta().getLore();
        }
        if (lore != null) {
            int lel = getLelByNBT(item);
            if (lore.containsAll(tip.getMessageList("UNDER_LINE"))) {
                lore.removeAll(tip.getMessageList("UNDER_LINE"));
            }
            List<String> ll = levelCalc.makeLelStr(lel);
            if (lore.containsAll(ll)) {
                lore.removeAll(ll);
            }
            for (int j = 0; j < lore.size() && j >= 0; j++) {
                if (j >= 0) {
                    if (lore.get(j).contains(tip.getMessage("FIRST"))) {
                        lore.remove(j);
                        j--;
                    }
                }
            }
        }
        return lore;
    }

    public static int getLelByNBT(ItemStack i) {
        String n = mulNBT.getNBTdataStr(i, LevelCalc.cln);
        if (n == null) return 0;
        return Integer.parseInt(mulNBT.getNBTdataStr(i, LevelCalc.cln));
    }

    public static Stone getStoneByNBT(ItemStack i) {
        if (i != null) {
            String id = getNBTID(i);
            if (id != null) {
                return Intensify.dataer.getCuilianStone(id);
            }
        }
        return Intensify.dataer.NULLStone;
    }

    public static List<String> getListStringByType(Material itemid) {
        if (Intensify.dataer.arms.contains(itemid)) {
            return Intensify.dataer.powerArms;
        }
        if (Intensify.dataer.boots.contains(itemid)) {
            return Intensify.dataer.powerBoots;
        }
        if (Intensify.dataer.chestplate.contains(itemid)) {
            return Intensify.dataer.powerChestplate;
        }
        if (Intensify.dataer.leggings.contains(itemid)) {
            return Intensify.dataer.powerLeggings;
        }
        if (Intensify.dataer.helmet.contains(itemid)) {
            return Intensify.dataer.powerHelmet;
        }
        return null;
    }

    public static List<String> getCuiLianTypeForLocal(Material itemid) {
        if (Intensify.dataer.arms.contains(itemid)) {
            return Intensify.dataer.localArms;
        }
        if (Intensify.dataer.boots.contains(itemid)) {
            return Intensify.dataer.localBoots;
        }
        if (Intensify.dataer.chestplate.contains(itemid)) {
            return Intensify.dataer.localChestplate;
        }
        if (Intensify.dataer.leggings.contains(itemid)) {
            return Intensify.dataer.localLeggings;
        }
        if (Intensify.dataer.helmet.contains(itemid)) {
            return Intensify.dataer.localHelmet;
        }
        return null;
    }

    public static List<String> getLore(List<String> powerlist, int level, Material itemid) {
        List<String> lore = new ArrayList<>();
        for (String str : powerlist) {
            LocType fitype = LocType.getType(itemid);
            for (Sectype sectype : Sectype.values()) {
                if (str.equals(sectype)) {
                    for (String s : levelCalc.getLevelString(sectype)) {
                        double doubled = levelCalc.getLelDouble(fitype, sectype, level);
                        if (doubled != 0) {
                            lore.add(tip.getMessage("FIRST") + s.replace("{0}", String.valueOf(doubled)));
                        }
                    }
                }
            }

        }
        return lore;
    }

    public static Double getAddLevel(List<ItemStack> itemlist, Sectype sec) {
        Double value = 0D;
        for (ItemStack item : itemlist) {
            if (item != null) {
                if (item.hasItemMeta()) {
                    if (item.getItemMeta().hasLore()) {
                        if (Intensify.dataer.itemList.contains(item.getType())) {
                            if (getLelByNBT(item) != -1) {
                                if (getListStringByType(item.getType()).contains(sec.toString())) {
                                    value += levelCalc.getLelDouble(LocType.getType(item.getType()), sec, getLelByNBT(item));
                                }
                            }
                        }
                    }
                }
            }
        }
        return value;
    }

    public static List<ItemStack> getAddAll(LivingEntity le){
        return addAll(NewAPI.getItemInHand(le), NewAPI.getItemInOffHand(le), le.getEquipment().getHelmet(), le.getEquipment().getChestplate(), le.getEquipment().getLeggings(), le.getEquipment().getBoots());
    }

    private static List<ItemStack> addAll(ItemStack... i1) {
        List<ItemStack> item = new ArrayList<>();
        for (ItemStack imk : i1) {
            if (imk == null) continue;
            if (imk.getType() == Material.AIR) continue;
            if (!imk.hasItemMeta()) continue;
            if (!imk.getItemMeta().hasLore()) continue;
            if (Intensify.dataer.itemList.contains(imk.getType())) {
                if (NewAPI.getLelByNBT(imk) != -1) {
                    List<String> fol = getCuiLianTypeForLocal(imk.getType());
                    if (fol.contains("hand") || fol.contains("bag")) {
                        item.add(imk);
                    }
                }
            }

        }
        return item;
    }


    public static boolean isCuilianStone(ItemStack item) {
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
        for (Stone stone : Intensify.dataer.customCuilianStoneList) {
            if (stone.id.equals(id))
                return true;
        }
        return false;
    }

    public static String getNBTID(ItemStack item) {
        return Intensify.um.getMulNBT().getNBTdataStr(item, Stone.NBTID);
    }

    public static ItemStack getItemInHand(LivingEntity p) {
        if (KsAPI.serverVersion == 3 && p.getEquipment() != null) {
            return p.getEquipment().getItemInMainHand();
        }
        if (p.getType() == EntityType.PLAYER && p.getEquipment() != null) {
            return ((Player) p).getItemInHand();
        } else {
            return p.getEquipment().getItemInHand();
        }
    }

    public static ItemStack getItemInOffHand(LivingEntity p) {
        if (KsAPI.serverVersion == 3) {
            return p.getEquipment().getItemInOffHand();
        }
        return new ItemStack(Material.AIR);
    }

    public static double getMaxHealth(LivingEntity p) {
        if (KsAPI.serverVersion == 3) {
            return p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        }
        return p.getMaxHealth();
    }

    public static void setMaxHealth(LivingEntity p, double m) {
        if (KsAPI.serverVersion == 3) {
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(m);
        } else {
            p.setMaxHealth(m);
        }
    }

    public static List<ItemStack> removeDurability(List<ItemStack> items) {
        for (ItemStack item : items) {
            if (!item.getItemMeta().isUnbreakable()) {
                short nj = item.getDurability();
                if (nj - 1 > 0) {
                    item.setDurability((short) (nj - 1));
                } else {
                    item.setType(Material.AIR);
                }
            }
        }
        return items;
    }


}
