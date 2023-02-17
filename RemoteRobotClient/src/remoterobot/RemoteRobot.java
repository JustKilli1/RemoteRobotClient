package remoterobot;

import shared.Utils;

import javax.swing.*;

import gui.Design;

public class RemoteRobot implements Runnable{

    private int id;
    private String name;
    private boolean isActive;
    private Position robotPos;
    private JTextArea console;


    public RemoteRobot(int id, String name, JTextArea console) {
    	this.id = id;
        this.name = name;
        this.console = console;
        this.console.append(">> Robot created\n");
        this.console.append(">> Robot name set to \"" + name + "\"\n");
    }
    
    public RemoteRobot(int id, String name) {
    	this(id, name, Utils.createNewConsole());
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

    public JTextArea getConsole() { return console; }
}
