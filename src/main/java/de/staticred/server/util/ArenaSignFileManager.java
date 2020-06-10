package de.staticred.server.util;

import com.destroystokyo.paper.MaterialSetTag;
import de.staticred.server.Main;
import de.staticred.server.objects.Arena;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ArenaSignFileManager {

    private static ArenaSignFileManager INSTANCE = new ArenaSignFileManager();

    public static ArenaSignFileManager getInstance() {
        return INSTANCE;
    }

    private File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/sign","signs.yml");
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

    }

    public void saveFile() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSign(String arena, Sign sign) {
        configuration.set(arena + ".location", sign.getLocation());
        saveFile();
    }

    public @Nullable Sign loadSign(String arena) {
        Location loc = configuration.getLocation(arena + ".location");
        if(!MaterialSetTag.SIGNS.isTagged(loc.getBlock().getType()))  {
            deleteSign(arena);
            return null;
        }

        return (Sign) loc.getBlock().getState();
    }

    public boolean hasSign(String arena) {
        return configuration.getKeys(false).contains(arena);
    }

    public boolean isArenaSign(Sign sign) {
        for(String arena : configuration.getKeys(false)) {
            if(Objects.requireNonNull(loadSign(arena)).getLocation().equals(sign.getLocation())) return true;
        }
        return false;
    }

    public void deleteSign(String arena) {
        configuration.set(arena, null);
        saveFile();
    }

}
