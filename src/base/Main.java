package base;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import gui.ClientModel;
import gui.Design;
import gui.RoundBorder;
import gui.controller.AddRemoveRobotController;
import gui.controller.ChangeConsoleController;
import gui.controller.ChangeControlsController;
import gui.windows.MainWindow;

public class Main {

    //TODO Non static machen
	public static Design windowDesign;
	
	public static void main(String[] args) {
        System.out.println("Hello world!");
        windowDesign = new Design();
        windowDesign.setBackgroundColor(new Color(25, 25, 25));
        windowDesign.setComponentColor(new Color(32, 56, 82));
        windowDesign.setTextColor(new Color(220, 220, 220));
        windowDesign.setHeaderColor(new Color(220, 220, 220));
        windowDesign.setTextFont(new Font("Arial", Font.PLAIN, 13));
        windowDesign.setHeaderFont(new Font("Arial", Font.BOLD, 17));
        windowDesign.setBorder(new RoundBorder(windowDesign.getComponentColor(), 1, 10, 0));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                /*BorderPanels borderPanels = new BorderPanels();*/
                MainWindow window = new MainWindow();
                ClientModel model = new ClientModel();
                new AddRemoveRobotController(window, model);
                new ChangeConsoleController(window, model);
                new ChangeControlsController(window, model);
                window.setVisible(true);
            }
        });
	}


}