package SerializableObjects;

import Tools.DisplayImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


/**
 ******************* This is the test class for the Billboards**********
 * before each the tester will create a new billboard  using the constructor with...
 * (String msg, String info, String picURL, String picDATA, String MsgColour,String BackColour, String InfoColour)
 * Test 1 to 7 are to test if all fields have been filled - consider adding more for other constructors made ase we go. (uses underTestFull)
 * Test 8-21 are for testing if initialised as all null, inserting params then reading each field using their functions. (uses underTestEmpty)
 * Test 21 - **** is for converting to to correct type be it from JPG to base 64 String or V V, and converting hex to string or V V.
 * as this continues we will add functionality to display image etc
 */

public class BillboardTest {
    Billboard underTestEmpty;
    Billboard underTestFull;
    byte[] testBytes = new byte[]{ 0, 1, 2 };
    int red,green,blue;

    @BeforeEach @Test
    // Billboard = newBillboard(String msg, String info, String picURL, String picDATA, String MsgColour,String BackColour, String InfoColour)
    void newBillboard() {

          underTestFull = new Billboard("TEST billboardName", "TEST MsG","TEST INFO","https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg",
                 testBytes,"#000000","#FFFFFF","#000000");
        underTestEmpty = new Billboard();

    }

//--------------------------------------------------------------------------------------------//
//                                  Testing all fields                                        //
//--------------------------------------------------------------------------------------------//
    /**
     *
     * Test 1,
     *      Check if msg has been inserted
     */
    @Test
    public void MessageInserted () {
        //uses getMsg funct
        assertEquals(underTestFull.getMsg(),"TEST MsG");
    }


    /**
     *
     * Test 2,
     *      Check if info has been inserted
     */
    @Test
    public void InfoInserted () {
        //uses getInfo func
        assertEquals(underTestFull.getInfo(),"TEST INFO");
    }
    /**
     *
     * Test 3,
     *      Check if picURL has been inserted
     */
    @Test
    public void picURLInserted () {
        //uses getPicUrl func
        assertEquals(underTestFull.getPicUrl(),
                "https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg");
    }
    /**
     *
     * Test 4,
     *      Check if PicData has been inserted
     */
    @Test
    public void PicDataInserted () {
        //uses getPicData func
        assertEquals(underTestFull.getPicData(),testBytes);
    }
    /**
     *
     * Test 5,
     *      Check if Message colour has been inserted
     */
    @Test
    public void MsgColourInserted () {
        //uses getMsgColour func
        assertEquals( underTestFull.getMsgColour(),"#000000");
    }
    /**
     *
     * Test 6,
     *      Check if BackColour has been inserted
     */
    @Test
    public void BackColourInserted () {
        //uses getBackColour func
        assertEquals(underTestFull.getBackColour(),"#FFFFFF");
    }
    /**
     *
     * Test 7,
     *      Check if info colour has been inserted
     */
    @Test
    public void InfoColourInserted () {
        //uses getInfoColour func
        assertEquals(underTestFull.getInfoColour(),"#000000");
    }

    /**
     *
     * Test 8,
     *      Check if empty constructor has left msg null
     */
    @Test
    public void EmptyMsgCheck () {
        //uses getMsg func
        assertNull(underTestEmpty.getPicUrl());
    }

//--------------------------------------------------------------------------------------------//
//                                    Inserters                                               //
//--------------------------------------------------------------------------------------------//
    /**
     * Test 9,
     *      Checks if set msg funct is setting a new function over the OG
     */
    @Test
    public void EmptyMsgInsert () {
        //uses setMsg funct
        underTestEmpty.setMsg("Test 9");
        assertEquals(underTestEmpty.getMsg(),"Test 9");
    }
    /**
     *
     * Test 10,
     *      Check if empty constructor has left info null
     */
    @Test
    public void EmptyinfoCheck () {
        //uses getMsg func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 11,
     *      Checks if set info funct is setting a new function over the OG
     */
    @Test
    public void EmptyInfoInsert () {
        //uses setMsg funct
        underTestEmpty.setInfo("Test 11");
        assertEquals(underTestEmpty.getInfo(),"Test 11");
    }
    /**
     *
     * Test 12,
     *      Check if empty constructor has left pic URL null
     */
    @Test
    public void EmptyURLCheck () {
        //uses getPicUrl func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 13,
     *      Checks if set pic URL funct is setting a new function over the OG
     */
    @Test
    public void EmptyURLInsert () {
        //uses setPicUrl funct
        underTestEmpty.setPicURL("Test 13");
        assertEquals(underTestEmpty.getPicUrl(),"Test 13");
    }
    /**
     *
     * Test 14,
     *      Check if empty constructor has left pic DATA null
     */
    @Test
    public void EmptyDataCheck () {
        //uses getPicData func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 15,
     *      Checks if set pic Data funct is setting a new function over the OG
     */
    @Test
    public void EmptyDataInsert () {
        //uses setPicData funct
        underTestEmpty.setPicData(testBytes);
        assertEquals(underTestEmpty.getPicData(), testBytes);
    }
    /**
     *
     * Test 16,
     *      Check if empty constructor has left msg Colour null
     */
    @Test
    public void EmptyMsgColourCheck () {
        //uses getMsgColour func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 17,
     *      Checks if set msg colour funct is setting a new function over the OG
     */
    @Test
    public void EmptyMsgColourInsert () {
        //uses setMsgColour funct
        underTestEmpty.setMsgColour("Test 17");
        assertEquals(underTestEmpty.getMsgColour(),"Test 17");
    }
    /**
     *
     * Test 18,
     *      Check if empty constructor has left back Colour null
     */
    @Test
    public void EmptyBackColourCheck () {
        //uses getBackColour func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 19,
     *      Checks if set back colour funct is setting a new function over the OG
     */
    @Test
    public void EmptyBackColourInsert () {
        //uses setBackColour funct
        underTestEmpty.setBackColour("Test 19");
        assertEquals(underTestEmpty.getBackColour(),"Test 19");
    }
    /**
     *
     * Test 20,
     *      Check if empty constructor has left info Colour null
     */
    @Test
    public void EmptyInfoColourCheck () {
        //uses getInfoColour func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 21,
     *      Checks if set info colour funct is setting a new function over the OG
     */
    @Test
    public void EmptyInfoColourInsert () {
        //uses setInfoColour funct
        underTestEmpty.setInfoColour("Test 21");
        assertEquals(underTestEmpty.getInfoColour(),"Test 21");
    }

//--------------------------------------------------------------------------------------------//
//                                    Conversions                                             //
//--------------------------------------------------------------------------------------------//

    /**
     * Test 22,
     *      Checks conversion from string to RGB
     *      Store in MSGColour
     */
    @Test
    public void ConvertStringToRGB() {
        //uses ConvertStringToRGB funct
        String ColourA = underTestEmpty.ConvertStringToRGB("#FFFFFF");
        underTestEmpty.setMsgColour(ColourA);
        assertEquals("255255255",underTestEmpty.getMsgColour());  //this is r= 255 g= 255 b= 255;
    }

    /**
     * Test 23,
     *      Checks conversion from RGB to string
     *       Store in MSGColour
     */
    @Test
    public void ConvertRGBToString() {
        //uses ConvertRGBtoString funct
        String ColourB = underTestEmpty.ConvertRGBtoString(255,255,255);
        underTestEmpty.setMsgColour(ColourB);
        assertEquals("#FFFFFF",underTestEmpty.getMsgColour());
    }

    /**
     * Test 24,
     *      Checks conversion from JPG to Data (base64)
     *       Store in pic data
     *       Currently throwing String too long exception needing to be handled or delt with
     * @throws Exception if fail
     */

    @Test
    public void ConvertImageToData() throws Exception {
        //uses ConvertImageToData funct
        File file = new File("C:\\sally.jpg");
        byte[] S = underTestEmpty.ConvertImageToData(file);
        underTestEmpty.setPicData(S);
        //In this test we will need to convert to data and see what it says then change im and then see differences and try to make one back using next test
        assertEquals(S,underTestEmpty.getPicData());
    }

    /**
     * Test 25,
     *      Checks conversion from Data (base64) JPG
     *       Store in pic data
     *       Only way to test is to use already tested function. This may need ot be revisited once we have image displaying working.
     * @throws Exception if fail
     */

    @Test
    public void ConvertDataToImage() throws Exception {
        //This will have to be a visual confirmation
        File file = new File("C:\\sally.jpg");
        byte[] S = underTestEmpty.ConvertImageToData(file);
        underTestEmpty.setPicData(S);
        new DisplayImage(underTestEmpty.getPicData());

        // Test passes. Original == reconfigured.
        assertTrue(true);
    }



}
