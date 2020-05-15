package de.staticred.server.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class SlotDAO {

    public static SlotDAO INSTANCE = new SlotDAO();

    public static SlotDAO getINSTANCE() {
        return INSTANCE;
    }

    public void loadTable() throws SQLException {
        BungeeDataBaseConnection con = BungeeDataBaseConnection.INSTANCE;
        con.openConnection();
        con.executeUpdate("CREATE TABLE IF NOT EXISTS slots(server VARCHAR(255), slots INT(50))");
        con.closeConnection();
    }

    public int getSlots(String server) throws SQLException {
        BungeeDataBaseConnection con = BungeeDataBaseConnection.INSTANCE;
        con.openConnection();
        PreparedStatement ps = con.getConnection().prepareStatement("SELECT * FROM slots WHERE server = ?");
        ps.setString(1,server);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int time = rs.getInt("slots");
            rs.close();
            ps.close();
            con.closeConnection();
            return time;
        }

        rs.close();
        ps.close();
        con.closeConnection();
        return 0;
    }



}
