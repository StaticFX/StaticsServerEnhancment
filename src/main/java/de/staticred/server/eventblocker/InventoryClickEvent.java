package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.objects.Perks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void onInventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();


        //Player holder = (Player) e.getInventory().getHolder();

        //if(holder.getUniqueId() != p.getUniqueId()) return;

        if(e.getInventory().getSize() != 4*9) return;

        if(p.getOpenInventory().getTitle().equalsIgnoreCase("§cPerks")) {
            e.setCancelled(true);

            if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

            String item = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);

            for(Perks perk : Perks.values()) {
                if(perk.toString().equalsIgnoreCase(item)) {
                    if(Main.enabledPerks.get(p).contains(perk)) {
                        Main.enabledPerks.get(p).remove(perk);
                        executePerkChange(p,perk,false);
                        openInv(p);
                    }else {
                        Main.enabledPerks.get(p).add(perk);
                        executePerkChange(p,perk,true);
                        openInv(p);
                    }
                }
            }
        }
    }

    public void executePerkChange(Player p, Perks perk, boolean activation) {
        if(perk == Perks.FLY_PERK) {
            if(activation) p.setAllowFlight(true);
            if(!activation) p.setAllowFlight(false);
        }
        if(perk == Perks.FAST_DESTROY_PERK) {
            if(activation) p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999, 2,true,false));
            if(!activation) p.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
        if(perk == Perks.SPEED_PERK) {
            if(activation) p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1,true,false));
            if(!activation) p.removePotionEffect(PotionEffectType.SPEED);
        }
        if(perk == Perks.NIGHT_VISION_PERK) {
            if(activation) p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 1,true,false));
            if(!activation) p.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public void openInv(Player p) {
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
