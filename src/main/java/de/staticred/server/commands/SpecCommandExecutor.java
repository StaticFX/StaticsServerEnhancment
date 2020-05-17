package de.staticred.server.commands;

import de.staticred.server.Main;
import de.staticred.server.objects.Perks;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            Main.getInstance().executePerkChange(p, Perks.FLY_PERK,true);
        }else{
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage("§cSpec mode deactivated.");
        }

        return false;
    }
}
