package de.staticred.server.eventblocker;

import de.staticred.server.util.PlotUpgradeManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class VillagerClickedEvent implements Listener {

    @EventHandler
    public void on(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(e.getRightClicked() instanceof Villager) {
            Villager v = (Villager) e.getRightClicked();

            if(v.getCustomName() == null) return;

            if(v.getCustomName().equalsIgnoreCase("§c§lPlot-Upgrades")) {
                e.setCancelled(true);
                p.openInventory(PlotUpgradeManager.getInv(p));
                return;
            }
        }
    }

}
