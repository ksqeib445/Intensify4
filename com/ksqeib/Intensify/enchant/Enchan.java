package ksqeib.Intensify.enchant;

import ksqeib.Intensify.Main;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class Enchan {
	Main plugin;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<Integer, Enchantment> fm = new HashMap();
	
	@SuppressWarnings("deprecation")
	public Enchan(Main plugin)
	  {
		this.plugin=plugin;
	    fm.put(0, Enchantment.getById(0));
	    fm.put(1, Enchantment.getById(1));
	    fm.put(2, Enchantment.getById(2));
	    fm.put(3, Enchantment.getById(3));
	    fm.put(4, Enchantment.getById(4));
	    fm.put(5, Enchantment.getById(5));
	    fm.put(6, Enchantment.getById(6));
	    fm.put(7, Enchantment.getById(7));
	    fm.put(8, Enchantment.getById(8));
	    fm.put(9, Enchantment.getById(9));
	    fm.put(10, Enchantment.getById(10));
	    fm.put(16, Enchantment.getById(16));
	    fm.put(17, Enchantment.getById(17));
	    fm.put(18, Enchantment.getById(18));
	    fm.put(19, Enchantment.getById(19));
	    fm.put(20, Enchantment.getById(20));
	    fm.put(21, Enchantment.getById(21));
	    fm.put(22, Enchantment.getById(22));
	    fm.put(32, Enchantment.getById(32));
	    fm.put(33, Enchantment.getById(33));
	    fm.put(34, Enchantment.getById(34));
	    fm.put(35, Enchantment.getById(35));
	    fm.put(48, Enchantment.getById(48));
	    fm.put(49, Enchantment.getById(49));
	    fm.put(50, Enchantment.getById(50));
	    fm.put(51, Enchantment.getById(51));
	    fm.put(70, Enchantment.getById(70));
	  }
	public Enchantment getEnchan(int id) {
		return (Enchantment)this.fm.get(Integer.valueOf(id));
	}
}
