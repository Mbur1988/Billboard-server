package Clients.ControlPanel.ControlPanelTools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;

public class DurationSetter {

    static int mins;

    public static void setDuration() {

        JTextField tf_mins = new JTextField("               ");
        tf_mins.setBounds(100,100,100,100);

        JLabel lbl_duration = new JLabel("Set duration (in minutes): ");

        tf_mins.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tf_mins.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                mins = Integer.parseInt(tf_mins.getText());
                lbl_duration.setText("Set duration (in minutes): " + mins);
            }
        });
        schedulePanel.add(lbl_duration);
        schedulePanel.add(tf_mins);

    }

}
