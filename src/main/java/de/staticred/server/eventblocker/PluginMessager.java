package de.staticred.server.eventblocker;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.staticred.server.Main;
import de.staticred.server.objects.Event;
import de.staticred.server.objects.EventType;
import de.staticred.server.util.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class PluginMessager implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] bytes) {

        if(!channel.equals("c:bungeecord")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

        String subchannel = in.readUTF();

        if(Main.currentEvent != null) return;

        if(subchannel.equalsIgnoreCase("event")) {
            EventType eventType = EventType.valueOf(in.readUTF());
            String server = in.readUTF();
            String player = in.readUTF();

            if(server.equalsIgnoreCase("cbrealistic")) return;

            if(eventType == EventType.DOUBLE_XP) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.DOUBLE_XP);
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + player);
                Bukkit.broadcastMessage("§e§lZeit: 15m");
                Bukkit.broadcastMessage("§8-----------------");
                EventManager.startEvent(15);
                Main.currentEvent = event;
                return;
            }

            if(eventType == EventType.FAST_DESTROY) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.FAST_DESTROY);
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + player);
                Bukkit.broadcastMessage("§e§lZeit: 15m");
                Bukkit.broadcastMessage("§8-----------------");
                EventManager.startEvent(15);
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(onlinePlayer.hasPermission("perk.fastdestroy")) {
                        onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,99999,5,false,false));
                    }else{
                        onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,99999,2,false,false));
                    }
                }
                Main.currentEvent = event;
                return;
            }

            if(eventType == EventType.MOB_DROPRATE) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.MOB_DROPRATE);
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + player);
                Bukkit.broadcastMessage("§e§lZeit: 5m");
                Bukkit.broadcastMessage("§8-----------------");
                EventManager.startEvent(5);
                Main.currentEvent = event;
                return;
            }

            if(eventType == EventType.FLY_EVENT) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.FLY_EVENT);
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + player);
                Bukkit.broadcastMessage("§e§lZeit: 15m");
                Bukkit.broadcastMessage("§8-----------------");
                EventManager.startEvent(15);
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.setAllowFlight(true);
                    onlinePlayer.setFlying(true);
                }
                Main.currentEvent = event;
                return;
            }

            if(eventType == EventType.HALF_DAMAGE_EVENT) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.HALF_DAMAGE_EVENT);
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + player);
                Bukkit.broadcastMessage("§e§lZeit: 5m");
                Bukkit.broadcastMessage("§8-----------------");
                EventManager.startEvent(5);
                Main.currentEvent = event;
                return;
            }


            if(eventType == EventType.SHOP_SALE) {
                de.staticred.server.objects.Event event = new Event(p,System.currentTimeMillis(), EventType.SHOP_SALE);
                Bukkit.broadcastMessage("§8-----------------");
                Bukkit.broadcastMessage("§e§lEin Event wurde aktiviert!");
                Bukkit.broadcastMessage("§e§lEvent: " + event.getEventType().toString());
                Bukkit.broadcastMessage("§a§lVon: " + player);
                Bukkit.broadcastMessage("§e§lZeit: 10m");
                Bukkit.broadcastMessage("§8-----------------");
                EventManager.startEvent(10);
                Main.shopMultiplier = 0.9;
                Main.currentEvent = event;
                return;
            }


        }

        if(subchannel.equalsIgnoreCase("debug")) {
            String command = in.readUTF();

            Main.resultMessage.add(command);

        }

        if(subchannel.equalsIgnoreCase("ban") || subchannel.equalsIgnoreCase("mute")) {
            String message = in.readUTF();
            if(message.equalsIgnoreCase("executed")) Main.confirmed = true;
        }

    }
}
