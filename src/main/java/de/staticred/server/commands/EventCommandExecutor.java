package de.staticred.server.commands;

import de.staticred.server.db.EventDAO;
import de.staticred.server.util.EventManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class EventCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cDu musst ein spieler sein!");
            return false;
        }

        Player p = (Player) commandSender;

        if(!p.hasPermission("sse.cmd.events")) {
            p.sendMessage("§cKeine Rechte!");
            return false;
        }

        try {
            p.openInventory(EventManager.getEventInventory(EventDAO.getInstance().getTickedAmount(p.getUniqueId())));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            p.sendMessage("§cInternal Error. Please contact an admin.");
            return false;
        }
        return false;
    }
}
