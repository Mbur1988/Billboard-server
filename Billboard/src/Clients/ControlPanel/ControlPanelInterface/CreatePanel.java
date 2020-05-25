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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import static Clients.ControlPanel.ControlPanel.*;
import static Clients.ControlPanel.ControlPanelInterface.EditPanel.allListModel;
import static Clients.ControlPanel.ControlPanelInterface.ListPanel.listModel;
import static Clients.ControlPanel.ControlPanelInterface.SchedulePanel.billboardListModel;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;
import static SerializableObjects.Lists.sortAdd;
import static Tools.ColorIndex.*;

class CreatePanel extends ControlPanelInterface {

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
    static DefaultListModel usersListModel;
    private static JList list;
    private static JButton b_fileSelect;
    private static JFileChooser imageChooser;
    private static JFileChooser xmlChooser;
    private static JFileChooser dirChooser;
    private static JButton b_add;
    private static JButton b_save;
    private static JButton b_import;
    private static JButton b_export;

    /**
     * The main method of the create panel class populates the GUI page with all required objects
     */
    protected static void createPanelScreen() {

        createPanel.setLayout(null);
        billboard = new Billboard();

        // Add title labels
        // Title label for billboard details
        JLabel label_editBillboard = new JLabel("Billboard details");
        label_editBillboard.setBounds(0, 0, 400, 50);
        label_editBillboard.setFont(new Font("Courier", Font.PLAIN, 50));
        createPanel.add(label_editBillboard);

        // Title label for the current user's billboards
        JLabel lbl_billboards = new JLabel(user.getUsername() + "'s billboards");
        lbl_billboards.setBounds(screenWidth / 2 - 100, 0, 450, 50);
        lbl_billboards.setFont(new Font("Courier", Font.PLAIN, 50));
        createPanel.add(lbl_billboards);

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
        addLabel(createPanel, lbl_name, 10, 100, 300, 50);
        addLabel(createPanel, lbl_bgColor, 10, 150, 300, 50);
        addLabel(createPanel, lbl_title, 10, 200, 300, 50);
        addLabel(createPanel, lbl_titleColor, 10, 250, 300, 50);
        addLabel(createPanel, lbl_info, 10, 300, 300, 50);
        addLabel(createPanel, lbl_infoColor, 10, 350, 300, 50);
        addLabel(createPanel, lbl_picType, 10, 400, 300, 50);
        addLabel(createPanel, lbl_message, 190, 580, 340, 50);

        // Create text fields
        tf_name = new JTextField();
        tf_title = new JTextField();
        tf_info = new JTextField();
        tf_path = new JTextField();

        // Add text fields to the panel
        addTextfield(createPanel, tf_name, 190, 105, 300, 40);
        addTextfield(createPanel, tf_title, 190, 205, 300, 40);
        addTextfield(createPanel, tf_info, 190, 305, 300, 40);
        addTextfield(createPanel, tf_path, 190, 450, 300, 40);

        // Create combo boxes
        cb_bgColor = new JComboBox<>(COLOR_STRINGS);
        cb_titleColor = new JComboBox<>(COLOR_STRINGS);
        cb_infoColor = new JComboBox<>(COLOR_STRINGS);

        // Set default values of the combo boxes
        cb_bgColor.setSelectedItem("white");
        cb_titleColor.setSelectedItem("black");
        cb_infoColor.setSelectedItem("gray");

        // Add combo boxes to panel
        addCombobox(createPanel, cb_bgColor, 190, 155, 300, 40);
        addCombobox(createPanel, cb_titleColor, 190, 255, 300, 40);
        addCombobox(createPanel, cb_infoColor, 190, 355, 300, 40);

        // Create radio buttons
        rb_file = new JRadioButton("File:", true);
        rb_url = new JRadioButton("URL:");

        // Add radio buttons to panel
        addRadioButton(createPanel, rb_file, 190, 400, 150, 50);
        addRadioButton(createPanel, rb_url, 340, 400, 150, 50);

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
        usersListModel = new DefaultListModel();
        usersListModel.addAll(lists.userBillboards);

        // Create a new JList
        list = new JList(usersListModel);

        // Create and add JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth / 2 - 100, 100, 300, 400);
        createPanel.add(scrollPane);

        // Create buttons
        b_fileSelect = new JButton("Select File");
        b_import = new JButton("Import");
        b_export = new JButton("Export");
        b_add = new JButton("Add");
        b_save = new JButton("Save");
        JButton b_clear = new JButton("Clear");
        JButton b_load = new JButton("Load");
        JButton b_delete = new JButton("Delete");
        JButton b_preview = new JButton("Preview");

        // Add buttons to panel
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

        // Create and add a file chooser to select an xml file to import
        xmlChooser = new JFileChooser();
        xmlChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // Create and add a filter so that only xml file types may be selected
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter(
                "Extensible markup language file (.xml)",
                "xml");
        xmlChooser.setFileFilter(xmlFilter);
        // Add an action listener to handle the xml selection
        b_import.addActionListener(e -> {
            xmlChooser.showDialog(b_import, "Select .xml");
            File picture = xmlChooser.getSelectedFile();
            try {
                // Get the absolute directory path of the selected xml and use it to call importBb method
                String xmlFilePath = picture.getAbsolutePath();
                importBb(xmlFilePath);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        });

        // Create and add a directory chooser to select a directory to export the billboard to
        dirChooser = new JFileChooser();
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Add an action listener to handle the directory selection
        b_export.addActionListener(e -> {
            dirChooser.showDialog(b_export, "Select Directory");
            File directory = dirChooser.getSelectedFile();
            try {
                // Get the absolute directory path of the selected directory and use it to call exportBb method
                String dirFilePath = directory.getAbsolutePath();
                exportBb(dirFilePath);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        });

        // Handle button press events
        b_add.addActionListener(event -> addBb());
        b_save.addActionListener(event -> saveBb());
        b_clear.addActionListener(event -> clearFields());
        b_load.addActionListener(event -> loadBb());
        b_delete.addActionListener(event -> deleteBb());
        b_preview.addActionListener(event -> previewBb());
    }

    /**
     * Method to add the user created billboard to the database
     */
    private static void addBb() {
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
            user.setAction("addBillboard");
            // attempt connection to the server
            if (AttemptConnect()) {
                // Send user object to server
                objectStreamer.Send(user);
                // Send billboard object to server
                objectStreamer.Send(billboard);
                // Await confirmation that the billboard was added successfully
                if (dis.readBoolean()) {
                    // add new billboard to the list of the current user's billboards and resort it alphabetically
                    sortAdd(lists.userBillboards, billboard.getName());
                    sortAdd(lists.billboards, billboard.getName());
                    if(usersListModel != null) {
                        usersListModel.clear();
                        usersListModel.addAll(lists.userBillboards);
                    }
                    if(billboardListModel != null) {
                        billboardListModel.clear();
                        billboardListModel.addAll(lists.billboards);
                    }
                    if(allListModel != null) {
                        allListModel.clear();
                        allListModel.addAll(lists.billboards);
                    }
                    else if (listModel != null) {
                        listModel.clear();
                        listModel.addAll(lists.billboards);
                    }
                    // display confirmation message to the user and post log confirmation
                    lbl_message.setText("Billboard added");
                    Log.Confirmation("New billboard added successfully");
                }
                // If billboard not added then display message to the user
                else {
                    lbl_message.setText("Check that Billboard does not already exist");
                    Log.Error("Error when attempting to add new billboard");
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
        }
        // catch any unanticipated exceptions and print to console
        catch (Exception e) {
            e.printStackTrace();
            Log.Error("Add billboard attempt request failed");
        }
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
            user.setAction("saveBillboard");
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
        b_save.setVisible(false);
        b_add.setVisible(true);
        resetFields();
        lbl_message.setText("");
    }

    /**
     * Imports a billboard from an exported billboard .xml file. Populates the user input fields with the relevant info.
     * @param xmlFilePath the absolute directory path of the .xml file to import
     */
    private static void importBb(String xmlFilePath) {
        try {
            // use DocumentBuilderFactory to import the file and creates a document to extract the billboard details
            File file = new File(xmlFilePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            // create a node list of elements
            NodeList nodeList = document.getElementsByTagName("billboard");
            // get the root node and parse it to an element
            Node node = nodeList.item(0);
            Element root = (Element) node;
            // if the root node is null then the xml file is of the wrong format
            if (root == null) {
                lbl_message.setText("error importing billboard");
                Log.Confirmation("error importing billboard");
                return;
            }
            // reset the user input fields before attempting to populate them with the imported billboard
            resetFields();
            // extract message, picture and info elements
            Element message = (Element) root.getElementsByTagName("message").item(0);
            Element picture = (Element) root.getElementsByTagName("picture").item(0);
            Element info = (Element) root.getElementsByTagName("information").item(0);
            // set the background colour combo box using the appropriate attribute
            String colour = root.getAttribute("background");
            Color color = Color.decode(colour);
            cb_bgColor.setSelectedItem(ColorIndex.stringFromColor(color));
            // set the message text and colour if the message node exists
            if (message != null) {
                tf_title.setText(message.getTextContent());
                colour = message.getAttribute("colour");
                color = Color.decode(colour);
                cb_titleColor.setSelectedItem(ColorIndex.stringFromColor(color));
            }
            // set the info text and colour if the info node exists
            if (info != null) {
                tf_info.setText(info.getTextContent());
                colour = info.getAttribute("colour");
                color = Color.decode(colour);
                cb_infoColor.setSelectedItem(ColorIndex.stringFromColor(color));
            }
            // check whether the picture node exists
            if (picture != null) {
                // if the picture node attribute is url then add it to the path text field
                if (picture.hasAttribute("url")) {
                    rb_url.setSelected(true);
                    tf_path.setText(picture.getAttribute("url"));
                }
                // if the picture node attribute is file then add the image data to billboard and display message
                else if (picture.hasAttribute("file")) {
                    rb_file.setSelected(true);
                    tf_path.setText("Loaded image data");
                    String pic64 = picture.getAttribute("file");
                    // convert picture format from base64 to byte array
                    byte[] picData = billboard.SixFourToByte(pic64);
                    billboard.setPicData(picData);
                }
            }
            // set save button visible
            b_save.setVisible(false);
            b_add.setVisible(true);
            lbl_message.setText("Billboard imported");

        }
        // inform the user of any import error and update the log
        catch (ParserConfigurationException | SAXException | IOException e) {
            lbl_message.setText("error importing billboard");
            Log.Confirmation("error importing billboard");
            e.printStackTrace();
        }
    }

    /**
     * exports the user entered billboard details to the desired directory as an xml file named the same as the billboard
     * @param dirFilePath the directory path location to save the billboard .xml file
     */
    private static void exportBb(String dirFilePath) {
        try {
            // if the name field is empty then display a message to the user and return
            if (tf_name.getText().equals("")) {
                lbl_message.setText("Billboard must have a name");
                return;
            }
            // populate the static instance "billboard" with the billboard data entered by the user
            populateBillboard();
            // use DocumentBuilderFactory to create a document to extract the billboard details into
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // create the root element of the xml
            Element root = document.createElement("billboard");
            // set the root attribute background
            Color bg = billboard.getBackColour();
            root.setAttribute("background",
                    String.format("#%02x%02x%02x", bg.getRed(), bg.getGreen(), bg.getBlue()));
            // append the root element to the document
            document.appendChild(root);
            // Check if there is a billboard message to save
            if (!billboard.getMsg().equals("")) {
                // create a message element
                Element message = document.createElement("message");
                // set the message attribute colour
                Color msg = billboard.getMsgColour();
                message.setAttribute("colour",
                        String.format("#%02x%02x%02x", msg.getRed(), msg.getGreen(), msg.getBlue()));
                // set the text node of message
                message.appendChild(document.createTextNode(billboard.getMsg()));
                // append the message element to the root element
                root.appendChild(message);
            }
            // Check if there is a picture to save
            if (billboard.getPicUrl() != null || billboard.getPicData() != null) {
                // create a message element
                Element picture = document.createElement("picture");
                // if the picture is from a url then add a "url" attribute
                if (billboard.getPicUrl() != null) {
                    picture.setAttribute("url", billboard.getPicUrl());
                }
                // if the picture is from a file then add the picture in base64 format
                else {
                    picture.setAttribute("file", billboard.BytesToSixFour(billboard.getPicData()));
                }
                // append the picture element to the root element
                root.appendChild(picture);
            }
            // Check if there is any billboard info to save
            if (!billboard.getInfo().equals("")) {
                // create a information element
                Element info = document.createElement("information");
                // set the info attribute colour
                Color inf = billboard.getInfoColour();
                info.setAttribute("colour",
                        String.format("#%02x%02x%02x", inf.getRed(), inf.getGreen(), inf.getBlue()));
                // set the text node of message
                info.appendChild(document.createTextNode(billboard.getInfo()));
                // append the info element to the root element
                root.appendChild(info);
            }
            // set setXmlStandalone to true
            document.setXmlStandalone(true);
            // use transformer factory and DOM source to save the xml in the desired directory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(dirFilePath + "\\" + billboard.getName() + ".xml"));
            transformer.transform(source, streamResult);
            // display confirmation message to the user
            lbl_message.setText("Billboard exported");
        }
        // catch any unanticipated exceptions and print to console
        catch (TransformerException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
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
        user.setAction("getBillboard");
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
                    // disable the billboard name text field
                    tf_name.setEnabled(false);
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
                    b_add.setVisible(false);
                    b_save.setVisible(true);
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
        user.setAction("deleteBillboard");
        // Attempt connection to server
        if (AttemptConnect()) {
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // send the name of the billboard to be deleted from the database
                dos.writeUTF(name);
                // check if billboard scheduled
                if (!dis.readBoolean()) {
                    // check if deleted successfully
                    if (dis.readBoolean()) {
                        // remove the billboard from the list
                        lists.userBillboards.remove(name);
                        lists.billboards.remove(name);
                        if (usersListModel != null) {
                            usersListModel.removeElement(name);
                        }
                        if (billboardListModel != null) {
                            billboardListModel.removeElement(name);
                        }
                        if (allListModel != null) {
                            allListModel.removeElement(name);
                        }
                        else if (listModel != null) {
                            listModel.removeElement(name);
                        }
                        // display confirmation message to the user and post log confirmation
                        lbl_message.setText("Billboard deleted");
                        Log.Confirmation("Billboard successfully deleted");
                    }
                    // If billboard not deleted then display message to the user
                    else {
                        lbl_message.setText("Billboard not deleted");
                        Log.Error("Error when attempting to delete billboard");
                    }
                }
                // If billboard currently scheduled then display message to the user
                else {
                    lbl_message.setText("Billboard is currently scheduled");
                    Log.Error("Can't delete scheduled billboard");
                }
            }
            // catch any unanticipated exceptions and print to console
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
    private static void populateBillboard() throws IOException {
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
        b_fileSelect.setEnabled(true);
    }
}