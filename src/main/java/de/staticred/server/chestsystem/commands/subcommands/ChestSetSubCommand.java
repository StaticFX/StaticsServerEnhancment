package de.staticred.server.chestsystem.commands.subcommands;

import de.staticred.server.chestsystem.db.ChestDAO;
import de.staticred.server.chestsystem.util.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ChestSetSubCommand {

    public ChestSetSubCommand(CommandSender sender, String[] args) {
        execute(sender,args);
    }

    public void execute(CommandSender sender, String[] args) {

        if(sender.hasPermission("sts.cmd.chest.set")) {
            sender.sendMessage("§cKeine Rechte!");
            return;
        }

        if(args.length != 4) {
            sender.sendMessage("§cUse /chest <set> <player> <rarity> <amount>");
            return;
        }

        String playerName = args[1];

        Player target = Bukkit.getPlayer(playerName);

        if(target == null) {
            sender.sendMessage("§cPlayer could not be found!");
            return;
        }

        String rarityIn = args[2].toUpperCase();

        Rarity rarity = Rarity.valueOf(rarityIn);

        if(rarity == null) {
            sender.sendMessage("§cUse for rarity common/epic/legendary");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[3]);
        }catch (Exception e) {
            sender.sendMessage("§cPlease use a number as amount!");
            return;
        }

        try {
            ChestDAO.getInstance().setChestToUser(target.getUniqueId(),rarity,amount);
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage("§cInternal Error!");
            return;
        }

        sender.sendMessage("§aSuccessfully set Chest §6" + rarityIn + " from §c" + target.getName() + " to §e" + amount);
    }

}
