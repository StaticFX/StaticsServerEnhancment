package de.staticred.server.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlotUpgradeManager {

    public static Inventory getInv(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "§6§lJoe");


        if(p.hasPermission("plots.plot.3")) {
            inv.setItem(0,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(0,itemBuilder("§aLevel 3 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 20000 Dukaten"))));

            for(int i = 1; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(0,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        if(p.hasPermission("plots.plot.4")) {
            inv.setItem(1,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(1,itemBuilder("§aLevel 4 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 30000 Dukaten"))));

            for(int i = 2; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }
        inv.setItem(1,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        if(p.hasPermission("plots.plot.5")) {
            inv.setItem(2,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(2,itemBuilder("§aLevel 5 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 40000 Dukaten"))));

            for(int i = 3; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(2,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        if(p.hasPermission("plots.plot.6")) {
            inv.setItem(3,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(3,itemBuilder("§aLevel 6 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 50000 Dukaten"))));

            for(int i = 4; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(3,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));

        if(p.hasPermission("plots.plot.7")) {
            inv.setItem(4,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(4,itemBuilder("§aLevel 7 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 60000 Dukaten"))));

            for(int i = 5; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(4,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        if(p.hasPermission("plots.plot.8")) {
            inv.setItem(5,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(5,itemBuilder("§aLevel 8 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 70000 Dukaten"))));

            for(int i = 6; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(5,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        if(p.hasPermission("plots.plot.9")) {
            inv.setItem(6,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(6,itemBuilder("§aLevel 9 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 80000 Dukaten"))));

            for(int i = 7; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(6,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        if(p.hasPermission("plots.plot.10")) {
            inv.setItem(7,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(7,itemBuilder("§aLevel 10 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 90000 Dukaten"))));

            for(int i = 8; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(7,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));

        if(p.hasPermission("plots.plot.11")) {
            inv.setItem(8,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(8,itemBuilder("§aLevel 11 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 100000 Dukaten"))));

            for(int i = 9; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }
            return inv;
        }

        inv.setItem(8,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));

        if(p.hasPermission("plots.plot.12")) {
            inv.setItem(9,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(9,itemBuilder("§aLevel 12 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 110000 Dukaten"))));

            for(int i = 10; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(9,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));

        if(p.hasPermission("plots.plot.13")) {
            inv.setItem(10,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(10,itemBuilder("§aLevel 13 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 120000 Dukaten"))));

            for(int i = 11; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level schon erreicht",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(10,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));

        if(p.hasPermission("plots.plot.14")) {
            inv.setItem(11,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(11,itemBuilder("§aLevel 14 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 130000 Dukaten"))));

            for(int i = 12; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }

            return inv;
        }

        inv.setItem(11,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        if(p.hasPermission("plots.plot.15")) {
            inv.setItem(12,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(12,itemBuilder("§aLevel 15 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 140000 Dukaten"))));

            for(int i = 13; i < 14; i++) {
                inv.setItem(i,itemBuilder("§cDu hast dieses Level noch nicht erreicht",Material.BARRIER,new ArrayList<>(Arrays.asList(""))));
            }


            return inv;
        }

        inv.setItem(12,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        if(p.hasPermission("plots.plot.16")) {
            inv.setItem(13,itemBuilder("§cDu hast dieses Level schon erreicht!", Material.LIME_DYE,new ArrayList<>()));
        }else{
            inv.setItem(13,itemBuilder("§aLevel 16 kaufen",Material.PAPER,new ArrayList<>(Arrays.asList("§7- §eDieses Upgrade kostet 150000 Dukaten"))));

            return inv;
        }

        inv.setItem(13,itemBuilder("§cDu hast dieses Level schon erreicht!",Material.RED_DYE,new ArrayList<>(Arrays.asList(""))));


        return inv;
    }

    public static ItemStack itemBuilder(String displayName, Material m, List<String> lore) {
        ItemStack itemStack = new ItemStack(m, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
