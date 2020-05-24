package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.TimeSetter;

import static Clients.ControlPanel.ControlPanel.lists;
import static Clients.ControlPanel.ControlPanelTools.DateChooser.chooseDate;
import static Clients.ControlPanel.ControlPanelTools.DurationSetter.setDuration;
import static Clients.ControlPanel.ControlPanelTools.TimeSetter.setTime;

import javax.swing.*;
import java.awt.*;

class SchedulePanel extends ControlPanelInterface {

    static DefaultListModel scheduleListModel;
    static DefaultListModel billboardListModel;
    private static JList scheduleList;
    private static JList billboardList;

    protected static void schedulePanelScreen() {

        schedulePanel.setLayout(null);

        // Add title label
        // Title label for the billboards list
        JLabel lbl_select = new JLabel("Select billboard");
        lbl_select.setBounds(0, 0, 450, 50);
        lbl_select.setFont(new Font("Courier", Font.PLAIN, 40));
        schedulePanel.add(lbl_select);

        // Title label for change password details
        JLabel lbl_schedule = new JLabel("Schedule Billboard");
        lbl_schedule.setFont(new Font("Courier", Font.PLAIN, 40));
        lbl_schedule.setBounds((screenWidth / 2) - (450 / 2), 0, 450, 50);
        schedulePanel.add(lbl_schedule);

        // Title label for the billboards list
        JLabel lbl_schedules = new JLabel("All schedules");
        lbl_schedules.setBounds(((screenWidth / 3) * 2), 0, 450, 50);
        lbl_schedules.setFont(new Font("Courier", Font.PLAIN, 40));
        schedulePanel.add(lbl_schedules);

        chooseDate();
        setTime();
        setDuration();

        // Create and add a default list model
        billboardListModel = new DefaultListModel();
        scheduleListModel = new DefaultListModel();

        billboardListModel.addAll(lists.billboards);

        // Temporary line until schedules list can be retrieved from database
        scheduleListModel.addAll(lists.billboards);
        // Remove above line and uncomment below line once schedules list can be retrieved
//        scheduleListModel.addAll(lists.schedules);

        // Create a new JList
        billboardList = new JList(billboardListModel);
        scheduleList = new JList(scheduleListModel);

        // Create and add JScrollPane
        JScrollPane billboardScrollPane = new JScrollPane(billboardList);
        billboardScrollPane.setBounds(10, 100, 300, 400);
        schedulePanel.add(billboardScrollPane);

        JScrollPane scheduleScrollPane = new JScrollPane(scheduleList);
        scheduleScrollPane.setBounds(((screenWidth / 3) * 2), 100, 300, 400);
        schedulePanel.add(scheduleScrollPane);

    }

}
