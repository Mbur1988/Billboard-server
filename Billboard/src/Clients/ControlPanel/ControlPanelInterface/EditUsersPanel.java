package Clients.ControlPanel.ControlPanelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUsersPanel extends ControlPanelInterface {

    public static void editUserScreen() {

        editUserPanel.setLayout(null);

        JLabel label_editUser = new JLabel("Edit user");
        label_editUser.setFont(new Font("Courier", Font.PLAIN, 50));
        label_editUser.setBounds(0,0,300,50);
        editUserPanel.add(label_editUser);

        Font font = new Font("Courier", Font.PLAIN, 20);

        JLabel lbl_username = new JLabel("Username");
        lbl_username.setBounds(10, 250, 300, 50);
        lbl_username.setFont(font);
        editUserPanel.add(lbl_username);

        JTextField tf_username = new JTextField();
        tf_username.setBounds(350, 250, 300, 50);
        tf_username.setFont(font);
        editUserPanel.add(tf_username);

        JLabel lbl_password = new JLabel("Password");
        lbl_password.setBounds(10, 350, 300, 50);
        lbl_password.setFont(font);
        editUserPanel.add(lbl_password);

        JTextField tf_password = new JTextField();
        tf_password.setBounds(350, 350, 300, 50);
        tf_password.setFont(font);
        editUserPanel.add(tf_password);

        JLabel lbl_privileges = new JLabel("Privileges");
        lbl_privileges.setBounds(10, 500, 300, 50);
        lbl_privileges.setFont(font);
        editUserPanel.add(lbl_privileges);

        JCheckBox cb_createNew = new JCheckBox("Create New Billboard");
        cb_createNew.setBounds(400, 500, 300, 50);
        cb_createNew.setFont(font);
        editUserPanel.add(cb_createNew);

        JCheckBox cb_editBoard = new JCheckBox("Edit Existing Billboard");
        cb_editBoard.setBounds(400, 550, 300, 50);
        cb_editBoard.setFont(font);
        editUserPanel.add(cb_editBoard);

        JCheckBox cb_schedule = new JCheckBox("Schedule Billboards");
        cb_schedule.setBounds(400, 600, 300, 50);;
        cb_schedule.setFont(font);
        editUserPanel.add(cb_schedule);

        JCheckBox cb_editUsers = new JCheckBox("Edit Users");
        cb_editUsers.setBounds(400, 650, 300, 50);
        cb_editUsers.setFont(font);
        editUserPanel.add(cb_editUsers);

        JLabel lbl_users = new JLabel("List of all users");
        lbl_users.setBounds(screenWidth/2 + 30, 0, 400, 50);
        lbl_users.setFont(new Font("Courier", Font.PLAIN, 50));
        editUserPanel.add(lbl_users);

        // Build a list of all the users.
        DefaultListModel<String> list_allUsers = new DefaultListModel<>();
        list_allUsers.addElement("user1");
        list_allUsers.addElement("user2");
        list_allUsers.addElement("user3");
        list_allUsers.addElement("user4");
        list_allUsers.addElement("user5");
        list_allUsers.addElement("user6");
        list_allUsers.addElement("user7");

        // This forms the list and adds it.
        JList<String> list = new JList<>(list_allUsers);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth/2 + 30, 100, 300, 300);
        editUserPanel.add(scrollPane);

        JButton b_save = new JButton("Save Changes");
        b_save.setBounds(400, 800, 150, 30);
        editUserPanel.add(b_save);

        JButton b_load = new JButton("Edit User");
        b_load.setBounds(screenWidth/2 + 30, 400, 150, 30);
        editUserPanel.add(b_load);

    }

}
