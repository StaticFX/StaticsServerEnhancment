package de.staticred.server.scoreboard;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.staticred.server.Main;
import de.staticred.server.db.SlotDAO;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

public class Scoreboard {
    public static void generateScoreboard(Player p) {
        Economy eco = Main.eco;
        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.getObjective("sidebar");

        if(objective == null) {
            objective = board.registerNewObjective("sidebar","dummy");
        }

        objective.setDisplayName("»» §3Ziemlich.eu §r««");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);




        //1-2 placeholder
        objective.getScore("  ").setScore(24);

        //rang
        objective.getScore("§aRang:").setScore(23);

        User user = Main.api.getUserManager().getUser(p.getUniqueId());
        ContextManager contextManager = Main.api.getContextManager();
        String pr;
        String prefix = user.getCachedData().getMetaData(contextManager .getQueryOptions(user).orElseGet(contextManager::getStaticQueryOptions)).getPrefix();
        if(prefix == null || prefix.equalsIgnoreCase("&a")) {
            pr = "Kein Rang";
        }else {
            pr = prefix.substring(0,prefix.length() - 4).replaceAll("&","§");
        }

        Team rankTeam = board.registerNewTeam("rankTeam");
        rankTeam.setPrefix("§7»§a" + pr);
        rankTeam.addEntry("§a");
        objective.getScore("§a").setScore(22);

        //placerholder 2-4
        objective.getScore("   ").setScore(21);


        //money
        objective.getScore("§aDukaten:").setScore(19);
        Team moneyTeam = board.registerNewTeam("moneyTeam");
        moneyTeam.setPrefix("§7»§6 " + round(Main.eco.getBalance(p), 2));
        moneyTeam.addEntry("§b");
        objective.getScore("§b").setScore(18);

        //placerholder 4-6
        objective.getScore("     ").setScore(17);

        //world
        objective.getScore("§aVoteparty:").setScore(15);

        Team worldTeam = board.registerNewTeam("worldTeam");
        String text = "&a%VotingPlugin_VotePartyVotesCurrent%&7/&6%VotingPlugin_VotePartyVotesRequired%";
        text = PlaceholderAPI.setPlaceholders(p,text);
        worldTeam.setPrefix("§7»§3 " + text);
        worldTeam.addEntry("§3");
        objective.getScore("§3").setScore(14);


        //placeholder 6-8
        objective.getScore("            ").setScore(13);

        //spielerteam
        objective.getScore("§aSpieler:").setScore(11);

        sendFarmData(p);

        Team playerTeam = board.registerNewTeam("playerTeam");
        playerTeam.setPrefix("§7»§a " + Main.onlineplayer);
        playerTeam.addEntry("§7");
        objective.getScore("§7").setScore(10);

        objective.getScore("       ").setScore(9);
        objective.getScore("§e§lAktuelles Event:").setScore(8);

        Team evenTeam = board.registerNewTeam("eventTeam");

        if(Main.currentEvent == null) {
            evenTeam.setPrefix("§7»§a Kein Event");
        }else{
            evenTeam.setPrefix("§7»§a " + Main.currentEvent.getEventType().toString());
        }

        evenTeam.addEntry("§8");
        objective.getScore("§8").setScore(7);

        p.setScoreboard(board);

    }

    public static void startUpdater() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                org.bukkit.scoreboard.Scoreboard board = p.getScoreboard();
                Objective obj = board.getObjective("sidebar");

                Team rankTeam = board.getTeam("rankTeam");

                User user = Main.api.getUserManager().getUser(p.getUniqueId());
                ContextManager contextManager = Main.api.getContextManager();
                String pr;
                String prefix = user.getCachedData().getMetaData(contextManager .getQueryOptions(user).orElseGet(contextManager::getStaticQueryOptions)).getPrefix();
                if(prefix == null || prefix.equalsIgnoreCase("&a")) {
                    pr = "Kein Rang";
                }else if(prefix.contains("&l")) {
                    pr = prefix.substring(0,prefix.length() - 6).replaceAll("&","§");
                }else {
                    pr = prefix.substring(0,prefix.length() - 4).replaceAll("&","§");
                }


                if(rankTeam == null) {
                    rankTeam = board.registerNewTeam("rankTeam");
                    rankTeam.setPrefix("§7»§a " + pr);
                    rankTeam.addEntry("§a");
                    obj.getScore("§a").setScore(22);
                }else{
                    rankTeam.setPrefix("§7»§a " + pr);
                }

                Team moneyTeam = board.getTeam("moneyTeam");

                if(moneyTeam == null) {
                    moneyTeam = board.registerNewTeam("moneyTeam");
                    moneyTeam.setPrefix("§7»§6 " + round(Main.eco.getBalance(p), 2));
                    moneyTeam.addEntry("§b");
                    obj.getScore("§b").setScore(19);
                }else{
                    moneyTeam.setPrefix("§7»§6 " + round(Main.eco.getBalance(p), 2));
                }



                Team worldTeam = board.getTeam("worldTeam");



                if(worldTeam == null) {
                    String text = "&a%VotingPlugin_VotePartyVotesCurrent%&7/&6%VotingPlugin_VotePartyVotesNeeded%";
                    text = PlaceholderAPI.setPlaceholders(p,text);
                    worldTeam = board.registerNewTeam("worldTeam");
                    worldTeam.setPrefix("§7»§3 " + text);
                    worldTeam.addEntry("§3");
                    obj.getScore("§3").setScore(16);
                }else{              String text = "&a%VotingPlugin_VotePartyVotesCurrent%&7/&6%VotingPlugin_VotePartyVotesRequired%";
                    text = PlaceholderAPI.setPlaceholders(p,text);
                    worldTeam.setPrefix("§7»§3 " + text);
                }

                Team playerTeam = board.getTeam("playerTeam");


                sendFarmData(p);

                if(playerTeam == null) {
                    playerTeam = board.registerNewTeam("playerTeam");
                    playerTeam.setPrefix("§7»§a " + Main.onlineplayer);
                    playerTeam.addEntry("§7");
                    obj.getScore("§7").setScore(12);
                }else{
                    playerTeam.setPrefix("§7»§a " + Main.onlineplayer);
                }

                Team eventTeam = board.getTeam("eventTeam");


                sendFarmData(p);

                if(eventTeam == null) {
                    eventTeam = board.registerNewTeam("eventTeam");
                    if(Main.currentEvent == null) {
                        eventTeam.setPrefix("§7»§a Kein Event");
                    }else{
                        eventTeam.setPrefix("§7»§a " + Main.currentEvent.getEventType().toString());
                    }                    eventTeam.addEntry("§8");
                    obj.getScore("§8").setScore(7);
                }else{
                    if(Main.currentEvent == null) {
                        eventTeam.setPrefix("§7»§a Kein Event");
                    }else{
                        eventTeam.setPrefix("§7»§a " + Main.currentEvent.getEventType().toString());
                    }                     }

            }
        }, 0, 200);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public static void sendFarmData(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF("ALL");
        p.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
    }

}
