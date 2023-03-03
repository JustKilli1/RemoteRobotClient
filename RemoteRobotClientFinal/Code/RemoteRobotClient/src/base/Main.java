package base;

import java.awt.*;

import javax.swing.*;

import gui.ClientModel;
import gui.Design;
import gui.RoundBorder;
import gui.controller.AddRemoveRobotController;
import gui.controller.ChangeControlsController;
import gui.windows.MainWindow;
import shared.Utils;


public class Main {

    //TODO Non static machen
	public static Design windowDesign;
    private static boolean useJson = false;
    private static int maxRobots, port, portGround;
	
	public static void main(String[] args) {
        System.out.println("Hello world!");
        //port:[int]
        //portGround:[int]
        //maxRobot:[int]
        for(String arg : args) {
            if(arg.toLowerCase().contains("usejson")) useJson = true;
            if(arg.toLowerCase().contains("maxrobot")) {
                String[] split = arg.split(":");
                maxRobots = Utils.toInt(split[1]).get();
            }
            if(arg.toLowerCase().contains("port") && !arg.toLowerCase().contains("ground")) {
                String[] split = arg.split(":");
                port = Utils.toInt(split[1]).get();

            }
            if(arg.toLowerCase().contains("portground")) {
                String[] split = arg.split(":");
                portGround = Utils.toInt(split[1]).get();
            }
        }
        if(maxRobots <= 0) maxRobots = 5;

        windowDesign = new Design();
        windowDesign.setBackgroundColor(new Color(25, 25, 25));
        windowDesign.setComponentColor(new Color(32, 56, 82));
        windowDesign.setTextColor(new Color(220, 220, 220));
        windowDesign.setHeaderColor(new Color(220, 220, 220));
        windowDesign.setTextFont(new Font("Arial", Font.PLAIN, 13));
        windowDesign.setHeaderFont(new Font("Arial", Font.BOLD, 17));
        windowDesign.setBorder(new RoundBorder(windowDesign.getComponentColor(), 1, 10));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = MainWindow.getInstance();
                ClientModel model = new ClientModel(windowDesign);
                new AddRemoveRobotController(model);
                new ChangeControlsController(model);
                window.setVisible(true);
            }
        });
	}

    public static boolean isUseJson() {
        return useJson;
    }

    public static int getMaxRobots() {
        return maxRobots;
    }

    public static int getPort() {
        return port;
    }

    public static int getPortGround() {
        return portGround;
    }
}