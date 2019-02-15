package ksqeib.Intensify.Droper;


import ksqeib.Intensify.Main;
import ksqeib.Intensify.util.Dataer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobDrop
  implements Listener
{

  public MobDrop() {}

  
  @SuppressWarnings("deprecation")
@EventHandler
  public void onEntityDeath(EntityDeathEvent e)
  {
	  Player p=null;
    if ((e.getEntity() instanceof Player)) {
    	//是否为玩家
      return;
    }
    if (!Main.um.getIo().config.getBoolean("drop.mob")) {
    	//是否允许掉了
      return;
    }
    if(!(e.getEntity().getKiller() instanceof Player)) {
    	return;
    }else {
    	p=e.getEntity().getKiller();
    }
    EntityType et = e.getEntityType();
    //获取生物
    int id = -1;
    for (int i = 0; i < Dataer.getMobId().size(); i++)
    {
    	//遍历查询
      int a = Integer.parseInt(((String)Dataer.getMobId().get(i)).split(" ")[0]);
      if (a == et.getTypeId())
      {
    	  //生物ID正确
        id = i;
        break;
      }
    }
    if (id != -1)
    {
    	//生物ID正确
      ItemStack item = null;
      Double key = Dataer.rm.nextDouble()*100;
      String type = ((String)Dataer.getMobId().get(id)).split(" ")[1].toLowerCase();
      //标签
      Double chance = Double.parseDouble(Dataer.getMobId().get(id).split(" ")[2]);
      //概率
      item = Dataer.getItem(type);
      if (key < chance&&p!=null) {
    	  Main.um.getTip().getDnS(p,"qhdrop",new String[]{item.getItemMeta().getDisplayName()});
    	  //比概率小
        e.getDrops().add(item);
      }
    }
  }
}

