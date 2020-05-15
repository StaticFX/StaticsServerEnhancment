package de.staticred.server.commands;

import de.staticred.server.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommandExecutor implements CommandExecutor {

    static int timer = 0;

    @Override
    public boolean onCommand(CommandSender s, Command command, String string, String[] args) {

        if(!(s instanceof Player)) {
            s.sendMessage("§cYou must be a player");
            return false;
        }

        if(!s.hasPermission("sts.cmd.debug")) {
            s.sendMessage("§cKeine Rechte!");
            return false;
        }


        if(args.length != 1) {
            s.sendMessage("§cUse /debug <bungee>");
            return false;
        }


        if(args[0].equals("bungee")) {
            performBungeeDebug(s);
            return false;
        }


        s.sendMessage("§cUse /debug <bungee>");
        return false;
    }


    public void performBungeeDebug(CommandSender s) {

        s.sendMessage("§aNow sending debug messages to bungee.");

        Player p = (Player) s;


        Main.sendMessageToBungee(p,"c:bungeecord", "ban", "BLumeFX", "test", p.getName(), "30m");

        p.sendMessage("§aSend to bungee: channel: c:bungeecord subchannel: ban player: BLumeFX reason: test: player: StaticRed");
        p.sendMessage("§cWaiting for message from the bungee.");


        Main.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {

                timer++;

                if(Main.resultMessage.size() != 0) {
                    p.sendMessage("§aFound varius results.");

                    for(String string : Main.resultMessage) {
                        p.sendMessage(string);
                    }

                }else{
                    p.sendMessage("§cNo results found.");
                }

                if(timer == 5) {
                    Bukkit.getScheduler().cancelTask(Main.task);
                }
            }
        },0,20);


    }


}
