package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.Arena;
import de.staticred.server.util.ArenaFileManager;
import de.staticred.server.util.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {

        if(ArenaManager.fightingPlayers.contains(e.getPlayer()) || ArenaManager.waitingPlayers.contains(e.getPlayer()) && !Main.leftInGame.contains(e.getPlayer().getUniqueId().toString())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§cDu kannst keine Befehle in einem Kampf ausführen.");
            return;
        }

        String[] cmd = e.getMessage().split(" ");

        if(e.getPlayer().hasPermission("sts.team")) return;

        if(cmd[0].equalsIgnoreCase("/pl")) return;
        if(cmd[0].equalsIgnoreCase("/plugins")) return;

        if(e.getMessage().startsWith("pl")) return;
        if(e.getMessage().startsWith("pl")) return;


        if(cmd.length > 2) {
            if(cmd[0].equalsIgnoreCase("/p") || cmd[0].equalsIgnoreCase("/plot") || cmd[0].equalsIgnoreCase("/ps") ||
                    cmd[0].equalsIgnoreCase("/plotsquared") || cmd[0].equalsIgnoreCase("/p2") || cmd[0].equalsIgnoreCase("/2")
                    || cmd[0].equalsIgnoreCase("/plotme") ||  cmd[0].equalsIgnoreCase("/plots") ||

                    cmd[0].equalsIgnoreCase("/plotsquared:p") || cmd[0].equalsIgnoreCase("/plotsquared:plot") || cmd[0].equalsIgnoreCase("/plotsquared:ps") ||
                    cmd[0].equalsIgnoreCase("/plotsquared:plotsquared") || cmd[0].equalsIgnoreCase("/plotsquared:p2") || cmd[0].equalsIgnoreCase("/plotsquared:2")
                    || cmd[0].equalsIgnoreCase("/plotsquared:plotme") || cmd[0].equalsIgnoreCase("/plotsquared:plots")) {

                if(cmd[1].equalsIgnoreCase("so") || cmd[1].equalsIgnoreCase("setowner") || cmd[1].equalsIgnoreCase("set owner")
                        || cmd[1].equalsIgnoreCase("s o") || cmd[1].equalsIgnoreCase("s owner") || cmd[1].equalsIgnoreCase("set o")
                        || cmd[1].equalsIgnoreCase("seto") || cmd[1].equalsIgnoreCase("owner")) {
                    if(cmd[2].equalsIgnoreCase("-") || cmd[2].equalsIgnoreCase("null") || cmd[2].equalsIgnoreCase("none")) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage("You cant use this command here.");
                    }
                }
                if(cmd.length > 3) {
                    if(cmd[2].equalsIgnoreCase("so") || cmd[2].equalsIgnoreCase("setowner") || cmd[2].equalsIgnoreCase("set owner")
                            || cmd[2].equalsIgnoreCase("s o") || cmd[2].equalsIgnoreCase("s owner") || cmd[2].equalsIgnoreCase("set o")
                            || cmd[2].equalsIgnoreCase("seto") || cmd[2].equalsIgnoreCase("owner")) {
                        if(cmd[3].equalsIgnoreCase("-") || cmd[3].equalsIgnoreCase("null") || cmd[3].equalsIgnoreCase("none")) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage("You cant use this command here.");
                        }
                    }
                }
            }
        }
    }



}
