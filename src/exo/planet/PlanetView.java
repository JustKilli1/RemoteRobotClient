package exo.planet;

import exo.remoterobot.Direction;
import exo.remoterobot.MeasureData;
import exo.remoterobot.Position;
import exo.remoterobot.RemoteRobot;
import gui.Design;
import shared.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static exo.remoterobot.Direction.EAST;
import static exo.remoterobot.Direction.NORTH;

public class PlanetView {

    private Size planetSize;
    private Design design;
    private JPanel[][] planetFields;
    private JLabel robotIcon;
    private JPanel planetView;
    private Map<Integer, Position> robotIconPos;

    public PlanetView(Size planetSize, Design design, Position pos, RemoteRobot robot) {
        this.planetSize = planetSize;
        this.design = design;
        robotIconPos = new HashMap<>();
        robotIcon = new JLabel();
        robotIcon.setIcon(new ImageIcon("RobotIcon_SOUTH.png"));
        build(robot, pos);
    }

    private PlanetView(Size planetSize, Design design, JPanel[][] planetFields, JLabel robotIcon, JPanel planetView) {
        this.planetSize = planetSize;
        this.design = design;
        this.planetFields = planetFields;
        this.robotIcon = robotIcon;
        this.planetView = planetView;
    }

    public void build(RemoteRobot robot, Position pos) {
        planetView = new JPanel(new GridLayout(planetSize.getHeight(), planetSize.getWidth()));
        planetFields = new JPanel[planetSize.getHeight()][planetSize.getWidth()];
        for(int i = 0; i < planetSize.getHeight(); i++) {
            for(int z = 0; z < planetSize.getWidth(); z++) {
                JPanel fieldPanel = new JPanel();
                fieldPanel.setBackground(Ground.NICHTS.getColor());
                fieldPanel.setBackground(design.getComponentColor());
                planetFields[i][z] = fieldPanel;
                planetView.add(fieldPanel);
            }
        }
        planetFields[0][0].add(buildRobotIcon(robot, pos));
    }

    public void addGround(Position pos, MeasureData measureData) {
        if(!pos.inBounds()) return;
        planetFields[pos.getY()][pos.getX()].setBackground(measureData.getGround().getColor());
        JLabel temp = new JLabel();
        temp.setText(Math.ceil(measureData.getTemperature()) + "");
        temp.setForeground(Color.WHITE);
        temp.setFont(new Font("Arial", 1, 12));
        planetFields[pos.getY()][pos.getX()].add(temp);
        planetFields[pos.getY()][pos.getX()].validate();
    }

    public void setRobotPos(RemoteRobot robot, Position pos) {
        planetFields[pos.getY()][pos.getX()].add(buildRobotIcon(robot, pos));
        planetFields[pos.getY()][pos.getX()].validate();
        robotIconPos.put(robot.getId(), pos);
    }

    public void rotateRobot(RemoteRobot robot, Position pos) {
        removeRobot(robot);
        setRobotPos(robot, pos);
    }

    public void changeRobotPos(RemoteRobot robot, Position pos) {
        if(robotIconPos.containsKey(robot.getId())) {
            removeRobot(robot);
        }
        setRobotPos(robot, pos);
    }

    private JLabel buildRobotIcon(RemoteRobot robot, Position pos) {
        ImageIcon imageIcon = null;
        switch(pos.getDir()) {
            case NORTH -> imageIcon = new ImageIcon("RobotIcon_NORTH.png");
            case EAST -> imageIcon = new ImageIcon("RobotIcon_EAST.png");
            case SOUTH -> imageIcon = new ImageIcon("RobotIcon_SOUTH.png");
            case WEST -> imageIcon = new ImageIcon("RobotIcon_WEST.png");
        }
        JLabel icon = new JLabel(robot.getName(), imageIcon, SwingConstants.CENTER);
        icon.setHorizontalTextPosition(SwingConstants.CENTER);
        icon.setVerticalTextPosition(SwingConstants.TOP);
        icon.setIconTextGap(0);
        icon.setBackground(Color.WHITE);
        icon.setBorder(design.getBorder());
        icon.setOpaque(true);

        return icon;
    }

    public JPanel getPlanetView() {
        return planetView;
    }

    @Override
    public PlanetView clone() {
        return new PlanetView(planetSize, design, planetFields, robotIcon, planetView);
    }

    public void removeRobot(RemoteRobot robot) {
        if(!robotIconPos.containsKey(robot.getId())) return;
        Position pos = robotIconPos.get(robot.getId());
        planetFields[pos.getY()][pos.getX()].removeAll();
        planetFields[pos.getY()][pos.getX()].revalidate();
        planetFields[pos.getY()][pos.getX()].repaint();
        robotIconPos.remove(robot.getId());
    }

}
