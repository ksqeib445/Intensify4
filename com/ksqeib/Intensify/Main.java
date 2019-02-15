package ksqeib.Intensify;

import com.ksqeib.ksapi.command.Cmdregister;
import com.ksqeib.ksapi.util.UtilManager;
import ksqeib.Intensify.Droper.BlockDrop;
import ksqeib.Intensify.Droper.MobDrop;
import ksqeib.Intensify.command.Cmd;
import ksqeib.Intensify.enchant.Enchan;
import ksqeib.Intensify.event.Events;
import ksqeib.Intensify.event.Qhtime;
import ksqeib.Intensify.util.Dataer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public class Main extends JavaPlugin {
    // 构造需要
    public static UtilManager um;
    //其他构造
    public static Enchan enchan;
    public static Main instance;
    //启动
    @Override
    public void onEnable() {
        instance=this;
        init();
        um.getTip().getDnS(Bukkit.getConsoleSender(), "enable", null);
    }
    //关闭

    public void init() {
	    um=new UtilManager(this);
	    um.createalwaysneed(false,true);
        //注册
        um.createHelper("qh",um.getIo().loadYamlFile("helppage.yml",true));
        Cmdregister.registercmd(this,new Cmd("qh"));
        //其他注册
        enchan = new Enchan(this);
        //事件
        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginManager().registerEvents(new Qhtime(), this);
        getServer().getPluginManager().registerEvents(new BlockDrop(), this);
        getServer().getPluginManager().registerEvents(new MobDrop(), this);
        //完全启动
        soul();
        Dataer.init(um.getIo().config);
    }

    @Override
    public void onDisable() {
        um.getTip().getDnS(Bukkit.getConsoleSender(), "disable", null);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    public void reload() {
        um.getIo().init();
        Dataer.BlockId.clear();
        Dataer.MobId.clear();
        Dataer.random.clear();
        Dataer.init(um.getIo().config);
        Iterator localIterator = um.getIo().config.getConfigurationSection("id.items").getKeys(false).iterator();
        soul();
    }
    public void soul() {
        @SuppressWarnings("rawtypes")
        Iterator localIterator = um.getIo().config.getConfigurationSection("id.items").getKeys(false).iterator();
        while (localIterator.hasNext()) {
            String i = (String) localIterator.next();
            //获取ID
                FurnaceRecipe recipe = new FurnaceRecipe(new ItemStack(Material.getMaterial(Integer.parseInt(i))), Material.EMERALD);
                //创建(熔炉)配方
                recipe.setInput(Material.getMaterial(Integer.parseInt(i)));
                //设置放什么
                getServer().addRecipe(recipe);
                //加入魔法配方
        }
    }
}
