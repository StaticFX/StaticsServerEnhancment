package de.staticred.server.commands;

import de.staticred.server.Main;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.Perks;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class SpecCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String string, String[] args) {

        if(!(s instanceof Player)) {
            s.sendMessage("Du musst ein Spieler sein");
            return false;
        }
        Player p = ((Player) s);

        if(!p.hasPermission("sts.spec")) {
            p.sendMessage("§cKeine Rechte");
            return false;
        }

        if(args.length != 0) {
            p.sendMessage("§cUSe /spec");
            return false;
        }

        if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.CREATIVE) {
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage("§aSpec mode activated.");
            try {
                for(Perks perk : PerkDAO.getInstance().getPerks(p.getUniqueId())) {
                    Main.getInstance().executePerkChange(p,perk,true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage("§cSpec mode deactivated.");
            try {
                for(Perks perk : PerkDAO.getInstance().getPerks(p.getUniqueId())) {
                    Main.getInstance().executePerkChange(p,perk,true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return false;
    }
}
