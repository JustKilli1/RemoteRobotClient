package gui.controller;

import gui.ClientModel;
import gui.windows.MainWindow;
import remoterobot.RemoteRobot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller that's responsible for Adding and Removing new Robots<br>
 *<br>
 * Linked Views:<br>
 * - {@link MainWindow} --> Contains the List that displays the Robots to the User<br>
 *<br>
 * Required Models:<br>
 * - {@link ClientModel} --> Manages the Creation of new Robots
 * */
public class AddRemoveRobotController {

    private MainWindow view;
    private ClientModel model;


    public AddRemoveRobotController(MainWindow view, ClientModel model) {
        this.view = view;
        this.model = model;
        this.view.addRobotAddActionListener(new AddActionListener());
        this.view.addRobotRemoveActionListener(new RemoveActionListener());
    }

    /**
     * Listener Class for handling the Add Button ActionEvent
     * */
    public class AddActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String robotName = JOptionPane.showInputDialog(view, "Bitte Robotername eingeben", "Robotername");
            if(robotName == null || robotName.length() == 0) return;
            RemoteRobot robot = model.createNewRobot(robotName);
            view.addRobot(robot);
        }
    }
    /**
     * Listener Class for handling the Remove Button ActionEvent
     * */
    private class RemoveActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            RemoteRobot target = view.getSelectedRobot();
            if(target == null) return;
            view.removeRobot(target);
            model.deleteRemoteRobot(target);
        }
    }
}
