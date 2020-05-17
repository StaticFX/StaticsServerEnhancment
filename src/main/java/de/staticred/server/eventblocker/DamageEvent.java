package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.EventType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(Main.currentEvent != null && Main.currentEvent.getEventType() == EventType.HALF_DAMAGE_EVENT)
                event.setDamage(event.getDamage() / 2);
        }
    }

}
