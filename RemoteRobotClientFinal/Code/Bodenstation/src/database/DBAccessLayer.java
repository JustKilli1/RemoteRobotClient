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


    public void createMeasureDatabase() {
        String query = "CREATE TABLE IF NOT EXISTS TableMeasureData(" +
                "ID INT," +
                "idrobot INT," +
                "ground VARCHAR(20)," +
                "temperature INT," +
                "PRIMARY KEY(ID)" +
                ")";
        executeSQLRequest(query);
    }

    public boolean sendMeasureToDatabase(int id, MeasureData data) {
        String query = "INSERT INTO TableMeasureData (id, idrobot, ground, temperature) " +
                "VALUES (" + id + ", " + data.getRobotId() + ", '" + data.getGround() + "', " + data.getTemperature() +
                ")";
        System.out.println("Messdaten in datenbank geschrieben: " + data);
        return executeSQLRequest(query);
    }

    public ResultSet getLastId() {
        String query = "SELECT id FROM TableMeasureData ORDER BY id DESC LIMIT 1";
        return querySQLRequest(query);
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
