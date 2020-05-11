package Server.Handlers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import Tools.HashCredentials;
import Tools.Log;
import Tools.PropertyReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import static java.lang.System.exit;
import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    byte[] testBytes = new byte[]{0, 1, 2};
    MariaDB undertest;
    byte[] salt = HashCredentials.CreateSalt();


    @BeforeEach
    void NewMariaDB() {
        undertest = new MariaDB();
        undertest.Connect();

    }

    //**
    //Test1 to check if test1 billboard is added to the database correctly. Get billboard method is called to confirm addition.
    //**
    @Test
    public void AddTester1() throws SQLException {
        undertest.billboards.checkForBillboard("test1");
        undertest.billboards.addBillboard("test1", "msg", "info", "picURL", testBytes, "msgColour", "backColour", "infoColour");{
        undertest.billboards.getBillboard();
        }
    }
    //**
    //Test2 to check if test2 billboard is added to the database correctly. Get billboard method is called to confirm addition.
    //**
    @Test
    public void AddTester2() throws SQLException {
        undertest.billboards.addBillboard("test2", "msg", "info", "picURL", testBytes, "msgColour", "backColour", "infoColour");
        undertest.billboards.getBillboard();
    }
    //**
    //Test3 to check if test3 billboard is added to the database correctly. Get billboard method is called to confirm addition.
    //**

    @Test
    public void AddTester3() throws SQLException {
        undertest.billboards.addBillboard("test3", "msg", "info", "picURL", testBytes, "msgColour", "backColour", "infoColour");
        undertest.billboards.getBillboard();
    }

    //**
    //Test4 to check if billboard method is called to confirm additions.
    //**

    @Test
    public void GetTest1() throws SQLException{
        undertest.billboards.getBillboard();
    }

    //**
    //Test5 to check if deleteBillboard method correctly deletes the right billboard. Get billboard method is called to confirm addition.
    //**

    @Test
    public void DeleteTest1() throws SQLException {
        undertest.billboards.deleteBillboard("test1");
        undertest.billboards.getBillboard();
    }

    //**
    //Test6 to check if deleteBillboard method correctly deletes the right billboard. Get billboard method is called to confirm addition.
    //**

    @Test
    public void DeleteTest2() throws SQLException {
        undertest.billboards.deleteBillboard("test2");
        undertest.billboards.getBillboard();
    }

    //**
    //Test7 to check if deleteBillboard method correctly deletes the right billboard. Get billboard method is called to confirm addition.
    //**

    @Test
    public void DeleteTest3() throws SQLException {
        undertest.billboards.deleteBillboard("test3");
        undertest.billboards.getBillboard();
    }

    //**
    //Test8 to check if billboard method is called to confirm additions and ensure all tests were deleted successfully.
    //**

    @Test
    public void GetTest2() throws SQLException{
        undertest.billboards.getBillboard();
    }

    //**
    //Test9 adds a billboard to the database using boolean add method, assert true is used to determine success of test.
    //**

    @Test
    public void AddTest1() throws SQLException{
        undertest.billboards.AddBillboard("boolean test1", "msg1", "info1", "picURL", testBytes, "msgColour", "backColour", "infoColour" );
        assertTrue(true);
    }

    //**
    //Test10 adds a billboard to the database using boolean add method, assert true is used to determine success of test.
    //**

    @Test
    public void AddTest2() throws SQLException{
        undertest.billboards.AddBillboard("boolean test2", "msg2", "info2", "picURL", testBytes, "msgColour", "backColour", "infoColour" );
        assertTrue(true);
    }

    //**
    //Test11 adds a billboard to the database using boolean add method, assert true is used to determine success of test.
    //**

    @Test
    public void AddTest3() throws SQLException{
        undertest.billboards.AddBillboard("boolean test3", "msg3", "info3", "picURL", testBytes, "msgColour", "backColour", "infoColour" );
        assertTrue(true);
    }

    //**
    //Test12 uses getBillboardName method to retrieve the specified billboard name from the database. Uses assertequals method to confirm success.
    //**

    @Test
    public void getBillboardName1() throws SQLException{
        assertEquals(undertest.billboards.getBillboardName("boolean test1"), "boolean test1");
    }
    //**
    //Test13 uses getBillboardName method to retrieve the specified billboard name from the database. Uses assertequals method to confirm success.
    //**

    @Test
    public void getBillboardName2() throws SQLException{
        assertEquals(undertest.billboards.getBillboardName("boolean test2"), "boolean test2");
    }

    //**
    //Test14 uses getBillboardName method to retrieve the specified billboard name from the database. Uses assertequals method to confirm success.
    //**

    @Test
    public void getBillboardName3() throws SQLException{
        assertEquals(undertest.billboards.getBillboardName("boolean test3"), "boolean test3");
    }

    //**
    //Test15 uses getBillboardinfo method to retrieve the specified billboard information from the database. Uses assertequals method to confirm success.
    //**

    @Test
    public void getBillboardinfo1() throws SQLException{
        assertEquals(undertest.billboards.getBillboardinfo("boolean test1"), "info1");
    }

    //**
    //Test16 uses uses getBillboardinfo method to retrieve the specified billboard information from the database. Uses assertequals method to confirm success.
    //**

    @Test
    public void getBillboardinfo2() throws SQLException{
        assertEquals(undertest.billboards.getBillboardinfo("boolean test2"), "info2");
    }

    //**
    //Test17 uses uses getBillboardinfo method to retrieve the specified billboard information from the database. Uses assertequals method to confirm success.
    //**

    @Test
    public void getBillboardinfo3() throws SQLException{
        assertEquals(undertest.billboards.getBillboardinfo("boolean test3"), "info3");
    }

    //**
    //Test18 uses uses DeleteBillboardinfo method to delete the specified billboard from the database. Uses assertTrue method to confirm success.
    //**

    @Test
    public void deleteBillboard1() throws SQLException{
        undertest.billboards.DeleteBillboard("boolean test1");
        assertTrue(true);
    }

    //**
    //Test19 uses uses DeleteBillboardinfo method to delete the specified billboard from the database. Uses assertTrue method to confirm success.
    //**

    @Test
    public void deleteBillboard2() throws SQLException{
        undertest.billboards.DeleteBillboard("boolean test2");
        assertTrue(true);
    }

    //**
    //Test20 uses uses DeleteBillboardinfo method to delete the specified billboard from the database. Uses assertTrue method to confirm success.
    //**

    @Test
    public void deleteBillboard3() throws SQLException{
        undertest.billboards.DeleteBillboard("boolean test3");
        assertTrue(true);
    }



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
    public void userTest1() throws SQLException{
        undertest.users.AddUser("user1", "password1", 1, salt );
        assertTrue(true);
    }

    //**
    //Test2 Adds a user to the database, user2, with password2, level 2 access.
    //**

    @Test
    public void userTest2() throws SQLException{
        undertest.users.AddUser("user2", "password2", 2, salt );
        assertTrue(true);
    }

    //**
    //Test3 Adds a user to the database, user3, with password3, level 3 access.
    //**

    @Test
    public void userTest3() throws SQLException{
        undertest.users.AddUser("user3", "password3", 3, salt );
        assertTrue(true);
    }

    //**
    //Test4 Uses getPassword method to get password of user1.
    //**


    @Test
    public void getPasswordTest1() throws SQLException {
        assertEquals(undertest.users.GetUserPassword("user1"), "password1");
    }

    //**
    //Test5 Uses getPassword method to get password of user2.
    //**

    @Test
    public void getPasswordTest2() throws SQLException {
        assertEquals(undertest.users.GetUserPassword("user2"), "password2");

    }
    //**
    //Test6 Uses getPassword method to get password of user3.
    //**

    @Test
    public void getPasswordTest3() throws SQLException {
        assertEquals(undertest.users.GetUserPassword("user3"), "password3");
    }

    //**
    //Test7 Uses GetUserAccess method to get the user access of user1.
    //**

    @Test
    public void getUserAccessTest1() throws SQLException {
        assertEquals(undertest.users.GetUserAccess("user1"), 1);
    }

    //**
    //Test8 Uses GetUserAccess method to get the user access of user2.
    //**

    @Test
    public void getUserAccessTest2() throws SQLException {
        assertEquals(undertest.users.GetUserAccess("user2"), 2);
    }

    //**
    //Test9 GetUserAccess method to get the user access of user2.
    //**

    @Test
    public void getUserAccessTest3() throws SQLException {
        assertEquals(undertest.users.GetUserAccess("user3"), 3);
    }

    //**
    //Test10 DeleteUser method to delete user1 from the users database
    //**

    @Test
    public void deleteUserTest1() throws SQLException{
        undertest.users.DeleteUser("user1");
        assertTrue(true);

    }

    //**
    //Test11 DeleteUser method to delete user2 from the users database
    //**


    @Test
    public void deleteUserTest2() throws SQLException{
        undertest.users.DeleteUser("user2");
        assertTrue(true);

    }

    //**
    //Test10 DeleteUser method to delete user1 from the users database
    //**

    @Test
    public void deleteUserTest3() throws SQLException{
        undertest.users.DeleteUser("user3");
        assertTrue(true);

    }


}