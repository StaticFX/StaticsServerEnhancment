package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.Perks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerEvent implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (Main.enabledPerks.get((Player) e.getEntity()).contains(Perks.ANTI_HUNGER)) {
            e.setCancelled(true);
            ((Player) e.getEntity()).setFoodLevel(20);
        }
    }
}
