package ksqeib.Intensify.command;

import ksqeib.Intensify.listener.MoveLevelInventory;
import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Stone;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CuiCmd extends Command {
    public CuiCmd(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender cms, String label, String[] args) {
        if (args.length == 0) {
            //什么也没输入
            cms.sendMessage(Intensify.um.getHelper("cuilian").hY.getString("help.head"));
            cms.sendMessage(Intensify.um.getHelper("cuilian").hY.getString("help.start") + label + Intensify.um.getHelper("qh").hY.getString("help.help"));
            cms.sendMessage(Intensify.um.getHelper("cuilian").hY.getString("help.last"));
        } else if (args.length > 0) {
            Player p = null;
            if (cms instanceof Player) {
                p = (Player) cms;
            }
            switch (args[0]) {

                case "about":
                    Intensify.um.getTip().send("BY KSQEIB", cms, null);
                    if (args.length >= 2) {
                        Intensify.um.getTip().send("THIS" + Bukkit.getBukkitVersion() + "PL:" + Intensify.instance.getName(), cms, null);
                        Intensify.um.getTip().send("VER" + Intensify.instance.getDescription().getVersion(), cms, null);
                    }
                    break;
                default:

                case "help":
                    //输入help
                    Intensify.um.getHelper("cuilian").HelpPage(cms, label, args);
                    break;
                case "ov":
                    if (!(cms instanceof Player)) {

                        cms.sendMessage("后台无法使用该命令");
                    }
                    MoveLevelInventory.open(p);
                    break;
                case "give":
                    if (!cms.isOp()) {
                        cms.sendMessage("不是OP");
                        return true;
                    }
                    if (args.length == 4) {
                        Player pp = Bukkit.getPlayer(args[3]);
                        if (pp == null) {
                            cms.sendMessage("§c玩家不在线");
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("give")) {
                            int sl = Integer.parseInt(args[2]);
                            Stone s = Intensify.dataer.getCuilianStone(args[1]);
                            if (s == null) {
                                pp.sendMessage("不存在的淬炼石");
                            }
                            ItemStack item = s.getCuiLianStone();
                            item.setAmount(sl);
                            pp.getInventory().addItem(item);
                        }
                    }
                    break;
            }
        }
        return true;
    }
}
