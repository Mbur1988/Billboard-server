package Clients.ControlPanel.ControlPanelTools;

import javax.swing.*;

import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelTools.DurationSetter.tf_duration;

public class checkDurationVsRecurrence {

    public static void  checkDVsR (int minRec, int duration) {

        if (duration > minRec) {
            JOptionPane.showMessageDialog(schedulePanel,
                    "Duration cannot exceed recurrence ",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            tf_duration.setText("");
        }
    }

}
