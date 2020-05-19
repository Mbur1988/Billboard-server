package Clients.ControlPanel.ControlPanelInterface;

import SerializableObjects.Billboard;
import Tools.ColorIndex;
import Tools.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static Clients.ControlPanel.ControlPanel.*;
import static Tools.ColorIndex.*;

public class CreatePanel extends ControlPanelInterface {

    private static Billboard billboard;
    private static JLabel lbl_name;
    private static JLabel lbl_bgColor;
    private static JLabel lbl_title;
    private static JLabel lbl_titleColor;
    private static JLabel lbl_info;
    private static JLabel lbl_infoColor;
    private static JLabel lbl_picType;
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
    private static ButtonGroup group;
    private static DefaultListModel model;
    private static JList list;
    private static JScrollPane scrollPane;
    private static JButton b_fileSelect;
    private static JButton b_add;
    private static JButton b_save;
    private static JButton b_clear;
    private static JButton b_import;
    private static JButton b_export;
    private static JButton b_load;
    private static JButton b_delete;
    private static JButton b_preview;

    public static void createPanelScreen() {

        createPanel.setLayout(null);

        // Add title labels
        JLabel label_editUser = new JLabel("Billboard details");
        label_editUser.setBounds(0, 0, 400, 50);
        label_editUser.setFont(new Font("Courier", Font.PLAIN, 50));
        createPanel.add(label_editUser);

        JLabel lbl_users = new JLabel(user.getUsername() + "'s billboards");
        lbl_users.setBounds(screenWidth / 2 - 100, 0, 450, 50);
        lbl_users.setFont(new Font("Courier", Font.PLAIN, 50));
        createPanel.add(lbl_users);

        // Add labels
        JLabel lbl_name = new JLabel("Billboard name");
        JLabel lbl_bgColor = new JLabel("Background color");
        JLabel lbl_title = new JLabel("Title text");
        JLabel lbl_titleColor = new JLabel("Title color");
        JLabel lbl_info = new JLabel("Information text");
        JLabel lbl_infoColor = new JLabel("Information Color");
        JLabel lbl_picType = new JLabel("Picture type");
        JLabel lbl_message = new JLabel("");

        addLabel(createPanel, lbl_name, 10, 100, 300, 50);
        addLabel(createPanel, lbl_bgColor, 10, 150, 300, 50);
        addLabel(createPanel, lbl_title, 10, 200, 300, 50);
        addLabel(createPanel, lbl_titleColor, 10, 250, 300, 50);
        addLabel(createPanel, lbl_info, 10, 300, 300, 50);
        addLabel(createPanel, lbl_infoColor, 10, 350, 300, 50);
        addLabel(createPanel, lbl_picType, 10, 400, 300, 50);
        addLabel(createPanel, lbl_message, 190, 500, 340, 50);

        // Add text fields
        tf_name = new JTextField();
        tf_title = new JTextField();
        tf_info = new JTextField();
        tf_path = new JTextField();

        addTextfield(createPanel, tf_name, 190, 100, 300, 50);
        addTextfield(createPanel, tf_title, 190, 200, 300, 50);
        addTextfield(createPanel, tf_info, 190, 300, 300, 50);
        addTextfield(createPanel, tf_path, 190, 450, 300, 50);

        // Add combo boxes
        cb_bgColor = new JComboBox<>(COLORS);
        cb_titleColor = new JComboBox<>(COLORS);
        cb_infoColor = new JComboBox<>(COLORS);

        // Set default values
        cb_bgColor.setSelectedItem("white");
        cb_titleColor.setSelectedItem("black");
        cb_infoColor.setSelectedItem("gray");

        addCombobox(createPanel, cb_bgColor, 190, 150, 300, 50);
        addCombobox(createPanel, cb_titleColor, 190, 250, 300, 50);
        addCombobox(createPanel, cb_infoColor, 190, 350, 300, 50);

        // Add radio buttons
        rb_file = new JRadioButton("File:", true);
        rb_url = new JRadioButton("URL:");

        addRadioButton(createPanel, rb_file, 190, 400, 150, 50);
        addRadioButton(createPanel, rb_url, 340, 400, 150, 50);

        // Add radio buttons to group and register action listeners
        group = new ButtonGroup();
        group.add(rb_file);
        group.add(rb_url);

        // Create action listener and add it to the radio buttons
        ActionListener rb_ActionListener = actionEvent -> {
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            b_fileSelect.setVisible(aButton.getText().equals("File:"));
            tf_path.setText("");
        };
        rb_file.addActionListener(rb_ActionListener);
        rb_url.addActionListener(rb_ActionListener);

        // Add default list model
        model = new DefaultListModel();
        model.addAll(lists.billboards);

        // Add JList
        list = new JList(model);

        // Add JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth/2 - 100, 100, 300, 400);
        createPanel.add(scrollPane);


        b_fileSelect = new JButton("Select File");
        b_fileSelect.setBounds(20, 450, 150, 50);
        createPanel.add(b_fileSelect);

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        b_fileSelect.addActionListener(e -> {
            chooser.showDialog(b_fileSelect,"Select Image");
            File picture = chooser.getSelectedFile();
            String imageFilePath;
            try {
                imageFilePath = picture.getAbsolutePath();
                tf_path.setText(imageFilePath);
            } catch (NullPointerException ex) { }
        });

        // Add buttons
        b_add = new JButton("Add");
        b_save = new JButton("Save");
        b_clear = new JButton("Clear");
        b_import = new JButton("Import");
        b_export = new JButton("Export");
        b_load = new JButton("Edit");
        b_delete = new JButton("Delete");
        b_preview = new JButton("Preview");

        addButton(createPanel, b_add, 190, 500, 150, 30);
        b_save.setBounds(190, 500, 150, 30);
        addButton(createPanel, b_clear, 340, 500, 150, 30);
        addButton(createPanel, b_import, screenWidth/2 - 100, 530, 150, 30);
        addButton(createPanel, b_export, screenWidth/2 + 50, 530, 150, 30);
        addButton(createPanel, b_load, screenWidth/2 - 100, 500, 150, 30);
        addButton(createPanel, b_delete, screenWidth/2 + 50, 500, 150, 30);
        addButton(createPanel, b_preview, 190, 530, 300, 30);

        // Handle button events
        b_add.addActionListener(event -> addBb());

        b_save.addActionListener(event -> saveBb());

        b_clear.addActionListener(event -> clearFields());

        b_import.addActionListener(event -> importBb());

        b_export.addActionListener(event -> exportBb());

        b_load.addActionListener(event -> loadBb());

        b_delete.addActionListener(event -> deleteBb());

        b_preview.addActionListener(event -> previewBb());
    }

    private static void addBb() {
        populateBilboard();
        user.setAction("addBillboard");
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                objectStreamer.Send(billboard);
                // Await returned object from server
                if (dis.readBoolean()) {
                    lists.users.add(billboard.getName());
                    Collections.sort(lists.billboards);
                    model.clear();
                    model.addAll(lists.billboards);
                    lbl_message.setText("Billboard added");
                    Log.Confirmation("New billboard added successfully");
                }
                else {
                    lbl_message.setText("Billboard already exists");
                    Log.Error("Error when attempting to add new billboard");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.Error("Add billboard attempt request failed");
            }
            // Disconnect from server
            AttemptDisconnect();
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
        resetFields();
    }

    private static void saveBb() {

    }

    private static void clearFields() {
        resetFields();
    }

    private static void importBb() {

    }

    private static void exportBb() {

    }

    private static void loadBb() {

    }

    private static void deleteBb() {

    }

    private static void previewBb() {
        populateBilboard();
    }

    private static void populateBilboard() {
        try {
            String picURL = null;
            byte[] picDATA = null;
            if (rb_file.isSelected() && !tf_path.getText().equals("")) {
                picDATA = billboard.ConvertImageToData(tf_path.getText());
            } else if (rb_url.isSelected() && !tf_path.getText().equals("")) {
                picURL = tf_path.getText();
                picDATA = billboard.UrlToData(tf_path.getText());
            }
            billboard = new Billboard(
                    tf_name.getText(),
                    tf_title.getText(),
                    tf_info.getText(),
                    picURL, picDATA,
                    color.get(cb_titleColor.getSelectedItem()),
                    color.get(cb_bgColor.getSelectedItem()),
                    color.get(cb_infoColor.getSelectedItem()));
            billboard.showBillboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void resetFields() {
        tf_name.setEnabled(true);
        tf_name.setText("");
        tf_title.setText("");
        tf_info.setText("");
        tf_path.setText("");
        cb_bgColor.setSelectedItem("white");
        cb_titleColor.setSelectedItem("black");
        cb_infoColor.setSelectedItem("gray");
        rb_url.setSelected(false);
        rb_file.setSelected(true);
        b_fileSelect.setVisible(true);
    }
}