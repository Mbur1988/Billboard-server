package Tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class DisplayImage {
//    /**
//     * TODO DELETE ME--->
//     * TEST RUNNER
//     * This class is to display an image in the screen in a frame.
//     * @param arg file path or URL (include www. or http)
//     * @throws Exception on incorrect
//     *
//     */

//    public static void main(String[] arg) throws Exception
//    {
//        //DisplayImage abc = new DisplayImage("C:\\sally.jpg");
//    }
    // // // //<----TODO DELETE ME

    /**
     * Displat Image takes string of URL or
     *
     * @param filePath
     * @throws Exception
     */
    public DisplayImage(String filePath) throws Exception {
        if (filePath.substring(0,3) == "www." || filePath.substring(0, 3) == "http") {

        } else {
            BufferedImage img = ImageIO.read(new File(filePath));
            ImageIcon icon = new ImageIcon(img);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setBackground(Color.blue);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
//            frame.setBounds(600,200,200,600);
            JLabel lbl = new JLabel();
            lbl.setIcon(icon);
            frame.add(lbl);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    /**
     * This is the function that will be used to read data in byte array form and convert it to an image.
     *
     * @param data byte[] of an image to be used.
     * @throws Exception
     */
    public DisplayImage(byte[] data) throws Exception {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIcon icon = new ImageIcon(bImage2);
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       // System.out.println("image created");
    }
}


