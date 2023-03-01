package shared.logging.type;

import exo.remoterobot.RemoteRobot;
import exo.remoterobot.RobotConsole;
import shared.logging.LogCategory;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class RobotLogger {


    protected LogCategory logCategory;

    public RobotLogger(LogCategory logCategory) {
        this.logCategory = logCategory;

    }

    /**
     * Writes A LogMessage to a Robot Console
     * @param target The Robot which  Console gets used
     * @param messages The Messages
     * @see RemoteRobot
     * */
    public void logToConsole(RemoteRobot target, List<String> messages) {
        RobotConsole targetConsole = target.getRobotConsole();
        messages.forEach(msg -> targetConsole.print(msg));
    }

    /**
     * Writes A LogMessage to a File
     * @param message The Message that gets written to the File
     * */
    public void logToConsole(RemoteRobot target, String message) {
        logToConsole(target, Arrays.asList(message));
    }

}
