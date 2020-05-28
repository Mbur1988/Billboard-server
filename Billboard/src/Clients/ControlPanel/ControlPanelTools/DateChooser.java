package Clients.ControlPanel.ControlPanelTools;

import javax.swing.*;
import java.util.stream.IntStream;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

public class DateChooser {

    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//    private static final int[] hours = IntStream.range(0, 24).toArray(); // From 0 to 23
//    private static final int[] minutes = IntStream.range(0, 60).toArray(); // From 0 to 59

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
    //private static JTextField tf_duration;
    //private static JTextField tf_mins;
    private static JRadioButton rb_none;
    private static JRadioButton rb_daily;
    private static JRadioButton rb_hourly;
    private static JRadioButton rb_mins;

    public static void selectDay() {

        // Create the labels
        //lbl_none = new JLabel("None");
        lbl_day = new JLabel("Day:");
//        lbl_hour = new JLabel("Hour:");
//        lbl_mins = new JLabel("Minutes:");
//        lbl_duration = new JLabel("Duration:");
        lbl_recur = new JLabel("Recur:");

        // Add the labels to the panel
        addLabel(schedulePanel, lbl_day, (screenWidth/3), 50, 300, 40);
//        addLabel(schedulePanel, lbl_hour, (screenWidth/3), 90, 300, 40);
//        addLabel(schedulePanel, lbl_mins, (screenWidth/3), 130, 300, 40);
//        addLabel(schedulePanel, lbl_duration, (screenWidth/3), 170, 300, 40);
        addLabel(schedulePanel, lbl_recur, (screenWidth/3), 90, 300, 40);

        // Create and add the combo box
        cb_day = new JComboBox<>(days);
        addCombobox(schedulePanel, cb_day, (screenWidth/3) + 150, 50, 300, 40);

        // Create radio buttons
        rb_none = new JRadioButton("None", true);
        rb_daily = new JRadioButton("Daily");
        rb_hourly = new JRadioButton("Hourly");
        rb_mins = new JRadioButton("Mins:");

        // Add radio buttons
        addRadioButton(schedulePanel, rb_none, (screenWidth/3) + 60, 90, 150, 40);
       // addRadioButton(schedulePanel, rb_daily, (screenWidth/3) + 100, 90, 150, 40);

    }
}
