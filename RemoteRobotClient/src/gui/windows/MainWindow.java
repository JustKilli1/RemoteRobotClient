package gui.windows;

import gui.Design;
import gui.RoundBorder;
import remoterobot.RemoteRobot;
import shared.Utils;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import base.Main;

import java.awt.*;
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
        this.listModel.add(this.listModel.getSize(), robot);
    }

    public boolean removeRobot(RemoteRobot robot) {
        return this.listModel.removeElement(robot);
    }

    public RemoteRobot getSelectedRobot() {
        return robotList == null ? null : (RemoteRobot)robotList.getSelectedValue();
    }

    public void changeConsole(RemoteRobot robot) {
        if(robot == null) return;
        pnlCenter.remove(spConsoleView);
        pnlCenter.remove(pnlRobotCommands);
        consoleView = robot.getConsole();
        spConsoleView = Utils.createConsoleContainer(consoleView);
        updateRobotView();
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
        pnlWest.setPreferredSize(new Dimension(200, 100));
        buildRobotList();

        pnlCenter = new JPanel();
        pnlCenter.setBackground(windowDesign.getBackgroundColor());
        buildRobotView();

        add(pnlNorth, "North");
        add(pnlEast, "East");
        add(pnlSouth, "South");
        add(pnlWest, "West");
        add(pnlCenter, "Center");
    }

    private void buildRobotList() {
        listModel = new DefaultListModel<RemoteRobot>();
        robotList = new JList<RemoteRobot>(this.listModel);
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
        
        pnlWest.setBackground(windowDesign.getBackgroundColor());
        pnlWest.setPreferredSize(new Dimension(250, 300));
        pnlWest.add(this.spRobotList);
        pnlWest.add(this.btnAddRobot);
        pnlWest.add(this.btnRemoveRobot);
    }

    private void buildRobotView() {
        clientConsole = clientConsole == null ? Utils.createNewConsole() : clientConsole;
        consoleView = consoleView == null ? clientConsole : consoleView;
        spConsoleView = new JScrollPane(consoleView);
        spConsoleView.setPreferredSize(new Dimension(600, 500));
        spConsoleView.setBackground(windowDesign.getComponentColor());
        spConsoleView.setBorder(windowDesign.getBorder());
        
        pnlRobotCommands = new JPanel();
        pnlRobotCommands.setPreferredSize(new Dimension(2000, 50));
        pnlRobotCommands.setBackground(windowDesign.getBackgroundColor());

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

        pnlCenter.add(spConsoleView);
        pnlCenter.add(pnlRobotCommands);
    }
    
    private void updateRobotView() {
    	 pnlCenter.add(spConsoleView);
         pnlCenter.add(pnlRobotCommands);
    }

}
