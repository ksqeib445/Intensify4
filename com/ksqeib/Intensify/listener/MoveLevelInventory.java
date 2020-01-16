package ksqeib.Intensify.listener;

import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.main.NewAPI;
import ksqeib.Intensify.util.Dataer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MoveLevelInventory implements Listener {

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, Intensify.um.getTip().getMessage("MOVE_LEVEL_INVENTORY_NAME"));
        p.openInventory(inv);
        check(inv);
    }

    public static void check(Inventory inv) {
        ItemStack bariier = new ItemStack(Material.DIAMOND);
        inv.setItem(0, bariier);
        inv.setItem(1, bariier);
        inv.setItem(2, bariier);
        inv.setItem(6, bariier);
        inv.setItem(7, bariier);
        inv.setItem(8, bariier);
        ItemStack item = new ItemStack(Material.ANVIL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§a把左边装备所有等级移给右边装备");
        List<String> lore = new ArrayList();
        lore.add("§7该装备移等级需要：§a" + Dataer.instance.moveLevelUse);
        lore.add("§7把左边装备所有等级移给右边装备。");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(4, item);
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        if (inv == null) {
            return;
        }
        if (e.getRawSlot() < 0) {
            return;
        }
        if (inv.getName().equals(Intensify.um.getTip().getMessage("MOVE_LEVEL_INVENTORY_NAME"))) {
            check(inv);
            if (e.getRawSlot() <= 8) {
                int slot = e.getRawSlot();
                if (slot != 3 && slot != 5) {
                    e.setCancelled(true);
                }
                if (slot == 4) {
                    if (inv.getItem(3) == null || inv.getItem(5) == null || NewAPI.getLelByNBT(inv.getItem(3)) <= 0) {
                        return;
                    }
                    Integer moveLevel = NewAPI.getLelByNBT(inv.getItem(3)) - Dataer.instance.moveLevelUse;
                    int newLevel;
                    if (NewAPI.getLelByNBT(inv.getItem(5)) + moveLevel > Intensify.dataer.cuilianmax) {
                        newLevel = (Intensify.dataer.cuilianmax);
                    } else {
                        newLevel = (NewAPI.getLelByNBT(inv.getItem(5)) + moveLevel);
                    }
                    inv.setItem(3, NewAPI.setItemCuiLian(inv.getItem(3), -1));
                    inv.setItem(5, NewAPI.setItemCuiLian(inv.getItem(5), newLevel));
                }
            }
        }
    }

    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getPlayer();
        if (inv.getName() != null) {
            if (inv.getName().equals(Intensify.um.getTip().getMessage("MOVE_LEVEL_INVENTORY_NAME"))) {
                if (inv.getItem(3) != null || inv.getItem(5) != null) {
                    if (inv.getItem(3) != null) {
                        p.getInventory().addItem(inv.getItem(3));
                    }
                    if (inv.getItem(5) != null) {
                        p.getInventory().addItem(inv.getItem(5));
                    }
                    p.sendMessage("§b物品已经回到你的背包");
                }
            }
        }
    }
}
