package exo.remoterobot;


import base.Main;
import exo.planet.ExoPlanetCommands;
import exo.planet.Ground;
import exo.planet.PlanetView;
import exo.planet.Size;
import gui.ClientModel;
import gui.Design;
import gui.OutputManager;
import gui.controller.ChangeControlsController;
import shared.Utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class RemoteRobot implements Runnable{

    private int id, energy;
    private String name;
    private Design design;
    private boolean isActive;
    private Socket serverCon, groundCon;
    private BufferedReader input;
    private PrintWriter output, outputGround;
    private Position robotPos;
    private MeasureData lastData;
    private Size planetSize;
    private int turnsWithoutCharge, unsuccessfulMoves;
    private boolean orbited;
    private boolean landed = orbited = false;


    public RemoteRobot(int id, String name, Design design) {
    	this.id = id;
        this.name = name;
        this.design = design;
        isActive = true;
        energy = 100;
    }


    public String toString() {
        return id + ". " + name;
    }

    public boolean isAtPos(Position pos) {
        return robotPos.equals(pos);
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
            int port = Main.getPort() > 0 ? Main.getPort() : 8150;
            int portGround = Main.getPortGround() > 0 ? Main.getPortGround() : 7832;
            System.out.println(Main.getPort());
            System.out.println(Main.getPortGround());
            groundCon = new Socket("0.0.0.0", portGround);
            serverCon = new Socket("0.0.0.0", port);
            output = new PrintWriter(serverCon.getOutputStream(), true);
            outputGround = new PrintWriter(groundCon.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));

            while(isActive) {
                if(!orbited) sendCommand(ExoPlanetCommands.orbit(this));
                String response = input.readLine();
                System.out.println(response);
                if(ChangeControlsController.isEnableControls()) {
                    reactToResponse(response);
                } else {
                    String nextCommand = reactToResponse(response);
                    if(nextCommand != null) sendCommand(nextCommand);
                    turnsWithoutCharge++;
                }
                if(!landed) {
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    robotPos = new Position(random.nextInt(0, planetSize.getWidth()), random.nextInt(0, planetSize.getHeight()));
                    sendCommand(ExoPlanetCommands.land(robotPos));
                }
            }
            closeConnection();
            System.out.println(name + " Connection closed");
        } catch (Exception ex) {
            System.out.println("Es konnte keine Verbindung zum Server hergestellt werden. ._.");
            ex.printStackTrace();
            closeConnection();
        }
    }

    public String reactToResponse(String lastResponse) {
        if(lastResponse == null) return null;
        String[] responseSplit = lastResponse.split(":");
        Rotation randomRotation = ThreadLocalRandom.current().nextInt(0, 10) <= 2 ? Rotation.RIGHT : Rotation.LEFT;
        switch(responseSplit[0]) {
            case "init":
                planetSize = Size.parse(responseSplit[1]).get();
                OutputManager.setPlanetView(new PlanetView(planetSize, design));
                orbited = true;
                return null;
            case "landed":
                lastData = MeasureData.parse(responseSplit[1]).get();
                if(!OutputManager.alreadyMeasured(robotPos)) sendToGround(robotPos, lastData);
                OutputManager.addGround(robotPos, lastData);
                OutputManager.changeRobotPos(this, robotPos);
                landed = true;
                return ExoPlanetCommands.scan();
            case "scaned":
                if(turnsWithoutCharge >= 16 && (lastData.getGround().equals(Ground.SAND) || lastData.getGround().equals(Ground.PFLANZEN))) {
                    turnsWithoutCharge = 0;
                    return ExoPlanetCommands.charge(10);
                }
                lastData = MeasureData.parse(responseSplit[1]).get();
                if(lastData.getGround().equals(Ground.NICHTS))return ExoPlanetCommands.rotate(randomRotation);
                if(!OutputManager.alreadyMeasured(Utils.getScanPos(robotPos.clone()))) sendToGround(Utils.getScanPos(robotPos.clone()), lastData);
                OutputManager.addGround(Utils.getScanPos(robotPos.clone()), lastData);
                if(lastData.getTemperature() > 100) return ExoPlanetCommands.rotate(randomRotation);
                if(lastData.getTemperature() < -30) return ExoPlanetCommands.rotate(randomRotation);
                if(lastData.getGround().equals(Ground.LAVA)) return ExoPlanetCommands.rotate(randomRotation);
                if(ClientModel.isRobotAtPos(Utils.getScanPos(robotPos.clone()))) return ExoPlanetCommands.rotate(randomRotation);
                return ExoPlanetCommands.move();
            case "moved":
                Position newPos = Position.parse(responseSplit[1]).get();
                if(robotPos.equals(newPos)) unsuccessfulMoves++;
                if(unsuccessfulMoves > 2) {
                    unsuccessfulMoves = 0;
                    return ExoPlanetCommands.rotate(randomRotation);
                }
                robotPos = newPos;
                OutputManager.changeRobotPos(this, robotPos);
                return ExoPlanetCommands.scan();
            case "mvscaned":
                lastData = MeasureData.parse(responseSplit[1]).get();
                robotPos = Position.parse(responseSplit[2]).get();
                if(!OutputManager.alreadyMeasured(Utils.getScanPos(robotPos.clone()))) sendToGround(Utils.getScanPos(robotPos.clone()), lastData);
                OutputManager.addGround(Utils.getScanPos(robotPos.clone()), lastData);
                OutputManager.changeRobotPos(this, robotPos);
                break;
            case "rotated":
                robotPos.setDir(Direction.valueOf(responseSplit[1]));
                OutputManager.changeRobotPos(this, robotPos);
                return ExoPlanetCommands.scan();
            case "crashed", "error":
                System.out.println(lastResponse);
                JOptionPane.showMessageDialog(null, name + " ist gecrasht");
                ClientModel.deleteRemoteRobot(this);
                break;
            case "charged":
                String split[] = responseSplit[1].split("\\|");
                if(ChangeControlsController.isEnableControls()) JOptionPane.showMessageDialog(null, "Aufladen beendet. Akkustand: " + split[1], name, JOptionPane.INFORMATION_MESSAGE);
                return ExoPlanetCommands.scan();
        }
        return null;
    }

    public void sendCommand(String cmd) {
        output.println(cmd);
    }

    public void sendToGround(Position pos, MeasureData measureData) {
        outputGround.println(id + ":" + measureData);
        OutputManager.addScannedField(pos);
        System.out.println(name + ": Messdaten an Ground geschickt");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
        OutputManager.removeRobot(this);
    }

    public boolean equals(RemoteRobot robot) {
        if(id != robot.getId()) return false;
        if(name.equals(robot.getName())) return false;
        return true;
    }

    public int getEnergy() {
        return energy;
    }
}
