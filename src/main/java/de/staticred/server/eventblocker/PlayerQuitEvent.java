package de.staticred.server.eventblocker;

import de.staticred.server.db.EventDAO;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.Perks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void onPlayerLeave(org.bukkit.event.player.PlayerQuitEvent e) {

        Player p = e.getPlayer();

        e.setQuitMessage("");

        try {
            for(Perks perk : PerkDAO.getInstance().getPerks(p.getUniqueId())) {
                executePerkChange(p,perk,false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            EventDAO.getInstance().setLastOnline(p.getUniqueId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void executePerkChange(Player p, Perks perk, boolean activation) {
        if(perk == Perks.FLY_PERK) {
            if(activation) p.setAllowFlight(true);
            if(!activation) p.setAllowFlight(false);
        }
        if(perk == Perks.FAST_DESTROY_PERK) {
            if(activation) p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999, 2,true,false));
            if(!activation) p.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
        if(perk == Perks.SPEED_PERK) {
            if(activation) p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1,true,false));
            if(!activation) p.removePotionEffect(PotionEffectType.SPEED);
        }
    }

}
