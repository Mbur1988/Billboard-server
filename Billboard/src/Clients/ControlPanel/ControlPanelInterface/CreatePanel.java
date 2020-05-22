package Clients.ControlPanel.ControlPanelInterface;

import SerializableObjects.Billboard;
import Tools.ColorIndex;
import Tools.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    private static JFileChooser imageChooser;
    private static JFileChooser xmlChooser;
    private static JFileChooser dirChooser;
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
        lbl_name = new JLabel("Billboard name");
        lbl_bgColor = new JLabel("Background color");
        lbl_title = new JLabel("Title text");
        lbl_titleColor = new JLabel("Title color");
        lbl_info = new JLabel("Information text");
        lbl_infoColor = new JLabel("Information Color");
        lbl_picType = new JLabel("Picture type");
        lbl_message = new JLabel("");

        addLabel(createPanel, lbl_name, 10, 100, 300, 50);
        addLabel(createPanel, lbl_bgColor, 10, 150, 300, 50);
        addLabel(createPanel, lbl_title, 10, 200, 300, 50);
        addLabel(createPanel, lbl_titleColor, 10, 250, 300, 50);
        addLabel(createPanel, lbl_info, 10, 300, 300, 50);
        addLabel(createPanel, lbl_infoColor, 10, 350, 300, 50);
        addLabel(createPanel, lbl_picType, 10, 400, 300, 50);
        addLabel(createPanel, lbl_message, 190, 580, 340, 50);

        // Add text fields
        tf_name = new JTextField();
        tf_title = new JTextField();
        tf_info = new JTextField();
        tf_path = new JTextField();

        addTextfield(createPanel, tf_name, 190, 105, 300, 40);
        addTextfield(createPanel, tf_title, 190, 205, 300, 40);
        addTextfield(createPanel, tf_info, 190, 305, 300, 40);
        addTextfield(createPanel, tf_path, 190, 450, 300, 40);

        // Add combo boxes
        cb_bgColor = new JComboBox<>(COLOR_STRINGS);
        cb_titleColor = new JComboBox<>(COLOR_STRINGS);
        cb_infoColor = new JComboBox<>(COLOR_STRINGS);

        // Set default values
        cb_bgColor.setSelectedItem("white");
        cb_titleColor.setSelectedItem("black");
        cb_infoColor.setSelectedItem("gray");

        addCombobox(createPanel, cb_bgColor, 190, 155, 300, 40);
        addCombobox(createPanel, cb_titleColor, 190, 255, 300, 40);
        addCombobox(createPanel, cb_infoColor, 190, 355, 300, 40);

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
        model.addAll(lists.userBillboards);

        // Add JList
        list = new JList(model);

        // Add JScrollPane
        scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth / 2 - 100, 100, 300, 400);
        createPanel.add(scrollPane);

        // Add buttons
        b_fileSelect = new JButton("Select File");
        b_import = new JButton("Import");
        b_export = new JButton("Export");
        b_add = new JButton("Add");
        b_save = new JButton("Save");
        b_clear = new JButton("Clear");
        b_load = new JButton("Load");
        b_delete = new JButton("Delete");
        b_preview = new JButton("Preview");

        addButton(createPanel, b_fileSelect, 10, 450, 180, 40);
        addButton(createPanel, b_import, screenWidth / 2 - 100, 530, 150, 30);
        addButton(createPanel, b_export, screenWidth / 2 + 50, 530, 150, 30);
        addButton(createPanel, b_add, 190, 500, 150, 30);
        addButton(createPanel, b_save, 190, 500, 150, 30);
        b_save.setVisible(false);
        addButton(createPanel, b_clear, 340, 500, 150, 30);
        addButton(createPanel, b_load, screenWidth / 2 - 100, 500, 150, 30);
        addButton(createPanel, b_delete, screenWidth / 2 + 50, 500, 150, 30);
        addButton(createPanel, b_preview, 190, 530, 300, 30);

        imageChooser = new JFileChooser();
        imageChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image (jpg, png, bmp)",
                "jpg", "png", "bmp");
        imageChooser.setFileFilter(filter);
        b_fileSelect.addActionListener(e -> {
            imageChooser.showDialog(b_fileSelect, "Select Image");
            File picture = imageChooser.getSelectedFile();
            String imageFilePath;
            try {
                imageFilePath = picture.getAbsolutePath();
                tf_path.setText(imageFilePath);
            } catch (NullPointerException ex) { }
        });

        xmlChooser = new JFileChooser();
        xmlChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter(
                "Extensible markup language file (.xml)",
                "xml");
        xmlChooser.setFileFilter(xmlFilter);
        b_import.addActionListener(e -> {
            xmlChooser.showDialog(b_import, "Select .xml");
            File picture = xmlChooser.getSelectedFile();
            try {
                String xmlFilePath = picture.getAbsolutePath();
                importBb(xmlFilePath);
            } catch (NullPointerException ex) { }
        });

        dirChooser = new JFileChooser();
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        b_export.addActionListener(e -> {
            dirChooser.showDialog(b_export, "Select Directory");
            File directory = dirChooser.getSelectedFile();
            try {
                String dirFilePath = directory.getAbsolutePath();
                exportBb(dirFilePath);
            } catch (NullPointerException ex) { }
        });

        // Handle button events
        b_add.addActionListener(event -> addBb());

        b_save.addActionListener(event -> saveBb());

        b_clear.addActionListener(event -> clearFields());

        b_load.addActionListener(event -> loadBb());

        b_delete.addActionListener(event -> deleteBb());

        b_preview.addActionListener(event -> previewBb());
    }

    private static void addBb() {
        try {
            populateBillboard();
            user.setAction("addBillboard");
            if (AttemptConnect()) {
                // Send user object to server
                objectStreamer.Send(user);
                objectStreamer.Send(billboard);
                // Await returned object from server
                if (dis.readBoolean()) {
                    lists.userBillboards.add(billboard.getName());
                    Collections.sort(lists.userBillboards);
                    model.clear();
                    model.addAll(lists.userBillboards);
                    lbl_message.setText("Billboard added");
                    Log.Confirmation("New billboard added successfully");
                } else {
                    lbl_message.setText("Billboard already exists");
                    Log.Error("Error when attempting to add new billboard");
                }
                // Disconnect from server
                AttemptDisconnect();
            }
            // Post message to user if unable to connect to server
            else {
                Log.Error("Unable to connect to server");
            }
            resetFields();
        } catch (Exception e) {
            e.printStackTrace();
            Log.Error("Add billboard attempt request failed");
        }
    }

    private static void saveBb() {
        try {
            populateBillboard();
            user.setAction("saveBillboard");
            if (AttemptConnect()) {
                // Send user object to server
                objectStreamer.Send(user);
                objectStreamer.Send(billboard);
                // Await returned object from server
                if (dis.readBoolean()) {
                    lbl_message.setText("Billboard saved");
                    Log.Confirmation("New billboard saved successfully");
                } else {
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
            resetFields();
        } catch (Exception e) {
            e.printStackTrace();
            Log.Error("Add billboard attempt request failed");
        }
    }

    private static void clearFields() {
        b_save.setVisible(false);
        b_add.setVisible(true);
        resetFields();
        lbl_message.setText("");
    }

    private static void importBb(String xmlFilePath) {
        try {
            File file = new File(xmlFilePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("billboard");
            Node node = nodeList.item(0);
            Element root = (Element) node;
            Element message = (Element) root.getElementsByTagName("message").item(0);
            Element picture = (Element) root.getElementsByTagName("picture").item(0);
            Element info = (Element) root.getElementsByTagName("information").item(0);
            cb_bgColor.setSelectedItem(root.getAttribute("background"));
            tf_title.setText(message.getTextContent());
            cb_titleColor.setSelectedItem();
            tf_info.setText(info.getTextContent());
            cb_infoColor.setSelectedItem();
            picture.hasAttribute("url");
            picture.hasAttribute("file");



        } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
    }

    private static void exportBb(String dirFilePath) {
        try {
            populateBillboard();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("billboard");
            // Attribute of root element
            Color bg = billboard.getBackColour();
            root.setAttribute("background",
                    String.format("#%02x%02x%02x", bg.getRed(), bg.getGreen(), bg.getBlue()));
            document.appendChild(root);

            if (!billboard.getMsg().equals(null)) {
                // message element
                Element message = document.createElement("message");
                // Attribute of message element
                Color msg = billboard.getMsgColour();
                message.setAttribute("colour",
                        String.format("#%02x%02x%02x", msg.getRed(), msg.getGreen(), msg.getBlue()));
                // text node of message
                message.appendChild(document.createTextNode(billboard.getMsg()));
                root.appendChild(message);
            }

            if (billboard.getPicUrl() != null || billboard.getPicData() != null) {
                // picture element
                Element picture = document.createElement("picture");
                // Attribute of message element
                if (billboard.getPicUrl() != null) {
                    picture.setAttribute("url", billboard.getPicUrl());
                } else {
                    picture.setAttribute("file", billboard.BytesToSixFour(billboard.getPicData()));
                }
                root.appendChild(picture);
            }

            if (!billboard.getInfo().equals(null)) {
                // information element
                Element info = document.createElement("information");
                // Attribute of message element
                Color inf = billboard.getInfoColour();
                info.setAttribute("colour",
                        String.format("#%02x%02x%02x", inf.getRed(), inf.getGreen(), inf.getBlue()));
                // text node of message
                info.appendChild(document.createTextNode(billboard.getInfo()));
                root.appendChild(info);
            }

            document.setXmlStandalone(true);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(dirFilePath + "\\" + billboard.getName() + ".xml"));
            transformer.transform(source, streamResult);
        } catch (TransformerException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadBb() {
        String name = (String) list.getSelectedValue();
        user.setAction("editBillboard");
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                dos.writeUTF(name);
                if (dis.readBoolean()) {
                    billboard = (Billboard) objectStreamer.Receive();
                    tf_name.setEnabled(false);
                    tf_name.setText(name);
                    cb_bgColor.setSelectedItem(ColorIndex.stringFromColor(billboard.getBackColour()));
                    tf_title.setText(billboard.getMsg());
                    cb_titleColor.setSelectedItem(ColorIndex.stringFromColor(billboard.getMsgColour()));
                    tf_info.setText(billboard.getInfo());
                    cb_infoColor.setSelectedItem(ColorIndex.stringFromColor(billboard.getInfoColour()));
                    if (billboard.getPicUrl() != null) {
                        tf_path.setText(billboard.getPicUrl());
                        rb_url.setSelected(true);
                    } else if (billboard.getPicData() != null) {
                        tf_path.setText("Loaded image data");
                        tf_path.selectAll();
                        tf_path.requestFocus();
                        rb_file.setSelected(true);
                    } else {
                        tf_path.setText("");
                    }
                    b_add.setVisible(false);
                    b_save.setVisible(true);
                    lbl_message.setText("Billboard loaded");
                }
                else {
                    lbl_message.setText("Unable to load billboard");
                }
            }
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

    private static void deleteBb() {
        String name = (String) list.getSelectedValue();
        if (name.equals("")) {
            lbl_message.setText("No billboard selected deleted");
            Log.Confirmation("No billboard selected deleted");
            return;
        }
        user.setAction("deleteBillboard");
        // Attempt connection to server
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                dos.writeUTF(name);
                // check if billboard scheduled
                if (!dis.readBoolean()) {
                    // check if deleted successfully
                    if (dis.readBoolean()) {
                        lists.userBillboards.remove(name);
                        model.removeElement(name);
                        lbl_message.setText("Billboard deleted");
                        Log.Confirmation("Billboard successfully deleted");
                    } else {
                        lbl_message.setText("Billboard not deleted");
                        Log.Error("Error when attempting to delete billboard");
                    }
                } else {
                    lbl_message.setText("Billboard is currently scheduled");
                    Log.Error("Can't delete scheduled billboard");
                }
            }
            catch (IOException e) {
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
    }

    private static void previewBb() {
        try {
            populateBillboard();
            billboard.previewBillboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void populateBillboard() throws IOException {
        String picURL = null;
        byte[] picDATA = null;
        if (tf_path.getText().equals("Loaded image data")) {
            picDATA = billboard.getPicData();
        } else if (rb_file.isSelected() && !tf_path.getText().equals("")) {
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
                colorFromString((String) cb_titleColor.getSelectedItem()),
                colorFromString((String) cb_bgColor.getSelectedItem()),
                colorFromString((String) cb_infoColor.getSelectedItem()),
                user.getUsername(),
                false);
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