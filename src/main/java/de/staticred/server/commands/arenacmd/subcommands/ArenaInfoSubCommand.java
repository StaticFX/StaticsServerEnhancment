package de.staticred.server.commands.arenacmd.subcommands;

import de.staticred.server.util.ArenaManager;
import org.bukkit.entity.Player;

public class ArenaInfoSubCommand {

    public ArenaInfoSubCommand(Player executor, String[] args) {
        execute(executor,args);
    }

    public void execute(Player executor, String[] args) {

        if(!executor.hasPermission("sts.cmd.arena.info")) {
            executor.sendMessage("§cKeine Rechte!");
            return;
        }

        //arena create <name>
        if(args.length != 2) {
            executor.sendMessage("§a/arena <info> <name>");
            return;
        }

        String name = args[1];

        executor.sendMessage("§7---§a§lInfos über " + name + "§7---");
        executor.sendMessage("§aBespielt: " + ArenaManager.isArenaBeingPlayed(name));
        if(ArenaManager.getArena(name).getSpawnLocation1() != null) {
            executor.sendMessage("§aLocations gesetzt: True");
        }else{
            executor.sendMessage("§aLocations gesetzt: False");
        }



    }

}
