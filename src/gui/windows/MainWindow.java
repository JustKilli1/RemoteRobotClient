package gui.windows;

import gui.Design;
import gui.RoundBorder;
import remoterobot.RemoteRobot;
import remoterobot.RobotConsole;
import shared.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

import base.Main;

import java.awt.*;
import java.awt.event.ItemListener;
import java.util.List;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private Design windowDesign;
    private JPanel pnlNorth, pnlEast, pnlSouth, pnlWest, pnlCenter, pnlRobotCommands;
    private JList<RemoteRobot> robotList;
    private DefaultListModel<RemoteRobot> listModel = new DefaultListModel<>();
    private JScrollPane spRobotList, spConsoleView;
    private JButton btnAddRobot;
    private JButton btnRemoveRobot;
    private JButton btnSendCommand;
    private JTextArea clientConsole;
    private JTextArea consoleView;
    private JTextField tfCommands;
    private JCheckBox cbEnableControls;

    public MainWindow() {
        this.windowDesign = Main.windowDesign;
        this.windowInit();
        this.build();
    }

    public void addRobot(RemoteRobot robot) {
        listModel.add(listModel.getSize(), robot);
    }

    public boolean removeRobot(RemoteRobot robot) {
        return listModel.removeElement(robot);
    }

    public RemoteRobot getSelectedRobot() {
        return robotList == null ? null : robotList.getSelectedValue();
    }

    public void changeConsole(RemoteRobot robot) {
        if(robot == null) return;
        pnlCenter.remove(spConsoleView);
        pnlCenter.remove(pnlRobotCommands);
        consoleView = robot.getRobotConsole().getConsole();
        spConsoleView = robot.getRobotConsole().buildConsoleView();
        addCenterPanelComponents();
        pnlCenter.validate();
        pnlCenter.repaint();
    }

    public void addRobotAddActionListener(ActionListener listener) {
        this.btnAddRobot.addActionListener(listener);
    }

    public void addRobotRemoveActionListener(ActionListener listener) {
        this.btnRemoveRobot.addActionListener(listener);
    }

    public void addSendCommandActionListener(ActionListener listener) {
        this.btnSendCommand.addActionListener(listener);
    }

    public void addListSelectionListener(ListSelectionListener listener) {
        this.robotList.addListSelectionListener(listener);
    }

    public void addItemChangeListener(ItemListener listener) {
        cbEnableControls.addItemListener(listener);
    }

    private void windowInit() {
        setSize(new Dimension(1000, 1000));
        setDefaultCloseOperation(3);
        getContentPane().setLayout(new BorderLayout());
    }

    private void build() {

        pnlNorth = new JPanel();
        pnlNorth.setBackground(windowDesign.getBackgroundColor());
        pnlNorth.setPreferredSize(new Dimension(100, 200));

        pnlEast = new JPanel();
        pnlEast.setBackground(windowDesign.getBackgroundColor());
        pnlEast.setPreferredSize(new Dimension(50, 100));

        pnlSouth = new JPanel();
        pnlSouth.setBackground(windowDesign.getBackgroundColor());
        pnlSouth.setPreferredSize(new Dimension(100, 50));

        pnlWest = new JPanel();
        pnlWest.setBackground(windowDesign.getBackgroundColor());
        pnlWest.setPreferredSize(new Dimension(250, 300));
        buildRobotList();

        pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.setBackground(windowDesign.getBackgroundColor());
        pnlCenter.setPreferredSize(new Dimension(1000, 1000));
        buildRobotView();

        //add(pnlNorth, "North");
        add(pnlEast, "East");
        add(pnlSouth, "South");
        add(pnlWest, "West");
        add(pnlCenter, "Center");
    }

    private void buildRobotList() {
        listModel = new DefaultListModel<RemoteRobot>();
        robotList = new JList<>(this.listModel);
        robotList.setBackground(windowDesign.getComponentColor());
        robotList.setForeground(windowDesign.getTextColor());
        robotList.setFocusable(false);
        robotList.setFont(windowDesign.getTextFont());

        spRobotList = new JScrollPane(this.robotList);
        spRobotList.setPreferredSize(new Dimension(200, 500));
        spRobotList.setBackground(windowDesign.getComponentColor());
        spRobotList.setBorder(windowDesign.getBorder());
        spRobotList.getHorizontalScrollBar().setBackground(windowDesign.getComponentColor());
        spRobotList.getVerticalScrollBar().setBackground(windowDesign.getComponentColor());

        btnAddRobot = new JButton("Add");
        btnAddRobot.setBackground(windowDesign.getComponentColor());
        btnAddRobot.setForeground(windowDesign.getHeaderColor());
        btnAddRobot.setFocusable(false);
        btnAddRobot.setFont(windowDesign.getHeaderFont());
        btnAddRobot.setBorder(windowDesign.getBorder());

        btnRemoveRobot = new JButton("Remove");
        btnRemoveRobot.setBackground(windowDesign.getComponentColor());
        btnRemoveRobot.setForeground(windowDesign.getHeaderColor());
        btnRemoveRobot.setFocusable(false);
        btnRemoveRobot.setFont(windowDesign.getHeaderFont());
        btnRemoveRobot.setBorder(windowDesign.getBorder());

        pnlWest.add(this.spRobotList);
        pnlWest.add(this.btnAddRobot);
        pnlWest.add(this.btnRemoveRobot);
    }

    private void buildRobotView() {

        clientConsole = clientConsole == null ? RobotConsole.buildConsole() : clientConsole;
        consoleView = consoleView == null ? clientConsole : consoleView;

        spConsoleView = new JScrollPane(consoleView);
        spConsoleView.setPreferredSize(new Dimension(600, 500));
        spConsoleView.setBackground(windowDesign.getComponentColor());
        spConsoleView.setBorder(windowDesign.getBorder());
        
        pnlRobotCommands = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlRobotCommands.setPreferredSize(new Dimension(2000, 50));
        //pnlRobotCommands.setBackground(windowDesign.getBackgroundColor());
        pnlRobotCommands.setBackground(Color.RED);

        tfCommands = new JTextField();
        tfCommands.setPreferredSize(new Dimension(500, 30));
        tfCommands.setFont(windowDesign.getTextFont());
        tfCommands.setBorder(windowDesign.getBorder());
        tfCommands.setForeground(windowDesign.getTextColor());
        tfCommands.setBackground(windowDesign.getComponentColor());
        tfCommands.setCaretColor(Color.WHITE);

        btnSendCommand = new JButton("Send");
        btnSendCommand.setPreferredSize(new Dimension(100, 30));
        btnSendCommand.setFont(windowDesign.getHeaderFont());
        btnSendCommand.setBorder(windowDesign.getBorder());
        btnSendCommand.setForeground(windowDesign.getHeaderColor());
        btnSendCommand.setBackground(windowDesign.getComponentColor());
        btnSendCommand.setFocusable(false);

        cbEnableControls = new JCheckBox("Enable Controls");
        cbEnableControls.setBorder(windowDesign.getBorder());
        cbEnableControls.setBackground(windowDesign.getComponentColor());
        cbEnableControls.setFont(windowDesign.getHeaderFont());
        cbEnableControls.setForeground(windowDesign.getHeaderColor());


        //pnlRobotCommands.add(tfCommands);
        //pnlRobotCommands.add(btnSendCommand);
        pnlRobotCommands.add(cbEnableControls);

        addCenterPanelComponents();
    }

    private void addCenterPanelComponents() {
        GridBagConstraints constraint = new GridBagConstraints();

        constraint.gridx = constraint.gridy = 0;
        constraint.gridwidth = constraint.gridheight = 1;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.anchor = GridBagConstraints.NORTHWEST;
        constraint.weightx = constraint.weighty = 70;
        pnlCenter.add(spConsoleView, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.gridwidth = constraint.gridheight = 1;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.anchor = GridBagConstraints.EAST;
        constraint.weightx = constraint.weighty = 20;
        pnlCenter.add(pnlRobotCommands, constraint);
    }

    public void buildRobotStatsView(RemoteRobot robot) {
        //TODO TEST
        remove(pnlNorth);
        pnlNorth = new JPanel();
        pnlNorth.setBackground(windowDesign.getBackgroundColor());
        pnlNorth.setPreferredSize(new Dimension(100, 200));
        JPanel statsView = new JPanel(new GridLayout(3, 1));
        statsView.setPreferredSize(new Dimension(200, 100));
        JLabel label = new JLabel("ID: " + robot.getId());
        JLabel label2 = new JLabel("Name: " + robot.getName());
        JLabel label3 = new JLabel("Temperatur: " + robot.getTemperature());
        statsView.add(label);
        statsView.add(label2);
        statsView.add(label3);
        statsView.setBackground(windowDesign.getComponentColor());
        label.setForeground(windowDesign.getTextColor());
        label2.setForeground(windowDesign.getTextColor());
        label3.setForeground(windowDesign.getTextColor());
        statsView.setBorder(windowDesign.getBorder());
        label.setFont(windowDesign.getTextFont());
        label2.setFont(windowDesign.getTextFont());
        label3.setFont(windowDesign.getTextFont());
        pnlNorth.add(statsView);
        add(pnlNorth, "North");
        this.validate();
        this.repaint();
    }
}
