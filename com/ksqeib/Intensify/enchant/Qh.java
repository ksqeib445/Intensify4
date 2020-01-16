package ksqeib.Intensify.enchant;

import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Istone;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static ksqeib.Intensify.main.Intensify.um;

public class Qh {
    @SuppressWarnings("deprecation")
    public static String lorestart = "intensifylorestart";

    public static ItemStack qh(int hash, ItemStack in, Istone stone) {
        Player p = Bukkit.getPlayer(Intensify.dataer.player.get(hash));
        int id = in.getTypeId();
        ItemStack item = new ItemStack(in);
        if (item == null) {
            return new ItemStack(0);
        }
        item = addlorenbt(item);
        // 创建
        Enchantment enc = Enchantment.getById(um.getIo().getaConfig("config").getInt("id.items." + id));
        // 等级
        int level = item.getEnchantmentLevel(enc);
        if (level == Intensify.dataer.maxlevel) {
            // 最高级
            //降级=-=
        } else {
            if (um.getIo().rand(stone.getAdd() + Intensify.dataer.getChance(level), 100)) {
                // 按几率计算强化
                level += 1;
                // 成功
                um.getTip().getDnS(p, "qhsu", null);
                if (level < 1 && level != 0) {
                    item.removeEnchantment(enc);
                } else {
                    item.addUnsafeEnchantment(enc, level);
                }
                if (level > Intensify.dataer.getLevel("boardlevel")) {
                    Bukkit.getServer().broadcastMessage(um.getTip().getMessage("toall").replace("{0}", p.getName())
                            .replace("{1}", String.valueOf(level)));
                }
            } else {
                // 失败
                um.getTip().getDnS(p, "qhfail", null);
                level -= 1;
                if ((level >= Intensify.dataer.getLevel("boomlevel")) && (!stone.isIssafe())) {
                    // 等级高并且不安全就丢失
                    um.getTip().getDnS(p, "hqhfail", new String[]{String.valueOf(Intensify.dataer.getLevel("boomlevel"))});
                    return new ItemStack(0);
                }
            }
        }
        if (item != null && item != new ItemStack(0)) {
            item.addUnsafeEnchantment(enc, level);
            ItemMeta im = item.getItemMeta();
            im.setLore(setLore(item, level, Integer.parseInt(um.getMulNBT().getNBTdataStr(item, lorestart))));
            item.setItemMeta(im);
            return item;
        }
        return item;
    }


    public static List<String> getLore(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        return lore;
    }

    public static ItemStack addlorenbt(ItemStack itemStack) {
        int will = 0;
        String nbt = um.getMulNBT().getNBTdataStr(itemStack, lorestart);
        if (nbt == null) {
            if (getLore(itemStack) != null) {
                will = getLore(itemStack).size();
            }
            return um.getMulNBT().addNBTdata(itemStack, lorestart, String.valueOf(will));
        }
        return itemStack;
    }


    public static List<String> setLore(ItemStack item, int level, int lorestart) {
        List<String> lore = getLore(item);
        if (lore == null) {
            // 如果是第一次强化
            lore = new ArrayList();
            lore.add(um.getTip().getMessage("qhxx"));
            lore.add(um.getTip().getMessage("qhlel").replace("{0}", String.valueOf(level)));
            lore.add(style(level));
            // 加lore
            return lore;
        }
        if (level != Intensify.dataer.maxlevel) {
            //不是最高级
            if (lore.size() < lorestart + 3) {
                lore.add(um.getTip().getMessage("qhxx"));
                lore.add(um.getTip().getMessage("qhlel").replace("{0}", String.valueOf(level)));
                lore.add(style(level));
            } else {
                lore.set(lorestart, um.getTip().getMessage("qhxx"));
                lore.set(lorestart + 1, um.getTip().getMessage("qhlel").replace("{0}", String.valueOf(level)));
                lore.set(lorestart + 2, style(level));
            }
        } else {
            //是最高级
            if (lore.size() < lorestart + 3) {
                lore.add(um.getTip().getMessage("qhxx"));
                lore.add(um.getTip().getMessage("maxqh"));
                lore.add(style(level));
            } else {
                lore.set(lorestart, um.getTip().getMessage("qhxx"));
                lore.set(lorestart + 1, um.getTip().getMessage("maxqh"));
                lore.set(lorestart + 2, style(level));
            }
        }
        return lore;
    }


    public static String style(int level) {
        String str = um.getIo().getaConfig("config").getString("style.color").replace("&", "§");
        int r1 = level % 5;
        int r5 = (level - r1) / 5;
        while (r5 > 0) {
            str += Intensify.dataer.sb;
            r5--;
        }
        while (r1 > 0) {
            str += Intensify.dataer.cg;
            r1--;
        }
        return str;
    }
}
