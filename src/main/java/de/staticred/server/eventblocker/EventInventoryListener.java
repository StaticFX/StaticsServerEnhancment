package de.staticred.server.eventblocker;

import de.staticred.server.Main;
import de.staticred.server.db.EventDAO;
import de.staticred.server.objects.Event;
import de.staticred.server.objects.EventType;
import de.staticred.server.util.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;

public class EventInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        String title = p.getOpenInventory().getTitle();

        if(title.equalsIgnoreCase("§e§lEvents")) {
            e.setCancelled(true);

            ItemStack clickedItem = e.getCurrentItem();

            if(clickedItem == null) return;
            if(clickedItem.getItemMeta() == null) return;

            String itemName = clickedItem.getItemMeta().getDisplayName();

            if(Main.currentEvent != null) {
                p.sendMessage("§cEs ist schon ein Event im moment!");
                return;
            }

            try {
                if(EventDAO.getInstance().getTickedAmount(p.getUniqueId()) == 0) {
                    p.sendMessage("§aTicktes bekommst du unter: §6ziemlich.tebex.io");
                    return;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if(itemName.equals("§b§lDoppelte XP")) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.DOUBLE_XP);
                EventManager.sendMessageToBungee(event);
                Bukkit.broadcastMessage("\n");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + event.getExecuter().getName());
                Bukkit.broadcastMessage("§e§lZeit: 15m");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("\n");
                EventManager.startEvent(15);
                Main.currentEvent = event;
                try {
                    EventDAO.getInstance().setTickedAmount(p.getUniqueId(),EventDAO.getInstance().getTickedAmount(p.getUniqueId()) - 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            if(itemName.equals("§b§lSchneller abbauen")) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.FAST_DESTROY);
                EventManager.sendMessageToBungee(event);
                Bukkit.broadcastMessage("\n");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + event.getExecuter().getName());
                Bukkit.broadcastMessage("§e§lZeit: 15m");
                Bukkit.broadcastMessage("§8-----------------");
                EventManager.startEvent(15);
                Bukkit.broadcastMessage("\n");
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(onlinePlayer.hasPermission("perk.fastdestroy")) {
                        onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,99999,5,false,false));
                    }else{
                        onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,99999,2,false,false));
                    }
                }
                Main.currentEvent = event;
                try {
                    EventDAO.getInstance().setTickedAmount(p.getUniqueId(),EventDAO.getInstance().getTickedAmount(p.getUniqueId()) - 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            if(itemName.equals("§b§lEhöhte Mobdroprate")) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.MOB_DROPRATE);
                EventManager.sendMessageToBungee(event);
                Bukkit.broadcastMessage("\n");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + event.getExecuter().getName());
                Bukkit.broadcastMessage("§e§lZeit: 5m");
                Bukkit.broadcastMessage("§8-----------------");
                EventManager.startEvent(5);
                Bukkit.broadcastMessage("\n");
                Main.currentEvent = event;
                return;
            }

            if(itemName.equals("§b§lServer Fly")) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.FLY_EVENT);
                EventManager.sendMessageToBungee(event);
                Bukkit.broadcastMessage("\n");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + event.getExecuter().getName());
                Bukkit.broadcastMessage("§e§lZeit: 15m");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("\n");
                EventManager.startEvent(15);
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.setFlying(true);
                    onlinePlayer.setAllowFlight(true);
                    Main.currentEvent = event;
                }
                try {
                    EventDAO.getInstance().setTickedAmount(p.getUniqueId(),EventDAO.getInstance().getTickedAmount(p.getUniqueId()) - 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                    return;
            }

            if(itemName.equals("§b§lHalber Schaden")) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.FLY_EVENT);
                EventManager.sendMessageToBungee(event);
                Bukkit.broadcastMessage("\n");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + event.getExecuter().getName());
                Bukkit.broadcastMessage("§e§lZeit: 5m");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("\n");
                EventManager.startEvent(5);
                Main.currentEvent = event;
                try {
                    EventDAO.getInstance().setTickedAmount(p.getUniqueId(),EventDAO.getInstance().getTickedAmount(p.getUniqueId()) - 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            if(itemName.equals("§b§lShop Sale")) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.FLY_EVENT);
                EventManager.sendMessageToBungee(event);
                Bukkit.broadcastMessage("\n");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + event.getExecuter().getName());
                Bukkit.broadcastMessage("§e§lZeit: 10m");
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("\n");
                EventManager.startEvent(10);
                Main.shopMultiplier = 0.9;
                Main.currentEvent = event;
                try {
                    EventDAO.getInstance().setTickedAmount(p.getUniqueId(),EventDAO.getInstance().getTickedAmount(p.getUniqueId()) - 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            if(itemName.startsWith("§aDeine Tickets:")) {
                p.sendMessage("§aTicktes bekommst du unter: §6ziemlich.tebex.io");
            }



        }




    }

}
