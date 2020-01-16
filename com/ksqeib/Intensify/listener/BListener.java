package ksqeib.Intensify.listener;

import com.ksqeib.ksapi.util.Tip;
import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.main.NewAPI;
import ksqeib.Intensify.util.Dataer;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BListener implements Listener {
    Tip tip = Intensify.um.getTip();

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        List<ItemStack> items = NewAPI.addAll(NewAPI.getItemInHand(p), NewAPI.getItemInOffHand(p), p.getEquipment().getHelmet(), p.getEquipment().getChestplate(), p.getEquipment().getLeggings(), p.getEquipment().getBoots());
        int minLevel = NewAPI.getMinLevel(items);
        if (minLevel != -1) {
            if (minLevel >= Dataer.instance.cuiliannotice) {
                tip.broadcastMessage("JOIN_SERVER_NOTICE", new String[]{p.getName()});
            }
        }
    }

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        if ((!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (!e.hasBlock()) || (!e.getClickedBlock().getType().equals(Material.FURNACE))) {
            return;
        }
        Player p = e.getPlayer();
        Furnace f = (Furnace) e.getClickedBlock().getState();
        if (Intensify.dataer.furnaceUsingMap.get(f.getLocation()) != null) {
            Intensify.dataer.furnaceUsingMap.remove(f.getLocation());
        }
        Intensify.dataer.furnaceUsingMap.put(f.getLocation(), p.getName());
    }

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void FurnaceBurnEvent(FurnaceBurnEvent e) {
        ItemStack fuel = e.getFuel();
        Furnace f = (Furnace) e.getBlock().getState();
        if (NewAPI.isCuilianStone(fuel)) {
            if (Intensify.dataer.itemList.contains(f.getInventory().getSmelting().getType())) {
                if (NewAPI.getStoneByNBT(fuel).riseLevel + NewAPI.getLelByNBT(f.getInventory().getSmelting()) > Intensify.dataer.cuilianmax) {
                    e.setCancelled(true);
                    return;
                }
                Intensify.dataer.furnaceFuelMap.put(f.getLocation(), fuel);
                e.setBurning(true);
                e.setBurnTime(200);
            } else {
                e.setCancelled(true);
            }
        }
    }

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void FurnaceSmeltEvent(FurnaceSmeltEvent e) {
        ItemStack smelt = e.getSource();
        Furnace f = (Furnace) e.getBlock().getState();
        ItemStack fuel = Intensify.dataer.furnaceFuelMap.get(f.getLocation());
        if (fuel == null) return;
        if (NewAPI.isCuilianStone(fuel) && Intensify.dataer.furnaceUsingMap.get(f.getLocation()) != null) {
            Player p = Intensify.instance.getServer().getPlayer(Intensify.dataer.furnaceUsingMap.get(f.getLocation()));
            smelt.setAmount(1);
            smelt = NewAPI.cuilian(fuel, smelt, p);
            e.setResult(smelt);
            Intensify.dataer.furnaceFuelMap.remove(f.getLocation());
        }
    }
}
