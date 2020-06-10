package de.staticred.server.eventblocker;


import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import de.staticred.server.util.ArenaSignFileManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBlockDestroyEvent implements Listener {

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {

        if(MaterialSetTag.SIGNS.isTagged(e.getBlock().getType())) {
            Sign sign = (Sign) e.getBlock().getState();
            if(ArenaSignFileManager.getInstance().isArenaSign(sign)) {
                e.getPlayer().sendMessage("§aArena Schild erfolgreich zerstört.");
                ArenaSignFileManager.getInstance().deleteSign(ChatColor.stripColor(sign.getLine(1)));
            }
        }

    }

}
