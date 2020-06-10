package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlotInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(p.getOpenInventory().getTitle().equalsIgnoreCase("§6§lJoe")) {

            Economy eco = Main.eco;

            e.setCancelled(true);
            if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

            String item = e.getCurrentItem().getItemMeta().getDisplayName();

            if(item.equalsIgnoreCase("§aLevel 3 kaufen")) {
                if(eco.getBalance(p) > 20000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.3");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    eco.withdrawPlayer(p,20000);
                    p.closeInventory();
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 4 kaufen")) {
                if(eco.getBalance(p) > 30000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.4");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,30000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 5 kaufen")) {
                if(eco.getBalance(p) > 40000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.5");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,40000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 6 kaufen")) {
                if(eco.getBalance(p) > 50000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.6");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,50000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 7 kaufen")) {
                if(eco.getBalance(p) > 60000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.7");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,60000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 8 kaufen")) {
                if(eco.getBalance(p) > 70000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.8");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,70000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 9 kaufen")) {
                if(eco.getBalance(p) > 80000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.9");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,80000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 10 kaufen")) {
                if(eco.getBalance(p) > 90000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.10");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,90000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 11 kaufen")) {
                if(eco.getBalance(p) > 100000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.11");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,100000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 12 kaufen")) {
                if(eco.getBalance(p) > 110000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.12");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,110000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 13 kaufen")) {
                if(eco.getBalance(p) > 120000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.13");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,120000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 14 kaufen")) {
                if(eco.getBalance(p) > 130000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.14");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,130000);
                    return;
                }
            }

            if(item.equalsIgnoreCase("§aLevel 15 kaufen")) {
                if(eco.getBalance(p) > 140000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.15");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,140000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

            if(item.equalsIgnoreCase("§aLevel 16 kaufen")) {
                if(eco.getBalance(p) > 150000) {
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set plots.plot.16");
                    p.sendMessage("§aDu hast erfolgreich das nächste Level gekauft.");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
                    p.closeInventory();
                    eco.withdrawPlayer(p,150000);
                    return;
                }else{
                    p.sendMessage("§cDu hast nicht genügend Geld.");
                }
            }

        }


        }

}
