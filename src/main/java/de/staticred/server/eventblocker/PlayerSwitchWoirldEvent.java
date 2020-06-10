package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.Perks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.sql.SQLException;

public class PlayerSwitchWoirldEvent implements Listener {

    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent e) {
        try {
            for(Perks perk : PerkDAO.getInstance().getPerks(e.getPlayer().getUniqueId())) {
                Main.getInstance().executePerkChange(e.getPlayer(),perk,false);
            }
            for(Perks perk : PerkDAO.getInstance().getPerks(e.getPlayer().getUniqueId())) {
                Main.getInstance().executePerkChange(e.getPlayer(),perk,true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
