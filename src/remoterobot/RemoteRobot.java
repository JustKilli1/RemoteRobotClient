package remoterobot;

import shared.Utils;

import javax.swing.*;

public class RemoteRobot implements Runnable{

    private int id;
    private String name;
    private boolean isActive;
    private Position robotPos;
    private JTextArea console;

    public RemoteRobot(String name) {
        this.name = name;
        console = Utils.createNewConsole();
        console.append(">> Robot created\n");
        console.append(">> Robot name set to \"" + name + "\"\n");
    }

    public String toString() {
        return this.name;
    }

    public boolean isAtPos(Position pos) {
        return true;
    }

    public void changeActive() {
        this.isActive = !this.isActive;
    }

    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void run() {

    }

    public JTextArea getConsole() { return console; }
}
