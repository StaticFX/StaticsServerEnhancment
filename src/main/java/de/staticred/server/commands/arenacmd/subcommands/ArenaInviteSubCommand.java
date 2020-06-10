package de.staticred.server.commands.arenacmd.subcommands;

import de.staticred.server.util.ArenaFileManager;
import de.staticred.server.util.ArenaManager;
import org.bukkit.entity.Player;

public class ArenaInviteSubCommand {

    public ArenaInviteSubCommand(Player executor, String[] args) {
        execute(executor,args);
    }

    public void execute(Player executor, String[] args) {

        if(!executor.hasPermission("sts.cmd.arena.invite")) {
            executor.sendMessage("§cKeine Rechte");
            return;
        }
        //arena invite <player> <money> <player>
        if(args.length != 4) {
            executor.sendMessage("§a/arena <invite> <arena> <money> <player>");
            return;
        }

        String arena = args[0];

        if(!ArenaFileManager.getInstance().isArena(arena)) {
            executor.sendMessage("§cDiese Arena existiert nicht!");
            return;
        }


        if(!ArenaManager.isRegistered(arena)) {
            executor.sendMessage("§cDiese Arena ist noch nicht fertig. Bitte warte bis die Arena vom Admin fertiggestellt wurde...");
            return;
        }

        if(ArenaManager.isArenaBeingPlayed(arena)) {
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



    }



}
