package ControlPanelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ControlPanelInterface {

    static JFrame controlPanelScreen = new JFrame();
    static JPanel createPanel = new JPanel();
    static JPanel listPanel = new JPanel();
    static JPanel schedulePanel = new JPanel();
    static JPanel passwordPanel = new JPanel();
    static JPanel editUserPanel = new JPanel();

    // Get the size of the screen.
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth = screenSize.width;
    static int screenHeight = screenSize.height;

    public static void controlPanelScreen() {

        //Create the Control Panel Screen window and set it the size of the screen.

        controlPanelScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        controlPanelScreen.setResizable(false);

        // Create the individual panels.

        // Create the tabbed pane.
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(0,0,screenWidth,screenHeight);

        // Elements for each pane:

        // Add the tabs to the tab pane.
        tabs.add("Create Billboard",createPanel);
        tabs.add("List Billboards",listPanel);
        tabs.add("Schedule Billboard",schedulePanel);
        tabs.add("Change Password",passwordPanel);
        tabs.add("Edit Users", editUserPanel);

        try {
            CreatePanel.createPanelScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListPanel.listPanelScreen();
        SchedulePanel.schedulePanelScreen();
        ChangePWPanel.changePWScreen();
        EditUsersPanel.editUserScreen();

        controlPanelScreen.add(tabs);
        controlPanelScreen.setLayout(null);
        controlPanelScreen.setUndecorated(true);
        controlPanelScreen.setVisible(true);
        controlPanelScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
