package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.EventType;
import de.staticred.server.objects.Perks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class EXPChangeEvent implements Listener {

    @EventHandler
    public void onXpChange(PlayerExpChangeEvent e) {

        if(Main.currentEvent != null && Main.currentEvent.getEventType() == EventType.DOUBLE_XP) {
            if(Main.enabledPerks.get(e.getPlayer()).contains(Perks.DOUBLE_XP_PERK)) {
                e.setAmount(e.getAmount()*4);
            }else{
                e.setAmount(e.getAmount()*2);
            }
            return;
        }

        if(Main.enabledPerks.get(e.getPlayer()).contains(Perks.DOUBLE_XP_PERK)) {
            e.setAmount(e.getAmount()*2);
        }

    }

}
