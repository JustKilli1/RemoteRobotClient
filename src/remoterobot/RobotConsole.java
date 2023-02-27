package remoterobot;

import base.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class RobotConsole {

    public static final String CONSOLE_MESSAGE_PREFIX = ">> ";
    private JTextArea console;

    public RobotConsole() {
        buildConsole();
    }

    /**
     * Prints a List of Messages to the intern Console
     * @param messages Messages that get printed to console
     * */
    public void print(List<String> messages) {
        String displayMsg;
        for(int i = 0; i < messages.size(); i++) {
            displayMsg = CONSOLE_MESSAGE_PREFIX + messages.get(i);
            if(i + 1 != messages.size()) displayMsg += "\n";
            console.append(displayMsg);
        }
    }

    /**
     * Prints a Single Message to the Console
     * @param message Message that gets printed
     * */
    public void print(String message) {
        print(Arrays.asList(message));
    }

    /**
     * Return a Console inside a JScrollPane
     * @return Given Console inside of a JScrollPane Container
     * */
    public JScrollPane buildConsoleView() {
        JScrollPane container = new JScrollPane(console);
        container.setBackground(Main.windowDesign.getComponentColor());
        container.setPreferredSize(new Dimension(600, 500));
        container.setBorder(Main.windowDesign.getBorder());
        return container;
    }

    public JTextArea getConsole() {
        return console;
    }

    /**
     * Builds a Console with the given Design
     * */
    private void buildConsole() {
        console = new JTextArea(10, 1);
        console.setPreferredSize(new Dimension(500, 400));
        console.setEditable(false);
        console.setFont(Main.windowDesign.getTextFont());
        console.setBackground(Main.windowDesign.getComponentColor());
        console.setForeground(Main.windowDesign.getTextColor());
    }

}