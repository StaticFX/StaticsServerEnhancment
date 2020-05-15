package de.staticred.server.commands;

import de.staticred.server.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to execute this command.");
            return false;
        }

        Player p = (Player) commandSender;

        if(args.length != 1) {
            commandSender.sendMessage("§cUse /v 1/0");
            return false;
        }

        if(!Main.canVote) {
            p.sendMessage("§cDu kannst im Moment nicht abstimmen");
            return false;
        }


        if(args[0].equalsIgnoreCase("1")) {
            if(Main.voted.contains(p)) {
                p.sendMessage("§cDu hast bereits abgestimmt.");
                return false;
            }
            Main.voteYes++;
            Main.voted.add(p);
            p.sendMessage("§aDu hast erfolgreich für §2JA §agestimmt.");
            return true;

        }

        if(args[0].equalsIgnoreCase("0")) {
            if(Main.voted.contains(p)) {
                p.sendMessage("§cDu hast bereits abgestimmt.");
                return false;
            }
            Main.voteNo++;
            Main.voted.add(p);
            p.sendMessage("§aDu hast erfolgreich für §4NEIN §agestimmt.");
            return true;
        }

        commandSender.sendMessage("§cUse /v 1/0");
        return false;
    }
}
