package Clients.ControlPanel.ControlPanelTools;

import java.awt.event.*;
import javax.swing.*;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

public class DurationSetter {

    static int mins;

    public static void setDuration() {

        JLabel lbl_duration = new JLabel("Set duration (in minutes): ");
        addLabel(schedulePanel, lbl_duration, 10, 250, 300, 50);

        JTextField tf_mins = new JTextField("");
        addTextfield(schedulePanel, tf_mins, 10, 300, 300, 40);



        tf_mins.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tf_mins.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

                lbl_duration.setText("Set duration (in minutes): " + mins);
            }
        });
        // Only allow integer inputs for duration
        tf_mins.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String test = tf_mins.getText();
                if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
                {
                    mins = Integer.parseInt(tf_mins.getText());
                }

                else {
                    JOptionPane.showMessageDialog(schedulePanel, "Please set duration in number of minutes.", "Input Error", JOptionPane.ERROR_MESSAGE );
                    tf_mins.setText("");
                }
            }
        });

    }
}
