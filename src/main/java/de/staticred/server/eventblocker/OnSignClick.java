package de.staticred.server.eventblocker;

import com.destroystokyo.paper.MaterialSetTag;
import de.staticred.server.util.ArenaSignFileManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnSignClick implements Listener {

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player p = e.getPlayer();

        Block b = e.getClickedBlock();

        if(b.getType() == null) return;


        if(!MaterialSetTag.SIGNS.isTagged(b.getType())) return;
        Sign sign = (Sign) b.getState();

        if(!ArenaSignFileManager.getInstance().isArenaSign(sign)) return;

        String line3 = sign.getLine(2);
        String arenaName = sign.getLine(1).substring(2);


        if(line3.equalsIgnoreCase("§c1§8/§c2")) {
            p.performCommand("arena join " + arenaName);
            return;
        }
        if(line3.equalsIgnoreCase("§a0§8/§a2")) {
            p.performCommand("arena join " + arenaName);
            return;
        }



    }

}
