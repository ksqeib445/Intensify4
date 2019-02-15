package ksqeib.Intensify.util;

import ksqeib.Intensify.Main;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.Set;

public class Isqh {
	//学猫叫
	  public static Sound getAnvilSound()
	  {
	    Sound[] soundlist = Sound.values();
	    for (int i = 0; i < soundlist.length; i++)
	    {
	      if (soundlist[i].name().equalsIgnoreCase("BLOCK_ANVIL_USE")) {
	        return Sound.valueOf("BLOCK_ANVIL_USE");
	      }
	      if (soundlist[i].name().equalsIgnoreCase("ANVIL_USE")) {
	        return Sound.valueOf("ANVIL_USE");
	      }
	    }
	    return null;
	  }
		//判断强化石方法
	  public static boolean isqhStone(ItemStack item)
	  {
	    if (item == null) {
	      return false;
	    }
	    Set<String> lis=Dataer.qhitem.keySet();
	    for(String str:lis){
		    if (item.isSimilar(Dataer.getItem(str))) {
			      return true;
			    }
	    }
	    return false;
	  }
	  //是不是强化武器
	  @SuppressWarnings({ "rawtypes", "deprecation" })
	public static boolean isCanQhWeapon(ItemStack item)
	  {
	    if (item == null) {
	    	//不是假东西
	      return false;
	    }
	    Iterator it = Main.um.getIo().config.getConfigurationSection("id.items").getKeys(false).iterator();
	    //遍历
	    while (it.hasNext())
	    {
	      String i = (String)it.next();
	      int a = Integer.parseInt(i);
//	      Bukkit.getLogger().warning(item.getTypeId()+" "+a+" "+item.getAmount());

	      if ((a==item.getTypeId()) && (item.getAmount() == 1)) {
	    	  //如果ID相同，并且只有一个
	        return true;
	      }
	    }
	    return false;
	  }

}
