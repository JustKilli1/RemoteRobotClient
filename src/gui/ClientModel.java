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

    public RemoteRobot createNewRobot(String name) {
        //TODO BUG ID wird nicht richtig gesetzt nach löschen eines Robots. Auf field var umstellen anstatt list size zu benutzen
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

    public void changeControls(RemoteRobot robot) {
    }
}
