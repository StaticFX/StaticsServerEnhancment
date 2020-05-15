package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.Perks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class EXPChangeEvent implements Listener {

    @EventHandler
    public void onXpChange(PlayerExpChangeEvent e) {

        if(Main.enabledPerks.get(e.getPlayer()).contains(Perks.DOUBLE_XP_PERK)) {
            e.setAmount(e.getAmount()*2);
        }

    }

}
