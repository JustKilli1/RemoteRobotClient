package gui;

import remoterobot.Position;
import remoterobot.RemoteRobot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientModel {

    private ExecutorService robotThreads = Executors.newCachedThreadPool();
    private List<RemoteRobot> activeRobots = new ArrayList();
    private int robotIdCounter = 0;

    public RemoteRobot createNewRobot(String name) {
        robotIdCounter++;
        RemoteRobot robot = new RemoteRobot(robotIdCounter, name);
        activeRobots.add(robot);
        robotThreads.execute(robot);
        return robot;
    }

    public boolean deleteRemoteRobot(RemoteRobot robot) {
        robot.changeActive();
        this.activeRobots.remove(robot);
        return true;
    }

    public boolean isRobotAtPos(Position pos) {
        return this.activeRobots.stream().anyMatch(robot -> robot.isAtPos(pos));
    }

    public void sendCommand(RemoteRobot robot, String cmd) {
    }

    public void changeControls(RemoteRobot robot) {
    }
}
