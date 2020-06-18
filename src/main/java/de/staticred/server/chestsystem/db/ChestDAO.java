package de.staticred.server.chestsystem.db;

import de.staticred.server.chestsystem.util.Rarity;
import de.staticred.server.db.DataBaseConnection;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ChestDAO {

    private static ChestDAO INSTANCE = new ChestDAO();
    public static ChestDAO getInstance() {
        return INSTANCE;
    }

    public void loadTable() throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        con.executeUpdate("CREATE TABLE IF NOT EXISTS chests(UUID VARCHAR(36), common INT(100), epic INT(100), legendary INT(100))");
        con.closeConnection();
    }

    public void setChestToUser(UUID player, Rarity rarity, int amount) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        if(rarity == Rarity.COMMON) {
            con.executeUpdate("UPDATE chests SET common = ? WHERE UUID = ?)",amount, player.toString());
        }
        if(rarity == Rarity.EPIC) {
            con.executeUpdate("UPDATE chests SET epic = ? WHERE UUID = ?)",amount, player.toString());
        }
        if(rarity == Rarity.LEGENDARY) {
            con.executeUpdate("UPDATE chests SET legendary = ? WHERE UUID = ?)",amount, player.toString());
        }
        con.closeConnection();
    }

    public int getChestAmount(UUID player, Rarity rarity) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        PreparedStatement ps = con.getConnection().prepareStatement("SELECT * FROM chests WHERE UUID = ?");
        ps.setString(1,player.toString());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int amount = 0;
            if(rarity == Rarity.COMMON) {
                amount = rs.getInt("common");
            }
            if(rarity == Rarity.EPIC) {
                amount = rs.getInt("epic");
            }
            if(rarity == Rarity.LEGENDARY) {
                amount = rs.getInt("legendary");
            }
            return amount;
        }
        ps.close();
        con.closeConnection();
        rs.close();
        return 0;
    }

    public boolean isPlayerInDatabase(UUID player) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        PreparedStatement ps = con.getConnection().prepareStatement("SELECT * FROM chests WHERE UUID = ?");
        ps.setString(1,player.toString());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            ps.close();
            con.closeConnection();
            rs.close();
            return true;
        }
        ps.close();
        con.closeConnection();
        rs.close();
        return false;
    }

}
