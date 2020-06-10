package de.staticred.server.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Arena {

    private Location spawnLocation1;
    private Location spawnLocation2;
    private @Nullable Player player1;
    private @Nullable Player player2;
    private String arenaID;

    public Arena(Location spawnLocation1, Location spawnLocation2, @Nullable Player player1, @Nullable Player player2, String arenaID) {
        this.spawnLocation1 = spawnLocation1;
        this.spawnLocation2 = spawnLocation2;
        this.player1 = player1;
        this.player2 = player2;
        this.arenaID = arenaID;
    }

    public Arena(String arenaID) {
        this.arenaID = arenaID;
    }

    public Location getSpawnLocation1() {
        return spawnLocation1;
    }

    public void setSpawnLocation1(Location spawnLocation1) {
        this.spawnLocation1 = spawnLocation1;
    }

    public Location getSpawnLocation2() {
        return spawnLocation2;
    }

    public void setSpawnLocation2(Location spawnLocation2) {
        this.spawnLocation2 = spawnLocation2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public String getArenaID() {
        return arenaID;
    }

    public void setArenaID(String arenaID) {
        this.arenaID = arenaID;
    }
}
