package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.Tools;
import Clients.ControlPanel.ControlPanelTools.UserAccess;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
        tabs.setBounds(0,0,screenWidth,screenHeight);

        // Elements for each pane:

        // Add the tabs to the tab pane.
        if (UserAccess.dec2bool(user.getAccess())[0]) {
            tabs.add("Create Billboard",createPanel);
        }
        if (UserAccess.dec2bool(user.getAccess())[1]) {
            tabs.add("Edit Billboards", editPanel);
            EditPanel.editPanelScreen();
        } else {
            tabs.add("List Billboards", listPanel);
            ListPanel.listPanelScreen();
        }
        if (UserAccess.dec2bool(user.getAccess())[2]) {
            tabs.add("Schedule Billboards", schedulePanel);
            SchedulePanel.schedulePanelScreen();
        }
        tabs.add("Change Password",passwordPanel);
        ChangePWPanel.changePWScreen();
        if (UserAccess.dec2bool(user.getAccess())[3]) {
            tabs.add("Edit Users", editUserPanel);
            EditUsersPanel.editUserScreen();
        }

        try {
            CreatePanel.createPanelScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Tools.addExitButton(screenWidth - 105, screenHeight - 60, 100, 30);

        controlPanelScreen.getContentPane().add(tabs);
        controlPanelScreen.setLayout(null);
        controlPanelScreen.setUndecorated(true);
        controlPanelScreen.setVisible(true);
        controlPanelScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // May need HIDE_ON_CLOSE instead of exit.

    }
}
