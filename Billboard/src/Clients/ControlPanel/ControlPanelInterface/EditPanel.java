package Clients.ControlPanel.ControlPanelInterface;

import SerializableObjects.Billboard;
import Tools.ColorIndex;
import Tools.Log;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import static Clients.Client.*;
import static Clients.ControlPanel.ControlPanel.*;
import static Clients.ControlPanel.ControlPanelInterface.CreatePanel.usersListModel;
import static Clients.ControlPanel.ControlPanelInterface.ListPanel.listModel;
import static Clients.ControlPanel.ControlPanelInterface.SchedulePanel.billboardListModel;
import static Clients.ControlPanel.ControlPanelInterface.SchedulePanel.scheduleListModel;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;
import static Clients.ControlPanel.LoginInterface.LoginInterface.getSchedulesList;
import static Tools.ColorIndex.*;

class EditPanel extends ControlPanelInterface {

    // Variables required by the class
    private static Billboard billboard;
    private static JLabel lbl_message;
    private static JTextField tf_name;
    private static JTextField tf_title;
    private static JTextField tf_info;
    private static JTextField tf_path;
    private static JComboBox<String> cb_bgColor;
    private static JComboBox<String> cb_titleColor;
    private static JComboBox<String> cb_infoColor;
    private static JRadioButton rb_url;
    private static JRadioButton rb_file;
    static DefaultListModel allListModel;
    private static JList list;
    private static JButton b_fileSelect;
     private static JButton b_save;
    private static JFileChooser imageChooser;

    /**
     * The main method of the create panel class populates the GUI page with all required objects
     */
    protected static void editPanelScreen() {

        editPanel.setLayout(null);
        billboard = new Billboard();

        // Add title labels
        // Title label for billboard details
        JLabel label_editBillboard = new JLabel("Billboard details");
        label_editBillboard.setBounds(0, 0, 400, 50);
        label_editBillboard.setFont(new Font("Courier", Font.PLAIN, 50));
        editPanel.add(label_editBillboard);

        // Title label for the billboards list
        JLabel lbl_billboards = new JLabel("All billboards");
        lbl_billboards.setBounds(screenWidth / 2 - 100, 0, 450, 50);
        lbl_billboards.setFont(new Font("Courier", Font.PLAIN, 50));
        editPanel.add(lbl_billboards);

        // Create labels
        JLabel lbl_name = new JLabel("Billboard name");
        JLabel lbl_bgColor = new JLabel("Background color");
        JLabel lbl_title = new JLabel("Title text");
        JLabel lbl_titleColor = new JLabel("Title color");
        JLabel lbl_info = new JLabel("Information text");
        JLabel lbl_infoColor = new JLabel("Information Color");
        JLabel lbl_picType = new JLabel("Picture type");
        lbl_message = new JLabel("");

        // Add labels to the panel
        addLabel(editPanel, lbl_name, 10, 100, 300, 50);
        addLabel(editPanel, lbl_bgColor, 10, 150, 300, 50);
        addLabel(editPanel, lbl_title, 10, 200, 300, 50);
        addLabel(editPanel, lbl_titleColor, 10, 250, 300, 50);
        addLabel(editPanel, lbl_info, 10, 300, 300, 50);
        addLabel(editPanel, lbl_infoColor, 10, 350, 300, 50);
        addLabel(editPanel, lbl_picType, 10, 400, 300, 50);
        addLabel(editPanel, lbl_message, 190, 580, 340, 50);

        // Create text fields
        tf_name = new JTextField();
        tf_title = new JTextField();
        tf_info = new JTextField();
        tf_path = new JTextField();

        // Add text fields to the panel
        addTextfield(editPanel, tf_name, 190, 105, 300, 40);
        addTextfield(editPanel, tf_title, 190, 205, 300, 40);
        addTextfield(editPanel, tf_info, 190, 305, 300, 40);
        addTextfield(editPanel, tf_path, 190, 450, 300, 40);

        // Disable name field
        tf_name.setEnabled(false);

        // Create combo boxes
        cb_bgColor = new JComboBox<>(COLOR_STRINGS);
        cb_titleColor = new JComboBox<>(COLOR_STRINGS);
        cb_infoColor = new JComboBox<>(COLOR_STRINGS);

        // Set default values of the combo boxes
        cb_bgColor.setSelectedItem("white");
        cb_titleColor.setSelectedItem("black");
        cb_infoColor.setSelectedItem("gray");

        // Add combo boxes to panel
        addCombobox(editPanel, cb_bgColor, 190, 155, 300, 40);
        addCombobox(editPanel, cb_titleColor, 190, 255, 300, 40);
        addCombobox(editPanel, cb_infoColor, 190, 355, 300, 40);

        // Create radio buttons
        rb_file = new JRadioButton("File:", true);
        rb_url = new JRadioButton("URL:");

        // Add radio buttons to panel
        addRadioButton(editPanel, rb_file, 190, 400, 150, 50);
        addRadioButton(editPanel, rb_url, 340, 400, 150, 50);

        // Create a group for the radio buttons
        ButtonGroup group = new ButtonGroup();
        // Add radio buttons to group
        group.add(rb_file);
        group.add(rb_url);
        // Create action listener
        ActionListener rb_ActionListener = actionEvent -> {
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            b_fileSelect.setEnabled(aButton.getText().equals("File:"));
            tf_path.setText("");
        };
        // Add the action listener to the radio buttons
        rb_file.addActionListener(rb_ActionListener);
        rb_url.addActionListener(rb_ActionListener);

        // Create and add a default list model
        allListModel = new DefaultListModel();
        allListModel.addAll(listBillboards.billboards);

        // Create a new JList
        list = new JList(allListModel);

        // Create and add JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth / 2 - 100, 100, 300, 400);
        editPanel.add(scrollPane);

        // Create buttons
        b_fileSelect = new JButton("Select File");
        b_save = new JButton("Save");
        JButton b_clear = new JButton("Clear");
        JButton b_load = new JButton("Load");
        JButton b_delete = new JButton("Delete");
        JButton b_preview = new JButton("Preview");

        // Add buttons to panel
        addButton(editPanel, b_fileSelect, 10, 450, 180, 40);
        addButton(editPanel, b_save, 190, 500, 150, 30);
        addButton(editPanel, b_clear, 340, 500, 150, 30);
        addButton(editPanel, b_load, screenWidth / 2 - 100, 500, 150, 30);
        addButton(editPanel, b_delete, screenWidth / 2 + 50, 500, 150, 30);
        addButton(editPanel, b_preview, 190, 530, 300, 30);

        // Create and add a file chooser to add an image from a directory
        imageChooser = new JFileChooser();
        imageChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // Create and add a filter so that only jpg, png and bmp file types may be selected
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image (jpg, png, bmp)",
                "jpg", "png", "bmp");
        imageChooser.setFileFilter(filter);
        // Add an action listener to handle the image selection
        b_fileSelect.addActionListener(e -> {
            imageChooser.showDialog(b_fileSelect, "Select Image");
            File picture = imageChooser.getSelectedFile();
            String imageFilePath;
            try {
                // Add the absolute directory path of the selected image to the path text field
                imageFilePath = picture.getAbsolutePath();
                tf_path.setText(imageFilePath);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        });

        fieldsEnabled(false);

        // Handle button press events
        b_save.addActionListener(event -> saveBb());
        b_clear.addActionListener(event -> clearFields());
        b_load.addActionListener(event -> loadBb());
        b_delete.addActionListener(event -> deleteBb());
        b_preview.addActionListener(event -> previewBb());
    }

    /**
     * Method used to edit a current billboard in the database
     */
    private static void saveBb() {
        // if the name field is empty then display a message to the user and return
        if (tf_name.getText().equals("")) {
            lbl_message.setText("Billboard must have a name");
            return;
        }
        // if the name field is populated then attempt to add the billboard
        try {
            // populate the static instance "billboard" with the billboard data entered by the user
            populateBillboard();
            // set the action request to the server
            user.setAction("Edit Billboard");
            // attempt connection to the server
            if (AttemptConnect()) {
                // Send user object to server
                objectStreamer.Send(user);
                objectStreamer.Send(billboard);
                // Await confirmation that the billboard was edited successfully
                if (dis.readBoolean()) {
                    // display confirmation message to the user and post log confirmation
                    lbl_message.setText("Billboard saved");
                    Log.Confirmation("New billboard saved successfully");
                }
                // If billboard not edited then display message to the user
                else {
                    lbl_message.setText("Unable to edit billboard");
                    Log.Error("Error when attempting to saved billboard");
                }
                // Disconnect from server
                AttemptDisconnect();
            }
            // Post message to user if unable to connect to server
            else {
                Log.Error("Unable to connect to server");
            }
            // clear all user input fields
            resetFields();
            // disable user input fields
            fieldsEnabled(false);
        }
        // catch any unanticipated exceptions and print to console
        catch (Exception e) {
            e.printStackTrace();
            Log.Error("Add billboard attempt request failed");
        }
    }

    /**
     * method used to reset the user input fields, clear the message and set add button visible
     */
    private static void clearFields() {
        resetFields();
        fieldsEnabled(false);
        lbl_message.setText("");
    }

    /**
     * Loads the billboard credentials of the selected billboard from the database via the server
     */
    private static void loadBb() {
        // get the name of the billboard to load from the Jlist
        String name = (String) list.getSelectedValue();
        // ensure that there is a name selected
        if (name.equals("")) {
            lbl_message.setText("No billboard selected");
            Log.Confirmation("No billboard selected");
            return;
        }
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
                    // receive the requested billboard as an object
                    billboard = (Billboard) objectStreamer.Receive();
                    // enable the edit billboard fields
                    fieldsEnabled(true);
                    // populate the user input fields with the billboard credentials
                    tf_name.setText(name);
                    cb_bgColor.setSelectedItem(ColorIndex.stringFromColor(billboard.getBackColour()));
                    tf_title.setText(billboard.getMsg());
                    cb_titleColor.setSelectedItem(ColorIndex.stringFromColor(billboard.getMsgColour()));
                    tf_info.setText(billboard.getInfo());
                    cb_infoColor.setSelectedItem(ColorIndex.stringFromColor(billboard.getInfoColour()));
                    // if the pic was loaded from a url then display the url
                    if (billboard.getPicUrl() != null) {
                        tf_path.setText(billboard.getPicUrl());
                        rb_url.setSelected(true);
                        b_fileSelect.setEnabled(false);
                    }
                    // if the pic was loaded from a file then inform the user that the picture data has been loaded
                    else if (billboard.getPicData() != null) {
                        tf_path.setText("Loaded image data");
                        tf_path.selectAll();
                        tf_path.requestFocus();
                        rb_file.setSelected(true);
                        b_fileSelect.setEnabled(true);
                    }
                    // if there is no picture then clear the path text field
                    else {
                        tf_path.setText("");
                    }
                    // make the save button visible
                    b_save.setEnabled(true);
                    // display message to the user
                    lbl_message.setText("Billboard loaded");
                }
                // if the server was unable to load the billboard then notify the user
                else {
                    lbl_message.setText("Unable to load billboard");
                }
            }
            // catch any unanticipated exceptions and print to console
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                Log.Error("Failed to retrieve billboard credentials");
            }
            // Disconnect from server
            AttemptDisconnect();
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
    }

    /**
     * Deletes the selected billboard from the database provided that it is not scheduled
     */
    private static void deleteBb() {
        // get the name of the billboard to delete from the Jlist
        String name = (String) list.getSelectedValue();
        // ensure that there is a name selected
        if (name.equals("")) {
            lbl_message.setText("No billboard selected");
            Log.Confirmation("No billboard selected");
            return;
        }
        // set the action request to the server
        user.setAction("Delete Billboard");
        // Attempt connection to server
        if (AttemptConnect()) {
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // send the name of the billboard to be deleted from the database
                dos.writeUTF(name);
                // check if deleted successfully
                if (dis.readBoolean()) {
                    // remove the billboard from the list
                    listUserBillboards.userBillboards.remove(name);
                    listBillboards.billboards.remove(name);
                    if (usersListModel != null) {
                        usersListModel.removeElement(name);
                    }
                    if (billboardListModel != null) {
                        billboardListModel.removeElement(name);
                    }
                    if (allListModel != null) {
                        allListModel.removeElement(name);
                    } else if (listModel != null) {
                        listModel.removeElement(name);
                    }
                    // display confirmation message to the user and post log confirmation
                    lbl_message.setText("Billboard deleted");
                    Log.Confirmation("Billboard successfully deleted");
                    getSchedulesList();
                    scheduleListModel.clear();
                    scheduleListModel.addAll(listSchedules.schedules);
                }
                // If billboard not deleted then display message to the user
                else {
                    lbl_message.setText("Billboard not deleted");
                    Log.Error("Error when attempting to delete billboard");
                }
            }
            // catch any unanticipated exceptions and print to console
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                Log.Error("Failed to delete billboard");
            }
            // Disconnect from server
            AttemptDisconnect();
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
        resetFields();
        fieldsEnabled(false);
    }

    /**
     * displays a preview of the billboard based on the user inputted data
     */
    private static void previewBb() {
        try {
            populateBillboard();
            billboard.previewBillboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the static billboard instance with the user inputted data
     * @throws IOException Signals that an I/O exception of some sort has occurred
     */
    private static void populateBillboard() throws Exception {
        // create picture staging variables
        String picURL = null;
        byte[] picDATA = null;
        // identify the picture data source if any and add it to the image variables
        if (tf_path.getText().equals("Loaded image data")) {
            picDATA = billboard.getPicData();
        } else if (rb_file.isSelected() && !tf_path.getText().equals("")) {
            picDATA = billboard.ConvertImageToData(tf_path.getText());
        } else if (rb_url.isSelected() && !tf_path.getText().equals("")) {
            picURL = tf_path.getText();
            picDATA = billboard.UrlToData(tf_path.getText());
        }
        // populate the billboard
        billboard = new Billboard(
                tf_name.getText(),
                tf_title.getText(),
                tf_info.getText(),
                picURL, picDATA,
                colorFromString((String) cb_titleColor.getSelectedItem()),
                colorFromString((String) cb_bgColor.getSelectedItem()),
                colorFromString((String) cb_infoColor.getSelectedItem()),
                user.getUsername(),
                false);
    }

    /**
     * reset the user input fields to their initial status
     */
    private static void resetFields() {
        tf_name.setText("");
        tf_title.setText("");
        tf_info.setText("");
        tf_path.setText("");
        cb_bgColor.setSelectedItem("white");
        cb_titleColor.setSelectedItem("black");
        cb_infoColor.setSelectedItem("gray");
        rb_url.setSelected(false);
        rb_file.setSelected(true);
        b_fileSelect.setEnabled(true);
    }

    private static void fieldsEnabled(boolean state) {
        tf_title.setEnabled(state);
        tf_info.setEnabled(state);
        tf_path.setEnabled(state);
        cb_bgColor.setEnabled(state);
        cb_titleColor.setEnabled(state);
        cb_infoColor.setEnabled(state);
        rb_url.setEnabled(state);
        rb_file.setEnabled(state);
        b_fileSelect.setEnabled(state);
        b_save.setEnabled(state);
    }
}