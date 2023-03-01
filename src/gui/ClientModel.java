package gui;

import exo.remoterobot.Position;
import exo.remoterobot.RemoteRobot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientModel {

    private ExecutorService robotThreads = Executors.newCachedThreadPool();
    private List<RemoteRobot> robots = new ArrayList();
    private RemoteRobot viewedRobot;
    private int robotIdCounter = 0;

    public RemoteRobot createNewRobot(String name) {
        robotIdCounter++;
        RemoteRobot robot = new RemoteRobot(robotIdCounter, name);
        robots.add(robot);
        robotThreads.execute(robot);
        return robot;
    }

    public boolean deleteRemoteRobot(RemoteRobot robot) {
        robot.setActive(false);
        robots.remove(robot);
        return true;
    }

    public boolean isRobotAtPos(Position pos) {
        return robots.stream().anyMatch(robot -> robot.isAtPos(pos));
    }

    public void sendCommand(RemoteRobot robot, String cmd) {
    }

    public void changeControls(RemoteRobot robot) {
    }

}
