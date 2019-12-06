package ksqeib.Intensify.main;

import com.ksqeib.ksapi.command.Cmdregister;
import com.ksqeib.ksapi.util.UtilManager;
import ksqeib.Intensify.Droper.BlockDrop;
import ksqeib.Intensify.Droper.MobDrop;
import ksqeib.Intensify.command.Cmd;
import ksqeib.Intensify.enchant.Enchan;
import ksqeib.Intensify.listener.Events;
import ksqeib.Intensify.listener.Qhtime;
import ksqeib.Intensify.util.Dataer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

import static ksqeib.Intensify.util.Dataer.storepath;

public class Intensify extends JavaPlugin {
    // 构造需要
    public static UtilManager um;
    //其他构造
    public static Enchan enchan;
    public static Intensify instance;
    public static Dataer dataer;
    public static int ServerVersion = 0;

    //启动
    @Override
    public void onEnable() {
        instance = this;
        init();
        um.getTip().getDnS(Bukkit.getConsoleSender(), "enable", null);
    }
    //关闭

    public void init() {
        enchan = new Enchan(this);
        um = new UtilManager(this);
        dataer = new Dataer();
        um.createio(false);
        um.createitemsr();
        um.createtip(true,"message.yml");
        um.createmulNBT();
        //注册
        um.createHelper("qh", um.getIo().loadYamlFile("helppage.yml", true));
        Cmdregister.registercmd(this, new Cmd("qh"));
        //其他注册
        //事件
        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginManager().registerEvents(new Qhtime(), this);
        getServer().getPluginManager().registerEvents(new BlockDrop(), this);
        getServer().getPluginManager().registerEvents(new MobDrop(), this);
        //完全启动
        um.getIo().loadaConfig(storepath + "/cuilianlevelup", true);
        um.getIo().loadaConfig(storepath + "/iitems", true);
        um.getIo().loadaConfig("config",true);
        dataer.init(um.getIo().getaConfig("config"));
        soul();
    }

    @Override
    public void onDisable() {
        um.getTip().getDnS(Bukkit.getConsoleSender(), "disable", null);
    }

    public void reload() {
        um.getIo().reload();
        dataer.clearAll();
        dataer.init(um.getIo().getaConfig("config"));
        soul();
    }

    public void soul() {
        Iterator localIterator = um.getIo().getaConfig("config").getConfigurationSection("id.items").getKeys(false).iterator();
        while (localIterator.hasNext()) {
            String i = (String) localIterator.next();
            //获取ID
            FurnaceRecipe recipe = new FurnaceRecipe(new ItemStack(Material.getMaterial(Integer.parseInt(i))), Material.EMERALD);
            //创建(熔炉)配方
            Material now = Material.getMaterial(Integer.parseInt(i));
            for (int n = 0; n < now.getMaxDurability(); n++) {
                //设置放什么
                recipe.setInput(now, n);
                //加入魔法配方
                getServer().addRecipe(recipe);
            }
        }
    }
}
