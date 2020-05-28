package Clients.ControlPanel.ControlPanelTools;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.stream.IntStream;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

public class DateChooser {

    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//    private static final int[] hours = IntStream.range(0, 24).toArray(); // From 0 to 23
//    private static final int[] minutes = IntStream.range(0, 60).toArray(); // From 0 to 59
    private static int mins;

    // Variables required by the class
    //private static JLabel lbl_none;
    private static JLabel lbl_day;
    private static JLabel lbl_hour;
    private static JLabel lbl_mins;
    private static JLabel lbl_duration;
    private static JLabel lbl_recur;
    private static JComboBox<String> cb_day;
//    private static JComboBox<String> cb_hour;
//    private static JComboBox<String> cb_min;
//    private static JTextField tf_mins;
    private static JRadioButton rb_none;
    private static JRadioButton rb_daily;
    private static JRadioButton rb_hourly;
    private static JRadioButton rb_mins;
    private static JButton b_upMins;
    private static JButton b_dwnMins;
    private static int min;

    public static void selectDay() {

        // Create labels
        lbl_day = new JLabel("Day:");
        lbl_mins = new JLabel("00");
        lbl_recur = new JLabel("Recur:");

        // Add labels to the panel
        addLabel(schedulePanel, lbl_day, (screenWidth/3), 50, 300, 40);
        addLabel(schedulePanel, lbl_recur, (screenWidth/3), 90, 300, 40);
        addLabel(schedulePanel, lbl_mins, (screenWidth/3) + 365, 90, 50, 40);

        // Create and add the combo box
        cb_day = new JComboBox<>(days);
        addCombobox(schedulePanel, cb_day, (screenWidth/3) + 150, 50, 300, 40);

        // Create radio buttons
        rb_none = new JRadioButton("None", true);
        rb_daily = new JRadioButton("Daily");
        rb_hourly = new JRadioButton("Hourly");
        rb_mins = new JRadioButton("Mins:");

        // Add radio buttons
        addRadioButton(schedulePanel, rb_none, (screenWidth/3) + 65, 90, 70, 40);
        addRadioButton(schedulePanel, rb_daily, (screenWidth/3) + 135, 90, 70, 40);
        addRadioButton(schedulePanel, rb_hourly, (screenWidth/3) + 205, 90, 80, 40);
        addRadioButton(schedulePanel, rb_mins, (screenWidth/3) + 285, 90, 80, 40);

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
        addButton(schedulePanel, b_upMins,(screenWidth/3) + 415, 95, 80, 15);
        addButton(schedulePanel, b_dwnMins,(screenWidth/3) + 415, 115, 80, 15);

        // Create action listeners
        ActionListener rb_ActionListener = actionEvent -> {
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            b_upMins.setEnabled(aButton.getText().equals("Mins:"));
            b_dwnMins.setEnabled(aButton.getText().equals("Mins:"));
        };

        b_upMins.addActionListener(e -> {
            if (min <= 59) {
                min ++;
            }
            if (min > 59) {
                min = 0;
            }
            lbl_mins.setText("" + min);
        });

        b_dwnMins.addActionListener(e -> {
            if (min >= 0) {
                min --;
            }

            if (min < 0) {
                min = 59;
            }
            lbl_mins.setText("" + min);
        });

        // Add the action listener to the radio buttons]
        rb_none.addActionListener(rb_ActionListener);
        rb_daily.addActionListener(rb_ActionListener);
        rb_hourly.addActionListener(rb_ActionListener);
        rb_mins.addActionListener(rb_ActionListener);

    }
}
