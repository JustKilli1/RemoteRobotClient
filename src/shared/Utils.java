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
    


}
