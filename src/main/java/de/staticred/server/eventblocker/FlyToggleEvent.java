package de.staticred.server.eventblocker;

import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FlyToggleEvent implements Listener {

    @EventHandler
    public void flyToggleEvent(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();

        Location loc = new Location(p.getLocation().getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockY(),p.getLocation().getBlockZ());
        Plot currentPlot = Plot.getPlot(loc);

        if(!p.hasPermission("perk.flyanywhere")) {
            if (currentPlot == null || !currentPlot.getOwners().contains(p.getUniqueId())) {
                e.setCancelled(true);
                p.setFlying(false);
            }
        }

    }

}
