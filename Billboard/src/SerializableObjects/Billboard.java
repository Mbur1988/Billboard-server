package SerializableObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import static java.awt.Font.BOLD;

public class Billboard implements Serializable {


    // Get the size of the screen.
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth = screenSize.width;
    static int screenHeight = screenSize.height;

    // Globals
    private String name;
    private String msg;
    private String info;
    private String picURL;
    private byte[] picDATA;
    private Color msgColour;
    private Color backColour;
    private Color infoColour;
    private String createdBy;
    private Boolean scheduled;
    private static JFrame BillboardScreen;
    private static JPanel BillboardScreenPanel;

    //setting a blank Billboard
    public Billboard() {
        this.name = null;
        this.msg = null;
        this.info = null;
        this.picURL = null;
        this.picDATA = null;
        this.msgColour = null;
        this.backColour = null;
        this.infoColour = null;
        this.createdBy = null;
        this.scheduled = null;
        //BillboardScreenPanel = null;
        //BillboardScreen = null;
    }

    /**
     *
     * @param name The name of the billboard for database control
     * @param msg The message to be displayed at the top
     * @param info The message to be displayed at the bottom
     * @param picURL the URL to the pic to be displayed. (incompatible to picDATA)
     * @param picDATA  the image that has been converted to 64 bit to be displayed. (incompatible to picURL)
     * @param MsgColour colour of the msg text in hex
     * @param BackColour colour of the back ground in hex
     * @param InfoColour colour of info writing
     */
    public Billboard(String name, String msg, String info, String picURL, byte[] picDATA, Color MsgColour, Color BackColour, Color InfoColour, String createdBy, Boolean scheduled) {

        this.name = name;
        this.msg = msg;
        this.info = info;
        this.picURL = picURL;
        this.picDATA = picDATA;
        this.msgColour = MsgColour;
        this.backColour = BackColour;
        this.infoColour = InfoColour;
        this.createdBy = createdBy;
        this.scheduled = scheduled;
        //BillboardScreenPanel = null;
        //BillboardScreen = null;

    }
    // - - - - - - -     Workers    - - - - - - - - - //

    /**
     * Create frame Makes a Jframe
     * @return frame JFrame
     */
    public JFrame createFrame() {
        BillboardScreenPanel = CreatePanel();
        JFrame frame = new JFrame("Flow Layout");
        //frame.setLayout(new FlowLayout());
        frame.setBackground(Color.blue);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //BillboardScreen.setContentPane(BillboardScreenPanel);
        return frame;
    }

    /**
     * Creates a Panel as the back ground of the screen,
     * @return returns a JPanel that is used primarily for the background of the Billboard.
     */
    public JPanel CreatePanel() {
        //Used to create the background of the Billboard
        JPanel BillboardScreen = new JPanel();
        BillboardScreen.setBounds(0 ,0,(screenWidth) ,screenHeight );
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
     * @param placement this is to determine the size of the label
     * @return returns a label to be added into the panel elsewhere
     * @exception. If name != message or info  or Message or Info
     */
    public static JLabel CreateTextArea(String Name, String message_input,String placement) throws Exception {

        if(Name.equals("message") || Name.equals("Message")) {
            JLabel upperText = new JLabel(message_input,SwingConstants.CENTER);
            upperText.setOpaque(false);


            if(placement.equals("msg only")) {

                upperText.setFont(new Font("Courier", BOLD, 150));
                upperText.setBounds(0, 0, screenWidth, screenHeight);

            }
            else if(placement.equals("msg and info")) {
                upperText.setFont(new Font("Courier", BOLD, 100));
                upperText.setBounds(0, 0, screenWidth, screenHeight/2);
            }
            else if(placement.equals("msg and pic")) {
                upperText.setFont(new Font("Courier", BOLD, 100));
                upperText.setBounds(0, 0, screenWidth, screenHeight / 3);
            }

            else if(placement.equals("all")) {
                upperText.setFont(new Font("Courier", BOLD, 100));
                upperText.setBounds(0, 0, screenWidth, screenHeight / 3);
            }
            return upperText;
        }


        else if(Name.equals("info") || Name.equals("Info")){
            JLabel lowerText = new JLabel(message_input,SwingConstants.CENTER);

            if(placement.equals("info only"))
            {
                lowerText.setFont(new Font("Courier", BOLD, 80));

                lowerText.setBounds(new Rectangle((screenWidth/8),(screenHeight/4),(3*screenWidth/4),(screenHeight/2)));
                lowerText.setHorizontalAlignment(SwingConstants.CENTER);
                lowerText.setVerticalAlignment(SwingConstants.CENTER);

            }

            else if(placement.equals("msg and info"))
            {
                lowerText.setFont(new Font("Courier", BOLD, 75));
                lowerText.setBounds(0, screenHeight/2, screenWidth, screenHeight/2);
            }
            else if(placement.equals("info and pic")) {
                lowerText.setFont(new Font("Courier", BOLD, 75));
                lowerText.setBounds((screenWidth/8), (2*screenHeight)/3, (3*screenWidth)/4, screenHeight / 3);
                lowerText.setHorizontalAlignment(SwingConstants.CENTER);
            }
            else if(placement.equals("all")) {
                lowerText.setFont(new Font("Courier", BOLD, 75));
                lowerText.setBounds((screenWidth/8), (2*screenHeight)/3, (3*screenWidth)/4, screenHeight / 3);
                lowerText.setHorizontalAlignment(SwingConstants.CENTER);

            }

            return lowerText;
        }
        else {
            throw new Exception("Not info or name");
        }

    }

    /**
     * This method finds the image that is stored at the location of the Filepath and creates an image in the system
     * @param Filepath location of image to use
     * @return Image of the file
     * @throws Exception can through IO exception and left open for softey.
     */
    public static JLabel CreateImageFilepath(String Filepath) throws Exception {

        BufferedImage img = ImageIO.read(new File(Filepath));
        ImageIcon icon = new ImageIcon(img);
        JLabel Image = new JLabel(icon);
        Image.setBounds(0,0,screenWidth,500);


        return Image;
    }

    /**
     * This is the method to display the image as a scaled version to be limited to half of the screen which ever
     * x or y that is.
     * @param Data byte[] of data to display, See ConvertImageToData
     * @return returns JLabel to display
     * @throws Exception IO exception and broad in case
     */
    public static JLabel CreateImageData(byte[] Data, String placement) throws Exception {
        // get image into BIS
        ByteArrayInputStream bis = new ByteArrayInputStream(Data);
        BufferedImage img = ImageIO.read(bis);
        //Helpers for neatness
        int imageWidth = img.getWidth();
        int imageHeights = img.getHeight();
        double widthRatio = (double)(screenWidth/2)  / imageWidth;
        double heightRatio =(double)(screenHeight/2) / imageHeights;
        //start of scale calculation logic

        //sets image changer as height as default as less likely
        double imageChanger = heightRatio;
        if (widthRatio <= heightRatio){
            // if the image is taller then wid make it the width ratio
            imageChanger = widthRatio;
        }
        //helpers for neatness
        int scaledWidth = (int) (img.getWidth()*imageChanger);
        int scaledHeight = (int) (img.getHeight()*imageChanger);
        //scales image
        Image scaledImage = img.getScaledInstance(scaledWidth,scaledHeight,Image.SCALE_SMOOTH);

        //output section
        ImageIcon icon = new ImageIcon(scaledImage);
        JLabel Image = new JLabel(icon);

        //placement adjustment
        if (placement.equals("Image Only") || placement.equals("all")){
            Image.setBounds(0,(screenHeight/2)-(scaledHeight/2),screenWidth,screenHeight/2);
            Image.setHorizontalAlignment(SwingConstants.CENTER);
        }
        else if(placement.equals("msg and pic")){
            Image.setBounds(0,(2*screenHeight/3)-(scaledHeight/2),screenWidth,screenHeight/2);
            Image.setHorizontalAlignment(SwingConstants.CENTER);
        }
        else if(placement.equals("info and pic")){
            Image.setBounds(0,(screenHeight/3)-(scaledHeight/2),screenWidth,screenHeight/2);
            Image.setHorizontalAlignment(SwingConstants.CENTER);
        }

        //Image.setBounds((screenWidth/2)-(scaledWidth/2),(screenHeight/2)-(scaledHeight/2),screenWidth/2,screenHeight/2);


        return Image;
    }

    public void showBillboard() throws Exception {
        showBillboard(false);
    }

    public void previewBillboard() throws Exception {
        showBillboard(true);
    }

    private void showBillboard(boolean PreviewDisplay) throws Exception {
        if(BillboardScreen == null || PreviewDisplay){
            BillboardScreen = createFrame();
            BillboardScreenPanel = CreatePanel();
            BillboardScreenPanel.setBackground(getBackColour());
            BillboardScreenPanel.setOpaque(true);
            BillboardScreen.setContentPane(BillboardScreenPanel);
        }
        else {
            BillboardScreenPanel.removeAll();
            BillboardScreenPanel.revalidate();
            BillboardScreenPanel.repaint();
            BillboardScreenPanel.setBackground(getBackColour());
        }

        //msg only
        if((getMsg() != null || getMsg() != "") && (getInfo() == null || getInfo().equals("")) && (getPicUrl() == null || getPicUrl() == "") && getPicData() == null){
            JLabel MessageText = CreateTextArea("message", msg,"msg only");
            MessageText.setForeground(msgColour);
            BillboardScreen.add(MessageText, BorderLayout.PAGE_START);
        }
        //info only
        else if((getMsg() == null || getMsg().equals("")) && (getInfo() != null || getInfo() != "" ) && (getPicUrl() == null || getPicUrl() == "") && getPicData() == null){
            JLabel InfoText = CreateTextArea("info", info,"info only");
            InfoText.setForeground(infoColour);
            BillboardScreen.add(InfoText, BorderLayout.PAGE_START);
        }
        //image only
        else if((getMsg() == null || getMsg().equals("")) && (getInfo() == null || getInfo().equals("")) && (picDATA != null || picURL != null)){
            JLabel imageDisplay = CreateImageData(getPicData(), "Image Only");
            BillboardScreen.getContentPane().add(imageDisplay, BorderLayout.CENTER);
        }
        //msg and image
        else if((getMsg() != null || getMsg() != "") && (getInfo() == null || getInfo() == "") && (getPicUrl() == null || getPicUrl() == "") && getPicData() == null){
            JLabel MessageText = CreateTextArea("message", msg, "msg and pic");
            MessageText.setForeground(msgColour);
            BillboardScreen.add(MessageText, BorderLayout.PAGE_START);
            JLabel imageDisplay = CreateImageData(getPicData(), "msg and pic");

            BillboardScreen.getContentPane().add(imageDisplay);
        }
        //info and image
        else if((getMsg() == null || getMsg()== "") &&(getInfo() != null || getInfo() != "" ) && (getPicUrl() == null || getPicUrl() =="") && getPicData() == null){
            JLabel imageDisplay = CreateImageData(getPicData(), "info and pic");
            BillboardScreen.getContentPane().add(imageDisplay);
            JLabel InfoText = CreateTextArea("info", info, "info and pic");
            InfoText.setForeground(infoColour);
            BillboardScreen.add(InfoText, BorderLayout.PAGE_END);
        }
        //info and msg
        else if(msg != null && info != null &&(getPicUrl() == null || getPicUrl() == "") && getPicData() == null){
            JLabel MessageText = CreateTextArea("message", msg, "msg and info");
            MessageText.setForeground(msgColour);
            JLabel InfoText = CreateTextArea("info", info, "msg and info");
            InfoText.setForeground(infoColour);
            BillboardScreen.add(MessageText, BorderLayout.PAGE_START);
            BillboardScreen.add(InfoText, BorderLayout.PAGE_END);
        }
        else {

            JLabel MessageText = CreateTextArea("message", msg, "all");
            JLabel InfoText = CreateTextArea("info", info, "all");
            JLabel imageDisplay = CreateImageData(getPicData(), "all");
            MessageText.setForeground(msgColour);
            InfoText.setForeground(infoColour);
            BillboardScreen.getContentPane().add(imageDisplay);
            BillboardScreen.add(MessageText, BorderLayout.PAGE_START);
            BillboardScreen.add(InfoText, BorderLayout.PAGE_END);
        }

        BillboardScreen.setVisible(true);
        BillboardScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        BillboardScreenPanel.repaint();
        BillboardScreenPanel.revalidate();

        // on ESC key close frame
        BillboardScreenPanel.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        BillboardScreenPanel.getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                BillboardScreen.dispose();
                BillboardScreen = null;
                if (!PreviewDisplay) {
                    System.exit(0);
                }
            }
        });



        //close preview button
        if(PreviewDisplay){
        JButton b3 = new JButton("Exit Preview");

        b3.setBounds(0, 0, 250, 50);
        BillboardScreenPanel.add(b3);
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                BillboardScreen.dispose();
                BillboardScreen = null;
            }   // need to change to a keep changes button
        });}

    }


    // - - - - - - - helpers bellow - - - - - - - - - //

    //  aux helper //

    /**
     * A cleaning function that is related to the clear button.
     */
    public void clearBillboard() {
        setMsg(null);
        setName(null);
        setInfo(null);
        setPicUrl(null);
        setPicData(null);
        setMsgColour(null);
        setBackColour(null);
        setInfoColour(null);
    }

    //  Getters  //

    /**
     * Helper to get the message stored
     * @return String msg
     */
    public String getBillboardName() {return name;}

    /**
     * Helper to get the billboard name stored
     * @return String info
     */
    public String getMsg(){ return msg; }

    /**
     * Helper to get the name of the billboard stored
     * @return String name
     */
    public String getName(){ return name; }

    /**
     * Helper to get the info stored
     * @return String info
     */
    public String getInfo() {return info; }

    /**
     * Helper to get the pic URL stored
     * @return String picURL
     */
    public String getPicUrl() {return picURL; }

    /**
     * Helper to get the picture data stored
     * @return String picDATA
     */
    public byte[] getPicData() {return picDATA; }

    /**
     * Helper to get the message colour stored
     * @return String msg colour
     */
    public Color getMsgColour() {return msgColour; }

    /**
     * Helper to get the background colour stored
     * @return String background colour
     */
    public Color getBackColour() {return backColour; }

    /**
     * Helper to get the info colour stored
     * @return String info colour
     */
    public Color getInfoColour() {return infoColour; }

    public String getCreatedBy() {
        return createdBy;
    }

    public Boolean getScheduled() {
        return scheduled;
    }

    //  setters  //

    /**
     * sets name of Billboard
     * @param name  Takes in the String Name and sets it into the Billboard name property
     */
    public void setName(String name) {this.name = name;}

    /**
     * sets message
     * @param message String message to be displayed
     */
    public void setMsg(String message) {this.msg = message;}

    /**
     * sets info
     * @param info String Informaiton to be displayed
     */
    public void setInfo(String info) { this.info = info;}

    /**
     * sets pictures URL
     * @param picURL URL to the pic String
     */
    public void setPicUrl(String picURL) { this.picURL = picURL;}

    /**
     * Sets Pic Data as string to be converted to base64
     * @param picData byte[] of image
     */
    public void setPicData(byte[] picData) {this.picDATA = picData;}

    /**
     * sets message colour as string to be converted to HEX
     * @param msgColour colour of string
     */
    public void setMsgColour(Color msgColour) {this.msgColour = msgColour ;}

    /**
     * Sets back colour as string to be converted to HEX
     * @param backColour colour of string
     */
    public void setBackColour(Color backColour) {this.backColour = backColour;}

    /**
     * Sets info colour as string to be converted to HEX
     * @param infoColour colour of string
     */
    public void setInfoColour(Color infoColour) { this.infoColour = infoColour;}

    //   converters    //
    /**
     * Converts a image from the file path to a byte array
     * @param filePath image to be converted
     * @return image in byte[] form
     * @throws IOException If FilePath does not exist in directory
     */
    public static byte[] ConvertImageToData(String filePath) throws Exception {
        BufferedImage bImage = ImageIO.read(new File(filePath));
        String formatName = "jpg";
        if (filePath.length() > 3) {
            formatName = filePath.substring(filePath.length() - 3);
        }
        return ByteArrayHelper(bImage, formatName);
    }

    /**
     * Converts a byte[] to a buffered image.
     * @param imageData byte[] data of image
     * @return buffered image
     * @throws IOException if cant read
     */
    public static BufferedImage ConvertDataToImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        BufferedImage image = ImageIO.read(bais);
        return image;
    }

    /**
     * Takes URL from the interwebs and converts to a Byte[] for use with display image.
     * @param input URL in a string format
     * @return byte[] of image.
     * @throws IOException in case
     */
    public static byte[] UrlToData(String input) throws IOException {
        URL url = new URL(input);
        BufferedImage image = ImageIO.read(url);
        String formatName = "jpg";
        if (input.length() > 3) {
            formatName = input.substring(input.length() - 3);
        }
        return ByteArrayHelper(image, formatName);
    }

    /**
     * Helper for changing URL to data
     * @param image Buffered image wanting conversion
     * @param type jpg
     * @return byte[] of image
     * @throws IOException if file is not there.
     */
    private static byte[] ByteArrayHelper(BufferedImage image, String type) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, type, out);
        return out.toByteArray();
    }

    /**
     * Converts a byte array image to a base64 format
     * @param byteArray the image to convert as a byte array
     * @return the converted image in base64 format in string form
     */
    public static String BytesToSixFour(byte[] byteArray) {
        String base64 = Base64.getEncoder().encodeToString(byteArray);
        return base64;
    }

    /**
     * Converts an image in base64 format to a byte array
     * @param stringSixFour Image in base64 format as a string
     * @return the converted image as a byte array
     */
    public static byte[] SixFourToByte(String stringSixFour) {
        byte[] Output = Base64.getDecoder().decode(stringSixFour);
        return Output;
    }
}



