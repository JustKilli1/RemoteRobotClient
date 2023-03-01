package gui;

import exo.planet.Ground;
import exo.planet.PlanetView;
import exo.remoterobot.MeasureData;
import exo.remoterobot.Position;
import exo.remoterobot.RemoteRobot;
import gui.windows.MainWindow;

public class OutputManager {
    private static RemoteRobot activeRobot;

    public static void addGround(Position pos, MeasureData measureData) {
        MainWindow.getInstance().addGround(pos, measureData);
    }

    public static void changeRobotPos(RemoteRobot callingRobot, Position pos) {
        MainWindow.getInstance().changeRobotPos(callingRobot, pos);
    }

    public static void rotateRobot(RemoteRobot robot, Position pos) {
        MainWindow.getInstance().getPlanetView().rotateRobot(robot, pos);
    }

    public static void setActiveRobot(RemoteRobot callingRobot) {
        if(activeRobot != null) activeRobot.setPlanetView(MainWindow.getInstance().getPlanetView());
        activeRobot = callingRobot;
        MainWindow.getInstance().changePlanetView(activeRobot.getPlanetView());
    }

    public static void setPlanetView(PlanetView planetView) {
        if(MainWindow.getInstance().getPlanetView() == null)
            MainWindow.getInstance().setPlanetView(planetView);
    }
}
