package de.staticred.server.eventblocker;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.staticred.server.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;

public class PluginMessager implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

        String subchannel = in.readUTF();

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
