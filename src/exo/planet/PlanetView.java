package exo.planet;

import exo.remoterobot.Direction;
import exo.remoterobot.Position;
import exo.remoterobot.RemoteRobot;

import javax.swing.*;
import java.awt.*;

public class PlanetView {

    private Size planetSize;
    private JPanel[][] planetFields;
    private JLabel robotIcon;
    private Position currentPos;

    public PlanetView(Size planetSize) {
        this.planetSize = planetSize;
        robotIcon = new JLabel();
        robotIcon.setIcon(new ImageIcon("RobotIcon.png"));
        currentPos = new Position(0, 0, Direction.EAST);
    }

    public JPanel build() {
        JPanel panel = new JPanel(new GridLayout(planetSize.getHeight(), planetSize.getWidth()));
        planetFields = new JPanel[planetSize.getHeight()][planetSize.getWidth()];
        for(int i = 0; i < planetSize.getHeight(); i++) {
            for(int z = 0; z < planetSize.getWidth(); z++) {
                JPanel fieldPanel = new JPanel();
                fieldPanel.setBackground(Ground.NICHTS.getColor());
                planetFields[i][z] = fieldPanel;
                panel.add(fieldPanel);
            }
        }
        planetFields[0][0].add(robotIcon);
        return panel;
    }

    public void addGround(Position pos, Ground ground) {
        planetFields[pos.getY()][pos.getX()].setBackground(ground.getColor());
    }

    public void changeRobotPos(Position pos) {
        planetFields[currentPos.getY()][currentPos.getX()].removeAll();
        planetFields[currentPos.getY()][currentPos.getX()].validate();
        planetFields[pos.getY()][pos.getX()].add(robotIcon);
        planetFields[pos.getY()][pos.getX()].validate();
    }
}
