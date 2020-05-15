package de.staticred.server.commands;

import de.staticred.server.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String string, String[] args) {

        if(!(s instanceof Player)) {
            s.sendMessage("§cDu musst ein Spieler sein");
            return false;
        }
        Player p = ((Player) s);

        if(args.length != 0) {
            p.sendMessage("§cUse: /shop");
            return false;
        }

        if(!p.hasPermission("sts.shop")) {
            p.sendMessage("§cDu hast keine Rechte für diesen Befehl.");
            return false;
        }


        Main.getShop().openShop(p);
        return false;
    }
}
