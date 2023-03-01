package exo.remoterobot;


import exo.planet.ExoPlanetCommands;
import exo.planet.Ground;
import exo.planet.PlanetView;
import exo.planet.Size;
import gui.Design;
import gui.OutputManager;
import gui.controller.ChangeControlsController;
import shared.Utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RemoteRobot implements Runnable{

    private int id;
    private double temperature;
    private String name;
    private Design design;
    private boolean isActive;
    private Socket serverCon;
    private BufferedReader input;
    private PrintWriter output;
    private Position robotPos;
    private RobotConsole console;
    private PlanetView planetView;
    private MeasureData lastData;


    public RemoteRobot(int id, String name, Design design) {
    	this.id = id;
        this.name = name;
        this.design = design;
        this.console = new RobotConsole();
        isActive = true;
        console.print("Robot \"" + name + "\" created");
        temperature = 12;
    }


    public String toString() {
        return id + ". " + name;
    }

    public boolean isAtPos(Position pos) {
        return true;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void run() {
        try {
            serverCon = new Socket("localhost", 8150);
            output = new PrintWriter(serverCon.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));

            sendCommand(ExoPlanetCommands.orbit(this));
            sendCommand(ExoPlanetCommands.land(new Position(0, 0, Direction.EAST)));


            while(isActive) {
                String response = input.readLine();
                System.out.println(response);
                if(ChangeControlsController.isEnableControls()) {
                    reactToResponse(response);
                } else {
                    sendCommand(getNextCommand(response));
                }
            }
            closeConnection();
            System.out.println("Connection closed");
        } catch (Exception ex) {
            System.out.println("Es konnte keine Verbindung zum Server hergestellt werden. ._.");
            ex.printStackTrace();
            closeConnection();
        }
    }

    public void reactToResponse(String lastResponse) {
        if(lastResponse == null) return;
        String[] responseSplit = lastResponse.split(":");
        switch(responseSplit[0]) {
            case "init":
                Size size = Size.parse(responseSplit[1]).get();
                robotPos = new Position(0, 0, Direction.EAST);
                OutputManager.setPlanetView(new PlanetView(size, design, robotPos, this));
                break;
            case "landed":
                lastData = MeasureData.parse(responseSplit[1]).get();
                OutputManager.addGround(robotPos, lastData);
                break;
            case "scaned":
                lastData = MeasureData.parse(responseSplit[1]).get();
                OutputManager.addGround(Utils.getScanPos(robotPos.clone()), lastData);
                break;
            case "moved":
                robotPos = Position.parse(responseSplit[1]).get();
                OutputManager.changeRobotPos(this, robotPos);
                break;
            case "mvscaned":
                lastData = MeasureData.parse(responseSplit[1]).get();
                robotPos = Position.parse(responseSplit[2]).get();
                OutputManager.addGround(Utils.getScanPos(robotPos.clone()), lastData);
                OutputManager.changeRobotPos(this, robotPos);
                break;
            case "rotated":
                robotPos.setDir(Direction.valueOf(responseSplit[1]));
                OutputManager.rotateRobot(this, robotPos);
                break;
            case "crashed":
                System.out.println(lastResponse);
                exit();
                break;
            case "error":
                System.out.println(lastResponse);
                exit();
                break;
            case "charged":
                String split[] = responseSplit[1].split("\\|");
                JOptionPane.showMessageDialog(null, "Aufladen beendet. Akkustand: " + split[1]);
                break;
        }
    }

    private String getNextCommand(String lastResponse) {
        String[] responseSplit = lastResponse.split(":");

        switch(responseSplit[0]) {
            case "init":
                Size size = Size.parse(responseSplit[1]).get();
                robotPos = new Position(0, 0, Direction.EAST);
                OutputManager.setPlanetView(new PlanetView(size, design, robotPos, this));
                return ExoPlanetCommands.land(robotPos);
            case "landed":
                lastData = MeasureData.parse(responseSplit[1]).get();
                OutputManager.addGround(robotPos, lastData);
                return ExoPlanetCommands.scan();
            case "scaned":
                lastData = MeasureData.parse(responseSplit[1]).get();
                if(lastData.getGround().equals(Ground.NICHTS))return ExoPlanetCommands.rotate(Rotation.RIGHT);
                Position fieldPos = robotPos;
                int setX = 0;
                int setY = 0;
                System.out.println(fieldPos);
                if(fieldPos.getDir().equals(Direction.EAST)) setX = 1;
                if(fieldPos.getDir().equals(Direction.WEST)) setX = -1;
                if(fieldPos.getDir().equals(Direction.NORTH)) setY = -1;
                if(fieldPos.getDir().equals(Direction.SOUTH)) setY = 1;
                fieldPos.setX(fieldPos.getX() + setX);
                fieldPos.setY(fieldPos.getY() + setY);
                OutputManager.addGround(fieldPos, lastData);
                if(lastData.getTemperature() > 100) return ExoPlanetCommands.rotate(Rotation.RIGHT);
                if(lastData.getTemperature() < -20) return ExoPlanetCommands.rotate(Rotation.RIGHT);
                if(lastData.getGround().equals(Ground.LAVA)){
                    return ExoPlanetCommands.rotate(Rotation.RIGHT);
                }
                return ExoPlanetCommands.move();
            case "moved":
                robotPos = Position.parse(responseSplit[1]).get();
                OutputManager.changeRobotPos(this, robotPos);
                return ExoPlanetCommands.scan();
            case "mvscaned":
                lastData = MeasureData.parse(responseSplit[1]).get();
                robotPos = Position.parse(responseSplit[2]).get();
                OutputManager.addGround(robotPos, lastData);
                OutputManager.changeRobotPos(this, robotPos);
                if(lastData.getGround().equals(Ground.LAVA)) return ExoPlanetCommands.rotate(Rotation.RIGHT);
                return ExoPlanetCommands.moveScan();
            case "rotated":
                robotPos.setDir(Direction.valueOf(responseSplit[1]));
                OutputManager.rotateRobot(this, robotPos);
                return ExoPlanetCommands.scan();
            case "crashed":
                System.out.println(lastResponse);
                return ExoPlanetCommands.exit();
            case "error":
                System.out.println(lastResponse);
                break;
        }
        return ExoPlanetCommands.exit();
    }

    public void sendCommand(String cmd) {
        output.println(cmd);
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

    public PlanetView getPlanetView() {
        return planetView;
    }

    public void closeConnection() {
        try {
            input.close();
            output.close();
            if (!serverCon.isClosed()) serverCon.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void exit() {
        isActive = false;
        sendCommand(ExoPlanetCommands.exit());
    }

    public boolean equals(RemoteRobot robot) {
        if(id != robot.getId()) return false;
        if(name.equals(robot.getName())) return false;
        return true;
    }

    public void setPlanetView(PlanetView planetView) {
        this.planetView = planetView;
    }
}
