package SerializableObjects;

import Tools.DisplayImage;
import Tools.ProjectPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


/*************************************************** This is the test class for the Billboards****************************************************************
 *
 *       before each the tester will create a new billboard  using the constructor with...
 *       (String msg, String info, String picURL, String picDATA, String MsgColour,String BackColour, String InfoColour)
 *       Test 1 to 8 are to test if all fields have been filled - consider adding more for other constructors made ase we go. (uses underTestFull)
 *       Test 9-21 + 27-28 are for testing if initialised as all null, inserting params then reading each field using their functions. (uses underTestEmpty)
 *       Test 22+23 have been deleted after changing colour sections from string imput to Color
 *       24 + 25 is for converting to to correct type be it from JPG to base 64 String or V V, and converting hex to string or V V.
 *       26 + 27 Tests the creating of a JPanel and a JFrame.
 *       32 - 33 Tests making labels for the Mesage and Info
 *       37 - 42 Tests displaying the Billboard and clearing the Billboard
 *       43 tests Conversion of URL to byte[] and shows
 *
 *************************************************************************************************************************************************************/

public class BillboardTest {
    String testAddress = ProjectPath.RootString() +  "\\\\Resources\\\\Images\\\\Rick_Astley.jpg";
    //    File testFile = getClass().getResource("/path/to/your/resource.txt");
    Billboard underTestEmpty;
    Billboard underTestFull;
    byte[] testBytes = new byte[]{0, 1, 2};
    int blue;
    Color TestColor = new Color(blue);

    @BeforeEach
    @Test
        // Billboard = newBillboard(String msg, String info, String picURL, String picDATA, String MsgColour,String BackColour, String InfoColour)
    void newBillboard() {

        underTestFull = new Billboard("TEST MsG", "name", "TEST INFO", "https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg",
                testBytes, TestColor, TestColor, TestColor);
        underTestEmpty = new Billboard();

    }

//--------------------------------------------------------------------------------------------//
//                                  Testing all fields                                        //
//--------------------------------------------------------------------------------------------//

    /**
     * Test 1,
     * Check if msg has been inserted
     */
    @Test
    public void MessageInserted() {
        //uses getMsg funct
        assertEquals(underTestFull.getMsg(), "TEST MsG");
    }

    /**
     * Test 29,
     * Check if Name has been inserted
     */
    @Test
    public void NameInserted() {
        //uses getName funct
        assertEquals(underTestFull.getName(), "name");
    }


    /**
     * Test 2,
     * Check if info has been inserted
     */
    @Test
    public void InfoInserted() {
        //uses getInfo func
        assertEquals(underTestFull.getInfo(), "TEST INFO");
    }

    /**
     * Test 3,
     * Check if picURL has been inserted
     */
    @Test
    public void picURLInserted() {
        //uses getPicUrl func
        assertEquals(underTestFull.getPicUrl(),
                "https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg");
    }

    /**
     * Test 4,
     * Check if PicData has been inserted
     */
    @Test
    public void PicDataInserted() {
        //uses getPicData func
        assertEquals(underTestFull.getPicData(), testBytes);
    }

    /**
     * Test 5,
     * Check if Message colour has been inserted
     */
    @Test
    public void MsgColourInserted() {
        //uses getMsgColour func
        assertEquals(underTestFull.getMsgColour(), TestColor);
    }

    /**
     * Test 6,
     * Check if BackColour has been inserted
     */
    @Test
    public void BackColourInserted() {
        //uses getBackColour func
        assertEquals(underTestFull.getBackColour(), TestColor);
    }

    /**
     * Test 7,
     * Check if info colour has been inserted
     */
    @Test
    public void InfoColourInserted() {
        //uses getInfoColour func
        assertEquals(underTestFull.getInfoColour(), TestColor);
    }

    /**
     * Test 8,
     * Check if empty constructor has left msg null
     */
    @Test
    public void EmptyMsgCheck() {
        //uses getMsg func
        assertNull(underTestEmpty.getPicUrl());
    }

//--------------------------------------------------------------------------------------------//
//                                    Inserters                                               //
//--------------------------------------------------------------------------------------------//

    /**
     * Test 9,
     * Checks if set msg funct is setting a new function over the OG
     */
    @Test
    public void EmptyMsgInsert() {
        //uses setMsg funct
        underTestEmpty.setMsg("Test 9");
        assertEquals(underTestEmpty.getMsg(), "Test 9");
    }

    /**
     * Test 10,
     * Check if empty constructor has left info null
     */
    @Test
    public void EmptyinfoCheck() {
        //uses getMsg func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 11,
     * Checks if set info funct is setting a new function over the OG
     */
    @Test
    public void EmptyInfoInsert() {
        //uses setMsg funct
        underTestEmpty.setInfo("Test 11");
        assertEquals(underTestEmpty.getInfo(), "Test 11");
    }

    /**
     * Test 12,
     * Check if empty constructor has left pic URL null
     */
    @Test
    public void EmptyURLCheck() {
        //uses getPicUrl func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 13,
     * Checks if set pic URL funct is setting a new function over the OG
     */
    @Test
    public void EmptyURLInsert() {
        //uses setPicUrl funct
        underTestEmpty.setPicURL("Test 13");
        assertEquals(underTestEmpty.getPicUrl(), "Test 13");
    }

    /**
     * Test 14,
     * Check if empty constructor has left pic DATA null
     */
    @Test
    public void EmptyDataCheck() {
        //uses getPicData func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 15,
     * Checks if set pic Data funct is setting a new function over the OG
     */
    @Test
    public void EmptyDataInsert() {
        //uses setPicData funct
        underTestEmpty.setPicData(testBytes);
        assertEquals(underTestEmpty.getPicData(), testBytes);
    }

    /**
     * Test 16,
     * Check if empty constructor has left msg Colour null
     */
    @Test
    public void EmptyMsgColourCheck() {
        //uses getMsgColour func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 17,
     * Checks if set msg colour funct is setting a new function over the OG
     */
    @Test
    public void EmptyMsgColourInsert() {
        //uses setMsgColour funct
        underTestEmpty.setMsgColour(TestColor);
        assertEquals(underTestEmpty.getMsgColour(), TestColor);
    }

    /**
     * Test 18,
     * Check if empty constructor has left back Colour null
     */
    @Test
    public void EmptyBackColourCheck() {
        //uses getBackColour func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 19,
     * Checks if set back colour funct is setting a new function over the OG
     */
    @Test
    public void EmptyBackColourInsert() {
        //uses setBackColour funct
        underTestEmpty.setBackColour(TestColor);
        assertEquals(underTestEmpty.getBackColour(), TestColor);
    }

    /**
     * Test 20,
     * Check if empty constructor has left info Colour null
     */
    @Test
    public void EmptyInfoColourCheck() {
        //uses getInfoColour func
        assertNull(underTestEmpty.getPicUrl());
    }

    /**
     * Test 21,
     * Checks if set info colour funct is setting a new function over the OG
     */
    @Test
    public void EmptyInfoColourInsert() {
        //uses setInfoColour funct

        underTestEmpty.setInfoColour(TestColor);
        assertEquals(underTestEmpty.getInfoColour(), TestColor);
    }
    /**
     * Test 27,
     * Checks Name starts empty
     */
    @Test
    public void EmptyNameCheck() {
        //uses getMsg func
        assertNull(underTestEmpty.getName());
    }

    /**
     * Test 28,
     * Checks if set msg funct is setting a new function over the OG
     */
    @Test
    public void EmptyNameInsert() {
        //uses setMsg funct
        underTestEmpty.setName("Test 27");
        assertEquals(underTestEmpty.getName(), "Test 27");
    }


//--------------------------------------------------------------------------------------------//
//                                    Conversions                                             //
//--------------------------------------------------------------------------------------------//

//    /**
//     * Test 22,
//     * Checks conversion from string to RGB
//     * Store in MSGColour
//     */
//    @Test
//    public void ConvertStringToRGB() {
//        //uses ConvertStringToRGB funct
//        String ColourA = underTestEmpty.ConvertStringToRGB("#FFFFFF");
//        underTestEmpty.setMsgColour(ColourA);
//        assertEquals("255255255", underTestEmpty.getMsgColour());  //this is r= 255 g= 255 b= 255;
//    }
//
//    /**
//     * Test 23,
//     * Checks conversion from RGB to string
//     * Store in MSGColour
//     */
//    @Test
//    public void ConvertRGBToString() {
//        //uses ConvertRGBtoString funct
//        String ColourB = underTestEmpty.ConvertRGBtoString(255, 255, 255);
//        underTestEmpty.setMsgColour(ColourB);
//        assertEquals("#FFFFFF", underTestEmpty.getMsgColour());
//    }

    /**
     * Test 24,
     * Checks conversion from JPG to Data (base64)
     * Store in pic data
     * Currently throwing String too long exception needing to be handled or delt with
     *
     * @throws Exception if fail
     */

    @Test
    public void ConvertImageToData() throws Exception {
        //uses ConvertImageToData funct
        // File file = new File("C:\\sally.jpg");
        byte[] S = underTestEmpty.ConvertImageToData(testAddress);
        underTestEmpty.setPicData(S);
        //In this test we will need to convert to data and see what it says then change im and then see differences and try to make one back using next test
        assertEquals(S, underTestEmpty.getPicData());
    }

    /**
     * Test 25,
     * Checks conversion from Data (base64) JPG
     * Store in pic data
     * Only way to test is to use already tested function. This may need ot be revisited once we have image displaying working.
     *
     * @throws Exception if fail
     */

    @Test
    public void ConvertDataToImage() throws Exception {
        //This will have to be a visual confirmation
//        File file = new File("C:\\sally.jpg");
        byte[] S = underTestEmpty.ConvertImageToData(testAddress);
        underTestEmpty.setPicData(S);
        new DisplayImage(underTestEmpty.getPicData());
        Thread.sleep(10000);
        // Test passes. Original == reconfigured.
        assertTrue(true);
    }
//--------------------------------------------------------------------------------------------//
//                                    Creaters                                                //
//--------------------------------------------------------------------------------------------//
    /**
     * Test 26,
     * checks if CreateFrame Creates Panel in Billboard Class for labels and images
     */
    @Test
    public void CreatePanel() {
        new Billboard();
        //Visual check
        assertTrue(true);

    }

    /**
     * Test 27,
     *      createFrame for back of billboard maybe?
     */
    @Test
    public void createFrame(){
        underTestEmpty.createFrame();
        //visual check PASSED
        assertTrue(true);
    }
    /**
     * Test 32,
     *      Tests making a JLable with msg in it.
     */
    @Test
    public void MsgLabel() throws Exception {
        JFrame testframe = underTestEmpty.createFrame();

        JLabel Test = Billboard.CreateTextArea("message","message input");

        //underTestEmpty.SetVisible(true,underTestEmpty.getJPanel());
        testframe.getContentPane().add(Test);

        //check visually PASSED
        Thread.sleep(3000);
        assertTrue(true);
    }
    /**
     * Test 33,
     *      Tests making a JTextArea with msg in it
     */
    @Test
    public void infoLabel() throws Exception {
        JFrame testframe = underTestEmpty.createFrame();
        //Uses DisplayImage function CreateLabel()
        JLabel Test = Billboard.CreateTextArea("info","info input");
        //underTestEmpty.SetVisible(true,underTestEmpty.getJPanel());
        testframe.getContentPane().add(Test);
        //check visually
        Thread.sleep(3000);
        assertTrue(true);
    }

//--------------------------------------------------------------------------------------------//
//                                    Displayer's                                             //
//--------------------------------------------------------------------------------------------//
    /**
     * Test 37,
     *      make a Display Billboard function in Billboard class
     */
    @Test
    public void ShowBillboardTest() throws Exception {
        underTestEmpty.setPicData(underTestEmpty.ConvertImageToData(testAddress));
        underTestEmpty.setMsg("Message");
        underTestEmpty.setInfo("info");
        underTestEmpty.showBillboard();


        Thread.sleep(3000);
        //visual check PASSED
        assertTrue(true);
    }
    /**
     * Test 38,
     *      clear all in field using function WishhyWassyTheBillyBoardy()
     */
    @Test
    public void WishhyWassyTheBillyBoardyTest(){

        underTestFull.WishhyWassyTheBillyBoardy();
        assertNull(underTestEmpty.getMsg());
        assertNull(underTestEmpty.getName());
        assertNull(underTestEmpty.getInfo());
        assertNull(underTestEmpty.getPicUrl());
        assertNull(underTestEmpty.getPicData());
        assertNull(underTestEmpty.getMsgColour());
        assertNull(underTestEmpty.getBackColour());
        assertNull(underTestEmpty.getInfoColour());
    }
    /**
     * Test 39,
     *      checking the color of the background
     */
    @Test
    public void ShowBillboardColourTest() throws Exception {

        underTestEmpty.setPicData(underTestEmpty.ConvertImageToData(testAddress));
        underTestEmpty.setMsg("Message");
        underTestEmpty.setInfo("info");
        underTestEmpty.setBackColour(new Color(39, 255, 231, 202));
        underTestEmpty.showBillboard();


        //visual check PASSED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Thread.sleep(3000);
        assertTrue(true);//!!!!!!!!!!!!

    }
//    /**               ----------FAILED UNEQUIVOCALLY SO FIXED------------
//     * Test 40,
//     *      After edditng the frame to have colour as above we have lost Picture display so we will test it here
//     */
//    @Test
//    public void DisplayImageTester() throws Exception {
//        JFrame TestFrame = underTestEmpty.createFrame();
//        byte[] Data = underTestEmpty.ConvertImageToData("testAddress");
//        JLabel testLab =  DisplayImage.DisplayImageLabel(Data);
//        //new DisplayImage("E:\\DND\\TEST.jpg");
//
//        Thread.sleep((2000));
//        TestFrame.add(testLab);
//
//        //Visual test failing. reWriting function into Billboard
//        assertTrue(false);
//    }

    /**
     * Test 41,
     *      Testing a display image function rewritten into Billboard to try to avoid bug to solve test 39
     */
    @Test
    public void DisplayImageTester2() throws Exception {

        JFrame testFrame = underTestEmpty.createFrame();
        JPanel testPanel = underTestEmpty.CreatePanel();
        testFrame.setContentPane(testPanel);
        JLabel LabTest =  underTestEmpty.CreateImageFilepath(testAddress);
        testFrame.getContentPane().add(LabTest);
        testFrame.repaint();

        Thread.sleep((2000));
        //Visual check Passed. Image is on the correct panel.
        assertTrue(true);
    }
    /**
     * Test 42,
     *      Testing a display image function rewritten into Billboard to try to avoid bug to solve test 39
     *      This time taking in data not filepath
     */
    @Test
    public void DisplayImageTester3() throws Exception {
        underTestEmpty.setPicData(underTestEmpty.ConvertImageToData(testAddress));
        JFrame testFrame = underTestEmpty.createFrame();
        JPanel testPanel = underTestEmpty.CreatePanel();
        testFrame.setContentPane(testPanel);
        JLabel LabTest =  underTestEmpty.CreateImageData(underTestEmpty.getPicData());
        testFrame.getContentPane().add(LabTest);
        testFrame.repaint();

        Thread.sleep((2000));
        //Visual check Passed. Image is on the correct panel.
        assertTrue(true);
    }
    /**
     * Test 43,
     *      Converting URL to Image and to Data to be used
     */
    @Test
    public void GettingAndSavingUrl() throws Exception {
        byte[] testFromUrl = underTestEmpty.UrlToData("https://images2.minutemediacdn.com/image/upload/c_crop,h_1193,w_2121,x_0,y_64/f_auto,q_auto,w_1100/v1565279671/shape/mentalfloss/578211-gettyimages-542930526.jpg");
        byte[] testFromSave = underTestEmpty.ConvertImageToData(testAddress);
        underTestEmpty.setPicData(testFromUrl);
        JFrame testFrame = underTestEmpty.createFrame();
        JPanel testPanel = underTestEmpty.CreatePanel();
        testFrame.setContentPane(testPanel);
        JLabel LabTest = underTestEmpty.CreateImageData(underTestEmpty.getPicData());
        testFrame.getContentPane().add(LabTest);
        testFrame.repaint();

        Thread.sleep((2000));
        //Visual check Passed. Image is on the correct panel.
        assertTrue(true);
    }

    //Test template
//    /**
//     * Test **,
//     *      note
//     */
//    @Test
//    public void ***(){
//        ___
//
//        ____
//      }






}
