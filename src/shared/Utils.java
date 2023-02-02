package shared;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Utils {

    public static Optional<Integer> toInt(String intAsStr) {
        try {
            return Optional.ofNullable(Integer.parseInt(intAsStr));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public static JTextArea createNewConsole() {
        JTextArea console = new JTextArea(10, 1);
        console.setPreferredSize(new Dimension(500, 400));
        console.setRows(10);
        console.setEditable(false);
        return console;
    }

}
