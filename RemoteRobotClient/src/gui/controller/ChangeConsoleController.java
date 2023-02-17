package gui.controller;

import gui.ClientModel;
import gui.windows.MainWindow;
import remoterobot.RemoteRobot;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChangeConsoleController {

    private MainWindow view;
    private ClientModel model;

    public ChangeConsoleController(MainWindow view, ClientModel model) {
        this.view = view;
        this.model = model;

        view.addListSelectionListener(new ChangeConsoleListSelectionListener());
    }

    private class ChangeConsoleListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            RemoteRobot robot = view.getSelectedRobot();
            view.changeConsole(robot);
        }
    }

}
