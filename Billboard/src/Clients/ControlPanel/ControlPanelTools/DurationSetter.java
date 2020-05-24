package Clients.ControlPanel.ControlPanelTools;

import java.awt.event.*;
import javax.swing.*;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

public class DurationSetter {

    static int mins;

    public static void setDuration() {

        JLabel lbl_duration = new JLabel("Set duration: ");
        addLabel(schedulePanel, lbl_duration, ((screenWidth / 3)), 250, 180, 50);

        JTextField tf_mins = new JTextField("duration (mins)");
        addTextfield(schedulePanel, tf_mins, ((screenWidth / 3)), 300, 160, 40);


        // Watch for focus gain or lost on the text field to determine when to set the value.
        tf_mins.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tf_mins.setText(null);
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Only allow integer inputs for duration
                try {
                    mins = Integer.parseInt(tf_mins.getText());
                    lbl_duration.setText("Set duration: " + mins);

                } catch (NumberFormatException i) {
                    if (tf_mins != null) {
                        JOptionPane.showMessageDialog(schedulePanel,
                                "Please set duration in number of minutes.",
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
    }
}
