package SerializableObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Billboard implements Serializable {
    // Get the size of the screen.
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth = screenSize.width;
    static int screenHeight = screenSize.height;

    // Globals

    private String msg;
    private String name;
    private String info;
    private String picURL;
    private byte[] picDATA;
    private String msgColour;
    private String backColour;
    private String infoColour;
    private JFrame BillboardScreen;
    private JPanel BillboardScreenPannel;

    //setting a blank Billboard
    public Billboard(){
       this.msg = null;
       this.name = null;
       this.info = null;
       this.picURL = null;
       this.picDATA = null;
       this.msgColour = null;
       this.backColour = null;
       this.infoColour = null;
       this.BillboardScreen = createFrame();
       this.BillboardScreenPannel = CreatePanel();

    }
    /**
     *
     * @param msg The message to be displayed at the top
     * @param info The message to be displayed at the bottom
     * @param picURL the URL to the pic to be displayed. (incompatible to picDATA)
     * @param picDATA  the image that has been converted to 64 bit to be displayed. (incompatible to picURL)
     * @param MsgColour colour of the msg text in hex
     * @param BackColour colour of the back ground in hex
     * @param InfoColour colour of info writing
     */
    public Billboard(String msg,String name, String info, String picURL, byte[] picDATA, String MsgColour, String BackColour, String InfoColour) {
        this.msg = msg;
        this.name = name;
        this.info = info;
        this.picURL = picURL;
        this.picDATA = picDATA;
        this.msgColour = MsgColour;
        this.backColour = BackColour;
        this.infoColour = InfoColour;

    }
    public static void main(String[] args) {
        new Billboard();

//        JFrame frame = new JFrame();
//
//        frame.setBackground(Color.cyan);
        //new DisplayImage("C:\\sally.jpg");

    }


    // - - - - - - -     Workers    - - - - - - - - - //

    /**
     * Create frame Makes a Jframe
     * @return frame JFrame
     */
    public JFrame createFrame() {
        JFrame frame = new JFrame("Flow Layout");
        //frame.setLayout(new FlowLayout());
        frame.setBackground(Color.blue);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }
    /**
     * Creates a Panel as the back ground of the screen, TODO fix bounds
     * @return returns a JPanel that is used primarily for the background of the Billboard.
     */
    public JPanel CreatePanel() {
        //Used to create the background of the Billboard
        JPanel BillboardScreen = new JPanel();
        BillboardScreen.setBounds((screenWidth ),35,(screenWidth) ,screenHeight - 100); //todo change to full screen
        BillboardScreen.setBackground(Color.blue);
        BillboardScreen.setLayout(null);
        Dimension previewSize = BillboardScreen.getSize();  //debug help
        BillboardScreen.setOpaque(true);
        BillboardScreen.setVisible(true);
        return BillboardScreen;
    }

    /**Takes a String for it name and will insert where needed and insert the inserted text also.
     *
     * @param Name this can be message or info
     * @param message_input this is what will be displayed in the text field associated with the name
     * @return returns a label to be added into the panel elsewhere (TODO tell where)
     * @exception. If name != message or info  or Message or Info
     */
    public static JLabel CreateTextArea(String Name, String message_input) throws Exception {//display as output change!!!!
        if(Name == "message" || Name == "Message") {
            JLabel upperText = new JLabel(message_input,SwingConstants.CENTER);
            upperText.setOpaque(false);
            upperText.setFont(new Font("Courier", Font.BOLD,40));
            upperText.setBounds(0, 0, screenWidth, 100);

            return upperText;
        }
        else if(Name == "info" || Name == "Info"){
            JLabel lowerText = new JLabel(message_input,SwingConstants.CENTER);
            lowerText.setFont(new Font("Courier", Font.BOLD,40));
            lowerText.setBounds(0, screenHeight-100, screenWidth, 100);

            return lowerText;
        }
        else {
            throw new Exception("Not info or name");
        }

    }


    // - - - - - - - helpers bellow - - - - - - - - - //

    //  Getters  //

    /**
     * Helper to get the message stored
     * @return String msg
     */
    public  String getMsg(){ return msg; }

    /**
     * Helper to get the name of the billboard stored
     * @return String name
     */
    public  String getName(){ return name; }

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
    /**
     * Gets the pannel that has been made for this billboard.
     * @return JPanel stored in Billboard.
     */
    public JPanel getJPanel() {return this.BillboardScreenPannel; }
    /**
     * Gets the pannel that has been made for this billboard.
     * @return JPanel stored in Billboard.
     */
    public JFrame getJFrame() {return this.BillboardScreen; }

    /**
     * Gets visibility of panel
     * @param panel JPanel created elswhere
     * @return Bool true if visible false if not
     */
    public boolean GetVisibility(JPanel panel) {
        return panel.isVisible();
    }

    //  setters  //

    /**
     * sets message
     * @param message String message to be displayed
     */
    public void setMsg(String message) {
        this.msg = message;

    }
    public void setName(String name) {
        this.name = name;
    }
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

    /**
     * Sets the Panel created elsewhere (see CreatePanel) to bool visibility
     * @param visibility bool true = visible, false = not
     * @param panel created panel elsewhere
     */
    public void SetVisible(Boolean visibility,JPanel panel) {
        panel.setVisible(visibility);
    }



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
    public byte[] ConvertImageToData(String filePath) throws Exception {
                BufferedImage bImage = ImageIO.read(new File(filePath));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos );
                byte [] data = bos.toByteArray();
    return data;
    }

    /**
     * Converts a byte[] to a buffered image.
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



