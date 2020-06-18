package de.staticred.server.chestsystem.filemanagers;

import de.staticred.server.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectChestFileManager {

    private static SelectChestFileManager INSTANCE;

    public static SelectChestFileManager getInstance() {
        return INSTANCE;
    }


    private File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/chest","chests.yml");
    private FileConfiguration conf;

    public void loadFile() {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        conf = YamlConfiguration.loadConfiguration(file);
    }

    private void saveFile() {
        try {
            conf.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addChest(Chest chest, String name) {
        conf.set(name + ".name", name);
        conf.set(name + ".loc",chest.getLocation());
        saveFile();
    }

    public Chest getChest(String name) {
        Location loc = conf.getLocation(name + ".loc");
        Block block = loc.getBlock();

        if(block.getType() == Material.AIR || block.getType() != Material.CHEST) {
            deleteChest(name);
            return null;
        }
        return (Chest) block.getState();
    }

    public void deleteChest(String name) {
        conf.set(name,null);
        saveFile();
    }

    public Map<Chest,String> getAllChests() {
        Set<String> list = conf.getKeys(false);

        HashMap<Chest,String> map = new HashMap<>();

        for(String name : list) {
            if(getChest(name) == null) continue;
            map.put(getChest(name),name);
        }

        return map;
    }


}
