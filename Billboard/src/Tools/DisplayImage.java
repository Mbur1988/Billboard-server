package Tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class DisplayImage {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth = screenSize.width;
    static int screenHeight = screenSize.height;



    /**
     * Display Image takes string of URL or
     *
     * @param filePath is the location of the file to be displayed
     * @throws Exception IO exception
     */
    public DisplayImage(String filePath) throws Exception {
        if (filePath.substring(0, 3) == "www.") {

        } else {
            BufferedImage img = ImageIO.read(new File(filePath));
            ImageIcon icon = new ImageIcon(img);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            //frame.setBackground(Color.blue);
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
     * @throws Exception IO Exception if no data.
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

    public static JLabel DisplayImageLabel(byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIcon icon = new ImageIcon(bImage2);
        JLabel lbl = new JLabel(icon,SwingConstants.CENTER);
        lbl.setOpaque(false);
        lbl.setBounds(0, 0,500 , 500);//check Dimentions
        lbl.setIcon(icon);
//        lbl.setVisible(true);
        //lbl.setIcon(icon);
        return lbl;
    }

}


