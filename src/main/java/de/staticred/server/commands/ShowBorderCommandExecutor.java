package de.staticred.server.commands;

import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import de.staticred.server.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ShowBorderCommandExecutor implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) return false;

        Player p = (Player) commandSender;

        Location loc = new Location(p.getLocation().getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockY(),p.getLocation().getBlockZ());
        Plot currentPlot = Plot.getPlot(loc);

        if(Main.cooldown.contains(p)) {
            p.sendMessage("§cBitte warte 60 sekunden zwischen jedem Befehl!");
            return false;
        }

        if(currentPlot == null) {
            p.sendMessage("§cDu befindest dich nicht auf einem Plot!");
            return false;
        }

        if(!currentPlot.hasOwner()) {
            p.sendMessage("§cDieses Plot gehört dir nicht!");
            return false;
        }

        if(!currentPlot.getOwner().toString().equals(p.getUniqueId().toString()) && !currentPlot.getTrusted().contains(p.getUniqueId())) {
            p.sendMessage("§cDieses Plot gehört dir nicht!");
            return false;
        }

        List<Location> locations = currentPlot.getAllCorners();

        Location cornerXY1 = locations.get(0);
        Location cornerXY2 = locations.get(2);

        p.sendMessage("§aDir werden nur die Ränder deines Plots angezeigt.");

        if(Main.playerTaskHashMap.containsKey(p)) Main.playerTaskHashMap.remove(p);

        Main.playerTaskHashMap.put(p,0);

        run(cornerXY1,cornerXY2,p,loc);


        Main.cooldown.add(p);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(Main.cooldown.contains(p))
                    Main.cooldown.remove(p);
            }
        },20*60);

        return false;
    }

    public static void run(Location cornerXY1, Location cornerXY2, Player p, Location loc) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {

                int timer = Main.playerTaskHashMap.get(p);

                timer++;


                if(cornerXY1.getX() > cornerXY2.getX()) {
                    for(int i = cornerXY2.getX(); i < cornerXY1.getX(); i++) {

                        double y = 255;
                        double y1 = 255;

                        org.bukkit.Location loc1 = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()),i,y,cornerXY1.getZ());

                        while(loc1.getBlock().getType() == Material.AIR) {
                            loc1.setY(loc1.getY() - 1);
                        }

                        loc1.setY(loc1.getY() + 2);


                        org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()),i,y1,cornerXY2.getZ());
                        while(location.getBlock().getType() == Material.AIR) {
                            location.setY(location.getY() - 1);
                        }

                        location.setY(location.getY() + 2);


                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
                        p.spawnParticle(Particle.REDSTONE, location, 25, dustOptions);
                        p.spawnParticle(Particle.REDSTONE, loc1, 25, dustOptions);
                    }
                    if(cornerXY1.getZ() > cornerXY2.getZ()) {
                        for (int i = cornerXY2.getZ(); i < cornerXY1.getZ(); i++) {
                            double z = 255;
                            double z1 = 255;

                            org.bukkit.Location loc1 = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), cornerXY1.getX(), z, i);

                            while(loc1.getBlock().getType() == Material.AIR) {
                                loc1.setY(loc1.getY() - 1);
                            }

                            loc1.setY(loc1.getY() + 2);


                            org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), cornerXY2.getX(), z1, i);
                            while(location.getBlock().getType() == Material.AIR) {
                                location.setY(location.getY() - 1);
                            }

                            location.setY(location.getY() + 2);

                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
                            p.spawnParticle(Particle.REDSTONE, location, 25, dustOptions);
                            p.spawnParticle(Particle.REDSTONE, loc1, 25, dustOptions);
                        }

                    }else{
                        for (int i = cornerXY1.getZ(); i < cornerXY2.getZ(); i++) {
                            double z = 255;
                            double z1 = 255;

                            org.bukkit.Location loc1 = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), cornerXY1.getX(), z, i);

                            while(loc1.getBlock().getType() == Material.AIR) {
                                loc1.setY(loc1.getY() - 1);
                            }

                            loc1.setY(loc1.getY() + 2);


                            org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), cornerXY2.getX(), z1, i);
                            while(location.getBlock().getType() == Material.AIR) {
                                location.setY(location.getY() - 1);
                            }

                            location.setY(location.getY() + 2);

                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
                            p.spawnParticle(Particle.REDSTONE, location, 25, dustOptions);
                            p.spawnParticle(Particle.REDSTONE, loc1, 25, dustOptions);
                        }
                    }
                }else{
                    for(int i = cornerXY1.getX(); i < cornerXY2.getX(); i++) {

                        double z = 255;
                        double z1 = 255;

                        org.bukkit.Location loc1 = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()),i,z,cornerXY1.getZ());

                        while(loc1.getBlock().getType() == Material.AIR) {
                            loc1.setY(loc1.getY() - 1);
                        }

                        loc1.setY(loc1.getY() + 2);


                        org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()),i,z1,cornerXY2.getZ());
                        while(location.getBlock().getType() == Material.AIR) {
                            location.setY(location.getY() - 1);
                        }

                        location.setY(location.getY() + 2);

                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
                        p.spawnParticle(Particle.REDSTONE, location, 25, dustOptions);
                        p.spawnParticle(Particle.REDSTONE, loc1, 25, dustOptions);
                    }
                    if(cornerXY1.getZ() > cornerXY2.getZ()) {
                        for (int i = cornerXY1.getZ(); i < cornerXY2.getZ(); i++) {
                            double z = 255;
                            double z1 = 255;

                            org.bukkit.Location loc1 = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), cornerXY1.getX(), z, i);

                            while(loc1.getBlock().getType() == Material.AIR) {
                                loc1.setY(loc1.getY() - 1);
                            }

                            loc1.setY(loc1.getY() + 2);


                            org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), cornerXY2.getX(), z1, i);
                            while(location.getBlock().getType() == Material.AIR) {
                                location.setY(location.getY() - 1);
                            }

                            location.setY(location.getY() + 2);

                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
                            p.spawnParticle(Particle.REDSTONE, location, 25, dustOptions);
                            p.spawnParticle(Particle.REDSTONE, loc1, 25, dustOptions);
                        }

                    }else{
                        for (int i = cornerXY1.getZ(); i < cornerXY2.getZ(); i++) {
                            double z = 255;
                            double z1 = 255;

                            org.bukkit.Location loc1 = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), cornerXY1.getX(), z, i);

                            while(loc1.getBlock().getType() == Material.AIR) {
                                loc1.setY(loc1.getY() - 1);
                            }

                            loc1.setY(loc1.getY() + 2);


                            org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), cornerXY2.getX(), z1, i);
                            while(location.getBlock().getType() == Material.AIR) {
                                location.setY(location.getY() - 1);
                            }

                            location.setY(location.getY() + 2);

                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
                            p.spawnParticle(Particle.REDSTONE, location, 25, dustOptions);
                            p.spawnParticle(Particle.REDSTONE, loc1, 25, dustOptions);
                        }
                    }
                }

                if(timer != 20) ShowBorderCommandExecutor.run(cornerXY1,cornerXY2,p,loc);
                Main.playerTaskHashMap.put(p,timer);


            }



        }, 20);

    }




}
