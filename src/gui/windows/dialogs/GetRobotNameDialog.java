package gui.windows.dialogs;

import javax.swing.*;
import java.awt.*;

public class GetRobotNameDialog extends JDialog {

    private JPanel panel;
    private JLabel label;
    private JTextField textField;
    private JButton button;

    public GetRobotNameDialog(JFrame frame) {
        super(frame);
    }

    public void open() {
        setSize(new Dimension(400, 300));
        setTitle("Robotername eingeben");
        setModal(true);
        open();
        setModal(true);
    }

    public void getRobotName() {

    }

    private void build() {
        panel = new JPanel(new GridLayout(3, 1));
        label = new JLabel("Bitte Roboter Name eingeben");
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 50));
        button =  new JButton("Ok");
        button.setPreferredSize(new Dimension(100, 50));

        panel.add(label);
        panel.add(textField);
        panel.add(button);
    }

}
