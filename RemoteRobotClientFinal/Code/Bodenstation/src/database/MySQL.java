package database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MySQL {
    private String host = "localhost";
    private String port = "3306";
    private String database = "";
    private String username = "root";
    private String password = "";
    private Connection con;

    public MySQL() {
        try {
            Properties prop = new Properties();
            prop.load(new FileReader("config.properties"));
            port = prop.getProperty("db.port", "3306");
            host = prop.getProperty("db.host", "localhost");
            username = prop.getProperty("db.user");
            password = prop.getProperty("db.pass");
            database = prop.getProperty("db.database");
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

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
