package database;

import server.MeasureData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccessLayer {
    private MySQL mySql;

    public DBAccessLayer() {
        this.mySql = new MySQL();
        this.mySql.connect();
    }


    public void disable() {
        this.mySql.disconnect();
    }

    public void sendMeasureToDatabase(MeasureData data) {
        System.out.println("Messdaten in datenbank geschrieben: " + data);
    }

    private void checkAndReconnectConnection() {
        if (!this.mySql.isConnected()) {
            this.mySql.connect();
        }

    }

    public boolean executeSQLRequest(String sqlQuery) {
        this.checkAndReconnectConnection();
        if (this.mySql.isConnected()) {
            Connection connection = this.mySql.getConnection();

            try {
                PreparedStatement ps = connection.prepareStatement(sqlQuery);
                ps.executeUpdate();
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public ResultSet querySQLRequest(String sqlQuery) {
        this.checkAndReconnectConnection();
        if (this.mySql.isConnected()) {
            Connection connection = this.mySql.getConnection();

            try {
                PreparedStatement ps = connection.prepareStatement(sqlQuery);
                return ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

}
