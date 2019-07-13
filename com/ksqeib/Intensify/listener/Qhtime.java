package ksqeib.Intensify.listener;

import ksqeib.Intensify.enchant.Qh;
import ksqeib.Intensify.main.Intensify;
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
        boolean flag;
        int hash = e.getBlock().hashCode();
        //获取玩家
        Player p = Bukkit.getPlayer(Intensify.dataer.player.get(hash));
        //获取方块ID
        ItemStack source = e.getSource();
        //获取资源()上方物品
        String id = Intensify.dataer.fuelItem.get(hash);
        //获取燃料
        if(source==null)return;
        ItemStack qh = new ItemStack(source);
        flag = Isqh.isCanQhWeapon(source);
        if (flag){
            try {
                qh = Qh.qh(hash, source,Intensify.dataer.getStonebyId(id));
            }catch (Exception ex){
                ex.printStackTrace();
                qh=source;
                Bukkit.getLogger().warning("在强化过程中发生严重问题");
            }
            Intensify.dataer.fuelItem.remove(hash);
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
        } else if (flag && (id == null)) {
            e.setResult(qh);
        }
        e.setCancelled(false);


    }

}
