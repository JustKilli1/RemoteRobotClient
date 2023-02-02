package base;

import gui.ClientModel;
import gui.RoundBorder;
import gui.controller.AddRemoveRobotController;
import gui.controller.ChangeConsoleController;
import gui.Design;
import gui.windows.MainWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Design design = new Design();
        design.setBackgroundColor(new Color(25, 25, 25));
        design.setComponentColor(new Color(32, 56, 82));
        design.setTextColor(new Color(220, 220, 220));
        design.setHeaderColor(new Color(220, 220, 220));
        design.setTextFont(new Font("Arial", Font.PLAIN, 13));
        design.setHeaderFont(new Font("Arial", Font.BOLD, 17));
        design.setBorder(new RoundBorder(design.getComponentColor(),1,10,0));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow(design);
                ClientModel model = new ClientModel();
                new AddRemoveRobotController(window, model);
                new ChangeConsoleController(window, model);
                window.setVisible(true);
            }
        });

    }
}