package de.staticred.server.db;

import de.staticred.server.objects.Perks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PerkDAO {

    private static PerkDAO instance = new PerkDAO();

    public static PerkDAO getInstance() {
        return instance;
    }

    public void addPerk(UUID uuid, Perks perk) throws SQLException {
        if(hasPerk(uuid, perk)) return;
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        con.executeUpdate("INSERT INTO perks(UUID, perk) VALUES(?,?)", uuid.toString(), perk.toString());
        con.closeConnection();
    }

    public void removePerk(UUID uuid, Perks perk) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        con.executeUpdate("DELETE FROM perks where UUID = ? AND perk = ?", uuid.toString(), perk.toString());
        con.closeConnection();
    }

    public List<Perks> getPerks(UUID uuid) throws SQLException {
        DataBaseConnection con = DataBaseConnection.INSTANCE;
        con.openConnection();
        PreparedStatement ps = con.getConnection().prepareStatement("SELECT * FROM perks WHERE UUID = ?");
        ps.setString(1,uuid.toString());
        ResultSet rs = ps.executeQuery();
        ArrayList<Perks> perks = new ArrayList();
        while(rs.next()) {
            Perks type;
            try{
                type = Perks.valueOf(rs.getString("perk"));
            }catch (Exception e) {
                rs.close();
                ps.close();
                con.closeConnection();
                throw new SQLException("Invalid type! Please check your DataBase!");

            }
            perks.add(type);
        }

        rs.close();
        ps.close();
        con.closeConnection();
        return perks;
    }

    public boolean hasPerk(UUID uuid, Perks perk) throws SQLException {
        return getPerks(uuid).contains(perk);
    }



}
