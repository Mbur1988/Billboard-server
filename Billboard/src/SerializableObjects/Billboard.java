package SerializableObjects;

import java.io.Serializable;

public class Billboard implements Serializable {

    private String msg;
    private String info;
    private String picURL;
    private String picDATA;
    private String msgColour;
    private String backColour;
    private String infoColour;

    //setting a blank Billboard
    public Billboard(){
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
     * @param msg The message to be displayed at the top
     * @param info The message to be displayed at the bottom
     * @param picURL the URL to the pic to be displayed. (incompattible to picDATA)
     * @param picDATA  the image that has been converted to 64 bit to be displayed. (incompattible to picURL)
     * @param MsgColour colour of the msg text in hex
     * @param BackColour colour of the back ground in hex
     * @param InfoColour colour of info writting
     */
    public Billboard(String msg, String info, String picURL, String picDATA, String MsgColour, String BackColour, String InfoColour) {
        this.msg = msg;
        this.info = info;
        this.picURL = picURL;
        this.picDATA = picDATA;
        this.msgColour = MsgColour;
        this.backColour = BackColour;
        this.infoColour = InfoColour;

    }


    // - - - - - - - helpers bellow - - - - - - - - - //

    //  Getters  //

    /**
     * Helper to get the message stored
     * @return String msg
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
    public  String getPicData() {return picDATA; }

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
     * sets message
     * @param message
     */
    public void setMsg(String message) { this.msg = message;}

    /**
     * sets info
     * @param info
     */
    public void setInfo(String info) { this.info = info;}

    /**
     * sets pictures URL
     * @param picURL
     */
    public void setPicURL(String picURL) { this.picURL = picURL;}

    /**
     * Sets Pic Data as string to be converted to base64
     * @param picData
     */

    public void setPicData(String picData) {this.picDATA = picData;}

    /**
     * sets message colour as string to be converted to HEX
     * @param msgColour
     */

    public void setMsgColour(String msgColour) {this.msgColour = msgColour ;}

    /**
     * Sets back colour as string to be converted to HEX
     * @param backColour
     */

    public void setBackColour(String backColour) {this.backColour = backColour;}

    /**
     * Sets info colour as string to be converted to HEX
     * @param infoColour
     */

    public void setInfoColour(String infoColour) { this.infoColour = infoColour;}
}
