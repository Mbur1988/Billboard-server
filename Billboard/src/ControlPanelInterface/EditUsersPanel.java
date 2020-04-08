package ControlPanelInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUsersPanel extends ControlPanelInterface {

    public static void editUserScreen() {

        editUserPanel.setLayout(null);

        JLabel label_editUser = new JLabel("Edit users");
        label_editUser.setBounds(0,0,300,300);
        editUserPanel.add(label_editUser);

        JButton b_Exit = new JButton("Exit"); // Exit button.
        b_Exit.setBounds(screenWidth - 105, screenHeight - 60, 100, 30);
        editUserPanel.add(b_Exit);

        b_Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlPanelScreen.dispose();
            }
        });


    }

}
