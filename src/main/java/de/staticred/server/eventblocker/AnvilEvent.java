package de.staticred.server.eventblocker;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AnvilEvent implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {

        ItemStack item1 = e.getInventory().getFirstItem();
        ItemStack item2 = e.getInventory().getSecondItem();
        ItemStack item3 = e.getInventory().getResult();

        HashMap<String, Integer> enchantNewLevelMax = new HashMap<>();

        enchantNewLevelMax.put("protection",10);
        enchantNewLevelMax.put("fire_protection",10);
        enchantNewLevelMax.put("feather_falling",10);
        enchantNewLevelMax.put("blast_protection",10);
        enchantNewLevelMax.put("projectile_protection",10);
        enchantNewLevelMax.put("thorns",5);
        enchantNewLevelMax.put("depth_strider",10);
        enchantNewLevelMax.put("sharpness",10);
        enchantNewLevelMax.put("smite",10);
        enchantNewLevelMax.put("bane_of_arthropods",10);
        enchantNewLevelMax.put("knockback",5);
        enchantNewLevelMax.put("fire_aspect",3);
        enchantNewLevelMax.put("looting",5);
        enchantNewLevelMax.put("sweeping",5);
        enchantNewLevelMax.put("efficiency",10);
        enchantNewLevelMax.put("unbreaking",10);
        enchantNewLevelMax.put("power",10);
        enchantNewLevelMax.put("punch",10);
        enchantNewLevelMax.put("luck_of_the_sea",5);
        enchantNewLevelMax.put("loyalty",5);
        enchantNewLevelMax.put("impaling",10);
        enchantNewLevelMax.put("riptide",5);
        enchantNewLevelMax.put("quick_charge",4);
        enchantNewLevelMax.put("piercing",5);
        enchantNewLevelMax.put("multishot",5);
        enchantNewLevelMax.put("respiration",10);


        if(item1 == null) return;
        if(item2 == null) return;
        if(item3 == null) return;

        e.getInventory().setMaximumRepairCost(250);

        if(item1.getEnchantments().size() == 0 && item2.getEnchantments().size() == 0) return;

        if(item1.getEnchantments().size() == 0 && item1.getType() != Material.ENCHANTED_BOOK) {
            item3.addUnsafeEnchantments(item2.getEnchantments());
            e.getInventory().setResult(item3);
            e.setResult(item3);
            return;
        }

        if(item2.getEnchantments().size() == 0 && item2.getType() != Material.ENCHANTED_BOOK)  {
            item3.addUnsafeEnchantments(item1.getEnchantments());
            e.getInventory().setResult(item3);
            e.setResult(item3);
            return;
        }


        if(item2.getEnchantments().size() == 0 && item2.getType() != Material.ENCHANTED_BOOK) return;

        for(Enchantment enchant : item1.getEnchantments().keySet()) {

            Set<Enchantment> enchants;
            if(item2.getType() == Material.ENCHANTED_BOOK) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item2.getItemMeta();
                enchants = meta.getStoredEnchants().keySet();
            }else{
                enchants = item2.getEnchantments().keySet();
            }
            for(Enchantment enchant2 : enchants) {
                if(enchant.getName().equals(enchant2.getName())) {
                    int level1 = item1.getEnchantmentLevel(enchant);
                    int level2;

                    if(item2.getType() == Material.ENCHANTED_BOOK) {
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item2.getItemMeta();
                        level2 = meta.getStoredEnchantLevel(enchant2);
                    }else{
                        level2 = item1.getEnchantmentLevel(enchant2);
                    }

                    System.out.println(level1);
                    System.out.println(level2);

                    int level3;


                    if(level1 != level2) {
                        if(level1 > level2) {
                            level3 = level1;
                        }else{
                            level3 = level2;
                        }
                        item3.addUnsafeEnchantment(enchant,level3);
                        e.getInventory().setResult(item3);
                        e.setResult(item3);
                        return;
                    }

                    for(String register : enchantNewLevelMax.keySet()) {
                        Enchantment registeredEnchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(register));
                        if(enchant.getName().equalsIgnoreCase(registeredEnchantment.getName())) {
                            if(level1 < enchantNewLevelMax.get(register)) {
                                level3 = level1 + 1;
                                item3.addUnsafeEnchantment(enchant,level3);
                                e.setResult(item3);
                                e.getInventory().setResult(item3);
                            }else{
                                level3 = enchantNewLevelMax.get(register);
                                item3.addUnsafeEnchantment(enchant,level3);
                                e.setResult(item3);
                                e.getInventory().setResult(item3);
                            }
                        }
                    }
                }
            }
        }
    }
}
