package de.staticred.server.eventblocker;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.staticred.server.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginChannelListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();


        if(subchannel.equalsIgnoreCase("PlayerCount")) {
            String server = in.readUTF();
            Main.onlineplayer = in.readInt();
        }
    }

}
