package gui.controller;

import gui.ClientModel;
import gui.windows.MainWindow;
import remoterobot.RemoteRobot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRemoveRobotController {

    private MainWindow view;
    private ClientModel model;

    public AddRemoveRobotController(MainWindow view, ClientModel model) {
        this.view = view;
        this.model = model;
        this.view.addRobotAddActionListener(new AddActionListener());
        this.view.addRobotRemoveActionListener(new RemoveActionListener());
    }

    public class AddActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String robotName = JOptionPane.showInputDialog(view, "Bitte Robotername eingeben", "Robotername");
            if(robotName.length() == 0) return;
            RemoteRobot robot = model.createNewRobot(robotName);
            view.addRobot(robot);
        }
    }

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
