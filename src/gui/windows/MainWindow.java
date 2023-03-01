package gui.windows;

import gui.Design;
import exo.remoterobot.RemoteRobot;
import exo.remoterobot.RobotConsole;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import base.Main;
import shared.Utils;

import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private Design design;
    private JPanel pnlMain;
    private JScrollPane spRobotList, spRobotConsoleView;
    private JList<RemoteRobot> lRobotList;
    private DefaultListModel<RemoteRobot> listModel = new DefaultListModel<>();
    private JButton btnAddRobot, btnRemoveRobot;
    private RobotConsole robotConsole;
    private JCheckBox cbEnableControls;
    private GridBagConstraints constraint;


    public MainWindow() {
        design = Main.windowDesign;
        windowInit();
        build();
    }

    public void addRobot(RemoteRobot robot) {
        listModel.add(listModel.getSize(), robot);
    }

    public boolean removeRobot(RemoteRobot robot) {
        return listModel.removeElement(robot);
    }

    public RemoteRobot getSelectedRobot() {
        return lRobotList == null ? null : lRobotList.getSelectedValue();
    }
    public void addRobotAddActionListener(ActionListener listener) {
        btnAddRobot.addActionListener(listener);
    }

    public void addRobotRemoveActionListener(ActionListener listener) {
        btnRemoveRobot.addActionListener(listener);
    }

    public void addListSelectionListener(ListSelectionListener listener) {
        lRobotList.addListSelectionListener(listener);
    }

    public void addItemChangeListener(ItemListener listener) {
        cbEnableControls.addItemListener(listener);
    }

    public void changeConsole(RemoteRobot robot) {
        if(robot == null)  robotConsole.setConsole(null);
        else robotConsole.setConsole(robot.getRobotConsole().getConsole());
    }

    private void windowInit() {
        setSize(new Dimension(1000, 1000));
        setDefaultCloseOperation(3);
        getContentPane().setLayout(new BorderLayout());
    }

    private void build() {
        constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.anchor = GridBagConstraints.NORTHWEST;
        constraint.insets = new Insets(20, 20, 20, 20);
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
        pnlMain.add(spRobotList, constraint);

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
        robotConsole = new RobotConsole();
        spRobotConsoleView = robotConsole.buildConsoleView();

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
