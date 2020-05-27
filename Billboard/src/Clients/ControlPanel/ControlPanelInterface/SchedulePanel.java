package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.TimeSetter;

import static Clients.ControlPanel.ControlPanel.lists;
import static Clients.ControlPanel.ControlPanelTools.DateChooser.chooseDate;
import static Clients.ControlPanel.ControlPanelTools.DurationSetter.setDuration;
import static Clients.ControlPanel.ControlPanelTools.TimeSetter.setTime;
import static Clients.ControlPanel.ControlPanelTools.Tools.addButton;

import javax.swing.*;
import java.awt.*;

class SchedulePanel extends ControlPanelInterface {

    static DefaultListModel scheduleListModel;
    static DefaultListModel billboardListModel;
    private static JList scheduleList;
    private static JList billboardList;
    private static JButton b_add;
    private static JButton b_save;
    private static JButton b_clear;
    private static JButton b_load;
    private static JButton b_delete;

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
        lbl_schedule.setBounds(((screenWidth / 3)),0, 450, 50);
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

        scheduleListModel.addAll(lists.schedules);

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

        // Create buttons
        b_add = new JButton("Add");
        b_clear = new JButton("Clear All");
        b_delete = new JButton("Delete");
        b_load = new JButton("Load");
        b_save = new JButton("Save");

        // Add buttons to panel
        addButton(schedulePanel, b_add, ((screenWidth / 3)), 380, 160, 30);
        addButton(schedulePanel, b_clear, ((screenWidth / 3)), 345, 160, 30);
        addButton(schedulePanel, b_delete, ((screenWidth / 3) * 2) + 150, 500, 150, 30);
        addButton(schedulePanel, b_load, (screenWidth / 3) * 2, 500, 150, 30);
        addButton(schedulePanel, b_save, ((screenWidth / 3)), 380, 160, 30);
        b_save.setVisible(false);

    }

}
