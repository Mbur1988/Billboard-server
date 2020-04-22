package Clients.ControlPanel.ControlPanelInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUsersPanel extends ControlPanelInterface {

    public static void editUserScreen() {

        editUserPanel.setLayout(null);

        JLabel label_editUser = new JLabel("Edit users");
        label_editUser.setBounds(0,0,300,300);
        editUserPanel.add(label_editUser);

    }

}
