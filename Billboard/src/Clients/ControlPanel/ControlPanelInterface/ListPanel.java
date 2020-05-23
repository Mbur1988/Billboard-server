package Clients.ControlPanel.ControlPanelInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ListPanel extends ControlPanelInterface {

    protected static void listPanelScreen(){

        listPanel.setLayout(null);

        // List Billboards Panel:
        JLabel label_List = new JLabel("List of all billboards on your system:");
        label_List.setBounds(0, 0, 250, 30);
        listPanel.add(label_List);

        // Build a list of all the billboards.
        DefaultListModel<String> list_allBillboards = new DefaultListModel<>();
        list_allBillboards.addElement("will");
        list_allBillboards.addElement("need");
        list_allBillboards.addElement("a for");
        list_allBillboards.addElement("loop");
        list_allBillboards.addElement("to get");
        list_allBillboards.addElement("all boards");
        list_allBillboards.addElement("from server");

        // This forms the list and adds it.
        JList<String> list = new JList<>(list_allBillboards);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0,50,300,300);

        listPanel.add(scrollPane);

        // Add a text area that's unable to be edited to show scheduling info etc...
        JTextArea boardInformation = new JTextArea("Select a billboard to view details. \n \n Will need an if statement to show " +
                "selected board.");
        boardInformation.setBounds(305,50,500,300);
        boardInformation.setEditable(false);
        listPanel.add(boardInformation);

        // Add buttons
        JButton b_Preview2 = new JButton("Preview"); // Preview selected billboard.
        b_Preview2.setBounds(0,350, 300,30);
        listPanel.add(b_Preview2);

        JButton b_Edit = new JButton("Edit"); // Preview selected billboard.
        b_Edit.setBounds(0,385, 300,30);
        listPanel.add(b_Edit);

        JButton b_Delete = new JButton("Delete"); // Preview selected billboard.
        b_Delete.setBounds(0,420, 300,30);
        listPanel.add(b_Delete);


    }
}
