package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.Tools;
import Tools.UserAccess;

import javax.swing.*;
import java.awt.*;
import static Clients.ControlPanel.ControlPanel.user;

public class ControlPanelInterface {

    public static JFrame controlPanelScreen = new JFrame();
    public static JPanel createPanel = new JPanel();
    public static JPanel listPanel = new JPanel();
    public static JPanel editPanel = new JPanel();
    public static JPanel schedulePanel = new JPanel();
    public static JPanel passwordPanel = new JPanel();
    public static JPanel editUserPanel = new JPanel();

    // Get the size of the screen.
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screenWidth = screenSize.width;
    public static int screenHeight = screenSize.height;

    public static void controlPanelScreen() {

        //Create the Control Panel Screen window and set it the size of the screen.

        controlPanelScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        controlPanelScreen.setResizable(false);

        // Create the individual panels.

        // Create the tabbed pane.
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(0, 0, screenWidth, screenHeight);

        // Elements for each pane:

        // If the user has authorisation to create billboards then add the create panel screen
        if (UserAccess.dec2bool(user.getAccess())[0]) {
            tabs.add("My Billboards", createPanel);
            CreatePanel.createPanelScreen();
        }
        // If the user has authorisation to edit all billboards then add the edit panel screen
        if (UserAccess.dec2bool(user.getAccess())[1]) {
            tabs.add("All Billboards", editPanel);
            EditPanel.editPanelScreen();
        }
        // If the user is not authorised to edit all billboards then add the list panel screen
        else {
            tabs.add("All Billboards", listPanel);
            ListPanel.listPanelScreen();
        }
        // If the user has authorisation to schedule billboards then add the schedule panel screen
        if (UserAccess.dec2bool(user.getAccess())[2]) {
            tabs.add("Schedule Billboards", schedulePanel);
            SchedulePanel.schedulePanelScreen();
        }
        // All users are able to access the password panel
        tabs.add("Change Password",passwordPanel);
        ChangePWPanel.changePWScreen();
        // If the user has authorisation to edit users then add the edit user screen
        if (UserAccess.dec2bool(user.getAccess())[3]) {
            tabs.add("Edit Users", editUserPanel);
            EditUsersPanel.editUserScreen();
        }

        // Add exit and logout buttons to all panels
        Tools.addExitButton(screenWidth - 105, screenHeight - 60, 100, 30);
        //Tools.addLogoutButton(screenWidth - 210, screenHeight - 60, 100, 30);

        controlPanelScreen.getContentPane().add(tabs);
        controlPanelScreen.setLayout(null);
        controlPanelScreen.setUndecorated(true);
        controlPanelScreen.setVisible(true);
        controlPanelScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // May need HIDE_ON_CLOSE instead of exit.
    }
}
