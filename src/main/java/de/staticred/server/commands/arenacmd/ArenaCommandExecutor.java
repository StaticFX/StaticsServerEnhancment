package de.staticred.server.commands.arenacmd;

import de.staticred.server.commands.arenacmd.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou must be a player!");
            return false;
        }

        Player executor = (Player) commandSender;

        if(!executor.hasPermission("sts.cmd.arena")) {
            executor.sendMessage("§cKeine Rechte!");
            return false;
        }

        if(args.length == 0) {
            executor.sendMessage("§7---§a§lArena by Static§7---");
            executor.sendMessage("§a/arena <create> §7- §aerstellt eine Arena");
            executor.sendMessage("§a/arena <delete> §7- §aLösche eine Arena");
            executor.sendMessage("§a/arena <info> §7- §aGibt dir Infos über eine Arena");
            executor.sendMessage("§a/arena <join> §7- §aJoine einer Arena");
            executor.sendMessage("§a/arena <list> §7- §aSchaue dir alle Arenen an");
            executor.sendMessage("§a/arena <leave> §7- §aVerlasse einen Kampf");
            executor.sendMessage("§a/arena <invite> §7- §aLade einen anderen Spieler ein.");
            return false;
        }

        if(args[0].equalsIgnoreCase("create")) {
            new ArenaCreateSubCommand(executor,args);
            return false;
        }

        if(args[0].equalsIgnoreCase("delete")) {
            new ArenaDeleteSubCommand(executor,args);
            return false;
        }

        if(args[0].equalsIgnoreCase("join")) {
            new ArenaJoinSubCommand(executor,args);
            return false;
        }

        if(args[0].equalsIgnoreCase("list")) {
            new ArenaListSubCommand(executor,args);
            return false;
        }

        if(args[0].equalsIgnoreCase("leave")) {
            new ArenaLeaveSubCommand(executor,args);
            return false;
        }

        if(args[0].equalsIgnoreCase("info")) {
            new ArenaInfoSubCommand(executor,args);
            return false;
        }

        if(args[0].equalsIgnoreCase("invite")) {
            new ArenaInviteSubCommand(executor,args);
            return false;
        }


        return false;
    }
}
