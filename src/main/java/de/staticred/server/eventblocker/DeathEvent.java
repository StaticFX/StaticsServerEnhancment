package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.Perks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player p = e.getEntity();
        if(Main.enabledPerks.get(p).contains(Perks.KEEP_INVENTORY_PERK)) {
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
        if(Main.enabledPerks.get(p).contains(Perks.KEEP_XP_PERK)) {
            e.setKeepLevel(true);
            e.setDroppedExp(0);
        }

        if(Main.eco.getBalance(p) > 2500) {
            Main.eco.withdrawPlayer(p,2500);
            p.sendMessage("§cDu bist gestorben und hast deswegen 2500 Dukaten verloren.");
        }



    }



}
