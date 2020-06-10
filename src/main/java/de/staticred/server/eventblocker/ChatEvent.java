package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.Arena;
import de.staticred.server.util.ArenaFileManager;
import de.staticred.server.util.ArenaManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(Main.vote) e.setCancelled(true);


        if(ArenaManager.isCreatingArena(e.getPlayer())) {
            e.setCancelled(true);

            if(e.getMessage().equalsIgnoreCase("spawn1")) {
                Arena arena = ArenaManager.getCreatedArena(e.getPlayer());
                arena.setSpawnLocation1(e.getPlayer().getLocation());
                ArenaManager.resetArena(e.getPlayer(),arena);
                e.getPlayer().sendMessage("§aDu hast die Spawnlocation 1 erfolgreich gesetzt.");
                return;
            }

            if(e.getMessage().equalsIgnoreCase("spawn2")) {
                Arena arena = ArenaManager.getCreatedArena(e.getPlayer());
                arena.setSpawnLocation2(e.getPlayer().getLocation());
                ArenaManager.resetArena(e.getPlayer(),arena);
                e.getPlayer().sendMessage("§aDu hast die Spawnlocation 2 erfolgreich gesetzt.");
                return;
            }

            if(e.getMessage().equalsIgnoreCase("Cancel")) {
                ArenaManager.removeCreatingArena(e.getPlayer());
                e.getPlayer().sendMessage("§aDu hast den erstell Modus erfolgreich verlassen.");
                return;
            }

            if(e.getMessage().equalsIgnoreCase("Finish")) {
                Arena arena = ArenaManager.getCreatedArena(e.getPlayer());
                if(arena.getSpawnLocation1() == null || arena.getSpawnLocation2() == null) {
                    e.getPlayer().sendMessage("§cBitte setzte erst beide Locations bevor du die Arena erstellst.");
                    return;
                }
                ArenaManager.removeCreatingArena(e.getPlayer());
                ArenaManager.registerArena(arena);
                e.getPlayer().sendMessage("§aEine Arena wurde erstellt");
                return;
            }

        }

    }

}
