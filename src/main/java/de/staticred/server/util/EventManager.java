package de.staticred.server.util;

import de.staticred.server.Main;
import de.staticred.server.objects.Event;
import de.staticred.server.objects.Perks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class EventManager {

    public static Inventory getEventInventory(int tickets) {
        Inventory inv = Bukkit.createInventory(null,3*9, "§e§lEvents");
        ItemStack doublexp = itemBuilder("§b§lDoppelte XP",Material.EXPERIENCE_BOTTLE, Arrays.asList("§7 - §eAktiviert doppelte XP für alle", "§7 - §eHält 15 Minuten"));
        ItemStack fastdestroy = itemBuilder("§b§lSchneller abbauen",Material.DIAMOND_PICKAXE, Arrays.asList("§7 - §eAktiviert HASTE 2 für alle", "§7 - §eHält 15 Minuten"));
        ItemStack mobdroprate = itemBuilder("§b§lEhöhte Mobdroprate",Material.CREEPER_HEAD, Arrays.asList("§7 - §eAktiviert eine erhöte Dropchance für alle", "§7 - §eHält 5 Minuten"));
        ItemStack fly_event = itemBuilder("§b§lServer Fly",Material.FEATHER, Arrays.asList("§7 - §eAktiviert das Fliegen für alle", "§7 - §eHält 15 Minuten"));
        ItemStack half_damage = itemBuilder("§b§lHalber Schaden",Material.APPLE, Arrays.asList("§7 - §eAktiviert halben Schaden für alle (gesamter Server)", "§7 - §eHält 5 Minuten"));
        ItemStack shop_sale = itemBuilder("§b§lShop Sale",Material.GOLDEN_APPLE, Arrays.asList("§7 - §eAktiviert 10% Sale im shop", "§7 - §eHält 10 Minuten"));

        ItemStack ticketsItem = itemBuilder("§aDeine Tickets: §e" + tickets,Material.PAPER, Arrays.asList("§7 - §eEin Ticket pro event."));


        inv.setItem(0,doublexp);
        inv.setItem(1,fastdestroy);
        inv.setItem(2,mobdroprate);
        inv.setItem(3,fly_event);
        inv.setItem(4,half_damage);
        inv.setItem(5,shop_sale);

        inv.setItem(18,ticketsItem);

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

    public static void startEvent(int minutes) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getInstance(),() -> {
            Bukkit.broadcastMessage("§8------------");
            Bukkit.broadcastMessage("§c§lDas aktuelle Event endet in §420 §c§lsekunden.");
            sendAllPlayersSound(Sound.BLOCK_NOTE_BLOCK_PLING);
            Bukkit.broadcastMessage("§8------------");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                Bukkit.broadcastMessage("§8------------");
                Bukkit.broadcastMessage("§c§lDas aktuelle Event endet in §45 §c§lsekunden.");
                sendAllPlayersSound(Sound.BLOCK_NOTE_BLOCK_PLING);
                Bukkit.broadcastMessage("§8------------");
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                    Bukkit.broadcastMessage("§8------------");
                    Bukkit.broadcastMessage("§c§lDas aktuelle Event endet in §44 §c§lsekunden.");
                    Bukkit.broadcastMessage("§8------------");
                    sendAllPlayersSound(Sound.BLOCK_NOTE_BLOCK_PLING);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                        Bukkit.broadcastMessage("§8------------");
                        Bukkit.broadcastMessage("§c§lDas aktuelle Event endet in §43 §c§lsekunden.");
                        Bukkit.broadcastMessage("§8------------");
                        sendAllPlayersSound(Sound.BLOCK_NOTE_BLOCK_PLING);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                            Bukkit.broadcastMessage("§8------------");
                            Bukkit.broadcastMessage("§c§lDas aktuelle Event endet in §42 §c§lsekunden.");
                            Bukkit.broadcastMessage("§8------------");
                            sendAllPlayersSound(Sound.BLOCK_NOTE_BLOCK_PLING);
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                Bukkit.broadcastMessage("§8------------");
                                Bukkit.broadcastMessage("§c§lDas aktuelle Event endet in §41 §c§lsekunden.");
                                Bukkit.broadcastMessage("§8------------");
                                sendAllPlayersSound(Sound.BLOCK_NOTE_BLOCK_PLING);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                    Main.currentEvent = null;
                                    Bukkit.broadcastMessage("§8------------");
                                    Bukkit.broadcastMessage("§c§lDas aktuelle Event wurde beendet.");
                                    Bukkit.broadcastMessage("§8------------");
                                    for(Player onlineplayer : Bukkit.getOnlinePlayers()) {
                                        Main.getInstance().executePerkChange(onlineplayer, Perks.FAST_DESTROY_PERK,true);
                                    }
                                    for(Player onlineplayer : Bukkit.getOnlinePlayers()) {
                                        Main.getInstance().executePerkChange(onlineplayer, Perks.FLY_PERK,true);
                                    }
                                    Main.shopMultiplier = 1;
                                    sendAllPlayersSound(Sound.BLOCK_NOTE_BLOCK_PLING);
                                }, 20);
                            }, 20);
                        }, 20);
                    }, 20);
                }, 20);
            }, 15*20);
        },20*60*minutes - 20*20);
    }

    public static void sendMessageToBungee(Event event) {
        Main.sendMessageToBungee(event.getExecuter(), "c:bungeecord","event",event.getEventType().toString(), "cbrealistic",event.getExecuter().getName());
    }

    public static void sendAllPlayersSound(Sound sound) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(),sound, 1,1);
        }
    }


}
