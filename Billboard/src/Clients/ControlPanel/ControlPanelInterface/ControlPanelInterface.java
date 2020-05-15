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
        if (UserAccess.dec2bin(user.getAccess())[0] == 1) {
            tabs.add("Create Billboard",createPanel);
        }
        if (UserAccess.dec2bin(user.getAccess())[1] == 1) {
            tabs.add("Edit Billboards", listPanel);
        }
        if (UserAccess.dec2bin(user.getAccess())[2] == 1) {
            tabs.add("Schedule Billboards", schedulePanel);
        }
        tabs.add("Change Password",passwordPanel);
        if (UserAccess.dec2bin(user.getAccess())[3] == 1) {
            tabs.add("Edit Users", editUserPanel);
        }

        try {
            CreatePanel.createPanelScreen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListPanel.listPanelScreen();
        SchedulePanel.schedulePanelScreen();
        ChangePWPanel.changePWScreen();
        EditUsersPanel.editUserScreen();

        Tools.addExitButton(screenWidth - 105, screenHeight - 60, 100, 30);

        controlPanelScreen.getContentPane().add(tabs);
        controlPanelScreen.setLayout(null);
        controlPanelScreen.setUndecorated(true);
        controlPanelScreen.setVisible(true);
        controlPanelScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // May need HIDE_ON_CLOSE instead of exit.

    }

}
