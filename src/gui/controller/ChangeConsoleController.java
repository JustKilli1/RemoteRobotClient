package gui.controller;

import gui.ClientModel;
import gui.windows.MainWindow;
import remoterobot.RemoteRobot;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controller that handles the requested change of the displayed Console
 *<br>
 * Linked Views:<br>
 * - {@link MainWindow} --> Contains the Console<br>
 *<br>
 * Required Models:<br>
 * - {@link ClientModel} --> Handles the Console change call
 * */
public class ChangeConsoleController {

    private MainWindow view;
    private ClientModel model;

    public ChangeConsoleController(MainWindow view, ClientModel model) {
        this.view = view;
        this.model = model;

        view.addListSelectionListener(new ChangeConsoleListSelectionListener());
    }

    /**
     * Listener Class for handling the ListSelectionChangeEvent
     * */
    private class ChangeConsoleListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            RemoteRobot robot = view.getSelectedRobot();
            view.changeConsole(robot);
            view.buildRobotStatsView(robot);
        }
    }

}
