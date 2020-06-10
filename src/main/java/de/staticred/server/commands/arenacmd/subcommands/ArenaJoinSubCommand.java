package de.staticred.server.commands.arenacmd.subcommands;

import de.staticred.server.Main;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.Arena;
import de.staticred.server.objects.Perks;
import de.staticred.server.util.ArenaFileManager;
import de.staticred.server.util.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ArenaJoinSubCommand {

    public ArenaJoinSubCommand(Player executor, String[] args) {
        execute(executor,args);
    }

    public void execute(Player executor, String[] args) {
        if(!executor.hasPermission("sts.cmd.arena.join")) {
            executor.sendMessage("§cKeine Rechte!");
            return;
        }

        //arena create <name>
        if(args.length != 2) {
            executor.sendMessage("§a/arena <join> <name>");
            return;
        }

        String name = args[1];

        if(!ArenaFileManager.getInstance().isArena(name)) {
            executor.sendMessage("§cDiese Arena existiert nicht!");
            return;
        }


        if(!ArenaManager.isRegistered(name)) {
            executor.sendMessage("§cDiese Arena ist noch nicht fertig. Bitte warte bis die Arena vom Admin fertiggestellt wurde...");
            return;
        }

        if(ArenaManager.isArenaBeingPlayed(name)) {
            executor.sendMessage("§cDiese Arena wird schon bespielt!");
            return;
        }

        if(ArenaManager.waitingPlayers.contains(executor) || ArenaManager.fightingPlayers.contains(executor)) {
            executor.sendMessage("§cDu bist bereits in einer Arena.");
            return;
        }

        if(ArenaManager.isPlayerQueueing(executor)) {
            executor.sendMessage("§cDu wartest bereits in einer Arena.");
            return;
        }

        if(ArenaManager.hasPlayer1Joined(name)) {
            executor.sendMessage("§aEin Spieler ist in dieser Arena, das Spiel wird nun gestartet.");
            Arena arena = ArenaManager.getArena(name);
            ArenaManager.addPlayer2(executor,arena.getArenaID());
            arena.getPlayer1().sendMessage("§aEin Spieler ist gejoined, das Spiel wird nun gestartet.");
            executor.teleport(arena.getSpawnLocation2());
            arena.getPlayer1().teleport(arena.getSpawnLocation1());

            Player player1 = arena.getPlayer1();
            Player player2 = executor;

            ArenaManager.updateArena(arena);
            ArenaManager.waitingPlayers.add(player1);
            ArenaManager.waitingPlayers.add(player2);


            try {
                for(Perks perk : PerkDAO.getInstance().getPerks(player1.getUniqueId())) {
                    Main.getInstance().executePerkChange(player1,perk,false);
                }
                for(Perks perk : PerkDAO.getInstance().getPerks(player2.getUniqueId())) {
                    Main.getInstance().executePerkChange(player2,perk,false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
            player2.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
            player1.sendTitle("§a5","");
            player2.sendTitle("§a5","");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                player1.sendTitle("§a4","");
                player2.sendTitle("§a4","");
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                    player1.sendTitle("§a3","");
                    player2.sendTitle("§a3","");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                        player1.sendTitle("§a2","");
                        player2.sendTitle("§a2","");
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                            player1.sendTitle("§a1","");
                            player2.sendTitle("§a1","");
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                ArenaManager.waitingPlayers.remove(player1);
                                ArenaManager.waitingPlayers.remove(player2);
                                player1.sendTitle("§4§lKÄMPFT","");
                                player2.sendTitle("§4§lKÄMPFT","");
                                ArenaManager.fightingPlayers.add(player1);
                                ArenaManager.fightingPlayers.add(player2);
                                player1.playSound(player1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                                player2.playSound(player1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                            },20);
                            player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                            player2.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                        },20);
                        player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                        player2.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    },20);
                    player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    player2.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                },20);
                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                player2.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
            }, 20);


            return;
        }

        Arena arena = ArenaManager.getArena(name);
        ArenaManager.addPlayer1(executor,arena.getArenaID());

        executor.sendMessage("§aDu bist nun in der Warteschlange von Arena: §c" + name);

    }

}
