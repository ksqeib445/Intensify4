package ksqeib.Intensify.enchant;

import ksqeib.Intensify.main.Intensify;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class Enchan {
	Intensify plugin;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<Integer, Enchantment> fm = new HashMap();
	
	@SuppressWarnings("deprecation")
	public Enchan(Intensify plugin)
	  {
		this.plugin=plugin;
		for(Enchantment ec:Enchantment.values()){
			fm.put(ec.getId(),ec);
		}
	  }
	public Enchantment getEnchan(int id) {
		return this.fm.get(id);
	}
}
