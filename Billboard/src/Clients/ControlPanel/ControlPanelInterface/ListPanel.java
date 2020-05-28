package Clients.ControlPanel.ControlPanelInterface;

import SerializableObjects.Billboard;
import Tools.ColorIndex;
import Tools.Log;
import Tools.ObjectStreamer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Clients.Client.dis;
import static Clients.Client.dos;
import static Clients.Client.objectStreamer;
import static Clients.ControlPanel.ControlPanel.*;
import static Clients.ControlPanel.ControlPanel.AttemptDisconnect;
import static Clients.ControlPanel.ControlPanelTools.Tools.addButton;

class ListPanel extends ControlPanelInterface {

    private static Billboard billboard;
    static DefaultListModel listModel;
    private static JList list;

    protected static void listPanelScreen(){

        listPanel.setLayout(null);
        billboard = new Billboard();

        // Add title labels
        // Title label for billboard details
        JLabel label_editUser = new JLabel("All Billboards");
        label_editUser.setBounds(screenWidth / 2 - 100, 0, 400, 50);
        label_editUser.setFont(new Font("Courier", Font.PLAIN, 50));
        listPanel.add(label_editUser);

        // Create and add a default list model
        listModel = new DefaultListModel();
        listModel.addAll(listBillboards.billboards);

        // Create a new JList
        list = new JList(listModel);

        // Create and add JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth / 2 - 100, 100, 300, 400);
        listPanel.add(scrollPane);

        // Create button
        JButton b_preview = new JButton("Preview");

        // Add button to panel
        addButton(listPanel, b_preview, screenWidth / 2 - 100, 500, 300, 30);

        // Handle button press events
        b_preview.addActionListener(event -> previewBb());
    }

    private static void previewBb() {
        // get the name of the billboard to load from the Jlist
        String name = (String) list.getSelectedValue();
        // set the action request to the server
        user.setAction("Get Billboard Information");
        // attempt connection to the server
        if (AttemptConnect()) {
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // send the name of the billboard to retrieve from the database
                dos.writeUTF(name);
                // await confirmation that the billboard has been successfully retrieved from the database
                if (dis.readBoolean()) {
                    // receive the requested billboard
                    billboard = (Billboard) objectStreamer.Receive();
                    // preview the billboard
                    billboard.previewBillboard();
                }
            }
            // catch any unanticipated exceptions and print to console
            catch (Exception e) {
                e.printStackTrace();
                Log.Error("Failed to preview billboard");
            }
            // Disconnect from server
            AttemptDisconnect();
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
    }
}
