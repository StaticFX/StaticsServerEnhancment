package de.staticred.server.commands;

import de.staticred.server.db.EventDAO;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TicketCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String string, String[] args) {

        if(!s.hasPermission("sts.cmd.ticket")) {
            s.sendMessage("§cKeine Rechte!");
            return false;
        }

        if(args.length < 1) {
            s.sendMessage("§cUse /ticket <set/info>");
            return false;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if(args.length != 3) {
                s.sendMessage("§cUse /ticket <set> <player> <amount>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if(target == null) {
                s.sendMessage("§cDer Spieler ist nicht online.");
                return false;
            }

            int tickets;

            try {
                tickets = EventDAO.getInstance().getTickedAmount(target.getUniqueId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                s.sendMessage("§cInternal Error. Please contact an admin!");
                return false;
            }

            int amount;

            try {
                amount = Integer.parseInt(args[2]);
            } catch (Exception exception) {
                s.sendMessage("§cNutzte eine valide Zahl als amount!");
                return false;
            }

            try {
                EventDAO.getInstance().setTickedAmount(target.getUniqueId(),amount);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }

            s.sendMessage("§aDie Tickets von " + target.getName() + " wurde auf " + amount + " gestzt.");
            return true;

        }
        if (args[0].equalsIgnoreCase("info")) {
            if(args.length != 2) {
                s.sendMessage("§cUse /ticket <set> <player>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if(target == null) {
                s.sendMessage("§cDer Spieler ist nicht online.");
                return false;
            }

            int tickets;

            try {
                tickets = EventDAO.getInstance().getTickedAmount(target.getUniqueId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                s.sendMessage("§cInternal Error. Please contact an admin!");
                return false;
            }

            s.sendMessage("§aTickets von §c" + target.getName() + ": §e" + tickets);
            return true;


        }

        if(args[0].equalsIgnoreCase("add")) {
            if(args.length != 3) {
                s.sendMessage("§cUse /ticket <add> <player> <amount>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if(target == null) {
                s.sendMessage("§cDer Spieler ist nicht online.");
                return false;
            }

            int tickets;

            try {
                tickets = EventDAO.getInstance().getTickedAmount(target.getUniqueId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                s.sendMessage("§cInternal Error. Please contact an admin!");
                return false;
            }

            int amount;

            try {
                amount = Integer.parseInt(args[2]);
            } catch (Exception exception) {
                s.sendMessage("§cNutzte eine valide Zahl als amount!");
                return false;
            }

            try {
                EventDAO.getInstance().setTickedAmount(target.getUniqueId(),tickets + amount);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }

            s.sendMessage("§aDie Tickets von " + target.getName() + " wurde auf " + (tickets + amount) + " gestzt.");
            return true;
        }



            return false;
    }
}
