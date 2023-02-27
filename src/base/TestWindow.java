package base;

import gui.Design;
import remoterobot.RobotConsole;

import javax.swing.*;
import java.awt.*;

public class TestWindow extends JFrame {

    private Design windowDesign;
    private RobotConsole console;
    JLabel label;
    public TestWindow() {
        windowDesign = Main.windowDesign;
        windowInit();
        build();
    }

    private void windowInit() {
        setSize(new Dimension(1000, 1000));
        setDefaultCloseOperation(3);
        getContentPane().setLayout(new BorderLayout());
    }

    private void build() {
        label = new JLabel("Erster Text");
        label.setFont(new Font("Arial", 2, 20));
        //add(label);
        console = new RobotConsole();
        add(console.buildConsoleView());
    }

    public void change() {
        label.setText("Zweiter Text");
    }

    public void changeConsole(RobotConsole console) {
        this.console.setConsole(console.getConsole());
    }

}
