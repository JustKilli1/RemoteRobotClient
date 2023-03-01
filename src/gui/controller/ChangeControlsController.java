package gui.controller;

import exo.planet.ExoPlanetCommands;
import exo.remoterobot.Rotation;
import gui.ClientModel;
import gui.windows.MainWindow;

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

    public ChangeControlsController(MainWindow view, ClientModel model) {
        this.view = view;
        this.model = model;
        view.addItemChangeListener(new SwitchControlsChangeListener());

    }


    private class SwitchControlsChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == 1) {
                System.out.println(e.getStateChange());
                System.out.println(view.getSelectedRobot().getName());
            }

        }
    }

    private class RobotControlsKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_RIGHT) {

            } else if(e.getKeyCode()== KeyEvent.VK_Q) {
                robot.sendCommand(ExoPlanetCommands.rotate(Rotation.LEFT));
            } else if(e.getKeyCode()== KeyEvent.VK_E) {
                robot.sendCommand(ExoPlanetCommands.rotate(Rotation.RIGHT));
            } else if(e.getKeyCode()== KeyEvent.VK_SPACE) {
                robot.sendCommand(ExoPlanetCommands.moveScan());
            } else if(e.getKeyCode()== KeyEvent.VK_S) {
                robot.sendCommand(ExoPlanetCommands.scan());
            }
        }
    }

}
