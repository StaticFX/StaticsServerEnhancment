package de.staticred.server.commands.arenacmd.subcommands;

import de.staticred.server.objects.Arena;
import de.staticred.server.util.ArenaFileManager;
import de.staticred.server.util.ArenaManager;
import org.bukkit.entity.Player;

public class ArenaCreateSubCommand {

    public ArenaCreateSubCommand(Player executor, String[] args) {
        execute(executor,args);
    }

    public void execute(Player executor, String[] args) {

        if(!executor.hasPermission("sts.cmd.arena.create")) {
            executor.sendMessage("§cKeine Rechte!");
            return;
        }


        //arena create <name>

        if(args.length != 2) {
            executor.sendMessage("§a/arena <create> <name>");
            return;
        }

        String name = args[1];

        if(ArenaFileManager.getInstance().isArena(name)) {
            executor.sendMessage("§cDieser Arenaname existiert schon.");
            return;
        }


        if(ArenaManager.isCreatingArena(executor)) {
            executor.sendMessage("§cDu erstellst im moment eine Arena. Scheibe §4§lCANCEL §rin den Chat um den Vorgang abzubrechen.");
            return;
        }

        executor.sendMessage("§aDu erstellst nun eine Arena. Um die Spawnlocations zu setzten schreibe einfach §a§lSpawn1 §r§aund §a§lSpawn2 §r§ain den Chat");
        executor.sendMessage("§aNutzte §4§lCANCEL §aum den Erstell-Modus zu verlassen.");
        executor.sendMessage("§aNutzte §a§lFINISH §aum die Arena zu erstellen.");
        ArenaManager.addCreatingArena(executor,new Arena(name));
    }

}
