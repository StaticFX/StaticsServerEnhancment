package de.staticred.server.commands.arenacmd.subcommands;

import de.staticred.server.util.ArenaFileManager;
import de.staticred.server.util.ArenaManager;
import org.bukkit.entity.Player;

public class ArenaDeleteSubCommand {


    public ArenaDeleteSubCommand(Player executor, String[] args) {
        execute(executor,args);
    }

    public void execute(Player executor, String[] args) {
        if(!executor.hasPermission("sts.cmd.arena.delete")) {
            executor.sendMessage("§cKeine Rechte!");
            return;
        }

        //arena create <name>
        if(args.length != 2) {
            executor.sendMessage("§a/arena <delete> <name>");
            return;
        }

        String name = args[1];

        if(!ArenaFileManager.getInstance().isArena(name)) {
            executor.sendMessage("§cDieser Arenaname existiert nicht.");
            return;
        }

        if(ArenaManager.isArenaBeingPlayed(name)) {
            executor.sendMessage("§cDie Arena wird gerade bespielt. Sie kann nicht gelöscht werden.");
            return;
        }

        ArenaManager.deleteArena(name);
        executor.sendMessage("§aDie Arena wurde gelöscht.");
    }

}
