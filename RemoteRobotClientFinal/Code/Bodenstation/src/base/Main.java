package base;

import database.DBAccessLayer;
import server.Server;

public class Main {

    private static int groundPort;

    public static void main(String[] args) {

        for(String arg : args) {
            if(arg.toLowerCase().contains("port")) {
                String[] split = arg.split(":");
                groundPort = Integer.parseInt(split[1]) > 0 ? Integer.parseInt(split[1]) : 7832;
            }
        }
        if(groundPort == 0) groundPort = 7832;
            System.out.println("Hello world!");
            Server server = new Server();
            server.start();
            DBAccessLayer sql = new DBAccessLayer();
            sql.createMeasureDatabase();
    }

    public static int getGroundPort() {
        return groundPort;
    }
}