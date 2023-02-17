package shared;

import java.awt.Dimension;
import java.util.Optional;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import base.Main;
import gui.Design;

public class Utils {

    public static Optional<Integer> toInt(String intAsStr) {
        try {
            return Optional.ofNullable(Integer.parseInt(intAsStr));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
    
    /**
     * Creates a Console with the given Design
     * @param design Design of the Returning Console
     * @return Console as JTextArea
     * */
    public static JTextArea createNewConsole() {
        JTextArea console = new JTextArea(10, 1);
        console.setPreferredSize(new Dimension(500, 400));
        console.setEditable(false);
        console.setFont(Main.windowDesign.getTextFont());
        console.setBackground(Main.windowDesign.getComponentColor());
        console.setForeground(Main.windowDesign.getTextColor());
        return console;
    }
    
    /**
     * Return a Console inside a JScrollPane
     * @param target Target console
     * @param design Design of the Container
     * @return Given Console inside of a JScollPane Container
     * */
    public static JScrollPane createConsoleContainer(JTextArea target) {
    	JScrollPane container = new JScrollPane(target);
        container.setBackground(Main.windowDesign.getComponentColor());
        container.setPreferredSize(new Dimension(600, 500));
        container.setBorder(Main.windowDesign.getBorder());
        return container;
    }

}
