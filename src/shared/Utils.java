package shared;

import java.awt.*;
import java.util.Optional;

import javax.swing.*;

import base.Main;
import exo.remoterobot.Direction;
import exo.remoterobot.Position;
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

    public static Position getScanPos(Position base) {
        int setX = 0;
        int setY = 0;
        if(base.getDir().equals(Direction.EAST)) setX = 1;
        if(base.getDir().equals(Direction.WEST)) setX = -1;
        if(base.getDir().equals(Direction.NORTH)) setY = -1;
        if(base.getDir().equals(Direction.SOUTH)) setY = 1;
        base.setX(base.getX() + setX);
        base.setY(base.getY() + setY);
        return base;
    }
    


}
