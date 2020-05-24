package Clients.ControlPanel.ControlPanelTools;

import javax.swing.*;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

public class TimeSetter {

    static int hour = 00;
    static int min = 00;

    public static void setTime() {

        JLabel lbl_hour = new JLabel("Hour: " + hour + "    " + "Min: " + min);

        JButton b_upHour = new JButton("+ Hr");

        b_upHour.addActionListener(e -> {
            if (hour <= 23) {
                hour ++;
            }
            if (hour > 23){
                hour = 00;
            }

            lbl_hour.setText("Hour: " + hour + "    " + "Min: " + min);
        });

        JButton b_dwnHour = new JButton("- Hr");

        b_dwnHour.addActionListener(e -> {
            if (hour >= 0){
                hour --;
            }
            if (hour < 0) {
                hour = 23;
            }

            lbl_hour.setText("Hour: " + hour + "    " + "Min: " + min);
        });

        JButton b_upMin = new JButton("+ Min");

        b_upMin.addActionListener(e -> {
            if (min <= 59) {
                min ++;
            }

            if (min > 59) {
                min = 0;
            }
            lbl_hour.setText("Hour: " + hour + "    " + "Min: " + min);
        });

        JButton b_dwnMin = new JButton("- Min");

        b_dwnMin.addActionListener(e -> {
            if (min >= 0) {
                min --;
            }

            if (min < 0) {
                min = 59;
            }
            lbl_hour.setText("Hour: " + hour + "    " + "Min: " + min);
        });

        addLabel(schedulePanel, lbl_hour, ((screenWidth / 3)), 200, 200, 20);
        addButton(schedulePanel, b_upHour,((screenWidth / 3)), 170, 80, 20);
        addButton(schedulePanel, b_dwnHour,((screenWidth / 3)), 230, 80, 20);
        addButton(schedulePanel, b_upMin,((screenWidth / 3) + 90), 170, 80, 20);
        addButton(schedulePanel, b_dwnMin,((screenWidth / 3) + 90), 230, 80, 20);
    }

}
