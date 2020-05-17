package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.objects.EventType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import java.util.Collection;
import java.util.Random;

public class EntityDeathEvent implements Listener {

    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent e) {
        if(Main.currentEvent != null && Main.currentEvent.getEventType() == EventType.DOUBLE_XP) {
            e.getDrops().clear();

            LootTable loottable = Bukkit.getLootTable(e.getEntityType().getKey());
            LootContext.Builder bd = new LootContext.Builder(e.getEntity().getLocation());

            bd.lootedEntity(e.getEntity());
            bd.lootingModifier(3);
            LootContext lc = bd.build();

            Random random = new Random();

            Collection<ItemStack> items = loottable.populateLoot(random,lc);

            for(ItemStack item : items) {
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(),item);
            }
        }
    }
}
