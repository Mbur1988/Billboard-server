package SerializableObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import Tools.*;

public class Billboard implements Serializable {

    private String billboardName;
    private String msg;
    private String info;
    private String picURL;
    private byte[] picDATA;
    private String msgColour;
    private String backColour;
    private String infoColour;

    //setting a blank Billboard
    public Billboard(){

        this.billboardName = null;
       this.msg = null;
       this.info = null;
       this.picURL = null;
       this.picDATA = null;
       this.msgColour = null;
       this.backColour = null;
       this.infoColour = null;
    }
    /**
     *
     * @param billboardName The name of the billboard for database control
     * @param msg The message to be displayed at the top
     * @param info The message to be displayed at the bottom
     * @param picURL the URL to the pic to be displayed. (incompattible to picDATA)
     * @param picDATA  the image that has been converted to 64 bit to be displayed. (incompattible to picURL)
     * @param MsgColour colour of the msg text in hex
     * @param BackColour colour of the back ground in hex
     * @param InfoColour colour of info writting
     */
    public Billboard(String billboardName, String msg, String info, String picURL, byte[] picDATA, String MsgColour, String BackColour, String InfoColour) {
        this.billboardName = billboardName;
        this.msg = msg;
        this.info = info;
        this.picURL = picURL;
        this.picDATA = picDATA;
        this.msgColour = MsgColour;
        this.backColour = BackColour;
        this.infoColour = InfoColour;

    }
    public static void main(String[] args) throws Exception {
        new Billboard();

        JFrame frame = new JFrame();

        frame.setBackground(Color.cyan);
        new DisplayImage("C:\\sally.jpg");

    }

    // - - - - - - - helpers bellow - - - - - - - - - //

    //  Getters  //

    /**
     * Helper to get the message stored
     * @return String msg
     */

    public String getBillboardName() {return billboardName;}
    /**
     * Helper to get the billboard name stored
     * @return String info
     */

    public  String getMsg(){ return msg; }

    /**
     * Helper to get the info stored
     * @return String info
     */
    public  String getInfo() {return info; }

    /**
     * Helper to get the pic URL stored
     * @return String picURL
     */
    public  String getPicUrl() {return picURL; }

    /**
     * Helper to get the picture data stored
     * @return String picDATA
     */
    public  byte[] getPicData() {return picDATA; }

    /**
     * Helper to get the message colour stored
     * @return String msg colour
     */
    public String getMsgColour() {return msgColour; }


    /**
     * Helper to get the background colour stored
     * @return String background colour
     */
    public String getBackColour() {return backColour; }

    /**
     * Helper to get the info colour stored
     * @return String info colour
     */
    public String getInfoColour() {return infoColour; }

    //  setters  //

    /**
     * sets name of Billboard
     * @param name
     */

    public void setName(String name) {this.billboardName = name;}

    /**
     * sets message
     * @param message String message to be displayed
     */

    public void setMsg(String message) { this.msg = message;}

    /**
     * sets info
     * @param info String Informaiton to be displayed
     */
    public void setInfo(String info) { this.info = info;}

    /**
     * sets pictures URL
     * @param picURL URL to the pic String
     */
    public void setPicURL(String picURL) { this.picURL = picURL;}

    /**
     * Sets Pic Data as string to be converted to base64
     * @param picData byte[] of image
     */

    public void setPicData(byte[] picData) {this.picDATA = picData;}

    /**
     * sets message colour as string to be converted to HEX
     * @param msgColour colour of string
     */

    public void setMsgColour(String msgColour) {this.msgColour = msgColour ;}

    /**
     * Sets back colour as string to be converted to HEX
     * @param backColour colour of string
     */

    public void setBackColour(String backColour) {this.backColour = backColour;}

    /**
     * Sets info colour as string to be converted to HEX
     * @param infoColour colour of string
     */

    public void setInfoColour(String infoColour) { this.infoColour = infoColour;}


    //   converters    //

    /**
     * Converts a String in a Hex form and spits out a 9 number string representing rrrgggbbb
     *
     * @param string String that represents a hex colour Ex %FFFFFF
     * @return String representing red green blue Ex 255255255
     */
    public String ConvertStringToRGB(String string) {
        String Out;
        int R =(Integer.parseInt(string.substring(1,3),16));
        int G =(Integer.parseInt(string.substring(3,5),16));
        int B =(Integer.parseInt(string.substring(5,7),16));
        return Out = "" + R + G + B;
    }

    /**
     * Converts a RGB 9 number string into a Hex String.
     *
     * @param r = red   (0-255)
     * @param g = green (0-255)
     * @param b = blue  (0-255)
     * @return String of the entered RGB as a Hex representation in a string. Ex #FFFFFF
     */
    public String ConvertRGBtoString(int r, int g, int b) {
        String hex = String.format("#%02X%02X%02X", r, g, b);
        return hex;
    }
   //Needs to be completed.

    /**
     * Converts a image from the file path to a byte array
     * @param filePath image to be converted
     * @return image in byte[] form
     * @throws Exception if no file found
     */
    public byte[] ConvertImageToData(File filePath) throws Exception {
                BufferedImage bImage = ImageIO.read(new File(String.valueOf(filePath)));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos );
                byte [] data = bos.toByteArray();
    return data;
    }

    /**
     * Converts a byte[] to a buffered immage.
     * @param imageData byte[] data of image
     * @return buffered image
     * @throws IOException if cant read
     */

    public BufferedImage ConvertDataToImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        BufferedImage image = ImageIO.read(bais);
        return image;
    }

}



