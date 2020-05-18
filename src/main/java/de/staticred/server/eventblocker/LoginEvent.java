package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.db.EventDAO;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.EventType;
import de.staticred.server.objects.Perks;
import de.staticred.server.scoreboard.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class LoginEvent implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!Main.enabledPerks.containsKey(p)) Main.enabledPerks.put(p,new ArrayList<>());

        try {
            if(!EventDAO.getInstance().isInDatabase(p.getUniqueId()))
                EventDAO.getInstance().addPlayer(p.getUniqueId(), 0);
            Scoreboard.generateScoreboard(p);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String group = Main.api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup();

        if(group.equalsIgnoreCase("ziemlich")) {
            try {
                if(isNewDay(p.getUniqueId())) {
                    EventDAO.getInstance().setTickedAmount(p.getUniqueId(),EventDAO.getInstance().getTickedAmount(p.getUniqueId()) + 1);
                    p.sendMessage("§cDir wurde Automatisch dein tägliches Event-Ticket zugewiesen.");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        e.setJoinMessage("");

        if(!Main.updater) {
            Scoreboard.startUpdater();
            Main.updater = true;
        }



        if(Main.currentEvent != null && Main.currentEvent.getEventType() == EventType.FLY_EVENT) {
            p.setFlying(true);
            p.setAllowFlight(true);
        }
        if(Main.currentEvent != null && Main.currentEvent.getEventType() == EventType.FAST_DESTROY) {
            if(p.hasPermission("perk.fastdestroy")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,99999,5,true,false));
            }else{
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,99999,2,true,false));
            }
        }



            try {
                 if(!EventDAO.getInstance().isInDatabase(p.getUniqueId())) {
                     EventDAO.getInstance().addPlayer(p.getUniqueId(),0);
                 }

                if(p.hasPermission("perk.fly")) {
                    PerkDAO.getInstance().addPerk(p.getUniqueId(), Perks.FLY_PERK);
                }else{
                    PerkDAO.getInstance().removePerk(p.getUniqueId(), Perks.FLY_PERK);
                }
                if(p.hasPermission("perk.keepinventory"))  {
                    PerkDAO.getInstance().addPerk(p.getUniqueId(), Perks.KEEP_INVENTORY_PERK);
                }else{
                    PerkDAO.getInstance().removePerk(p.getUniqueId(), Perks.KEEP_INVENTORY_PERK);
                }
                if(p.hasPermission("perk.keepxp")) {
                    PerkDAO.getInstance().addPerk(p.getUniqueId(), Perks.KEEP_XP_PERK);
                }else{
                    PerkDAO.getInstance().removePerk(p.getUniqueId(), Perks.KEEP_XP_PERK);
                }
                if(p.hasPermission("perk.anti_hunger")) {
                    PerkDAO.getInstance().addPerk(p.getUniqueId(), Perks.ANTI_HUNGER);
                }else{
                    PerkDAO.getInstance().removePerk(p.getUniqueId(), Perks.ANTI_HUNGER);
                }
                if(p.hasPermission("perk.speed")) {
                    PerkDAO.getInstance().addPerk(p.getUniqueId(), Perks.SPEED_PERK);
                }else{
                    PerkDAO.getInstance().removePerk(p.getUniqueId(), Perks.SPEED_PERK);
                }
                if(p.hasPermission("perk.fastdestroy")) {
                    PerkDAO.getInstance().addPerk(p.getUniqueId(), Perks.FAST_DESTROY_PERK);
                }else{
                    PerkDAO.getInstance().removePerk(p.getUniqueId(), Perks.FAST_DESTROY_PERK);
                }
            if(p.hasPermission("perk.doublexp")) {
                PerkDAO.getInstance().addPerk(p.getUniqueId(), Perks.DOUBLE_XP_PERK);
            }else{
                PerkDAO.getInstance().removePerk(p.getUniqueId(), Perks.DOUBLE_XP_PERK);
            }
            if(p.hasPermission("perk.nightvision")) {
                PerkDAO.getInstance().addPerk(p.getUniqueId(), Perks.NIGHT_VISION_PERK);
            }else{
                PerkDAO.getInstance().removePerk(p.getUniqueId(), Perks.NIGHT_VISION_PERK);
            }
            } catch (SQLException ex) {
                ex.printStackTrace(); }



        try {
            for(Perks perk : PerkDAO.getInstance().getPerks(p.getUniqueId())) {
                Main.enabledPerks.get(p).add(perk);
                executePerkChange(p,perk,true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        if(perk == Perks.NIGHT_VISION_PERK) {
            if(activation) p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 1,true,false));
            if(!activation) p.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public boolean isNewDay(UUID player) throws SQLException {
        if(EventDAO.getInstance().isPlayerInDatabse(player)) {

            Date date = new Date(EventDAO.getInstance().lastSeen(player));

            Calendar calender = Calendar.getInstance();
            Calendar calender1 = Calendar.getInstance();
            calender.setTime(date);
            calender1.setTime(new Date(System.currentTimeMillis()));


            return calender.get(Calendar.DAY_OF_YEAR) == calender1.get(Calendar.DAY_OF_YEAR) &&
                    calender.get(Calendar.YEAR) == calender1.get(Calendar.YEAR);
        }
        return false;
    }

}
