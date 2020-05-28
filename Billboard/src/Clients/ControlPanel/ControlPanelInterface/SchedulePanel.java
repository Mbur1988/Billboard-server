package Clients.ControlPanel.ControlPanelInterface;

import SerializableObjects.Schedule;
import Tools.Log;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static Clients.Client.*;
import static Clients.ControlPanel.ControlPanel.*;
import static Clients.ControlPanel.ControlPanelTools.DayChooser.*;
import static Clients.ControlPanel.ControlPanelTools.DurationSetter.*;
import static Clients.ControlPanel.ControlPanelTools.TimeSetter.*;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

class SchedulePanel extends ControlPanelInterface {

    private static Schedule schedule;
    private static JLabel lbl_message;
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

        // Title label for schedule billboard
        JLabel lbl_schedule = new JLabel("Schedule Billboard");
        lbl_schedule.setFont(new Font("Courier", Font.PLAIN, 40));
        lbl_schedule.setBounds((screenWidth / 3),0, 450, 50);
        schedulePanel.add(lbl_schedule);

        // Title label for the billboards list
        JLabel lbl_schedules = new JLabel("All schedules");
        lbl_schedules.setBounds(((screenWidth / 3) * 2), 0, 450, 50);
        lbl_schedules.setFont(new Font("Courier", Font.PLAIN, 40));
        schedulePanel.add(lbl_schedules);

        selectDay();
        setTime();
        setDuration();

        lbl_message = new JLabel("");
        addLabel(schedulePanel, lbl_message, (screenWidth / 3), 500, 340, 50);

        // Create and add a default list model
        billboardListModel = new DefaultListModel();
        scheduleListModel = new DefaultListModel();

        billboardListModel.addAll(listBillboards.billboards);

        scheduleListModel.addAll(listSchedules.schedules);

        // Create a new JList
        billboardList = new JList(billboardListModel);
        scheduleList = new JList(scheduleListModel);

        // Create and add JScrollPane
        JScrollPane billboardScrollPane = new JScrollPane(billboardList);
        billboardScrollPane.setBounds(10, 100, 300, 400);
        schedulePanel.add(billboardScrollPane);

        JScrollPane scheduleScrollPane = new JScrollPane(scheduleList);
        scheduleScrollPane.setBounds(((screenWidth / 3) * 2), 100, 300, 370);
        schedulePanel.add(scheduleScrollPane);

        // Create buttons
        b_add = new JButton("Add");
        b_clear = new JButton("Clear All");
        b_delete = new JButton("Delete");
        b_load = new JButton("Load");
        b_save = new JButton("Save");

        // Add buttons to panel
        addButton(schedulePanel, b_add, (screenWidth / 3), 470, 160, 30);
        addButton(schedulePanel, b_clear, ((screenWidth / 3) + 170), 470, 160, 30);
        addButton(schedulePanel, b_delete, ((screenWidth / 3) * 2) + 150, 470, 150, 30);
        addButton(schedulePanel, b_load, (screenWidth / 3) * 2, 470, 150, 30);
        addButton(schedulePanel, b_save, (screenWidth / 3), 470, 160, 30);
        b_save.setVisible(false);

        // Handle button press events
        b_add.addActionListener(event -> addSchedule());
        b_save.addActionListener(event -> saveSchedule());
        b_clear.addActionListener(event -> clearFields());
        b_load.addActionListener(event -> loadSchedule());
        b_delete.addActionListener(event -> deleteSchedule());
    }

    private static void addSchedule() {
        // if any required fields are empty then display a message to the user and return
        if (duration == 0) {
            lbl_message.setText("Enter a duration");
            return;
        }
        else if (billboardList.getSelectedValue() == null) {
            lbl_message.setText("Select a billboard");
            return;
        }
        try {
            // populate the static instance "schedule" with the schedule data entered by the user
            populateSchedule();
            // set the action request to the server
            user.setAction("Schedule Billboard");
            // attempt connection to the server
            if (AttemptConnect()) {
                // Send user object to server
                objectStreamer.Send(user);
                // Send schedule object to server
                objectStreamer.Send(schedule);
                // Await confirmation that the schedule was added successfully
                if (dis.readBoolean()) {
                    // add new schedule to the list schedules and resort it alphabetically
                    listSchedules.sortAdd(schedule.getScheduleName());
                    scheduleListModel.clear();
                    scheduleListModel.addAll(listSchedules.schedules);
                    // display confirmation message to the user and post log confirmation
                    lbl_message.setText("Schedule added");
                    Log.Confirmation("New schedule added successfully");
                }
                // If schedule not added then display message to the user
                else {
                    lbl_message.setText("Check that schedule does not already exist");
                    Log.Error("Error when attempting to add new schedule");
                }
                // Disconnect from server
                AttemptDisconnect();
            }
            // Post message to user if unable to connect to server
            else {
                Log.Error("Unable to connect to server");
            }
            // clear all user input fields
            resetFields();
        }
        // catch any unanticipated exceptions and print to console
        catch (Exception e) {
            e.printStackTrace();
            Log.Error("Add schedule attempt request failed");
        }
    }

    private static void saveSchedule() {
    }

    private static void clearFields() {
        resetFields();
        lbl_message.setText("");
    }

    private static void loadSchedule() {
    }

    private static void deleteSchedule() {
        String schedule = (String) scheduleList.getSelectedValue();
        user.setAction("Remove Billboard");
        // Attempt connection to server
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // send the username of the user to delete
                dos.writeUTF(schedule);
                // await confirmation that the user has been successfully deleted
                if (dis.readBoolean()) {
                    // remove user from list
                    listUsers.users.remove(schedule);
                    scheduleListModel.removeElement(schedule);
                    // display message to the user
                    lbl_message.setText("Schedule deleted");
                    Log.Confirmation("Schedule successfully deleted");
                }
                // Post message to user if unable to delete user
                else {
                    lbl_message.setText("Schedule not deleted");
                    Log.Error("Error when attempting to delete Schedule");
                }
            }
            // catch any unanticipated exceptions and print to console
            catch (IOException e) {
                e.printStackTrace();
                Log.Error("Failed to delete Schedule");
            }
            // Disconnect from server
            AttemptDisconnect();
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
        // clear all user input fields
        resetFields();
    }

    private static void populateSchedule() {
        String billboard = (String)billboardList.getSelectedValue();
        String day = (String) cb_day.getSelectedItem();
        // populate the schedule
        schedule = new Schedule(
                day + "_" + time + "_" + billboard,
                billboard,
                day,
                time,
                duration,
                0);
    }

    private static void resetFields() {
        clearDay();
        clearTime();
        clearDuration();
    }
}
