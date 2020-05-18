package de.staticred.server.db;

import de.staticred.server.objects.Perks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class EventDAO {

    public static EventDAO INSTANCE = new EventDAO();

    public static EventDAO getInstance() {
        return INSTANCE;
    }

    public void loadTable() throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        con.executeUpdate("CREATE TABLE IF NOT EXISTS events(uuid VARCHAR(36), ticketAmount INT(100))");
        con.executeUpdate("CREATE TABLE IF NOT EXISTS eventstime(uuid VARCHAR(36), lastSeen LONG)");
        con.closeConnection();
    }


    public long lastSeen(UUID player) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        PreparedStatement ps = con.getConnection().prepareStatement("SELECT * FROM eventstime WHERE uuid = ?");
        ps.setString(1,player.toString());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            long lastSeen = rs.getLong("lastSeen");
            rs.close();
            ps.close();
            con.closeConnection();
            return lastSeen;
        }

        rs.close();
        ps.close();
        con.closeConnection();
        return 0;
    }

    public void setLastOnline(UUID player) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();

        if(isPlayerInDatabse(player)) {
            con.executeUpdate("UPDATE eventstime SET lastSeend = ? WHERE uuid = ?",System.currentTimeMillis(), player.toString());
        }else{
            con.executeUpdate("INSERT INTO eventstime(uuid, lastSeen) VALUES(?,?)", player.toString(),System.currentTimeMillis());
        }

        con.closeConnection();

    }

    public boolean isPlayerInDatabse(UUID player) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        PreparedStatement ps = con.getConnection().prepareStatement("SELECT * FROM eventstime WHERE uuid = ?");
        ps.setString(1,player.toString());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            rs.close();
            ps.close();
            con.closeConnection();
            return true;
        }

        rs.close();
        ps.close();
        con.closeConnection();
        return false;
    }

    public void addPlayer(UUID player, int amount) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        con.executeUpdate("INSERT INTO events(uuid, ticketAmount) VALUES(?, ?)", player.toString(),amount);
        con.closeConnection();
    }

    public void setTickedAmount(UUID player, int amount) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        con.executeUpdate("UPDATE events SET ticketAmount = ? WHERE uuid = ?", amount,  player.toString());
        con.closeConnection();
    }

    public int getTickedAmount(UUID player) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        PreparedStatement ps = con.getConnection().prepareStatement("SELECT * FROM events WHERE uuid = ?");
        ps.setString(1,player.toString());
        ResultSet rs = ps.executeQuery();
        ArrayList<Perks> perks = new ArrayList();
        while(rs.next()) {
            int i = rs.getInt("ticketAmount");
            rs.close();
            ps.close();
            con.closeConnection();
            return i;
        }

        rs.close();
        ps.close();
        con.closeConnection();
        return 0;
    }

    public boolean isInDatabase(UUID player) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        PreparedStatement ps = con.getConnection().prepareStatement("SELECT * FROM events WHERE uuid = ?");
        ps.setString(1,player.toString());
        ResultSet rs = ps.executeQuery();
        ArrayList<Perks> perks = new ArrayList();
        while(rs.next()) {
            rs.close();
            ps.close();
            con.closeConnection();
            return true;
        }

        rs.close();
        ps.close();
        con.closeConnection();
        return false;
    }

}
