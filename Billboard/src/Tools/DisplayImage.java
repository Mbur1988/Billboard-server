package Tools;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class DisplayImage {

    /**
     * This class is to display an image in the screen in a frame.
     * @param arg o = filePath(String), 1 = URL(string),
     * @param data image in byte[] form
     * @throws Exception on incorrect number of inputs
     */
    public static void main(String[] arg,byte[] data) throws Exception
    {
        DisplayImage abc = new DisplayImage(arg[0],arg[1],data);
    }

    public DisplayImage(String filePath,String URL, byte [] data) throws Exception
    {

        if(filePath != null ) {
            BufferedImage img = ImageIO.read(new File(filePath));
            ImageIcon icon = new ImageIcon(img);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(400, 600);    //need to make this size of image
            JLabel lbl = new JLabel();
            lbl.setIcon(icon);
            frame.add(lbl);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (URL != null) {

        } else if (data != null) {

        } else if (filePath == null && URL == null && data == null) {
            throw new  Exception("no image input");
        } else {
            throw new Exception("Only accepts one Image");
        }
    }
}