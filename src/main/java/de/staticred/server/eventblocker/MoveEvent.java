package de.staticred.server.eventblocker;

import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import de.staticred.server.Main;
import de.staticred.server.objects.EventType;
import de.staticred.server.objects.Perks;
import de.staticred.server.util.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if(Main.currentEvent != null && Main.currentEvent.getEventType() == EventType.FLY_EVENT) {
            p.setAllowFlight(true);
            return;
        }

        if(ArenaManager.waitingPlayers.contains(p)) {
            e.setCancelled(true);
        }


        if(!p.hasPermission("perk.fly")) {
            p.setAllowFlight(false);
        }


        if(!p.hasPermission("perk.flyanywhere")) {
            if(Main.enabledPerks.get(p).contains(Perks.FLY_PERK)) {
                if(p.isFlying()) {
                    Location loc = new Location(p.getLocation().getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockY(),p.getLocation().getBlockZ());
                    Plot currentPlot = Plot.getPlot(loc);
                    if(currentPlot == null) {
                        if(!Main.flyWarn.contains(p)) {
                            startTimer(p);
                            Main.flyWarn.add(p);
                        }
                        return;
                    }
                    if(!currentPlot.getOwners().contains(p.getUniqueId()) && !currentPlot.getTrusted().contains(p.getUniqueId())) {
                        if(!Main.flyWarn.contains(p)) {
                            startTimer(p);
                            Main.flyWarn.add(p);
                        }
                        return;
                    }
                    //muss auf seinem plot sein
                 }
            }
        }

    }

    public void startTimer(Player p) {
        p.sendMessage("§c§lYou left your plot. Please retain to it within §a§l3 seconds §c§lor your fly will be deactivated.");
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                Location loc = new Location(p.getLocation().getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockY(),p.getLocation().getBlockZ());
                Plot currentPlot = Plot.getPlot(loc);

                if(currentPlot == null || !currentPlot.getOwners().contains(p.getUniqueId()) && !currentPlot.getTrusted().contains(p.getUniqueId())) {
                    p.setFlying(false);
                    p.sendMessage("§c§lYour fly was disabled because you left your plot.");
                }

                Main.flyWarn.remove(p);
            }
        },20*3);
    }

}
