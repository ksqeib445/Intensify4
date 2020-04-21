package ksqeib.Intensify.droper;

import ksqeib.Intensify.main.Intensify;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockDrop implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) {
            //结束没
            return;
        }
        if (!Intensify.um.getIo().getaConfig("config").getBoolean("drop.block")) {
            return;
        }
        Block block = e.getBlock();
        Player p = e.getPlayer();
        //偷瞄方块和玩家
        int id = -1;
        for (int i = 0; i < Intensify.dataer.getBlockId().size(); i++) {
            int a = Integer.parseInt(Intensify.dataer.getBlockId().get(i).split(" ")[0]);
            if (a == block.getType().getId()) {
                //判断是否为掉了方块
                id = i;
                break;
            }
        }
        if (id != -1) {
            //如果不是没有
            ItemStack item = null;
            //几率(生成的)
            Double key = Intensify.dataer.rm.nextDouble() * 100;

            String type = Intensify.dataer.getBlockId().get(id).split(" ")[1].toLowerCase();
            //几率(文件里的)
            Double chance = Double.parseDouble(Intensify.dataer.getBlockId().get(id).split(" ")[2]);

            item = Intensify.dataer.getItem(type);
            if ((key < chance) && p != null) {
                //比概率小
                Intensify.um.getTip().getDnS(p, "qhdrop", item.getItemMeta().getDisplayName());
                //掉在地上
                block.getWorld().dropItem(block.getLocation(), item);
            }
        }
    }

}
