package de.staticred.server.chestsystem.commands.subcommands;

import de.staticred.server.chestsystem.db.ChestDAO;
import de.staticred.server.chestsystem.util.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ChestInfoSubCommand {

    public ChestInfoSubCommand(CommandSender sender, String[] args) {
        execute(sender,args);
    }

    public void execute(CommandSender sender, String[] args) {

        if(sender.hasPermission("sts.cmd.chest.info")) {
            sender.sendMessage("§cKeine Rechte!");
            return;
        }

        if(args.length != 2) {
            sender.sendMessage("§cUse /chest <info> <player>");
            return;
        }

        String playerName = args[1];

        Player target = Bukkit.getPlayer(playerName);

        if(target == null) {
            sender.sendMessage("§cPlayer could not be found!");
            return;
        }

        sender.sendMessage("§8----§cChests from §e" + target.getName() + "§8----");
        try {
            sender.sendMessage("§7Common: §a" + ChestDAO.getInstance().getChestAmount(target.getUniqueId(), Rarity.COMMON));
            sender.sendMessage("§5Epic: §a" + ChestDAO.getInstance().getChestAmount(target.getUniqueId(), Rarity.EPIC));
            sender.sendMessage("§6Legendary: §a" + ChestDAO.getInstance().getChestAmount(target.getUniqueId(), Rarity.LEGENDARY));
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage("§cInternal Exception");
            return;
        }
        sender.sendMessage("§8----§cChestSystem by Static§8----");
    }

}
