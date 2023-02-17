package gui;

import remoterobot.Position;
import remoterobot.RemoteRobot;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import gui.controller.AddRemoveRobotController;
import gui.controller.ChangeConsoleController;
import gui.windows.MainWindow;

public class ClientModel {

    private ExecutorService robotThreads = Executors.newCachedThreadPool();
    private List<RemoteRobot> activeRobots = new ArrayList();

    public RemoteRobot createNewRobot(String name) {
        RemoteRobot robot = new RemoteRobot(activeRobots.size(), name);
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

    public void changeControlls(RemoteRobot robot) {
    }
}
