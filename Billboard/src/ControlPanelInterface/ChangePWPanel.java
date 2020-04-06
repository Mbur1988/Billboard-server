package ControlPanelInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePWPanel extends ControlPanelInterface {

    public static void changePWScreen() {

        passwordPanel.setLayout(null);

        JLabel label_changePW = new JLabel("Don't forget to change your pw every 12 years!!!");
        label_changePW.setBounds(0,0,300,300);
        passwordPanel.add(label_changePW);

        JButton b_Exit = new JButton("Exit"); // Exit button.
        b_Exit.setBounds(screenWidth - 105, screenHeight - 60, 100, 30);
        passwordPanel.add(b_Exit);

        b_Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlPanelScreen.dispose();
            }
        });

    }

}
