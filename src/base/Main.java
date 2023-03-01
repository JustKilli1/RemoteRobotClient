package base;

import java.awt.*;

import javax.swing.*;

import exo.remoterobot.RobotConsole;
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
        windowDesign.setBorder(new RoundBorder(windowDesign.getComponentColor(), 1, 10));

/*        gui.windows.TestWindow window = new gui.windows.TestWindow();*/
/*        window.setVisible(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < 10; i++) {
                        Thread.currentThread().sleep(1000);
                        RobotConsole test = new RobotConsole();
                        test.print("---------------------------------------------------");
                        test.print("Durchlauf: " + i);
                        test.print("Test Nachricht");
                        test.print("---------------------------------------------------");
                        window.changeConsole(test);
                    }
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
                window.change();
            }
        }).start();*/

/*        TestWindow testWindow = new TestWindow();*/


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = MainWindow.getInstance();
                ClientModel model = new ClientModel(windowDesign);
                new AddRemoveRobotController(model);
/*                new ChangeConsoleController(model);*/
                new ChangeControlsController(model);
                window.setVisible(true);
            }
        });
	}


}