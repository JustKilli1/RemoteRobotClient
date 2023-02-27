package gui.controller;

import gui.ClientModel;
import gui.windows.MainWindow;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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


    public class SwitchControlsChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == 1) {
                System.out.println(e.getStateChange());
            }

        }
    }

}
