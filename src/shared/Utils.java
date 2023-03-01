package shared;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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

    public static BufferedImage rotateImage(BufferedImage image, Double degrees) {
        // Calculate the new size of the image based on the angle of rotaion
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        // Create a new image
        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();
        // Calculate the "anchor" point around which the image will be rotated
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;
        // Transform the origin point around the anchor point
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        // Paint the originl image
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }
    


}
