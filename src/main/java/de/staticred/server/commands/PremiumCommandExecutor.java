package de.staticred.server.commands;

import de.staticred.server.Main;
import de.staticred.server.db.PremiumDAO;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.time.Duration;

public class PremiumCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(("You must be a player!"));
            return false;
        }

        Player p = (Player) commandSender;

        if(!p.hasPermission("sts.premium")) {
            p.sendMessage("§cKeine Rechte!");
            return false;
        }

        if(args.length != 1) {
            p.sendMessage("§cUse: /premium [player]");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            p.sendMessage("§cBitte gebe einen validen Spieler an.");
            return false;
        }

        if(!Main.api.getUserManager().getUser(target.getUniqueId()).getPrimaryGroup().equalsIgnoreCase("default")) {
            p.sendMessage("§cDieser Spieler hat bereits eine Gruppe.");
            return false;
        }

        try {
            if(PremiumDAO.getInstance().gavePremium(p.getUniqueId())) {
                if(PremiumDAO.getInstance().getTimeStamp(p.getUniqueId()) < System.currentTimeMillis() + Duration.ofDays(7).toMillis()) {
                    p.sendMessage("§cBitte warte eine Woche bevor du einem Spieler Premium gibts.");
                    return false;
                }else{
                    PremiumDAO.getInstance().removePremiumDate(p.getUniqueId());
                    PremiumDAO.getInstance().addPremiumDate(p.getUniqueId());
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " +  target.getName() + " parent addtemp premium 7d");
                    p.sendMessage("§aDu hast erfolgreich " + target.getName() + " für eine Woche Premium gegeben.");
                    target.sendMessage("§aDu hast von " + p.getName() + " für eine Woche Premium bekommen.");
                    return true;
                }
            }else{
                PremiumDAO.getInstance().addPremiumDate(p.getUniqueId());
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " +  target.getName() + " parent addtemp premium 7d");
                p.sendMessage("§aDu hast erfolgreich " + target.getName() + " für eine Woche Premium gegeben.");
                target.sendMessage("§aDu hast von " + p.getName() + " für eine Woche Premium bekommen.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        p.sendMessage("§cUse: /premium [player]");
        return false;
    }
}
