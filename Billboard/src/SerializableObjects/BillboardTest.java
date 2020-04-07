package SerializableObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
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
    private Billboard underTest;

    @BeforeEach
    // Billboard = newBillboard(String msg, String info, String picURL, String picDATA, String MsgColour,String BackColour, String InfoColour)
    public Billboard newBillboard(String msg, String info, String picURL, String picDATA, String MsgColour, String BackColour, String InfoColour) throws Exception {
        //creates new empty billboard.
        underTest = newBillboard("TEST MsG","TEST INFO","https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg",
                "TEST","#000000","#FFFFFF","#000000");

        return null;
    }

    /**
     *
     * Test 1,
     *      Check if msg has been inserted
     */
    @Test
    public void MessageInserted () throws Exception{
        //uses getMsg func
        String messageStored = getMsg(undertest);
        Assertions.assertEquals(messageStored.msg,"msg");
    }


    /**
     *
     * Test 2,
     *      Check if info has been inserted
     */
    @Test
    public void InfoInserted () throws Exception{
        //uses getInfo func
        String InfoStored = getInfo(undertest);
        Assertions.assertEquals(InfoStored,"TEST INFO");
    }
    /**
     *
     * Test 3,
     *      Check if picURL has been inserted
     */
    @Test
    public void picURLInserted () throws Exception{
        //uses getpicURL func
        String picURLStored = getpicURL(undertest);
        Assertions.assertEquals(picURLStored,"https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg");
    }
    /**
     *
     * Test 4,
     *      Check if PicData has been inserted
     */
    @Test
    public void PicDataInserted () throws Exception{
        //uses getPicData func
        String PicDataStored = getPicData(undertest);
        Assertions.assertEquals(PicDataStored,"TEST");
    }
    /**
     *
     * Test 5,
     *      Check if Message colour has been inserted
     */
    @Test
    public void MsgColourInserted () throws Exception{
        //uses getMsgColour func
        String MsgColourStored = getMsgColour(undertest);
        Assertions.assertEquals(MsgColourStored,"#000000");
    }
    /**
     *
     * Test 6,
     *      Check if BackColour has been inserted
     */
    @Test
    public void BackColourInserted () throws Exception{
        //uses getBackColour func
        String BGStored = getBackColour(undertest);
        Assertions.assertEquals(BGStored,"#FFFFFF");
    }
    /**
     *
     * Test 7,
     *      Check if info colour has been inserted
     */
    @Test
    public void InfoColourInserted () throws Exception{
        //uses getInfoColour func
        String InfoColourStored = getInfoColour(undertest);
        Assertions.assertEquals(InfoColourStored,"#000000");
    }



}
