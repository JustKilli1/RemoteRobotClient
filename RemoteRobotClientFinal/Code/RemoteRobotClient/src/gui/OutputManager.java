package gui;

import exo.planet.PlanetView;
import exo.remoterobot.MeasureData;
import exo.remoterobot.Position;
import exo.remoterobot.RemoteRobot;
import gui.windows.MainWindow;

public class OutputManager {

    public static void addGround(Position pos, MeasureData measureData) {
        MainWindow.getInstance().addGround(pos, measureData);
    }

    public static boolean alreadyMeasured(Position pos) {
        return MainWindow.getInstance().getPlanetView().alreadyMeasured(pos);
    }
    public static void addScannedField(Position pos) {
        MainWindow.getInstance().getPlanetView().addScannedField(pos);
    }

    public static void changeRobotPos(RemoteRobot callingRobot, Position pos) {
        MainWindow.getInstance().getPlanetView().changeRobotPos(callingRobot, pos);
    }

    public static void setPlanetView(PlanetView planetView) {
        if(MainWindow.getInstance().getPlanetView() == null)
            MainWindow.getInstance().setPlanetView(planetView);
    }
    public static void removeRobot(RemoteRobot target) {
        MainWindow.getInstance().removeRobot(target);
    }
}
