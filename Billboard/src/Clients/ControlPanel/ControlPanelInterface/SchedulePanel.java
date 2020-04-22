package Clients.ControlPanel.ControlPanelInterface;

import javax.swing.*;

public class SchedulePanel extends ControlPanelInterface {

    public static void schedulePanelScreen(){

        schedulePanel.setLayout(null);

        JLabel label_Schedule = new JLabel("Billboard Schedule");
        label_Schedule.setBounds(0,0,300,300);
        schedulePanel.add(label_Schedule);

    }

}
