package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
    private String host = "localhost";
    private String port = "3306";
    private String database = "fehlernummern";
    private String username = "root";
    private String password = "uw]MGkmmYROl.Zw.";
    private Connection con;

    public void connect() {
        if (!this.isConnected()) {
            try {
                this.con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&useSSL=false", this.username, this.password);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public void disconnect() {
        if (this.isConnected()) {
            try {
                this.con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public boolean isConnected() {
        return this.con != null;
    }

    public Connection getConnection() {
        return this.con;
    }
}
