package de.staticred.server.commands;

import de.staticred.server.Main;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.Perks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;

public class PerksCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to execute this command!");
            return false;
        }

        Player p = (Player) commandSender;

        Inventory perkInventory = Bukkit.createInventory(p,4*9,"§cPerks");

        try {
            perkInventory.setItem(0,getItemStack(Material.FEATHER,"§bFly Perk"));
            perkInventory.setItem(1,generatePerkItem(p,Perks.FLY_PERK));
            perkInventory.setItem(4,getItemStack(Material.DIAMOND_BOOTS,"§bSpeed Perk"));
            perkInventory.setItem(5,generatePerkItem(p,Perks.SPEED_PERK));
            perkInventory.setItem(9,getItemStack(Material.CHEST,"§bKeep inventory Perk"));
            perkInventory.setItem(10,generatePerkItem(p,Perks.KEEP_INVENTORY_PERK));
            perkInventory.setItem(13,getItemStack(Material.DIAMOND_PICKAXE,"§bFast Destroy Perk"));
            perkInventory.setItem(14,generatePerkItem(p,Perks.FAST_DESTROY_PERK));
            perkInventory.setItem(18,getItemStack(Material.EXPERIENCE_BOTTLE,"§bKeep XP Perk"));
            perkInventory.setItem(19,generatePerkItem(p,Perks.KEEP_XP_PERK));
            perkInventory.setItem(22,getItemStack(Material.ENDER_EYE,"§bNightvision Perk"));
            perkInventory.setItem(23,generatePerkItem(p,Perks.NIGHT_VISION_PERK));
            perkInventory.setItem(27,getItemStack(Material.COOKED_BEEF,"§bAnti Hunger Perk"));
            perkInventory.setItem(28,generatePerkItem(p,Perks.ANTI_HUNGER));
            perkInventory.setItem(31,getItemStack(Material.EXPERIENCE_BOTTLE,"§bDoppel XP Perk"));
            perkInventory.setItem(32,generatePerkItem(p,Perks.DOUBLE_XP_PERK));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        p.openInventory(perkInventory);

        return false;
    }


    public ItemStack getItemStack(Material m, String name) {
        ItemStack itemStack = new ItemStack(m,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack generatePerkItem(Player p, Perks perk) throws SQLException {
        if(!PerkDAO.getInstance().hasPerk(p.getUniqueId(),perk)) return getItemStack(Material.BARRIER,"§cYou don´t have this perk");
        if(Main.enabledPerks.get(p).contains(perk)) return getItemStack(Material.LIME_DYE,"§a" + perk.toString());
        return getItemStack(Material.RED_DYE,"§c" + perk.toString());
    }

}
