package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.TimeSetter;
import static Clients.ControlPanel.ControlPanelTools.DateChooser.chooseDate;
import static Clients.ControlPanel.ControlPanelTools.DurationSetter.setDuration;
import static Clients.ControlPanel.ControlPanelTools.TimeSetter.setTime;

import javax.swing.*;
import java.awt.*;

public class SchedulePanel extends ControlPanelInterface {

    public static void schedulePanelScreen() {

        schedulePanel.setLayout(null);

        // Add title label
        // Title label for change password details
        JLabel lbl_Schedule = new JLabel("Billboard Schedule");
        lbl_Schedule.setFont(new Font("Courier", Font.PLAIN, 50));
        lbl_Schedule.setBounds(0, 0, 450, 60);
        schedulePanel.add(lbl_Schedule);

        chooseDate();
        setTime();
        setDuration();

    }

}
