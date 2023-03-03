package gui.windows;

import exo.planet.PlanetView;
import exo.remoterobot.MeasureData;
import exo.remoterobot.Position;
import gui.Design;
import exo.remoterobot.RemoteRobot;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import base.Main;
import shared.Utils;

import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame {
    private static MainWindow instance = new MainWindow();
    private Design design;
    private JPanel pnlMain, pnlPlanetView;
    private PlanetView planetView;
    private JScrollPane spRobotList;
    private JList<RemoteRobot> lRobotList;
    private DefaultListModel<RemoteRobot> listModel = new DefaultListModel<>();
    private JButton btnAddRobot, btnRemoveRobot;
    private JCheckBox cbEnableControls;
    private GridBagConstraints constraint;


    private MainWindow() {
        design = Main.windowDesign;
        windowInit();
        build();
    }

    public static MainWindow getInstance() {
        return instance;
    }

    public void addRobot(RemoteRobot robot) {
        listModel.add(listModel.getSize(), robot);
        setSelectedRobot();
    }

    public boolean removeRobot(RemoteRobot robot) {
        planetView.removeRobot(robot);
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

    public void addWindowKeyListener(KeyListener listener) {
        addKeyListener(listener);
        pnlMain.addKeyListener(listener);
        pnlPlanetView.addKeyListener(listener);
        spRobotList.addKeyListener(listener);
        lRobotList.addKeyListener(listener);
        btnAddRobot.addKeyListener(listener);
        btnRemoveRobot.addKeyListener(listener);
        cbEnableControls.addKeyListener(listener);
    }

    public void addItemChangeListener(ItemListener listener) {
        cbEnableControls.addItemListener(listener);
    }

    public void addGround(Position pos, MeasureData measureData) {
        if(planetView == null) return;
        planetView.addGround(pos, measureData);
    }

    private void windowInit() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(screenSize.width, screenSize.height));
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
        pnlPlanetView = Utils.createFillerPanel(design, true);

        cbEnableControls = new JCheckBox("Enable Controls");
        cbEnableControls.setBorder(design.getBorder());
        cbEnableControls.setBackground(design.getComponentColor());
        cbEnableControls.setFont(design.getHeaderFont());
        cbEnableControls.setForeground(design.getHeaderColor());
        cbEnableControls.setPreferredSize(new Dimension(30, 20));
        cbEnableControls.setSelected(true);

        constraint.gridx = 2;
        constraint.gridy = 1;
        constraint.gridwidth = 3;
        constraint.gridheight = 5;
        constraint.weightx = constraint.weighty = 65;
        constraint.insets = new Insets(50, 0, 0, 50);
        pnlMain.add(pnlPlanetView, constraint);

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

    public PlanetView getPlanetView() {
        return planetView;
    }

    public void setPlanetView(PlanetView planetView) {
        if(this.planetView != null) return;
        pnlMain.remove(pnlPlanetView);
        this.planetView = planetView;
        pnlPlanetView = planetView.getPlanetView();
        constraint.gridx = 2;
        constraint.gridy = 1;
        constraint.gridwidth = 3;
        constraint.gridheight = 5;
        constraint.weightx = constraint.weighty = 65;
        constraint.insets = new Insets(50, 0, 0, 50);
        pnlMain.add(pnlPlanetView, constraint);
        pnlMain.validate();
    }

    private void setSelectedRobot() {
        lRobotList.setSelectedIndex(listModel.getSize() - 1);
    }
}
