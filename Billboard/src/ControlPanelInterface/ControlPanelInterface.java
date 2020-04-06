package ControlPanelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import ControlPanelInterface.*;
/*
    To who ever lay eyes upon this code...
    I do so humbly apologise for my not so epic coding.
 */

public class ControlPanelInterface {

    static JPanel createPanel = new JPanel();
    static JFrame controlPanelScreen = new JFrame();

    // Get the size of the screen.
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth = screenSize.width;
    static int screenHeight = screenSize.height;

    public static void controlPanelScreen() throws IOException {

        //Create the Control Panel Screen window and set it the size of the screen.
//        JFrame controlPanelScreen = new JFrame();
        controlPanelScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        controlPanelScreen.setResizable(false);

        // Create the individual panels.
//        JPanel createPanel = new JPanel();
//        createPanel.setLayout(null);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(null);

        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(null);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(null);

        JPanel editUserPanel = new JPanel();
        editUserPanel.setLayout(null);

        JPanel createPanel = new JPanel();

        // Create the tabbed pane.
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(0,0,screenWidth,screenHeight);

        // Should probably break here and move the different panels to different files.
        // This one is getting a bit big.

        // Elements for each pane:
        CreatePanel.createPanelScreen(); // Following commented code moved to CreatePanel.java.
                                         // Also doesn't work...

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        JPanel previewPanel = new JPanel();
//        previewPanel.setBounds((screenWidth / 2) -450,25,900,screenHeight - 100);
//        previewPanel.setBackground(Color.white);
//        previewPanel.setLayout(null);
//
//        // Create new Billboard Panel:
//        JLabel label_nameBoard = new JLabel("Set Billboard Name: ");
//        label_nameBoard.setBounds(0,0,150,30);
//        createPanel.add(label_nameBoard);
//        createPanel.add(previewPanel);

//        JTextField Name = new JTextField();
//        Name.setBounds(120,0,150,30);
//        String billboardName = Name.getText();                  // Billboard name for storage on server etc.
//        createPanel.add(Name);
//
//        JLabel label_imageFile = new JLabel("Image Path:");
//        label_imageFile.setBounds(0, 50, 150, 30);
//        createPanel.add(label_imageFile);
//
//        JTextField imageFile = new JTextField();
//        imageFile.setBounds(120,50,150,30);
//
//        String imageFileName = imageFile.getText();
//
//        createPanel.add(imageFile);

//        JColorChooser colour = new JColorChooser();
//        colour.setBounds(0,100,500,300);    // I think all this is wrong but it seems to work lol
//        createPanel.add(colour);
//
//        //      This whole section is meant to add the picture to the preview window.
//        //      Doesn't work...
//
//        BufferedImage img = ImageIO.read(new File("F://Uni Files/QUT/2020/Semester 1 2020/CAB302 Software Development/Assignment 1/cab302_assignment/Billboard_ControlPanel/src/Images/cat.jpg"));
//        ImageIcon icon = new ImageIcon(img);
//        JLabel pic = new JLabel();
//        pic.setIcon(icon);
//        pic.setBounds(300,0,600,900);
//
//        // Upper billboard text.
//        String default_UpperText = "Text to be displayed at the top of the billboard...";
//        JTextArea upperText = new JTextArea(default_UpperText);
//        upperText.setBounds(0, 430, 500, 200);
//        createPanel.add(upperText);
//        //String topText = upperText.getText();
//
//        // This label will be placed at the top of the preview window to display the upper billboard text.
//        JLabel label_upperText = new JLabel();
//        label_upperText.setBounds(100,100,100,100);
//        previewPanel.add(label_upperText);
//
//        // Clear the hint text when the field is clicked.
//        upperText.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseClicked(MouseEvent e){
//                if ((upperText.getText()).equals(default_UpperText)) {
//                    upperText.setText("");
//                }
//            }
//        });
//
//        // Lower billboard text.
//        String default_LowerText = "Text to be displayed at the bottom of the billboard...";
//        JTextArea lowerText = new JTextArea(default_LowerText);
//        lowerText.setBounds(0, 680, 500, 200);
//        createPanel.add(lowerText);
//        String bottomText = lowerText.getText();
//
//        // This label will be placed at the bottom of the preview window to display the lower billboard text.
//        JLabel label_lowerText = new JLabel();
//        label_lowerText.setBounds(100,300,100,100);
//        previewPanel.add(label_lowerText);
//
//        // Clear the hint text when the field is clicked.
//        lowerText.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseClicked(MouseEvent e){
//                if ((lowerText.getText()).equals(default_LowerText)) {
//                    lowerText.setText("");
//                }
//            }
//        });
//
//        // Preview button.
//        JButton b_Preview = new JButton("Preview");
//        b_Preview.setBounds((screenWidth / 2) - 450 ,  screenHeight - 60, 900, 30);
//        createPanel.add(b_Preview);
//
//        b_Preview.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {   // Can I have this auto update without having
//                Color newcolour = colour.getColor();       // to click preview each time a change is made?
//                previewPanel.setBackground(newcolour);     // Think it's wrong anyway lol
//                label_upperText.setText(upperText.getText()); // Doesn't work...
//                label_lowerText.setText(lowerText.getText());
//                previewPanel.add(pic);
//            }
//        });
//
//
//        JButton b_Save = new JButton("Export"); // Export (save) button.
//        b_Save.setBounds(105, screenHeight - 60, 100, 30);
//        createPanel.add(b_Save);
//
//        JButton b_Import = new JButton("Import"); // Import button.
//        b_Import.setBounds(0, screenHeight - 60, 100, 30);
//        createPanel.add(b_Import);
//
//        JButton b_Clear = new JButton("Clear"); // Clear button.
//        b_Clear.setBounds(210, screenHeight - 60, 100, 30);
//        createPanel.add(b_Clear);
//
//        b_Clear.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {            // Find a way to reset all parameters.
//                previewPanel.setBackground(Color.white);
//                imageFile.setText(null);
//                Name.setText(null);
//            }
//        });
//
//        JButton b_Exit = new JButton("Exit"); // Exit button.
//        b_Exit.setBounds(screenWidth - 105, screenHeight - 60, 100, 30);
//        createPanel.add(b_Exit);
//
//        b_Exit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {            // Close the screen on exit.
//                controlPanelScreen.dispose();
//            }
//        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        list.setBounds(0,50,300,300);
        listPanel.add(list);

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

        JButton b_Exit2 = new JButton("Exit"); // Exit button.
        b_Exit2.setBounds(screenWidth - 105, screenHeight - 60, 100, 30);
        listPanel.add(b_Exit2);

        b_Exit2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlPanelScreen.dispose();
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Schedule Billboards Panel:
        JLabel label_Schedule = new JLabel("What's on when");

        // Edit Users panel:
        JLabel label_EditUsers = new JLabel("Edit Users");


        // Add the tabs to the tab pane.
        tabs.add("Create Billboard",createPanel);
        tabs.add("List Billboards",listPanel);
        tabs.add("Schedule Billboard",schedulePanel);
        tabs.add("Change Password",passwordPanel);
        tabs.add("Edit Users", editUserPanel);

        controlPanelScreen.add(tabs);
        controlPanelScreen.setLayout(null);
        controlPanelScreen.setUndecorated(true);
        controlPanelScreen.setVisible(true);
        controlPanelScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
