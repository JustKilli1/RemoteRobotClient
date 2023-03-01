package exo.planet;

import exo.remoterobot.Position;
import exo.remoterobot.RemoteRobot;
import exo.remoterobot.Rotation;

public class ExoPlanetCommands {

    //public static boolean useJSONProtocol;

    public static String orbit(RemoteRobot robot) {
        //if(useJSONProtocol) return "";
        return "orbit:" + robot.getName();
    }

    public static String land(Position position) {
        return "land:" + position.toString();
    }

    public static String scan() {
        return "scan";
    }
    public static String move() {
        return "move";
    }
    public static String moveScan() {
        return "mvscan";
    }
    public static String rotate(Rotation rotation) {
        return "rotate:" + rotation.toString();
    }
    public static String exit() {
        return "exit";
    }
}
