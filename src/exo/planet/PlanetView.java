package exo.planet;

import exo.remoterobot.Direction;
import exo.remoterobot.MeasureData;
import exo.remoterobot.Position;
import exo.remoterobot.RemoteRobot;
import gui.Design;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlanetView {

    private Size planetSize;
    private Design design;
    private JPanel[][] planetFields, knownFields;
    private JLabel robotIcon;
    private JPanel planetView;
    private Map<Integer, Position> robotIconPos;

    public PlanetView(Size planetSize, Design design) {
        this.planetSize = planetSize;
        this.design = design;
        robotIconPos = new HashMap<>();
        robotIcon = new JLabel();
        robotIcon.setIcon(new ImageIcon("RobotIcon_SOUTH.png"));
        build();
    }

    private PlanetView(Size planetSize, Design design, JPanel[][] planetFields, JLabel robotIcon, JPanel planetView) {
        this.planetSize = planetSize;
        this.design = design;
        this.planetFields = planetFields;
        this.robotIcon = robotIcon;
        this.planetView = planetView;
    }

    public void build() {
        planetView = new JPanel(new GridLayout(planetSize.getHeight(), planetSize.getWidth()));
        planetView.setBackground(design.getComponentColor());
        planetView.setBorder(design.getBorder());
        planetFields = new JPanel[planetSize.getHeight()][planetSize.getWidth()];
        knownFields = new JPanel[planetSize.getHeight()][planetSize.getWidth()];
        for(int i = 0; i < planetSize.getHeight(); i++) {
            for(int z = 0; z < planetSize.getWidth(); z++) {
                JPanel fieldPanel = new JPanel();
                fieldPanel.setBackground(design.getComponentColor());
                planetFields[i][z] = fieldPanel;
                planetView.add(fieldPanel);
            }
        }
    }

    public void addGround(Position pos, MeasureData measureData) {
        if(!pos.inBounds()) return;
        if(pos.getY() >= planetFields.length || pos.getX() >= planetFields[pos.getY()].length) return;
        planetFields[pos.getY()][pos.getX()].setBackground(measureData.getGround().getColor());
        planetFields[pos.getY()][pos.getX()].revalidate();
        addScannedField(pos);
    }

    public void addScannedField(Position pos) {
        knownFields[pos.getY()][pos.getX()] = new JPanel();
    }

    public boolean alreadyMeasured(Position pos) {
        if(!pos.inBounds() || knownFields.length <= pos.getY()) return true;
        if(knownFields[pos.getY()][pos.getX()] != null) return true;
        return false;
    }

    public void setRobotPos(RemoteRobot robot, Position pos) {
        planetFields[pos.getY()][pos.getX()].add(buildRobotIcon(robot, pos));
        planetFields[pos.getY()][pos.getX()].revalidate();
        robotIconPos.put(robot.getId(), pos);
    }
    public void changeRobotPos(RemoteRobot robot, Position pos) {
        removeRobot(robot);
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
        System.out.println(pos);
        planetFields[pos.getY()][pos.getX()].removeAll();
        planetFields[pos.getY()][pos.getX()].revalidate();
        planetFields[pos.getY()][pos.getX()].repaint();
        robotIconPos.remove(robot.getId());
    }

}
