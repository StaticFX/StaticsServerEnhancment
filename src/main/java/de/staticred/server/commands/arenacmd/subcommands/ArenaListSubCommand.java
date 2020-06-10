package de.staticred.server.commands.arenacmd.subcommands;

import de.staticred.server.objects.Arena;
import de.staticred.server.util.ArenaManager;
import org.bukkit.entity.Player;

public class ArenaListSubCommand {

    public ArenaListSubCommand(Player executor, String[] args) {
        execute(executor,args);
    }

    public void execute(Player executor, String[] args) {
        if(!executor.hasPermission("sts.cmd.arena.list")) {
            executor.sendMessage("§cKeine Rechte!");
            return;
        }

        //arena create <name>
        if(args.length != 1) {
            executor.sendMessage("§a/arena <list>");
            return;
        }

        executor.sendMessage("§7---§a§lArena by Static§7---");
        for(Arena arena : ArenaManager.getAllArenas()) {
            if(ArenaManager.isArenaBeingPlayed(arena.getArenaID())) {
                executor.sendMessage("§7 - §a" + arena.getArenaID() + " §7(§4Spielt§7)");
            }else if(ArenaManager.hasPlayer1Joined(arena.getArenaID())) {
                executor.sendMessage("§7 - §a" + arena.getArenaID() + " §7(§cWartet§7)");
            }else{
                executor.sendMessage("§7 - §a" + arena.getArenaID() + " §7(§8Leer§7)");
            }
        }

    }

}
