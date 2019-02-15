package ksqeib.Intensify.event;

import ksqeib.Intensify.enchant.Qh;
import ksqeib.Intensify.util.Dataer;
import ksqeib.Intensify.util.Isqh;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

public class Qhtime implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFurnaceSmelt(FurnaceSmeltEvent e) {

        //是否进行强化
        boolean flag = false;
        int hash = e.getBlock().hashCode();
        //获取玩家
        Player p = Bukkit.getPlayer(Dataer.player.get(Integer.valueOf(hash)));
        //获取方块ID
        ItemStack source = e.getSource();
        //获取资源()上方物品
        ItemStack fuel = Dataer.fuelItem.get(Integer.valueOf(hash));
        //获取燃料
        if(source==null)return;
        ItemStack qh = new ItemStack(source);
        flag = Isqh.isCanQhWeapon(qh);

        if ((flag) && (fuel != null) && (fuel.hasItemMeta())) {
            String name=Dataer.getStoneinName(fuel);
            qh = Qh.qh(hash, source, Dataer.getSafer(name), false, source.getTypeId(), Dataer.getChance(name));
//            if (fuel.getItemMeta().equals(Dataer.getItem("normal").getItemMeta())) {
//                qh = Qh.qh(hash, source, false, false, source.getTypeId(), Dataer.getChance("normal"));
//            } else if (fuel.getItemMeta().equals(Dataer.getItem("luck").getItemMeta())) {
//                qh = Qh.qh(hash, source, false, false, source.getTypeId(), Dataer.getChance("luck"));
//            } else if (fuel.getItemMeta().equals(Dataer.getItem("safe").getItemMeta())) {
//                qh = Qh.qh(hash, source, true, false, source.getTypeId(), Dataer.getChance("safe"));
//            } else if (fuel.getItemMeta().equals(Dataer.getItem("vip").getItemMeta())) {
//                qh = Qh.qh(hash, source, true, false, source.getTypeId(), Dataer.getChance("vip"));
//            } else if (fuel.getItemMeta().equals(Dataer.getItem("admin").getItemMeta())) {
//                qh = Qh.qh(hash, source, true, true, source.getTypeId(), 100);
//            }
            e.setCancelled(false);
            Dataer.fuelItem.remove(Integer.valueOf(hash));
            //强化出结果
            if (qh != null) {
                if (p != null&&p.isOnline()){
                    p.playSound(p.getLocation(), Isqh.getAnvilSound(), 1.0F, 1.0F);
                    e.setResult(qh);
                }else {
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),qh);
                    e.setResult(new ItemStack(Material.AIR));
                }
            }
        } else if (flag && (fuel == null)) {
            e.setResult(source);
        }


    }

}
