package gui;

import exo.remoterobot.Position;
import exo.remoterobot.RemoteRobot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientModel {

    private ExecutorService robotThreads = Executors.newCachedThreadPool();
    private static List<RemoteRobot> robots = new ArrayList();
    private boolean enableControls;
    private int robotIdCounter = 0;
    private Design design;

    public ClientModel(Design design) {
        this.design = design;
        enableControls = false;
    }

    public RemoteRobot createNewRobot(String name) {
        robotIdCounter++;
        RemoteRobot robot = new RemoteRobot(robotIdCounter, name, design);
        robots.add(robot);
        robotThreads.execute(robot);
        return robot;
    }

    public boolean deleteRemoteRobot(RemoteRobot robot) {
        robot.exit();
        robots.remove(robot);
        return true;
    }

    public static boolean isRobotAtPos(Position pos) {
        return robots.stream().anyMatch(robot -> robot.isAtPos(pos));
    }

    public void sendCommand(RemoteRobot robot, String cmd) {
    }

    public void changeControls(RemoteRobot robot) {
    }

    public boolean isEnableControls() {
        return enableControls;
    }

    public void setEnableControls(boolean enableControls) {
        this.enableControls = enableControls;
    }
}
