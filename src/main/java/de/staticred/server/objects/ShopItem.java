package de.staticred.server.objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopItem {
    private String name;
    private int buyPrice;
    private int sellPrice;
    private int amount;
    private ItemStack item;
    private Material mat;

    public ShopItem(Material mat, String name, int buyPrice, int sellPrice, int amount)
    {
        this.name = name;

        this.buyPrice = Integer.parseInt(Long.toString(Math.round(buyPrice * 1.5)));
        this.sellPrice = 0;
        this.amount = amount;
        this.mat = mat;

        List<String> lore = new ArrayList();

        lore.add(ChatColor.DARK_PURPLE + "BUY: " + ChatColor.YELLOW + this.buyPrice);

        this.item = new ItemStack(mat, amount);
        ItemMeta itemMeta = this.item.getItemMeta();

        if(mat == Material.BLACK_STAINED_GLASS_PANE) {
            itemMeta.setDisplayName("§8´");
        }else{
            itemMeta.setDisplayName(name);
        }
        itemMeta.setLore(lore);
        this.item.setItemMeta(itemMeta);
    }

    public String getName()
    {
        return this.name;
    }

    public int getBuyPrice()
    {
        return this.buyPrice;
    }

    public int getSellPrice()
    {
        return this.sellPrice;
    }

    public int getAmount()
    {
        return this.amount;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public Material getMaterial()
    {
        return this.mat;
    }
}
