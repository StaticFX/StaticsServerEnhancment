package de.staticred.server.commands;

import de.staticred.server.Main;
import de.staticred.server.db.HeadDAO;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.SQLException;
import java.time.Duration;

public class HeadCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to execute this command.");
            return false;
        }

        Player p = (Player) commandSender;

        if(!p.hasPermission("sts.head")) {
            p.sendMessage("§cKeine Rechte!");
            return false;
        }

        if(args.length != 1) {
            p.sendMessage("§cUse: /head [name]");
            return false;
        }

        String name = args[0];


        if(Main.api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup().equalsIgnoreCase("agent")) {
            try {
                if(HeadDAO.getInstance().gaveHead(p.getUniqueId())) {
                    if(HeadDAO.getInstance().getTimeStamp(p.getUniqueId())  + Duration.ofDays(7).toMillis() > System.currentTimeMillis()) {
                        p.sendMessage("§cBitte warte 2 wochen bis du dir einen Kopf geben kannst.");
                        return false;
                    }else{
                        HeadDAO.getInstance().removeHeadDate(p.getUniqueId());
                        HeadDAO.getInstance().addHeadDate(p.getUniqueId());
                        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
                        SkullMeta meta = (SkullMeta) skull.getItemMeta();
                        meta.setOwner(name);
                        meta.setDisplayName(name);
                        skull.setItemMeta(meta);
                        p.getInventory().addItem(skull);
                        p.sendMessage("§aDu hast dir einen Kopf gegeben.");
                        return true;
                    }
                }else{
                    HeadDAO.getInstance().addHeadDate(p.getUniqueId());
                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setOwner(name);
                    meta.setDisplayName(name);
                    skull.setItemMeta(meta);
                    p.getInventory().addItem(skull);
                    p.sendMessage("§aDu hast dir einen Kopf gegeben.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }





        }else if(Main.api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup().equalsIgnoreCase("ziemlich") || Main.api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup().equalsIgnoreCase("newziemlich") || p.hasPermission("sts.softteam")) {
            try {
                if (HeadDAO.getInstance().gaveHead(p.getUniqueId())) {
                    if (HeadDAO.getInstance().getTimeStamp(p.getUniqueId()) + Duration.ofDays(7).toMillis() > System.currentTimeMillis()) {
                        p.sendMessage("§cBitte warte 1 wochen bis du dir einen Kopf geben kannst.");
                        return false;
                    } else {
                        HeadDAO.getInstance().removeHeadDate(p.getUniqueId());
                        HeadDAO.getInstance().addHeadDate(p.getUniqueId());
                        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
                        SkullMeta meta = (SkullMeta) skull.getItemMeta();
                        meta.setOwner(name);
                        meta.setDisplayName(name);
                        skull.setItemMeta(meta);
                        p.getInventory().addItem(skull);
                        p.sendMessage("§aDu hast dir einen Kopf gegeben.");
                        return true;
                    }
                } else {
                    HeadDAO.getInstance().addHeadDate(p.getUniqueId());
                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setOwner(name);
                    meta.setDisplayName(name);
                    skull.setItemMeta(meta);
                    p.getInventory().addItem(skull);
                    p.sendMessage("§aDu hast dir einen Kopf gegeben.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }
        return false;
    }
}
