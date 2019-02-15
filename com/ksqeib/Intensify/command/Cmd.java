package ksqeib.Intensify.command;

import ksqeib.Intensify.Main;
import ksqeib.Intensify.enchant.Qh;
import ksqeib.Intensify.util.Dataer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Cmd extends Command {
    public Cmd(String name) {
        super(name);
    }

    public Cmd(String name, String doc, String usage, List<String> alies) {
        super(name, doc, usage, alies);
    }

    @Override
    public boolean execute(CommandSender cms, String label, String[] args) {// 指令!
        //输入指令后
        if (args.length == 0) {
            //什么也没输入
            cms.sendMessage(Main.um.getHelper("qh").hY.getString("help.head"));
            cms.sendMessage(Main.um.getHelper("qh").hY.getString("help.start") + label + Main.um.getHelper("qh").hY.getString("help.help"));
            cms.sendMessage(Main.um.getHelper("qh").hY.getString("help.last"));
        } else if (args.length > 0) {
            Player p = null;
            if (cms instanceof Player) {
                p = (Player) cms;
            }
            switch (args[0]) {

                case "about":
                    Main.um.getTip().send("BY KSQEIB",cms,null);
                    if (args.length >= 2) {
                        Main.um.getTip().send("THIS" + Bukkit.getBukkitVersion() + "PL:" + Main.instance.getName(),cms,null);
                        Main.um.getTip().send("VER" + Main.instance.getDescription().getVersion(),cms,null);
                    }
                    break;
                default:

                case "help":
                    //输入help
                    Main.um.getHelper("qh").HelpPage(cms, label, args);
                    break;
                case "give":
                    if (!Main.um.getPerm().isp(cms, args[0])) {
                        Main.um.getTip().getDnS(p,"nop",null);
                    } else {
                        if (args.length >= 4) {
                            //给予指令
                            Player targetplayer = Bukkit.getPlayer(args[1]);
                            Boolean su = false;
                            int many = Integer.parseInt(args[3]);
                            ItemStack item = Dataer.getItem(args[2]);
                            item.setAmount(many);
                            targetplayer.getInventory().addItem(item);
                            item.setAmount(1);
                            su = true;
                            //发送消息
                            if (su) {
                                Main.um.getTip().getDnS(p,"toinventory",null);
                            } else {
                                Main.um.getTip().getDnS(p,"wrong",null);
                            }
                        } else {
                            Main.um.getTip().getDnS(p,"subwrong",null);
                        }
                    }

                    break;
                case "do":
                    if (Main.um.getPerm().isp(cms, args[0])) {
                        if (args.length >= 4) {
                            boolean isSafe = Boolean.parseBoolean(args[1]);
                            boolean isAdmin = Boolean.parseBoolean(args[2]);
                            int chance = Integer.parseInt(args[3]);
                            p.setItemInHand(Qh.qh(p, p.getItemInHand(), isSafe, isAdmin, p.getItemInHand().getTypeId(), chance));
                        } else {
                            Main.um.getTip().getDnS(p,"subwrong",null);
                        }
                    } else {
                        Main.um.getTip().getDnS(p,"nop",null);
                    }
                    break;
                case "reload":
                    if (Main.um.getPerm().isp(cms, "." + args[0])) {
                        Main.instance.reload();
                        Main.um.getTip().getDnS(p,"reload",null);
                    } else {
                        Main.um.getTip().getDnS(p,"nop",null);
                    }
                    break;
            }
        }

        return true;
    }
    // 指令!
}
