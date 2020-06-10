package de.staticred.server.util;

import de.staticred.server.Main;
import de.staticred.server.objects.Arena;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArenaFileManager {

    private static ArenaFileManager INSTANCE = new ArenaFileManager();

    public static ArenaFileManager getInstance() {
        return INSTANCE;
    }

    private File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/arena","arenas.yml");
    private FileConfiguration configuration;


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

        configuration = YamlConfiguration.loadConfiguration(file);

        for(Arena arena : getAllArenas()) {
            ArenaManager.registerArena(arena);
        }

    }

    private void saveFile() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Arena> getAllArenas() {
        List<Arena> tempList = new ArrayList<>();
        for(String id : configuration.getKeys(false)) {
            tempList.add(getArena(id));
        }
        return tempList;
    }


    public void addArena(Arena arena) {
        String name = arena.getArenaID();
        configuration.set(name + ".id",name);
        configuration.set(name + ".loc1",arena.getSpawnLocation1());
        configuration.set(name + ".loc2",arena.getSpawnLocation2());
        saveFile();
    }

    public boolean isArena(String id) {
        return configuration.getKeys(false).contains(id);
    }

    public void deleteArena(String id) {
        configuration.set(id,null);
        saveFile();
    }



    public Arena getArena(String id) {
        Location loc1 = configuration.getLocation(id + ".loc1");
        Location loc2 = configuration.getLocation(id + ".loc2");
        return new Arena(loc1,loc2,null,null,id);
    }

}
