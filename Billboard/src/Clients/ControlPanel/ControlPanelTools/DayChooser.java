package Clients.ControlPanel.ControlPanelTools;

import javax.swing.*;
import java.awt.event.ActionListener;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;
import static Clients.ControlPanel.ControlPanelTools.DurationSetter.tf_duration;
import static Clients.ControlPanel.ControlPanelTools.DurationSetter.duration;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;
import static java.lang.Integer.parseInt;

public class DayChooser {

    // Variables required by the class
    private static final String[] days = {
            "1. Monday",
            "2. Tuesday",
            "3. Wednesday",
            "4. Thursday",
            "5. Friday",
            "6. Saturday",
            "7. Sunday"};
    private static JLabel lbl_day;
    public static JLabel lbl_mins;
    private static JLabel lbl_recur;
    public static JComboBox<String> cb_day;
    public static JRadioButton rb_none;
    public static JRadioButton rb_daily;
    public static JRadioButton rb_hourly;
    public static JRadioButton rb_mins;
    private static JButton b_upMins;
    private static JButton b_dwnMins;
    public static int minRec = 999999;

    public static void selectDay() {

        // Create labels
        lbl_day = new JLabel("Day:");
        lbl_mins = new JLabel("00");
        lbl_recur = new JLabel("Recur:");

        // Add labels to the panel
        addLabel(schedulePanel, lbl_day, (screenWidth/3), 90, 300, 40);
        addLabel(schedulePanel, lbl_recur, (screenWidth/3), 130, 300, 40);
        addLabel(schedulePanel, lbl_mins, (screenWidth/3) + 230, 250, 50, 40);

        // Create and add the combo box
        cb_day = new JComboBox<>(days);
        addCombobox(schedulePanel, cb_day, (screenWidth/3) + 130, 90, 200, 40);

        // Create radio buttons
        rb_none = new JRadioButton("None", true);
        rb_daily = new JRadioButton("Daily");
        rb_hourly = new JRadioButton("Hourly");
        rb_mins = new JRadioButton("Mins:");

        // Add radio buttons
        addRadioButton(schedulePanel, rb_none, (screenWidth/3) + 130, 130, 90, 40);
        addRadioButton(schedulePanel, rb_daily, (screenWidth/3) + 130, 170, 90, 40);
        addRadioButton(schedulePanel, rb_hourly, (screenWidth/3) + 130, 210, 90, 40);
        addRadioButton(schedulePanel, rb_mins, (screenWidth/3) + 130, 250, 90, 40);

        // Create a group for the radio buttons
        ButtonGroup group = new ButtonGroup();

        // Add radio buttons to group
        group.add(rb_none);
        group.add(rb_daily);
        group.add(rb_hourly);
        group.add(rb_mins);

        // Create buttons
        b_upMins = new JButton("+");
        b_dwnMins = new JButton("-");

        // Add buttons
        addButton(schedulePanel, b_upMins,(screenWidth/3) + 265, 250, 50, 20);
        addButton(schedulePanel, b_dwnMins,(screenWidth/3) + 265, 270, 50, 20);

        // Set default state of the buttons
        b_upMins.setEnabled(false);
        b_dwnMins.setEnabled(false);

        // Create action listeners
        ActionListener rb_ActionListener = actionEvent -> {
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            b_upMins.setEnabled(aButton.getText().equals("Mins:"));
            b_dwnMins.setEnabled(aButton.getText().equals("Mins:"));
            if (aButton.getText().equals("None")) {
                tf_duration.setText("");
                duration = 0;
                minRec = 999999;
            }
            else if (aButton.getText().equals("Daily")) {
                tf_duration.setText("");
                duration = 0;
                minRec = 1440;
            }
            else if (aButton.getText().equals("Hourly")) {
                tf_duration.setText("");
                duration = 0;
                minRec = 60;
            }
            else {
                tf_duration.setText("");
                duration = 0;
                minRec = parseInt(lbl_mins.getText());
            }
        };

        b_upMins.addActionListener(e -> {
            if (minRec <= 59) {
                minRec ++;
            }
            if (minRec > 59) {
                minRec = 0;
            }
            lbl_mins.setText("" + minRec);
        });

        b_dwnMins.addActionListener(e -> {
            if (minRec >= 0) {
                minRec --;
            }

            if (minRec < 0) {
                minRec = 59;
            }
            lbl_mins.setText("" + minRec);
        });

        // Add the action listener to the radio buttons]
        rb_none.addActionListener(rb_ActionListener);
        rb_daily.addActionListener(rb_ActionListener);
        rb_hourly.addActionListener(rb_ActionListener);
        rb_mins.addActionListener(rb_ActionListener);
    }

    public static void clearDay() {
        cb_day.setSelectedItem("Monday");
        rb_none.setSelected(true);
        minRec = 999999;
        lbl_mins.setText("0");
    }

    public static void updateDay(String newDay, int recurDuration) {
        cb_day.setSelectedItem(newDay);
        if (recurDuration == 0) {
            rb_none.setSelected(true);
        }
        else if (recurDuration == 1440) {
            rb_daily.setSelected(true);
        }
        else if (recurDuration == 60) {
            rb_hourly.setSelected(true);
        }
        else {
            rb_mins.setSelected(true);
            lbl_mins.setText(String.valueOf(recurDuration));
        }
    }
}
