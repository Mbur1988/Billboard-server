package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.Tools;

import javax.swing.*;
import java.awt.*;

public class ChangePWPanel extends ControlPanelInterface {

    public static void changePWScreen() {

        passwordPanel.setLayout(null);

//        JLabel label_changePW = new JLabel("Don't forget to change your password every 12 years!!!");
//        label_changePW.setBounds(0,0,500,300);
//        passwordPanel.add(label_changePW);

        Tools.addLabel_panel(ControlPanelInterface.passwordPanel, "lbl_pw", "Change pw", 0, 0,
                500, 300, "Courier", 1, 20, 2, 0);

    }

}
