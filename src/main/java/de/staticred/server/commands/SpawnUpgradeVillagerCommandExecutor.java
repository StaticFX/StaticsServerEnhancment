package de.staticred.server.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class SpawnUpgradeVillagerCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou must be a player!");
            return false;
        }

        Player p = (Player) commandSender;

        if(!p.hasPermission("sts.cmd.spawnvillager")) {
            p.sendMessage("§cKeine Rechte");
            return false;
        }

        Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
        v.setCustomName("§c§lPlot-Upgrades");
        v.setAI(false);
        p.sendMessage("Villager created!");

        return false;
    }
}
