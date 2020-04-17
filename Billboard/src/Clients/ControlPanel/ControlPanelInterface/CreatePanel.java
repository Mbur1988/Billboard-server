package Clients.ControlPanel.ControlPanelInterface;

import SerializableObjects.Billboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class CreatePanel extends ControlPanelInterface {

    public static void createPanelScreen() throws Exception {

        createPanel.setLayout(null);
        Billboard BillboardBeingMade = new Billboard();


        // Create new Billboard Panel:
        JLabel label_nameBoard = new JLabel("Set Billboard Name: ");
        label_nameBoard.setBounds(0,0,150,30);
        createPanel.add(label_nameBoard);
        //createPanel.add(previewPanel);

        JTextField Name = new JTextField();
        Name.setBounds(120,0,150,30);
       // String billboardName = Name.getText();                  // Billboard name for storage on server etc.
        BillboardBeingMade.setName(Name.getText());
        createPanel.add(Name);

        JLabel label_imageFile = new JLabel("Image URL:");
        label_imageFile.setBounds(0, 50, 150, 30);
        createPanel.add(label_imageFile);

        JTextField imageFile = new JTextField();                //todo entering URL
        imageFile.setBounds(70,50,150,30);
        createPanel.add(imageFile);

        JLabel label_OR = new JLabel("OR");
        label_OR.setBounds(250, 50, 50, 30);
        createPanel.add(label_OR);

        JButton b_fileSelect = new JButton("Select File");
        b_fileSelect.setBounds(310,50,150,30);
        createPanel.add(b_fileSelect);

        JFileChooser chooser = new JFileChooser();

        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        b_fileSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.showDialog(b_fileSelect,"Select Image");
                File picture = chooser.getSelectedFile();
                String imageFilePath = picture.getAbsolutePath();

                //BufferedImage img = null;
                //imageFile.setText(imageFilePath);

                try {
                    //img = ImageIO.read(new File(imageFile.getText()));
                    BillboardBeingMade.setPicData(BillboardBeingMade.ConvertImageToData(imageFilePath));
                } catch (Exception ioException) {
                    ioException.printStackTrace();
                }


             // ImageIcon icon = new ImageIcon(img);
             // // display image from data as icon   //TODO watch to make centre (screen width - width of pic /2????)
             // JLabel pic = new JLabel();
             // pic.setIcon(icon);
             // pic.setBounds(300,0,previewSize.width,previewSize.height - 200);
             // previewPanel.add(pic);
            }
        });
        //colour chooser
        JColorChooser colour = new JColorChooser();
        colour.setBounds(0,100,600,300);    // I think all this is wrong but it seems to work lol
        colour.setPreviewPanel(new JPanel());
        createPanel.add(colour);    //output of color chooser
        BillboardBeingMade.setBackColour(colour.getColor());

        // Upper billboard text.
        String default_UpperText = "Click here to enter text...";
        JTextArea upperTextPanel = new JTextArea(default_UpperText);
        upperTextPanel.setBounds(0, 515, screenWidth/2, 100);
        upperTextPanel.setLineWrap(true);
        createPanel.add(upperTextPanel);
        // Clear the hint text when the field is clicked.
        String msgText = upperTextPanel.getText();
        BillboardBeingMade.setMsg(msgText);
        upperTextPanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if ((upperTextPanel.getText()).equals(default_UpperText)) {
                    upperTextPanel.setText("");
                }
            }
        });

        // Lower billboard text.
        String default_LowerText = "Text to be displayed at the bottom of the billboard...";

        JTextArea lowerTextPannel = new JTextArea(default_LowerText);
        lowerTextPannel.setBounds(0, 815, 500, 100);
        createPanel.add(lowerTextPannel);
        String bottomText = lowerTextPannel.getText();
        BillboardBeingMade.setInfo(bottomText);
        // Clear the hint text when the field is clicked.
        lowerTextPannel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if ((lowerTextPannel.getText()).equals(default_LowerText)) {
                    lowerTextPannel.setText("");
                }
            }
        });

        // Preview button.
        JButton b_Preview = new JButton("Preview");

        b_Preview.setBounds((0) ,  screenHeight - 60, screenWidth/2, 30);
        createPanel.add(b_Preview);



        b_Preview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newcolour = colour.getColor();
                String colourChosen = String.valueOf(newcolour.getRGB());

                //previewPanel.setBackground(newcolour);
                //label_upperText.setText(upperText.getText()); // Doesn't work...
                //label_lowerText.setText(lowerText.getText());
               // previewPanel.add(pic);

                try {
                    BillboardBeingMade.showBillboard();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton b_Save = new JButton("Export"); // Export (save) button.
        b_Save.setBounds(screenWidth - 435, screenHeight - 60, 100, 30);
        createPanel.add(b_Save);

        JButton b_Import = new JButton("Import"); // Import button.
        b_Import.setBounds(screenWidth - 325, screenHeight - 60, 100, 30);
        createPanel.add(b_Import);

        JButton b_Clear = new JButton("Clear"); // Clear button.
        b_Clear.setBounds(screenWidth - 215, screenHeight - 60, 100, 30);
        createPanel.add(b_Clear);

        b_Clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {            // Find a way to reset all parameters.
                BillboardBeingMade.WishhyWassyTheBillyBoardy();
            }
        });

        JButton b_Exit = new JButton("Exit"); // Exit button.
        b_Exit.setBounds(screenWidth - 105, screenHeight - 60, 100, 30);
        createPanel.add(b_Exit);

        b_Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {            // Close the screen on exit.
                controlPanelScreen.dispose();
            }
        });

    }
}