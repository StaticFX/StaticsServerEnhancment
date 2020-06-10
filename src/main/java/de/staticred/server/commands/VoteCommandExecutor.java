package de.staticred.server.commands;

import de.staticred.server.Main;
import de.staticred.server.db.VoteDAO;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;

public class VoteCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You need to be a player to execute this command.");
            return false;
        }

        Player p = (Player) commandSender;

        if(args.length < 3) {
            p.sendMessage("§cUse /vote [BAN/MUTE] [PLAYER] [Reason...]");
            return false;
        }

        if(args[0].equalsIgnoreCase("ban")) {
            if(!p.hasPermission("vote.ban")) {
                p.sendMessage("§cKeine rechte!");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if(target == null) {
                p.sendMessage("Bitte nutzte einen validen Spieler.");
                return false;
            }

            if(target.hasPermission("sts.softteam")) {
                p.sendMessage("Du kannst nicht für ein Teammitglied voten.");
                return false;
            }

            if(target.getUniqueId() == p.getUniqueId()) {
                p.sendMessage("Du kannst nicht für dich selbst voten.");
                return false;
            }

            String reason = "";

            for(int i = 2; i < args.length; i++) {
                reason = reason + args[i] + " ";
            }

            if(Main.vote) {
                p.sendMessage("Es läuft bereits eine Abstimmung.");
                return false;
            }


            try {
                if(VoteDAO.getInstance().hasVote(p.getUniqueId()) && !p.hasPermission("sts.infinityVote")) {
                    if(VoteDAO.getInstance().getTimeStamp(p.getUniqueId(),true) > VoteDAO.getInstance().getTimeStamp(p.getUniqueId(),true) + Duration.ofDays(1).toMillis()) {
                        VoteDAO.getInstance().removeVote(p.getUniqueId());
                        VoteDAO.getInstance().addVote(p.getUniqueId(), System.currentTimeMillis(),true);
                        Main.vote = true;
                        sendVoteMessage(true,p,target,reason);
                        Main.ban = true;
                        return true;
                    }else{
                        p.sendMessage("§cDu hast schon abgestimmt. Bitte warte mind. 1 Tag.");
                        return false;
                    }
                }else{
                    VoteDAO.getInstance().addVote(p.getUniqueId(), System.currentTimeMillis(),true);
                    Main.vote = true;
                    Main.ban = true;
                    sendVoteMessage(true,p,target,reason);
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                p.sendMessage("Internal Error. Please contact an admin.");
                return false;
            }
        }


        if(args[0].equalsIgnoreCase("mute")) {
            if(!p.hasPermission("vote.mute")) {
                p.sendMessage("§cI´m sorry, but you don´t have the permission to execute this command");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if(target == null) {
                p.sendMessage("Bitte nutzte einen validen Spieler.");
                return false;
            }

            if(target.hasPermission("sts.softteam")) {
                p.sendMessage("Du kannst nicht für ein Teammitglied voten.");
                return false;
            }

            if(target.getUniqueId() == p.getUniqueId()) {
                p.sendMessage("Du kannst nicht für dich selbst voten.");
                return false;
            }

            String reason = "";

            for(int i = 2; i < args.length; i++) {
                reason = reason + args[i] + " ";
            }

            if(Main.vote) {
                p.sendMessage("Es läuft bereits eine Abstimmung.");
                return false;
            }

            try {
                if(VoteDAO.getInstance().hasVote(p.getUniqueId()) && !p.hasPermission("sts.infinityVote")) {
                    if(VoteDAO.getInstance().getTimeStamp(p.getUniqueId(),false) > VoteDAO.getInstance().getTimeStamp(p.getUniqueId(),false)) {
                        VoteDAO.getInstance().removeVote(p.getUniqueId());
                        VoteDAO.getInstance().addVote(p.getUniqueId(), System.currentTimeMillis(),false);
                        Main.vote = true;
                        sendVoteMessage(false,p,target,reason);
                        Main.ban = false;
                        return true;
                    }else{
                        p.sendMessage("§cDu hast schon abgestimmt. Bitte warte noch einen Tag.");
                        return false;
                    }
                }else{
                    VoteDAO.getInstance().addVote(p.getUniqueId(), System.currentTimeMillis(),false);
                    Main.vote = true;
                    Main.ban = false;
                    sendVoteMessage(false,p,target,reason);
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                p.sendMessage("Internal Error. Please contact an admin.");
                return false;
            }
        }

        p.sendMessage("§cUse /vote [BAN/MUTE] [PLAYER] [Reason...]");
        return false;
    }

    public void sendVoteMessage(boolean ban, Player p, Player target, String reason) {
        Main.voteYes = 0;
        Main.voteNo = 0;
        Main.canVote = true;
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("§8§l-----§e§lVOTE§8§l-----\n ");
            player.sendMessage("§eErsteller: §a" + p.getName());
            player.sendMessage("§eAngeklagter: §c" + target.getName());
            player.sendMessage("§eGrund: §a" + reason);
            if(ban) {
                player.sendMessage("§eArt: §aBan");
            }else{
                player.sendMessage("§eArt: §aMute");
            }
            player.sendMessage(" ");
            net.md_5.bungee.api.chat.TextComponent textC = new net.md_5.bungee.api.chat.TextComponent();
            textC.setText("§a§lJA");
            textC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aFür 'ja' stimmen").create()));
            textC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/v 1"));
            net.md_5.bungee.api.chat.TextComponent tc2 = new net.md_5.bungee.api.chat.TextComponent();
            tc2.setText("§c§lNEIN");
            tc2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cFür 'nein' stimmen").create()));
            tc2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/v 0"));
            net.md_5.bungee.api.chat.TextComponent tc3 = new net.md_5.bungee.api.chat.TextComponent(" §7| ");
            textC.addExtra(tc3);
            textC.addExtra(tc2);
            player.spigot().sendMessage(textC);
            player.sendMessage(" \n§8§l-----§e§lVOTE§8§l-----");
        }


        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {

                if(!p.isOnline()) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("§cDer Spieler, der die Abstimmung gestartet hat, ist Offline gegangen. §c§lDie Abstimmung wurde abgebrochen.");
                    }

                    Main.voteYes = 0;
                    Main.voteNo = 0;
                    Main.canVote = false;
                    Main.vote = false;
                    Main.voted.clear();
                    return;
                }

                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage("§8§l-----§e§lVOTE§8§l-----\n ");
                    player.sendMessage("§cDie Abstimmung ist vorbei.");
                    player.sendMessage("§cErgebnis: §aJa: " + Main.voteYes + " §cNein: " + Main.voteNo);

                    if(Main.voteYes > Main.voteNo) {
                        player.sendMessage("§aEs wurde für §lJa §r§agestimmt, der Spieler wird nun für 30m bestraft.");
                    }else{
                        player.sendMessage("§aEs wurde für §cNein §agestimmt, der Spieler wird nicht bestraft.");
                    }
                    player.sendMessage(" \n§8§l-----§e§lVOTE§8§l-----");
                }

                if(Main.voteYes > Main.voteNo) {
                    if(Main.ban) {
                        Main.sendMessageToBungee(p,"c:bungeecord", "ban", target.getName(), reason, p.getName(), "30min");
                        target.kickPlayer("Voted out");
                    }else{
                        Main.sendMessageToBungee(p,"c:bungeecord", "mute", target.getName(), reason, p.getName(), "1h");
                    }
                }

                Main.voteYes = 0;
                Main.voteNo = 0;
                Main.canVote = false;
                Main.vote = false;
                Main.voted.clear();
            }
        },30*20);
    }

}
