package de.staticred.server.util;

import de.staticred.server.objects.Arena;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaManager {

    private static List<Arena> registeredArena = new ArrayList<>();
    private static List<String> registeredArenaID = new ArrayList<>();
    private static HashMap<String, Arena> idArenaLinker = new HashMap<>();
    private static HashMap<String, List<Player>> arenaPlayerQueue = new HashMap<>();
    public static List<Player> waitingPlayers = new ArrayList<>();
    public static List<Player> fightingPlayers = new ArrayList<>();

    private static HashMap<Player, Arena> playerCreatingArena = new HashMap<>();


    public static List<Arena> getAllArenas() {
        return registeredArena;
    }

    public static boolean isPlayerQueueing(Player p) {
        for(Arena arenaID : registeredArena) {
            if(arenaID.getPlayer1() == p || arenaID.getPlayer2() == p) return true;
        }
        return false;
    }

    public static void resetArena(Player player, Arena arena) {
        playerCreatingArena.replace(player,arena);
    }

    public static void addCreatingArena(Player player, Arena arena) {
        playerCreatingArena.put(player,arena);
    }

    public static boolean isCreatingArena(Player player) {
        return playerCreatingArena.containsKey(player);
    }

    public static void removeCreatingArena(Player player) {
        playerCreatingArena.remove(player);
    }

    public static Arena getCreatedArena(Player player) {
        return playerCreatingArena.get(player);
    }

    public static void registerArena(Arena arena) {
        ArenaFileManager.getInstance().addArena(arena);
        registeredArena.add(arena);
        registeredArenaID.add(arena.getArenaID());
        idArenaLinker.put(arena.getArenaID(),arena);
        arenaPlayerQueue.put(arena.getArenaID(), new ArrayList<>());
        updateArena(arena);
    }

    public static void stopQueuing(Player p) {
        Arena tempArena = getArena(p);
        addPlayer1(null,tempArena.getArenaID());
        List<Player> queueList = arenaPlayerQueue.get(tempArena.getArenaID());
        queueList.clear();
        arenaPlayerQueue.replace(tempArena.getArenaID(),queueList);
        updateArena(tempArena);
    }

    public static void deleteArena(String id) {
        registeredArena.remove(idArenaLinker.remove(id));
        registeredArena.removeIf(arena1 -> arena1.getArenaID().equals(id));
        idArenaLinker.remove(id);
        ArenaFileManager.getInstance().deleteArena(id);
        arenaPlayerQueue.remove(id);
        if(ArenaSignFileManager.getInstance().hasSign(id)) {
           ArenaSignFileManager.getInstance().deleteSign(id);
        }
    }

    public static Arena getArena(String id) {
        return idArenaLinker.get(id);
    }

    public static void fightFinished(Arena arena) {
        addPlayer1(null,arena.getArenaID());
        addPlayer2(null,arena.getArenaID());
        List<Player> queueList = arenaPlayerQueue.get(arena.getArenaID());
        queueList.clear();
        arenaPlayerQueue.replace(arena.getArenaID(),queueList);
        if(ArenaSignFileManager.getInstance().hasSign(arena.getArenaID())) {
            Sign sign = ArenaSignFileManager.getInstance().loadSign(arena.getArenaID());
            sign.setLine(2,"§a0§8/§a2");
            sign.update();
        }
    }

    public static Arena getArena(Player p) {
        for(Arena arena : registeredArena) {
            if(arena.getPlayer1().getUniqueId().equals(p.getUniqueId())) {
                return arena;
            }
            if(arena.getPlayer2().getUniqueId().equals(p.getUniqueId())) {
                return arena;
            }
        }
        return null;
    }

    public static boolean isArenaBeingPlayed(String id) {
        return idArenaLinker.get(id).getPlayer1() != null && idArenaLinker.get(id).getPlayer2() != null;
    }

    public static void updateArena(Arena arena) {
        registeredArena.removeIf(arena1 -> arena1.getArenaID().equals(arena.getArenaID()));
        idArenaLinker.replace(arena.getArenaID(),arena);
        registeredArena.add(arena);

        if(ArenaSignFileManager.getInstance().hasSign(arena.getArenaID())) {
            Sign sign = ArenaSignFileManager.getInstance().loadSign(arena.getArenaID());
            if(ArenaManager.isArenaBeingPlayed(arena.getArenaID())) {
                sign.setLine(2,"§42§8/§42");
            }else if(ArenaManager.hasPlayer1Joined(arena.getArenaID())) {
                sign.setLine(2,"§c1§8/§c2");
            }else{
                sign.setLine(2,"§a0§8/§a2");
            }
            sign.update();
        }

    }

    public static void addPlayer1(Player player, String id) {
        Arena tempArena = idArenaLinker.replace(id,(idArenaLinker.get(id)));
        tempArena.setPlayer1(player);
        registeredArena.removeIf(arena1 -> arena1.getArenaID().equals(id));

        tempArena.setPlayer1(player);
        idArenaLinker.replace(id,tempArena);
        registeredArena.add(tempArena);
        List<Player> queueList = new ArrayList<>();
        queueList.add(player);
        arenaPlayerQueue.replace(id,queueList);

        if(ArenaSignFileManager.getInstance().hasSign(id)) {
            Sign sign = ArenaSignFileManager.getInstance().loadSign(id);
            sign.setLine(2,"§c1§8/§c2");
            sign.update();
        }

    }

    public static boolean hasPlayer1Joined(String id) {
        return arenaPlayerQueue.get(id).size() == 1;
    }

    public static void addPlayer2(Player player, String id) {
        Arena tempArena = idArenaLinker.replace(id,(idArenaLinker.get(id)));
        registeredArena.removeIf(arena1 -> arena1.getArenaID().equals(id));
        tempArena.setPlayer2(player);
        idArenaLinker.replace(id,tempArena);
        registeredArena.add(tempArena);
        List<Player> queueList = new ArrayList<>();
        arenaPlayerQueue.replace(id,queueList);

        if(ArenaSignFileManager.getInstance().hasSign(id)) {
            Sign sign = ArenaSignFileManager.getInstance().loadSign(id);
            sign.setLine(2,"§42§8/§42");
            sign.update();
        }
    }

    public static boolean isRegistered(String id) {
        return registeredArenaID.contains(id);
    }

}
