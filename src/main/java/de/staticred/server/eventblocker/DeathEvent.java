package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.Arena;
import de.staticred.server.objects.Perks;
import de.staticred.server.util.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player p = e.getEntity();

        if(ArenaManager.fightingPlayers.contains(p)) {
            e.setKeepLevel(true);
            e.setKeepInventory(true);
            e.getDrops().clear();
            Arena arena = ArenaManager.getArena(p);

            Player killer = p.getKiller();
            killer.setHealth(20);

            try {
                for(Perks perk : PerkDAO.getInstance().getPerks(p.getUniqueId())) {
                    Main.getInstance().executePerkChange(p,perk,true);
                }
                for(Perks perk : PerkDAO.getInstance().getPerks(killer.getUniqueId())) {
                    Main.getInstance().executePerkChange(killer,perk,true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


            killer.sendTitle("§a§lDu hast gewonnen!","");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                p.sendMessage("§aDie Arena wird zurückgesetzt in: §e5");
                killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e5");
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                    p.sendMessage("§aDie Arena wird zurückgesetzt in: §e4");
                    killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e4");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                        p.sendMessage("§aDie Arena wird zurückgesetzt in: §e3");
                        killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e3");
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                            p.sendMessage("§aDie Arena wird zurückgesetzt in: §e2");
                            killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e2");
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                p.sendMessage("§aDie Arena wird zurückgesetzt in: §e1");
                                killer.sendMessage("§aDie Arena wird zurückgesetzt in: §e1");
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                    killer.performCommand("spawn");
                                    ArenaManager.fightFinished(arena);
                                    ArenaManager.fightingPlayers.remove(p);
                                    ArenaManager.fightingPlayers.remove(killer);
                                }, 20);
                            }, 20);
                        }, 20);
                    }, 20);
                }, 20);
            }, 20);

           killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1,1);

            p.sendMessage("§cDu hast verloren!");

            return;
        }

        if(Main.enabledPerks.get(p).contains(Perks.KEEP_INVENTORY_PERK)) {
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
        if(Main.enabledPerks.get(p).contains(Perks.KEEP_XP_PERK)) {
            e.setKeepLevel(true);
            e.setDroppedExp(0);
        }



    }



}
