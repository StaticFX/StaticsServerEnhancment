package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.db.EventDAO;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.Arena;
import de.staticred.server.objects.Perks;
import de.staticred.server.util.ArenaManager;
import org.bukkit.Bukkit;
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

        if(ArenaManager.isPlayerQueueing(p)) ArenaManager.stopQueuing(p);

        if(ArenaManager.fightingPlayers.contains(p) || ArenaManager.waitingPlayers.contains(p)) {

            Main.leftInGame.add(p.getUniqueId().toString());

            Player killer;

            if(ArenaManager.getArena(p).getPlayer1() == p) {
                killer = ArenaManager.getArena(p).getPlayer2();

            } else {
                killer = ArenaManager.getArena(p).getPlayer1();
            }

            Arena arena = ArenaManager.getArena(killer);

            try {
                for(Perks perk : PerkDAO.getInstance().getPerks(killer.getUniqueId())) {
                    Main.getInstance().executePerkChange(killer,perk,true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            killer.sendMessage("§cDer gegner hat das Spiel verlassen.");
            killer.sendTitle("§a§lDu hast gewonnen!","");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e5");
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                    killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e4");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                        killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e3");
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                            killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e2");
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e1");
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                    killer.performCommand("spawn");
                                    ArenaManager.fightFinished(arena);
                                    ArenaManager.fightingPlayers.remove(killer);
                                }, 20);
                            }, 20);
                        }, 20);
                    }, 20);
                }, 20);
            }, 20);
        }

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
