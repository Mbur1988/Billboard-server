package Clients.ControlPanel.ControlPanelTools;

import javax.swing.*;
import java.sql.Time;
import java.time.LocalTime;

import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

public class TimeSetter {

    private static int hour = 00;
    private static int min = 00;
    public static LocalTime time = LocalTime.of(hour, min);
    private static JLabel lbl_time;

    public static void setTime() {

        lbl_time = new JLabel("Hour: " + hour + "    " + "Min: " + min);

        JButton b_upHour = new JButton("+ Hr");

        b_upHour.addActionListener(e -> {
            if (hour <= 23) {
                hour ++;
            }
            if (hour > 23){
                hour = 00;
            }
            time = LocalTime.of(hour, min);
            lbl_time.setText("Hour: " + hour + "    " + "Min: " + min);
        });

        JButton b_dwnHour = new JButton("- Hr");

        b_dwnHour.addActionListener(e -> {
            if (hour >= 0){
                hour --;
            }
            if (hour < 0) {
                hour = 23;
            }
            time = LocalTime.of(hour, min);
            lbl_time.setText("Hour: " + hour + "    " + "Min: " + min);
        });

        JButton b_upMin = new JButton("+ Min");

        b_upMin.addActionListener(e -> {
            if (min <= 59) {
                min ++;
            }
            if (min > 59) {
                min = 0;
            }
            time = LocalTime.of(hour, min);
            lbl_time.setText("Hour: " + hour + "    " + "Min: " + min);
        });

        JButton b_dwnMin = new JButton("- Min");

        b_dwnMin.addActionListener(e -> {
            if (min >= 0) {
                min --;
            }

            if (min < 0) {
                min = 59;
            }
            time = LocalTime.of(hour, min);
            lbl_time.setText("Hour: " + hour + "    " + "Min: " + min);
        });

        addLabel(schedulePanel, lbl_time, ((screenWidth / 3)), 330, 200, 20);
        addButton(schedulePanel, b_upHour,((screenWidth / 3)), 300, 80, 20);
        addButton(schedulePanel, b_dwnHour,((screenWidth / 3)), 360, 80, 20);
        addButton(schedulePanel, b_upMin,((screenWidth / 3) + 90), 300, 80, 20);
        addButton(schedulePanel, b_dwnMin,((screenWidth / 3) + 90), 360, 80, 20);
    }

    public static void clearTime() {
        min = 0;
        hour = 0;
        lbl_time.setText("Hour: " + hour + "    " + "Min: " + min);
        time = LocalTime.of(hour, min);
    }

    public static void updateTime(LocalTime newTime) {
        time = newTime;
        hour = time.getHour();
        min = time.getMinute();
        lbl_time.setText("Hour: " + hour + "    " + "Min: " + min);
    }
}
