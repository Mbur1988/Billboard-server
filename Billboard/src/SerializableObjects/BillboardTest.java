package SerializableObjects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 ******************* This is the test class for the Billboards**********
 * before each the tester will create a new billboard  using the constructor with...
 * (String msg, String info, String picURL, String picDATA, String MsgColour,String BackColour, String InfoColour)
 * Test 1 to 7 are to test if all fields have been filled - consider adding more for other constructors made ase we go.
 *
 * as this continues we will add functionality to display image etc
 */

public class BillboardTest {
    Billboard underTest;

    @BeforeEach @Test
    // Billboard = newBillboard(String msg, String info, String picURL, String picDATA, String MsgColour,String BackColour, String InfoColour)
    void newBillboard() throws Exception {
          underTest = new Billboard("TEST MsG","TEST INFO","https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg",
                 "TEST","#000000","#FFFFFF","#000000");

    }

    /**
     *
     * Test 1,
     *      Check if msg has been inserted
     */
    @Test
    public void MessageInserted () throws Exception{
        //uses getMsg funct
        assertEquals(underTest.getMsg(),"TEST MsG");
    }


    /**
     *
     * Test 2,
     *      Check if info has been inserted
     */
    @Test
    public void InfoInserted () throws Exception{
        //uses getInfo func
        assertEquals(underTest.getInfo(),"TEST INFO");
    }
    /**
     *
     * Test 3,
     *      Check if picURL has been inserted
     */
    @Test
    public void picURLInserted () throws Exception{
        //uses getpicURL func
        assertEquals(underTest.getpicURL(),"https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg");
    }
    /**
     *
     * Test 4,
     *      Check if PicData has been inserted
     */
    @Test
    public void PicDataInserted () throws Exception{
        //uses getPicData func
        assertEquals(underTest.getPicData(),"TEST");
    }
    /**
     *
     * Test 5,
     *      Check if Message colour has been inserted
     */
    @Test
    public void MsgColourInserted () throws Exception{
        //uses getMsgColour func
        assertEquals( underTest.getMsgColour(),"#000000");
    }
    /**
     *
     * Test 6,
     *      Check if BackColour has been inserted
     */
    @Test
    public void BackColourInserted () throws Exception{
        //uses getBackColour func
        assertEquals(underTest.getBackColour(),"#FFFFFF");
    }
    /**
     *
     * Test 7,
     *      Check if info colour has been inserted
     */
    @Test
    public void InfoColourInserted () throws Exception{
        //uses getInfoColour func
        assertEquals(underTest.getInfoColour(),"#000000");
    }



}
