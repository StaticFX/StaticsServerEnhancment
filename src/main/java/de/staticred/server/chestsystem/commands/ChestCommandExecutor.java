package de.staticred.server.chestsystem.commands;

import de.staticred.server.chestsystem.commands.subcommands.ChestInfoSubCommand;
import de.staticred.server.chestsystem.commands.subcommands.ChestSetSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChestCommandExecutor implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command command, String string, String[] args) {

        if(!s.hasPermission("sts.cmd.chest")) {
            s.sendMessage("§cKeine Rechte!");
            return  false;
        }

        if(args.length == 0) {
            s.sendMessage("§8----§cChestSystem by Static§8----");
            s.sendMessage("§c/chest <info> &7- §eGives information about a players chesttickets.");
            s.sendMessage("§c/chest <set> §7- §eSet a players chesttickets");
            s.sendMessage("§c/chest <open> §7- §eSimulates a chest //mostly debug");
            s.sendMessage("§c/chest <spawn> §7- §eConverts a normal chest into a selectchest!");
            return false;
        }

        if (args[0].equalsIgnoreCase("info")) {
            new ChestInfoSubCommand(s,args);
            return false;
        }
        if (args[0].equalsIgnoreCase("set")) {
            new ChestSetSubCommand(s,args);
            return false;
        }

        s.sendMessage("§8----§cChestSystem by Static§8----");
        s.sendMessage("§c/chest <info> &7- §eGives information about a players chesttickets.");
        s.sendMessage("§c/chest <set> §7- §eSet a players chesttickets");
        s.sendMessage("§c/chest <open> §7- §eSimulates a chest //mostly debug");
        s.sendMessage("§c/chest <spawn> §7- §eConverts a normal chest into a selectchest!");



        return false;
    }
}
