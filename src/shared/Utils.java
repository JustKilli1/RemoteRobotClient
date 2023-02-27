package shared;

import java.awt.*;
import java.util.Optional;

import javax.swing.*;

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

    public static JPanel createFillerPanel(Design design, boolean componentBackground) {
        JPanel panel = new JPanel();
        Color background = componentBackground ? design.getComponentColor() : design.getBackgroundColor();
        panel.setBackground(background);
        return panel;
    }
    


}
