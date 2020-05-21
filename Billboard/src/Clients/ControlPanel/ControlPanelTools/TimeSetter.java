package Clients.ControlPanel.ControlPanelTools;

import java.awt.event.*;
import javax.swing.*;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;

public class TimeSetter {

    static int hour = 00;
    static int min = 00;

    public static void setTime() {

        JLabel lbl_hour = new JLabel("Hour: " + hour + "    " + "Min: " + min);

        JButton b_upHour = new JButton("+ Hr");

        b_upHour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hour <= 23) {
                    hour ++;
                }
                if (hour > 23){
                    hour = 00;
                }

                lbl_hour.setText("Hour: " + hour + "    " + "Min: " + min);
            }
        });

        JButton b_dwnHour = new JButton("- Hr");

        b_dwnHour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hour >= 0){
                    hour --;
                }
                if (hour < 0) {
                    hour = 23;
                }

                lbl_hour.setText("Hour: " + hour + "    " + "Min: " + min);
            }
        });

        JButton b_upMin = new JButton("+ Min");

        b_upMin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (min <= 59) {
                    min ++;
                }

                if (min > 59) {
                    min = 0;
                }
                lbl_hour.setText("Hour: " + hour + "    " + "Min: " + min);
            }
        });

        JButton b_dwnMin = new JButton("- Min");

        b_dwnMin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (min >= 0) {
                    min --;
                }

                if (min < 0) {
                    min = 59;
                }
                lbl_hour.setText("Hour: " + hour + "    " + "Min: " + min);
            }
        });

        schedulePanel.add(lbl_hour);
        schedulePanel.add(b_upHour);
        schedulePanel.add(b_dwnHour);
        schedulePanel.add(b_upMin);
        schedulePanel.add(b_dwnMin);
    }

}
