package de.staticred.server.eventblocker;

import de.staticred.server.util.ArenaFileManager;
import de.staticred.server.util.ArenaManager;
import de.staticred.server.util.ArenaSignFileManager;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SignChangeEvent implements Listener {

    @EventHandler
    public void onSignChange(org.bukkit.event.block.SignChangeEvent e) {

        Player editor = e.getPlayer();


        if(!editor.hasPermission("sts.arena.sign.create")) return;

        String line = e.getLine(0);

        if(ArenaSignFileManager.getInstance().isArenaSign((Sign) e.getBlock().getState())) return;

        if(line.startsWith("[") && line.endsWith("]")) {
            String betweenBrackets = line.substring(1,line.length() - 1);
            if(!betweenBrackets.equalsIgnoreCase("arena")) return;

            String arena = e.getLine(1);

            if(!ArenaFileManager.getInstance().isArena(arena)) return;

            e.setLine(0,"§8[§2Arena§8]");
            e.setLine(1,"§e" + arena);

            if(ArenaManager.isArenaBeingPlayed(arena)) {
               e.setLine(2,"§42§8/§42");
            }else if(ArenaManager.hasPlayer1Joined(arena)) {
                e.setLine(2,"§c1§8/§c2");
            }else{
                e.setLine(2,"§a0§8/§a2");
            }
            editor.sendMessage("§aDas Schild wurde erfolgreich erstellt.");

            ArenaSignFileManager.getInstance().saveSign(arena, (Sign) e.getBlock().getState());

        }
    }

}
