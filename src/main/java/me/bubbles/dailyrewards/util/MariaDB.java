package me.bubbles.dailyrewards.util;

import java.sql.*;

public final class MariaDB {

    private String url;
    private String user;
    private String password;

    private Connection connection;
    private boolean connected=false;

    public MariaDB(String endpoint, String database, String username, String password) {
        this.url="jdbc:mysql://"+endpoint+"/"+database+"?autoReconnect=true&useSSL=false";
        this.user=username;
        this.password=password;
    }

    public MariaDB connect() {

        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        this.connected=true;

        return this;

    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public Long getTime(String uuid) {

        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM dailyrewards WHERE uuid=?");
            statement.setString(1,uuid);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                return results.getLong("time");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void setTime(String uuid, long time) {

        try {
            PreparedStatement statement = getConnection().prepareStatement("UPDATE dailyrewards SET time=? WHERE uuid=?");
            statement.setLong(1,time);
            statement.setString(2,uuid);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean writeUser(String playerUUID,long time) {
        Statement statement = null;
        try {
            //INSERT INTO users(uuid,discord,invites,able)
            //VALUES(
            //  uuid,
            //  discord,
            //  invites,
            //  able
            //);
            statement = getConnection().createStatement();
            String init =
                    "INSERT INTO dailyrewards(uuid, time)\n" +
                            "VALUES('%uuid%','%time%');";
            String cur =
                    init.replace("%uuid%",playerUUID).replace("%time%",String.valueOf(time));
            statement.execute(cur);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void execute(String exc) {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.execute(exc);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
