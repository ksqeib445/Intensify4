package ksqeib.Intensify.enchant;

import ksqeib.Intensify.Main;
import ksqeib.Intensify.util.Dataer;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static ksqeib.Intensify.Main.um;

public class Qh {
	@SuppressWarnings("deprecation")
	public static ItemStack qh(Player p, ItemStack itemStack, boolean safe, boolean admin, int id, int chance) {

		ItemStack item = itemStack;
		if (item == null) {
			return new ItemStack(0);
		}
		// 创建
		Enchantment enc = Main.enchan.getEnchan(um.getIo().config.getInt("id.items." + id));
		// 等级
		int level = item.getEnchantmentLevel(enc);
		
		// 进阶后加lore
		if (level == Dataer.getLevel("maxlevel")) {
			// 最高级
			//降级=-=
		} else {
			if (admin) {
				level=Dataer.getLevel("maxlevel");
				// 管理员强化
				um.getTip().getDnS(p, "qhsu", null);
			}else
			if (Rdom(level, chance)) {
				// 按几率计算强化
				level+=1;
				// 成功
				um.getTip().getDnS(p, "qhsu", null);
                if (level < 1&&level!=0) {
					item.removeEnchantment(enc);
				} else {
					item.addUnsafeEnchantment(enc, level);
				}
				if (level > Dataer.getLevel("boardlevel")) {
					Bukkit.getServer().broadcastMessage(um.getIo().getMessage("toall").replace("{0}", p.getName())
							.replace("{1}", String.valueOf(level)));
				}
			} else {
				// 失败
				um.getTip().getDnS(p, "qhfail", null);
				level -= 1;
				if ((level >= Dataer.getLevel("boomlevel")) && (!safe)) {
					// 等级高并且不安全就丢失
					um.getTip().getDnS(p, "hqhfail", new String[]{String.valueOf(Dataer.getLevel("boomlevel"))});
					return new ItemStack(0);
				}
			}
		}
		if (item != null && item != new ItemStack(0)) {
			item.addUnsafeEnchantment(enc, level);
			ItemMeta im = item.getItemMeta();
			im.setLore(setLore(item, level));
//            im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_ATTRIBUTES);
			item.setItemMeta(im);
			return item;
		}
		return item;
	}

	public static ItemStack qh(int hash, ItemStack itemStack, boolean safe, boolean admin, int id, int chance)
	  {
	    Player p =Bukkit.getPlayer(Dataer.player.get(Integer.valueOf(hash)));
	    
	    return qh(p,itemStack,safe,admin,id,chance);
	  }
	public static List<String> getLore(ItemStack item) {
		List<String> lore = item.getItemMeta().getLore();
		return lore;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> setLore(ItemStack item, int level) {
		List<String> lore = getLore(item);
		if (lore == null) {
			// 如果是第一次强化
			lore = new ArrayList();
			lore.add(um.getIo().getMessage("qhxx"));
			lore.add(um.getIo().getMessage("qhlel").replace("{0}", String.valueOf(level)));
			lore.add(style(level));
			// 加lore
			return lore;
		}
		if (level != Dataer.getLevel("maxlevel")) {
			//不是最高级
			lore.set(0, um.getIo().getMessage("qhxx"));
			lore.set(1, um.getIo().getMessage("qhlel").replace("{0}", String.valueOf(level)));
			styellore(lore,level);
		} else {
			//是最高级
			lore.set(0, um.getIo().getMessage("qhxx"));
			lore.set(1, um.getIo().getMessage("maxqh"));
			styellore(lore,level);
		}
		return lore;
	}
	public static List<String> styellore(List<String> lore,int level){
		//未更新
		if(lore.size()<3){
			lore.add(style(level));
		}else {
			lore.set(2, style(level));
		}
		return lore;
	}
	public static String style(int level) {
		String str = um.getIo().config.getString("style.color").replace("&", "§");
		if (level !=(Dataer.getLevel("maxlevel")+1)) {
			//如果不是满级
			for (int i = 0; i < level; i++) {
				//弄格子
				str = str + Dataer.cg;
			}
			for (int i = 0; i < Dataer.getLevel("maxlevel") - level; i++) {
				//满格子
				str = str + Dataer.sb;
			}
			
			return str;
		}
		for (int i = 0; i < level; i++) {
			
			str = str + Dataer.cg;
		}
		return str;
	}
	  public static boolean Rdom(int level, int chance)
	  {
	    int get =Dataer.random.get(level);
	    get += chance;
	    if (Dataer.rm.nextInt(100) < get) {
	      return true;
	    }
	    return false;
	  }
}
