package Server.Handlers;
import Clients.ControlPanel.ControlPanelTools.DurationSetter;
import SerializableObjects.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import Tools.HashCredentials;
import Tools.Log;
import Tools.PropertyReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.System.exit;
import static java.lang.System.out;
import static java.time.Duration.*;
import static java.time.temporal.ChronoUnit.*;
import static org.junit.jupiter.api.Assertions.*;

/*************************************************** This is the test class for the billboards database in MariaDB*******************************************
 *First set of Tests will check multiple instances of the addBillboard method, with confirmation in the log confirming that particular billboard was added.
 *Each test will call getBillboard method to confirm that the billboard was added to the database.
 * Second set of Tests will check multiple instances of deleteBillboard method, with confirmation in the log confirming that the billboard was deleted
 * Each test will call getBillboard method to confirm that the billboard was deleted from the database.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *************************************************************************************************************************************************************/
public class MariaDBTest {

    static MariaDB undertest;
    static String password;
    static int access;
    static byte[] salt;
    static boolean existing = false;
    static byte[] test = new byte[56];
    static LocalTime testTime = LocalTime.of(4, 24);
    static LocalTime editTestTime = LocalTime.of(1, 36);

    @BeforeEach
     void NewMariaDB() throws SQLException {
        undertest = new MariaDB();
        undertest.Connect();
        if (undertest.users.checkForUser("user1")) {
            existing = true;
            password = undertest.users.getPassword("user1");
            access = undertest.users.getAccess("user1");
            salt = undertest.users.getSalt("user1");
        }


    }
    @AfterEach
     void cleanup() throws SQLException {
        if (undertest.users.checkForUser("user1")) {
            undertest.users.delete("user1");
        }
        if (existing) {
            undertest.users.add("user1", password, access, salt);
        }
    }


    

//    //**
//    //Test1 to check if test1 billboard is added to the database correctly. Get billboard method is called to confirm addition.
//    //**
    @Test
    public void AddTest() throws SQLException {
        if (!undertest.billboards.checkForBillboard("test1")) {
            undertest.billboards.AddBillboard("test1", "msg", "info", "picURL", new byte[]{0, 1, 2}, "msgColour", "backColour", "infoColour", "admin", true);
        }
            assertEquals(undertest.billboards.checkForBillboard("test1"), true);
    }
//
//    //**
//    //Test2 to check if billboard method is called to confirm additions.
//    //**
    @Test
    public void GetTest1() throws SQLException{
        undertest.billboards.getBillboard("test1");
    }
//
//    //**
//    //Test5 to check if deleteBillboard method correctly deletes the right billboard. Get billboard method is called to confirm addition.
//    //**
//
    @Test
    public void DeleteTest1() throws SQLException {
        undertest.billboards.DeleteBillboard("test1");
        undertest.billboards.getBillboard("test1");
    }
//
//    //**
//    //Test6 to check if deleteBillboard method correctly deletes the right billboard. Get billboard method is called to confirm addition.
//    //**
//
//    @Test
//    public void DeleteTest2() throws SQLException {
//        undertest.billboards.deleteBillboard("test2");
//        undertest.billboards.getBillboard();
//    }
//
//    //**
//    //Test7 to check if deleteBillboard method correctly deletes the right billboard. Get billboard method is called to confirm addition.
//    //**
//
//    @Test
//    public void DeleteTest3() throws SQLException {
//        undertest.billboards.deleteBillboard("test3");
//        undertest.billboards.getBillboard();
//    }
//
//    //**
//    //Test8 to check if billboard method is called to confirm additions and ensure all tests were deleted successfully.
//    //**
//
//    @Test
//    public void GetTest2() throws SQLException{
//        undertest.billboards.getBillboard();
//    }
//
//    //**
//    //Test9 adds a billboard to the database using boolean add method, assert true is used to determine success of test.
//    //**
//
    @Test
    public void AddTest1() throws SQLException {
        undertest.billboards.AddBillboard("test board1", "msg1", "info1", "picurl1", test, "msgcolour1", "backcolour1", "infocolour1", "admin2", false);
        assertTrue(undertest.billboards.checkForBillboard("test board1"));
        undertest.billboards.DeleteBillboard("test board1");
    }

    @Test
    public void EditTest() throws SQLException {
        undertest.billboards.AddBillboard("test board1", "msg1", "info1", "picurl1", test, "msgcolour1", "backcolour1", "infocolour1", "admin2", false);
        undertest.billboards.edit("test board1", "msgedit", "infoedit", "picurl1", test, "msgcolour1", "backcolour1", "infocolour1", "admin2", true);
        assertEquals(undertest.billboards.getBillboardInfo("test board1"), "infoedit");
        //undertest.billboards.DeleteBillboard("test board");
    }
//
//    //**
//    //Test10 adds a billboard to the database using boolean add method, assert true is used to determine success of test.
//    //**
//
//    @Test
//    public void AddTest2() throws SQLException{
//        undertest.billboards.AddBillboard("boolean test2", "msg2", "info2", "picURL", new byte[]{0, 1, 2}, "msgColour", "backColour", "infoColour" );
//        assertTrue(true);
//    }
//
//    //**
//    //Test11 adds a billboard to the database using boolean add method, assert true is used to determine success of test.
//    //**
//
//    @Test
//    public void AddTest3() throws SQLException{
//        undertest.billboards.AddBillboard("boolean test3", "msg3", "info3", "picURL", new byte[]{0, 1, 2}, "msgColour", "backColour", "infoColour" );
//        assertTrue(true);
//    }
//
//    //**
//    //Test12 uses getBillboardName method to retrieve the specified billboard name from the database. Uses assertequals method to confirm success.
//    //**
//
//    @Test
//    public void getBillboardName1() throws SQLException{
//        assertEquals(undertest.billboards.getBillboardName("boolean test1"), "boolean test1");
//    }
//    //**
//    //Test13 uses getBillboardName method to retrieve the specified billboard name from the database. Uses assertequals method to confirm success.
//    //**
//
//    @Test
//    public void getBillboardName2() throws SQLException{
//        assertEquals(undertest.billboards.getBillboardName("boolean test2"), "boolean test2");
//    }
//
//    //**
//    //Test14 uses getBillboardName method to retrieve the specified billboard name from the database. Uses assertequals method to confirm success.
//    //**
//
//    @Test
//    public void getBillboardName3() throws SQLException{
//        assertEquals(undertest.billboards.getBillboardName("boolean test3"), "boolean test3");
//    }
//
//    //**
//    //Test15 uses getBillboardinfo method to retrieve the specified billboard information from the database. Uses assertequals method to confirm success.
//    //**
//
//    @Test
//    public void getBillboardinfo1() throws SQLException{
//        assertEquals(undertest.billboards.getBillboardInfo("boolean test1"), "info1");
//    }
//
//    //**
//    //Test16 uses uses getBillboardinfo method to retrieve the specified billboard information from the database. Uses assertequals method to confirm success.
//    //**
//
//    @Test
//    public void getBillboardinfo2() throws SQLException{
//        assertEquals(undertest.billboards.getBillboardInfo("boolean test2"), "info2");
//    }
//
//    //**
//    //Test17 uses uses getBillboardinfo method to retrieve the specified billboard information from the database. Uses assertequals method to confirm success.
//    //**
//
//    @Test
//    public void getBillboardinfo3() throws SQLException{
//        assertEquals(undertest.billboards.getBillboardInfo("boolean test3"), "info3");
//    }
//
//    //**
//    //Test18 uses uses DeleteBillboardinfo method to delete the specified billboard from the database. Uses assertTrue method to confirm success.
//    //**
//
//    @Test
//    public void deleteBillboard1() throws SQLException{
//        undertest.billboards.DeleteBillboard("boolean test1");
//        assertTrue(true);
//    }
//
//    //**
//    //Test19 uses uses DeleteBillboardinfo method to delete the specified billboard from the database. Uses assertTrue method to confirm success.
//    //**
//
//    @Test
//    public void deleteBillboard2() throws SQLException{
//        undertest.billboards.DeleteBillboard("boolean test2");
//        assertTrue(true);
//    }
//
//    //**
//    //Test20 uses uses DeleteBillboardinfo method to delete the specified billboard from the database. Uses assertTrue method to confirm success.
//    //**
//
//    @Test
//    public void deleteBillboard3() throws SQLException{
//        undertest.billboards.DeleteBillboard("boolean test3");
//        assertTrue(true);
//    }
//
//    //**
//    //Test21 uses uses getAllBillboards method to retrieve the names of all billboards in the database. Uses a predefined list to test against.
//    //**
//
//    @Test
////    public void allEntries() throws SQLException{
////        List<String> actual = undertest.billboards.getAllBillboards();
////        List<String> expected = Arrays.asList("testBoard", "test1", "test25", "boolean test1");
////
////       assertEquals(actual, expected);
//
//    }



/*************************************************** This is the test class for the users database in MariaDB****************************************************************

 *A range of tests to determine viability of mariaDB user methods. The Methods tested range from adding users, getting user passwords,
 * deleting users. The assert true function is used to determine test success.
 * Test 1-3 will add 3 different users to the database, with different usernames, passswords and access levels.
 * Tests 4-? will then retrieve different information from the database concerning use details.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *************************************************************************************************************************************************************/

    //**
    //Test1 Adds a user to the database, user1, with password1, level 1 access.
    //**

    @Test
    public void userTest() throws SQLException {
        if (!undertest.users.checkForUser("user1")) {
            undertest.users.add("user1", "password1", 1, HashCredentials.CreateSalt());
        }
        assertTrue(undertest.users.checkForUser("user1"));
        undertest.users.delete("user1");
    }

    //**
    //Test2 Uses getPassword method to get password of user1.
    //**
    @Test
    public void getPasswordTest() throws SQLException {
        if (!undertest.users.checkForUser("user1")) {
            undertest.users.add("user1", "password1", 1, HashCredentials.CreateSalt());
        }
        assertEquals(undertest.users.getPassword("user1"), "password1");
        undertest.users.delete("user1");
    }

    //**
    //Test3 Uses GetUserAccess method to get the user access of user1.
    //**
    @Test
    public void getUserAccessTest() throws SQLException {
        if (!undertest.users.checkForUser("user1")) {
            undertest.users.add("user1", "password1", 1, HashCredentials.CreateSalt());
        }
        assertEquals(undertest.users.getAccess("user1"), 1);
        undertest.users.delete("user1");
    }

    //**
    //Test4 Uses GetUserAccess method to check user1 has salt.
    //**
    @Test
    public void getUserSaltTest() throws SQLException {
        if (!undertest.users.checkForUser("user1")) {
            undertest.users.add("user1", "password1", 1, HashCredentials.CreateSalt());
        }
        assertNotEquals(undertest.users.getSalt("user1"), null);
        undertest.users.delete("user1");
    }

//    //**
//    //Test5 Retrieves all usernames from the user's database.
//    //**
//    @Test
//    public void allUserEntries() throws SQLException {
//        if (!undertest.users.checkForUser("user1")) {
//            undertest.users.add("user1", "password1", 1, HashCredentials.CreateSalt());
//        }
//        List<String> expected = Arrays.asList("admin", "user1");
//        assertEquals(undertest.users.getAllUsernames(), expected);
//        undertest.users.delete("user1");
//    }

    //**
    //Test6 tests the edit of and existing user.
    //**
    @Test
    public void editUserEntry() throws SQLException {
        if (!undertest.users.checkForUser("user1")) {
            undertest.users.add("user1", "password1", 1, HashCredentials.CreateSalt());
        }
        undertest.users.edit("user1","password2", 2, HashCredentials.CreateSalt());
        assertEquals(undertest.users.getPassword("user1"), "password2");
        assertEquals(undertest.users.getAccess("user1"), 2);
        undertest.users.delete("user1");
    }



    /*************************************************** This is the test class for the scheduling database in MariaDB****************************************************************

     *A range of tests to determine viability of mariaDB scheudling methods. The Methods tested range from adding scheudling, deleting scheduling and editing scheduling
     * Add scheduling tests
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *************************************************************************************************************************************************************/


    @Test
    public void addScheduling1() throws SQLException{
        undertest.scheduling.AddSchedule("schedule1", "billboard1", "Thursday", testTime, 1, 0);
    }

    @Test
    public void getTime() throws SQLException{
        undertest.scheduling.getScheduleTime("schedule1");

    }

    @Test
    public void getDuration() throws SQLException{
        undertest.scheduling.getScheduleDuration("schedule1");

    }

    @Test
    public void deleteScheduledTest() throws SQLException{
        undertest.scheduling.deleteScheduled("schedule1");

    }

    @Test
    public void editTest1() throws SQLException{

        undertest.scheduling.edit("schedule1", "editbillboard1", "Thursday", editTestTime, 4, 4);
    }

    @Test
    public void getdaySchule() throws SQLException{

     List<String> actual = undertest.scheduling.getDaySchedules("thursday");
       List<String> expected = Arrays.asList("schedule1", "editbillboard1", "5");
       assertEquals(actual, expected);

    }

    //**
//    //Test21 uses uses getAllBillboards method to retrieve the names of all billboards in the database. Uses a predefined list to test against.
//    //**
//
//    @Test
////    public void allEntries() throws SQLException{
////        List<String> actual = undertest.billboards.getAllBillboards();
////        List<String> expected = Arrays.asList("testBoard", "test1", "test25", "boolean test1");
////
////       assertEquals(actual, expected);
//
//    }
}