package ControlPanelInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreatePanel extends ControlPanelInterface {

    public static void createPanelScreen() throws IOException {

       createPanel.setLayout(null);

        JPanel previewPanel = new JPanel();
        previewPanel.setBounds((screenWidth / 2) -450,25,900,screenHeight - 100);
        previewPanel.setBackground(Color.white);
        previewPanel.setLayout(null);

        // Create new Billboard Panel:
        JLabel label_nameBoard = new JLabel("Set Billboard Name: ");
        label_nameBoard.setBounds(0,0,150,30);
        createPanel.add(label_nameBoard);
        createPanel.add(previewPanel);

        JTextField Name = new JTextField();
        Name.setBounds(120,0,150,30);
        String billboardName = Name.getText();                  // Billboard name for storage on server etc.
        createPanel.add(Name);

        JLabel label_imageFile = new JLabel("Image Path:");
        label_imageFile.setBounds(0, 50, 150, 30);
        createPanel.add(label_imageFile);

        JTextField imageFile = new JTextField();
        imageFile.setBounds(120,50,150,30);

        String imageFileName = imageFile.getText();

        createPanel.add(imageFile);

        JColorChooser colour = new JColorChooser();
        colour.setBounds(0,100,500,300);    // I think all this is wrong but it seems to work lol
        createPanel.add(colour);

        //      This whole section is meant to add the picture to the preview window.
        //      Doesn't work...

        BufferedImage img = ImageIO.read(new File("F://Uni Files/QUT/2020/Semester 1 2020/CAB302 Software Development/Assignment 1/cab302_assignment/Billboard_ControlPanel/src/Images/cat.jpg"));
        ImageIcon icon = new ImageIcon(img);
        JLabel pic = new JLabel();
        pic.setIcon(icon);
        pic.setBounds(300,0,600,900);

        // Upper billboard text.
        String default_UpperText = "Text to be displayed at the top of the billboard...";
        JTextArea upperText = new JTextArea(default_UpperText);
        upperText.setBounds(0, 430, 500, 200);
        createPanel.add(upperText);
        //String topText = upperText.getText();

        // This label will be placed at the top of the preview window to display the upper billboard text.
        JLabel label_upperText = new JLabel();
        label_upperText.setBounds(100,100,100,100);
        previewPanel.add(label_upperText);

        // Clear the hint text when the field is clicked.
        upperText.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if ((upperText.getText()).equals(default_UpperText)) {
                    upperText.setText("");
                }
            }
        });

        // Lower billboard text.
        String default_LowerText = "Text to be displayed at the bottom of the billboard...";
        JTextArea lowerText = new JTextArea(default_LowerText);
        lowerText.setBounds(0, 680, 500, 200);
        createPanel.add(lowerText);
        //String bottomText = lowerText.getText();

        // This label will be placed at the bottom of the preview window to display the lower billboard text.
        JLabel label_lowerText = new JLabel();
        label_lowerText.setBounds(100,300,100,100);
        previewPanel.add(label_lowerText);

        // Clear the hint text when the field is clicked.
        lowerText.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if ((lowerText.getText()).equals(default_LowerText)) {
                    lowerText.setText("");
                }
            }
        });

        // Preview button.
        JButton b_Preview = new JButton("Preview");
        b_Preview.setBounds((screenWidth / 2) - 450 ,  screenHeight - 60, 900, 30);
        createPanel.add(b_Preview);

        b_Preview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {   // Can I have this auto update without having
                Color newcolour = colour.getColor();       // to click preview each time a change is made?
                previewPanel.setBackground(newcolour);     // Think it's wrong anyway lol
                label_upperText.setText(upperText.getText()); // Doesn't work...
                label_lowerText.setText(lowerText.getText());
                previewPanel.add(pic);
            }
        });


        JButton b_Save = new JButton("Export"); // Export (save) button.
        b_Save.setBounds(105, screenHeight - 60, 100, 30);
        createPanel.add(b_Save);

        JButton b_Import = new JButton("Import"); // Import button.
        b_Import.setBounds(0, screenHeight - 60, 100, 30);
        createPanel.add(b_Import);

        JButton b_Clear = new JButton("Clear"); // Clear button.
        b_Clear.setBounds(210, screenHeight - 60, 100, 30);
        createPanel.add(b_Clear);

        b_Clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {            // Find a way to reset all parameters.
                previewPanel.setBackground(Color.white);
                imageFile.setText(null);
                Name.setText(null);
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
