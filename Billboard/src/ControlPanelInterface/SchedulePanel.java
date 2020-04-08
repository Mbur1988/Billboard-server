package ControlPanelInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchedulePanel extends ControlPanelInterface {

    public static void schedulePanelScreen(){

        schedulePanel.setLayout(null);

        JLabel label_Schedule = new JLabel("Billboard Schedule");
        label_Schedule.setBounds(0,0,300,300);
        schedulePanel.add(label_Schedule);

        JButton b_Exit = new JButton("Exit"); // Exit button.
        b_Exit.setBounds(screenWidth - 105, screenHeight - 60, 100, 30);

        schedulePanel.add(b_Exit);

        b_Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlPanelScreen.dispose();
            }
        });
    }

}
