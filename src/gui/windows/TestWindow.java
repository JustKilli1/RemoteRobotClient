package gui.windows;

import base.Main;
import gui.Design;
import remoterobot.RemoteRobot;
import remoterobot.RobotConsole;
import shared.ConstraintBuilder;
import shared.Utils;

import javax.swing.*;
import java.awt.*;

public class TestWindow extends JFrame {
    private Design design;
    private JPanel pnlMain;
    private JScrollPane spRobotList, spRobotConsoleView;
    private JList lRobotList;
    private DefaultListModel<RemoteRobot> listModel = new DefaultListModel<>();
    private JButton btnAddRobot, btnRemoveRobot;
    private JTextArea taConsoleView;
    private JCheckBox cbEnableControls;
    private ConstraintBuilder constraintBuilder;


    public TestWindow() {
        design = Main.windowDesign;
        windowInit();
        build();
    }

    private void windowInit() {
        setSize(new Dimension(1000, 1000));
        setDefaultCloseOperation(3);
        getContentPane().setLayout(new BorderLayout());
    }

    private void build() {
        constraintBuilder = new ConstraintBuilder(GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, new Insets(20, 20, 20, 20));
        pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBackground(design.getBackgroundColor());

        buildRobotList();
        buildRobotView();

        add(pnlMain);
    }

    private void buildRobotList() {
        listModel = new DefaultListModel<>();

        lRobotList = new JList<>(listModel);
        lRobotList.setBackground(design.getComponentColor());
        lRobotList.setForeground(design.getTextColor());
        lRobotList.setFocusable(false);
        lRobotList.setFont(design.getTextFont());

        spRobotList = new JScrollPane(lRobotList);
        spRobotList.setBackground(design.getComponentColor());
        spRobotList.setBorder(design.getBorder());
        spRobotList.getHorizontalScrollBar().setBackground(design.getComponentColor());
        spRobotList.getVerticalScrollBar().setBackground(design.getComponentColor());

        btnAddRobot = new JButton("Add");
        btnAddRobot.setBackground(design.getComponentColor());
        btnAddRobot.setForeground(design.getHeaderColor());
        btnAddRobot.setFocusable(false);
        btnAddRobot.setFont(design.getHeaderFont());
        btnAddRobot.setBorder(design.getBorder());

        btnRemoveRobot = new JButton("Remove");
        btnRemoveRobot.setBackground(design.getComponentColor());
        btnRemoveRobot.setForeground(design.getHeaderColor());
        btnRemoveRobot.setFocusable(false);
        btnRemoveRobot.setFont(design.getHeaderFont());
        btnRemoveRobot.setBorder(design.getBorder());


        constraint.gridx = constraint.gridy = 0;
        constraint.gridwidth = 2;
        constraint.gridheight = 6;
        constraint.weightx = constraint.weighty = 10;
        constraint.insets = new Insets(50, 50, 0, 20);
        pnlMain.add(spRobotList, constraintBuilder.clone()
                .addGridX(0)
                        .addGridY(0)
                        .addGridWidth(2)
                        .addGridHeight(6)
                        .addWeightX(10)
                        .addWeightY(10)
                .build());

        constraint.gridx = 0;
        constraint.gridy = 6;
        constraint.gridwidth = constraint.gridheight = 1;
        constraint.weightx = constraint.weighty = 5;
        constraint.insets = new Insets(20, 50, 50, 10);
        pnlMain.add(btnAddRobot, constraint);

        constraint.gridx = 1;
        constraint.gridy = 6;
        constraint.gridwidth = constraint.gridheight = 1;
        constraint.weightx = constraint.weighty = 5;
        constraint.insets = new Insets(20, 10, 50, 20);
        pnlMain.add(btnRemoveRobot, constraint);
    }

    private void buildRobotView() {
        taConsoleView = RobotConsole.buildConsole();

        spRobotConsoleView = new JScrollPane(taConsoleView);
        spRobotConsoleView.setPreferredSize(new Dimension(600, 500));
        spRobotConsoleView.setBackground(design.getComponentColor());
        spRobotConsoleView.setBorder(design.getBorder());

        cbEnableControls = new JCheckBox("Enable Controls");
        cbEnableControls.setBorder(design.getBorder());
        cbEnableControls.setBackground(design.getComponentColor());
        cbEnableControls.setFont(design.getHeaderFont());
        cbEnableControls.setForeground(design.getHeaderColor());
        cbEnableControls.setPreferredSize(new Dimension(30, 20));

        constraint.gridx = 2;
        constraint.gridy = 1;
        constraint.gridwidth = 3;
        constraint.gridheight = 5;
        constraint.weightx = constraint.weighty = 65;
        constraint.insets = new Insets(50, 0, 0, 50);
        pnlMain.add(spRobotConsoleView, constraint);

        constraint.gridx = 2;
        constraint.gridy = 6;
        constraint.gridwidth = constraint.gridheight = 1;
        constraint.weightx = constraint.weighty = 5;
        constraint.insets = new Insets(20, 20, 20, 20);
        pnlMain.add(Utils.createFillerPanel(design, false), constraint);

        constraint.gridx = 3;
        constraint.gridy = 6;
        constraint.gridwidth = 1;
        constraint.gridheight = 2;
        constraint.weightx = constraint.weighty = 5;
        constraint.insets = new Insets(20, 20, 20, 20);
        pnlMain.add(Utils.createFillerPanel(design, false), constraint);

        constraint.gridx = 4;
        constraint.gridy = 6;
        constraint.gridwidth = constraint.gridheight = 1;
        constraint.weightx = constraint.weighty = 5;
        constraint.insets = new Insets(20, 20, 50, 50);
        pnlMain.add(cbEnableControls, constraint);
    }


}
