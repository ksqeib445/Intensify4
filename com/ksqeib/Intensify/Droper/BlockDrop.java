package ksqeib.Intensify.Droper;

import ksqeib.Intensify.Main;
import ksqeib.Intensify.util.Dataer;
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
		if (!Main.um.getIo().config.getBoolean("drop.block")) {
			return;
		}
		Block block = e.getBlock();
		Player p = e.getPlayer();
		//偷瞄方块和玩家
		int id = -1;
		for (int i = 0; i < Dataer.getBlockId().size(); i++) {
			int a = Integer.parseInt(((String) Dataer.getBlockId().get(i)).split(" ")[0]);
			if (a == block.getTypeId()) {
				//判断是否为掉了方块
				id = i;
				break;
			}
		}
		if (id != -1) {
			//如果不是没有
			ItemStack item = null;
			//几率(生成的)
		      Double key = Dataer.rm.nextDouble()*100;
			
			String type = ((String) Dataer.getBlockId().get(id)).split(" ")[1].toLowerCase();
			//几率(文件里的)
		      Double chance = Double.parseDouble(Dataer.getBlockId().get(id).split(" ")[2]);
			
			item = Dataer.getItem(type);
			if ((key < chance)&&p!=null) {
				//比概率小
				Main.um.getTip().getDnS(p,"qhdrop",new String[]{item.getItemMeta().getDisplayName()});
				//掉在地上
				block.getWorld().dropItem(block.getLocation(), item);
			}
		}
	}

}
