package ksqeib.Intensify.command;

import ksqeib.Intensify.enchant.Qh;
import ksqeib.Intensify.main.Intensify;
import ksqeib.Intensify.store.Istone;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cmd extends Command {
    public Cmd(String name) {
        super(name);
    }


    @Override
    public boolean execute(CommandSender cms, String label, String[] args) {// 指令!
        //输入指令后
        if (args.length == 0) {
            //什么也没输入
            Intensify.um.getHelper("qh").sendno(cms, label);
        } else {
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
                    Intensify.um.getHelper("qh").HelpPage(cms, label, args);
                    break;
                case "give":
                    if (!Intensify.um.getPerm().isp(cms, args[0])) {
                        Intensify.um.getTip().getDnS(p, "nop", null);
                    } else {
                        if (args.length >= 4) {
                            //给予指令
                            Player targetplayer = Bukkit.getPlayer(args[1]);
                            int many = Integer.parseInt(args[3]);
                            ItemStack item = Intensify.dataer.getItem(args[2]);
                            item.setAmount(many);
                            targetplayer.getInventory().addItem(item);
                            //发送消息
                            Intensify.um.getTip().getDnS(p, "toinventory", null);
                        } else {
                            Intensify.um.getTip().getDnS(p, "subwrong", null);
                        }
                    }

                    break;
                case "do":
                    if (args.length >= 3) {
                        boolean isSafe = Boolean.parseBoolean(args[2]);
                        int chance = Integer.parseInt(args[1]);
                        Intensify.dataer.player.put(p.hashCode(), p.getUniqueId());
                        p.getInventory().setItemInMainHand(Qh.qh(p.hashCode(), p.getInventory().getItemInMainHand(), new Istone(null, null, chance, isSafe)));
                    } else {
                        Intensify.um.getTip().getDnS(p, "subwrong", null);
                    }
                    break;
                case "reload":
                    if (Intensify.um.getPerm().isp(cms, "." + args[0])) {
                        Intensify.instance.reload();
                        Intensify.um.getTip().getDnS(p, "reload", null);
                    } else {
                        Intensify.um.getTip().getDnS(p, "nop", null);
                    }
                    break;
            }
        }

        return true;
    }
    // 指令!
}
