package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.EventType;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EntityDeathEvent implements Listener {

    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent e) {
        if(Main.currentEvent != null && Main.currentEvent.getEventType() == EventType.MOB_DROPRATE) {

             e.getDrops().clear();

             LootTable lt = ((Lootable) e.getEntity()).getLootTable();
             LootContext.Builder bd = new LootContext.Builder(e.getEntity().getLocation());



             bd = bd.luck(100);
             bd = bd.lootingModifier(100);
             bd = bd.lootedEntity(e.getEntity());
             bd = bd.killer(e.getEntity().getKiller());
             LootContext lc = bd.build();

             System.out.println(lc.getLootingModifier());

             Collection<ItemStack> items = lt.populateLoot(ThreadLocalRandom.current() ,lc);

             for(ItemStack item : items) {
                 e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(),item);
             }
        }
    }
}
