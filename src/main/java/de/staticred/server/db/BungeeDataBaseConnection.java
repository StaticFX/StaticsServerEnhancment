package de.staticred.server.db;

import de.staticred.server.filemanagment.ConfigFileManagment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BungeeDataBaseConnection {
    private Connection connection;
    String user, password,url, host;

    public static BungeeDataBaseConnection INSTANCE = new BungeeDataBaseConnection();

    public BungeeDataBaseConnection() {
        user = ConfigFileManagment.INSTANCE.getUser();
        password = ConfigFileManagment.INSTANCE.getPassword();
        host = ConfigFileManagment.INSTANCE.getHost();
        url = "jdbc:mysql://" + host + "/staffsystem?useSSL=false";
    }

    public void executeUpdate(String string, Object... obj) throws SQLException {

        PreparedStatement ps = getConnection().prepareStatement(string);
        for(int i = 0; i < obj.length; i++) {
            ps.setObject(i + 1, obj[i]);
        }
        ps.executeUpdate();
        ps.close();
    }


    public void openConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("driver not found");
        }
        connection = DriverManager.getConnection(url,user,password);
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnectionOpened() {
        return (connection != null);
    }

    public Connection getConnection() {
        return connection;
    }
}
