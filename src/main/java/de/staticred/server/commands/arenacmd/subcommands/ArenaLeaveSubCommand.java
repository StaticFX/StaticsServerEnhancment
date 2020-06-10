package de.staticred.server.commands.arenacmd.subcommands;

import de.staticred.server.objects.Arena;
import de.staticred.server.util.ArenaManager;
import org.bukkit.entity.Player;

public class ArenaLeaveSubCommand {

    public ArenaLeaveSubCommand(Player executor, String[] args) {
        execute(executor,args);
    }

    public void execute(Player executor, String[] args) {
        if(!executor.hasPermission("sts.cmd.arena.leave")) {
            executor.sendMessage("§cKeine Rechte!");
            return;
        }

        //arena create <name>
        if(args.length != 1) {
            executor.sendMessage("§a/arena <leave>");
            return;
        }


        if(!ArenaManager.isPlayerQueueing(executor)) {
            executor.sendMessage("§cDu sucht im Moment nach keiner Arena.");
            return;
        }

        if(ArenaManager.fightingPlayers.contains(executor)|| ArenaManager.waitingPlayers.contains(executor)) {
            executor.sendMessage("§cDu kannst die Arena nicht im Kampf verlassen.");
            return;
        }

        ArenaManager.stopQueuing(executor);
        executor.sendMessage("§aDu hast die Warteschlange verlassen.");



    }

}
