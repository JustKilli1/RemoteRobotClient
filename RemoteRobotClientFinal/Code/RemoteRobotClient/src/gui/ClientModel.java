package gui;

import base.Main;
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
        if(robots.size() == Main.getMaxRobots()) return null;
        robotIdCounter++;
        RemoteRobot robot = new RemoteRobot(robotIdCounter, name, design);
        robots.add(robot);
        robotThreads.execute(robot);
        return robot;
    }

    public static boolean deleteRemoteRobot(RemoteRobot robot) {
        robot.exit();
        robots.remove(robot);
        return true;
    }

    public static boolean isRobotAtPos(Position pos) {
        return robots.stream().anyMatch(robot -> robot.isAtPos(pos));
    }

    public static void sendCommandToAllRobots(String cmd) {
        robots.forEach(robot -> robot.sendCommand(cmd));
    }

}
