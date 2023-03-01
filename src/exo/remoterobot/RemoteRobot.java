package exo.remoterobot;


import exo.planet.ExoPlanetCommands;
import exo.planet.Ground;
import exo.planet.PlanetView;
import exo.planet.Size;
import shared.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RemoteRobot implements Runnable{

    private int id;
    private double temperature;
    private String name;
    private boolean isActive;
    private Socket serverCon;
    private BufferedReader input;
    private PrintWriter output;
    private Position robotPos;
    private RobotConsole console;
    private PlanetView planetView;
    private MeasureData lastData;


    public RemoteRobot(int id, String name) {
    	this.id = id;
        this.name = name;
        this.console = new RobotConsole();
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
            isActive = true;

            sendCommand(ExoPlanetCommands.orbit(this));
            sendCommand(ExoPlanetCommands.land(new Position(0, 0, Direction.EAST)));

            String response;
            while(((response = input.readLine()) != null) || isActive) {
                System.out.println(response);
                reactToResponse(response);
            }
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
                planetView = new PlanetView(size);
                robotPos = new Position(0, 0, Direction.EAST);
                break;
            case "landed":
                lastData = MeasureData.parse(responseSplit[1]).get();
                planetView.addGround(robotPos, lastData.getGround());
                break;
            case "scaned":
                lastData = MeasureData.parse(responseSplit[1]).get();
                planetView.addGround(Utils.getScanPos(robotPos.clone()), lastData.getGround());
                break;
            case "moved":
                robotPos = Position.parse(responseSplit[1]).get();
                planetView.changeRobotPos(robotPos);
                break;
            case "mvscaned":
                lastData = MeasureData.parse(responseSplit[1]).get();
                if(lastData.getGround().equals(Ground.NICHTS))return;
                robotPos = Position.parse(responseSplit[2]).get();
                planetView.addGround(Utils.getScanPos(robotPos.clone()), lastData.getGround());
                planetView.changeRobotPos(robotPos);
                break;
            case "rotated":
                robotPos.setDir(Direction.valueOf(responseSplit[1]));
                break;
            case "crashed":
                System.out.println(lastResponse);
                break;
            case "error":
                System.out.println(lastResponse);
                break;
        }
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

    public void closeConnection() {
        try {
            input.close();
            output.close();
            if (!serverCon.isClosed()) serverCon.close();
            isActive = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
