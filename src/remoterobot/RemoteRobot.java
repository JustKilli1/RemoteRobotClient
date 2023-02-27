package remoterobot;

import shared.Utils;

import javax.swing.*;

import gui.Design;

public class RemoteRobot implements Runnable{

    private int id;
    private double temperature;
    private String name;
    private boolean isActive;
    private Position robotPos;
    private RobotConsole console;


    public RemoteRobot(int id, String name) {
    	this.id = id;
        this.name = name;
        this.console = new RobotConsole();
        temperature = 12;
    }


    public String toString() {
        return id + ". " + name;
    }

    public boolean isAtPos(Position pos) {
        return true;
    }

    public void changeActive() {
        isActive = !isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void run() {
    	
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getTemperature() {
        return temperature;
    }

    public RobotConsole getRobotConsole() { return console; }
}
