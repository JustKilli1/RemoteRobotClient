package gui.controller;

import exo.planet.ExoPlanetCommands;
import exo.remoterobot.RemoteRobot;
import exo.remoterobot.Rotation;
import gui.ClientModel;
import gui.windows.MainWindow;
import shared.Utils;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controller that's responsible for Adding and Removing new Robots<br>
 *<br>
 * Linked Views:<br>
 * - {@link MainWindow} --> Contains the List that displays the Robots to the User<br>
 *<br>
 * Required Models:<br>
 * - {@link ClientModel} --> Manages the Creation of new Robots
 * */
public class ChangeControlsController {

    private MainWindow view;
    private ClientModel model;
    private static boolean enableControls;

    public ChangeControlsController(ClientModel model) {
        this.view = MainWindow.getInstance();
        this.model = model;
        enableControls = true;
        view.addItemChangeListener(new SwitchControlsChangeListener());
        view.addWindowKeyListener(new RobotControlsKeyListener());
    }


    public class SwitchControlsChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == 1) {
                enableControls = true;
            } else {
                enableControls = false;
                ClientModel.sendCommandToAllRobots(ExoPlanetCommands.scan());
            }

        }
    }

    public class RobotControlsKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            RemoteRobot robot = view.getSelectedRobot();
            if(robot == null) return;
            if(e.getKeyCode()== KeyEvent.VK_RIGHT) {

            } else if(e.getKeyCode()== KeyEvent.VK_Q) {
                if(enableControls) robot.sendCommand(ExoPlanetCommands.rotate(Rotation.LEFT));
            } else if(e.getKeyCode()== KeyEvent.VK_E) {
                if(enableControls) robot.sendCommand(ExoPlanetCommands.rotate(Rotation.RIGHT));
            } else if(e.getKeyCode()== KeyEvent.VK_D) {
                if(enableControls) robot.sendCommand(ExoPlanetCommands.move());
            } else if(e.getKeyCode()== KeyEvent.VK_S) {
                    if(enableControls) robot.sendCommand(ExoPlanetCommands.scan());
            } else if(e.getKeyCode() == KeyEvent.VK_F) {
                if(enableControls) {
                    String duration = JOptionPane.showInputDialog(view, "Bitte Dauer eingeben", "in sekunden");
                    if(duration == null || duration.length() == 0) return;
                    robot.sendCommand(ExoPlanetCommands.charge(Utils.toInt(duration).get()));
                }
            }
        }
    }

    public static boolean isEnableControls() {return enableControls;}

}
