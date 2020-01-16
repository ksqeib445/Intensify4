package ksqeib.Intensify.main;

import com.ksqeib.ksapi.KsAPI;
import com.ksqeib.ksapi.command.Cmdregister;
import com.ksqeib.ksapi.util.UtilManager;
import ksqeib.Intensify.Droper.BlockDrop;
import ksqeib.Intensify.Droper.MobDrop;
import ksqeib.Intensify.command.Cmd;
import ksqeib.Intensify.command.CuiCmd;
import ksqeib.Intensify.enchant.Enchan;
import ksqeib.Intensify.listener.*;
import ksqeib.Intensify.util.Dataer;
import ksqeib.Intensify.util.LevelCalc;
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
    public static LevelCalc levelCalc;
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
        levelCalc=new LevelCalc();
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
        levelCalc.init(um.getIo().getaConfig(storepath + "/cuilianlevelup"));
//        cuilian
        try {
            ServerVersion = KsAPI.getServerVersionType();
        } catch (NoSuchMethodException ex) {
            System.out.print("NewCustomCuiLian: 服务器版本获取失败，插件无法启动");
            this.setEnabled(false);
        }
        um.createHelper("cuilian", um.getIo().loadYamlFile("chelppage.yml", true));
        getServer().getPluginManager().registerEvents(new BListener(), this);
        getServer().getPluginManager().registerEvents(new MoveLevelInventory(), this);
        if (dataer.usingDefaultPower) {

            getServer().getPluginManager().registerEvents(new MainListener(), this);
        }
        Cmdregister.registercmd(this, new CuiCmd("cuilian"));
    }

    @Override
    public void onDisable() {
        um.getTip().getDnS(Bukkit.getConsoleSender(), "disable", null);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    public void reload() {
        um.getIo().reload();
        dataer.clearAll();
        dataer.init(um.getIo().getaConfig("config"));
        levelCalc.init(um.getIo().getaConfig(storepath + "/cuilianlevelup"));
        Iterator localIterator = um.getIo().getaConfig("config").getConfigurationSection("id.items").getKeys(false).iterator();
        soul();
    }

    public void soul() {
        @SuppressWarnings("rawtypes")
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
