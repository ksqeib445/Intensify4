package ksqeib.Intensify.listener;

import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.util.Isqh;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static ksqeib.Intensify.main.Intensify.um;

@SuppressWarnings({"deprecation"})
public class Events implements Listener {
    @EventHandler
    public void NoCraft(InventoryClickEvent event) {
        //防止用来做不法勾当
        if (!(event.getWhoClicked() instanceof Player)) {
            //如果不是玩家
            return;
        }
        if (event.getCurrentItem() == null) {
            //假东西
            return;
        }
        if (event.getInventory().getType() != InventoryType.WORKBENCH&&event.getInventory().getType()!=InventoryType.ANVIL&&event.getInventory().getType()!=InventoryType.BEACON
        &&event.getInventory().getType() != InventoryType.MERCHANT) {
            //如果不是熔炉或者工作台
            return;
        }
        if (Isqh.isqhStone(event.getCurrentItem())) {
            //如果是强化石
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void setItemInFurn(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
//
        if ((event.getInventory().getType() == InventoryType.FURNACE) && ((Isqh.isqhStone(event.getCursor())) || (Isqh.isCanQhWeapon(event.getCursor()))) && (
                (event.getSlotType() == InventoryType.SlotType.FUEL)|| (event.getSlotType() == InventoryType.SlotType.CRAFTING))) {
            ItemStack item = event.getCursor();
            ItemStack targetitem = event.getCurrentItem();
            event.setCursor(targetitem);
            event.setCurrentItem(item);
            event.setCancelled(true);
        }
    }

    public boolean furnCheck(Furnace furn) {
        //来开熔炉的玩家
        UUID pu = Intensify.dataer.player.get(furn.getBlock().hashCode());
        Player p= Bukkit.getPlayer(pu);
        ItemStack smelt = furn.getInventory().getSmelting();
        ItemStack fuel = furn.getInventory().getFuel();
        if (smelt.getEnchantmentLevel(Intensify.enchan.getEnchan(um.getIo().getaConfig("config").getInt("id.items." + smelt.getTypeId()))) >= Intensify.dataer.maxlevel) {
            //满级
            um.getTip().getDnS(p, "mmaxqh", null);
            return false;
        }
        if (Isqh.isqhStone(fuel)) {
            //是否为强化石
            //是否可强化
            if (Isqh.isCanQhWeapon(smelt)) {
                int hash = furn.getBlock().hashCode();
                Intensify.dataer.fuelItem.put(hash, Isqh.getNBTID(fuel));
                return true;
            }
            return false;
        }
        return false;
    }

    @EventHandler
    public synchronized void onPlayerInteract(PlayerInteractEvent e) {
        //右键
        if ((!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (!e.hasBlock()) || (!e.getClickedBlock().getType().equals(Material.FURNACE))) {
            return;
        }
        Player p = e.getPlayer();
        int hash = e.getClickedBlock().hashCode();
        if (Intensify.dataer.player.get(hash) != null) {
            Intensify.dataer.player.remove(hash);
            Intensify.dataer.player.put(hash, p.getUniqueId());
            return;
        }
        Intensify.dataer.player.put(hash, p.getUniqueId());
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        if (furnCheck((Furnace) e.getBlock().getState())) {
            //如果是强化熔炉
            e.setBurning(true);
            e.setBurnTime(240);
        }
    }
}
