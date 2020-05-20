package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.TimeSetter;

import static Clients.ControlPanel.ControlPanelTools.DateChooser.chooseDate;
import static Clients.ControlPanel.ControlPanelTools.DurationSetter.setDuration;
import static Clients.ControlPanel.ControlPanelTools.TimeSetter.setTime;

import javax.swing.*;

public class SchedulePanel extends ControlPanelInterface {

    public static void schedulePanelScreen(){

        JLabel lbl_Schedule = new JLabel("Billboard Schedule");

        lbl_Schedule.setBounds(100,100,100,100);
        schedulePanel.add(lbl_Schedule);

        chooseDate();
        setTime();
        setDuration();

    }

}
