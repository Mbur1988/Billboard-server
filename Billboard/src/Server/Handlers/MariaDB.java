package Server.Handlers;

import SerializableObjects.Billboard;
import Tools.HashCredentials;
import Tools.Log;
import Tools.PropertyReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import static Clients.ControlPanel.ControlPanel.lists;
import static Tools.ColorIndex.stringFromColor;
import static java.lang.System.exit;

public class MariaDB {

    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

    private String url, schema, username, password;
    private Connection connection;
    private Statement statement;
    public Users users;
    public Billboards billboards;
    public Scheduling scheduling;

    /**
     * MariaDB class constructor
     * Sets network configuration variables; url, schema, username and password
     */
    public MariaDB() {
        SetNetworkConfig();
        users = new Users();
        billboards = new Billboards();
        scheduling = new Scheduling();
    }

    /**
     * Sets the URL to be used by the MariaDB class instance
     *
     * @param url URL as string
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets the schema to be used by the MariaDB class instance
     *
     * @param schema
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Sets the userName to be used by the MariaDB class instance
     *
     * @param userName
     */
    public void setUsername(String userName) {
        this.username = userName;
    }

    /**
     * Sets the password to be used by the MariaDB class instance
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password used by the MariaDB class instance
     *
     * @return Password as string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Uses the db.props file to set the network configuration variables;
     * url, schema, username and password.
     */
    private void SetNetworkConfig() {
        try {
            setUrl(PropertyReader.GetProperty("db", "jdbc.url"));
            setSchema(PropertyReader.GetProperty("db", "jdbc.schema"));
            setUsername(PropertyReader.GetProperty("db", "jdbc.username"));
            setPassword(PropertyReader.GetProperty("db", "jdbc.password"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new connection to the database.
     * If the database is unavailable of the network configuration
     * variables are incorrect then the program will exit.
     */
    public void Connect() {
        try {
            Class.forName(JDBC_DRIVER).getConstructor().newInstance();
            connection = DriverManager.getConnection(url + "/" + schema, username, password);
            statement = connection.createStatement();
            CheckForTables();
            //deleteTest();
            Log.Confirmation("Database connection established");
        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Log.Error("Database connection failed due to:\n" + e);
            e.printStackTrace();
            // if database connection fails then end program
            exit(0);
        }
    }

//    /**
//     * Closes the current database connection
//     */
//    public void Disconnect() {
//        try {
//            statement.close();
//            connection.close();
//            Log.Warning("Database connection closed");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Checks whether the specified table exists within the database
     *
     * @param name name of the tabe as string
     * @return true if the table exists, else false
     * @throws SQLException
     */
    private boolean CheckForTable(String name) throws SQLException {
        ResultSet results = connection.getMetaData().getTables(null, null, name, null);
        if (results.next()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks the database for the required tables
     * If any of the required tables does not exist then a default version is generated
     *
     * @throws SQLException
     */
    private void CheckForTables() throws SQLException {
        if (!CheckForTable("users") && users!=null) {
            users.createTable();
        }
        else if (CheckForTable("users") && !users.checkForUsers()) {
            users.createDefaultUser();
        }
        if (!CheckForTable("billboards") && billboards!=null) {
            billboards.CreateBillboardsTable();
        }
        if (!CheckForTable("scheduling")&& scheduling!=null) {
            scheduling.CreateSchedulingTable();
        }
    }



    public class Users {

        /**
         * Creates a new users table and default admin user
         *
         * @throws SQLException
         */
        private void createTable() throws SQLException {
            statement.executeUpdate("CREATE TABLE users (username VARCHAR(64) UNIQUE KEY, password VARCHAR(64), access INT NOT NULL, salt VARBINARY(10));");
            Log.Confirmation("Table created: users");
            createDefaultUser();
        }

        /**
         * Adds a default user to the users table
         * @throws SQLException
         */
        private void createDefaultUser() throws SQLException {
            String username = "admin";
            String password = HashCredentials.Hash("default");
            int access = 15;
            byte[] salt = HashCredentials.CreateSalt();
            password = HashCredentials.Hash(password, salt);
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `users`(username, password, access, salt) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, access);
            pstmt.setBytes(4, salt);
            pstmt.executeUpdate();
            Log.Confirmation("User created: admin");
        }

        /**
         * Adds a new user to the users table as long as the username does not already exist
         *
         * @param username the username of the entry as String
         * @param password the password of the entry as String
         * @param access   the access level of the entry as an Integer
         * @return true if the entry is successful and false if an entry already exists with the same username
         * @throws SQLException
         */
        public boolean add(String username, String password, Integer access, byte[] salt) throws SQLException {
            if (checkForUser(username)) {
                return false;
            } else {
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `users`(username, password, access, salt) VALUES (?, ?, ?, ?)");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setInt(3, access);
                pstmt.setBytes(4, salt);
                pstmt.executeUpdate();
                return true;
            }
        }

        /**
         * Checks the users table for the specified entry
         * @param username the username to search the database for
         * @return boolean of whether the user exists
         * @throws SQLException
         */
        public boolean checkForUser(String username) throws SQLException {
            ResultSet result;
            if (username == null) {
                result = statement.executeQuery("SELECT * FROM users;");
            }
            else {
                result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
            }
            if (result.next()) {
                return true;
            }
            else {
                return false;
            }
        }

        private boolean checkForUsers() throws SQLException {
            return checkForUser(null);
        }

        /**
         * Deletes existing user field
         * @param username Username of the user to delete
         * @return boolean value true if operation was successful else false
         * @throws SQLException
         */
        public boolean delete(String username) throws SQLException {
            if (checkForUser(username)) {
                statement.executeQuery("DELETE FROM users WHERE username='" + username + "';");
                if (checkForUser(username)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }

        /**
         * Edits existing user fields; password, access and salt
         * @param username Username of the user to edit
         * @param password New password as string
         * @param access new access level as integer
         * @param salt new salt as byte array
         * @return boolean value true if operation was successful else false
         * @throws SQLException
         */
        public boolean edit(String username, String password, Integer access, byte[] salt) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
            if (result.next()) {
                if (password == null) {
                    password = users.getPassword(username);
                }
                if (access == null) {
                    access = users.getAccess(username);
                }
                if (salt == null) {
                    salt = users.getSalt(username);
                }
                users.delete(username);
                users.add(username, password, access, salt);
                return true;
            } else {
                return false;
            }
        }

        /**
         * Edits existing user access field
         * @param username Username of the user to edit
         * @param access new access level as integer
         * @return boolean value true if operation was successful else false
         * @throws SQLException
         */
        public boolean edit(String username, Integer access) throws SQLException {
            return edit(username, null, access, null);
        }

        /**
         * Edits existing user fields; password and salt
         *
         * @param username Username of the user to edit
         * @param password New password as string
         * @param salt new salt as byte array
         * @return boolean value true if operation was successful else false
         * @throws SQLException
         */
        public boolean edit(String username, String password, byte[] salt) throws SQLException {
            return edit(username, password, null, salt);
        }

        /**
         * Returns the password of an entry provided that it exists
         *
         * @param username the username of the entry as String
         * @return the password as a string else null if the entry does not exist
         * @throws SQLException
         */
        public String getPassword(String username) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
            if (result.next()) {
                return result.getString("password");
            } else {
                return null;
            }
        }

        /**
         * Returns the access level of an entry provided that it exists
         *
         * @param username the username of the entry as String
         * @return the access level as an integer else null if the entry does not exist
         * @throws SQLException
         */
        public Integer getAccess(String username) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
            if (result.next()) {
                return result.getInt("access");
            } else {
                return null;
            }
        }

        /**
         * Returns the password of an entry provided that it exists
         *
         * @param username the username of the entry as String
         * @return the password as a byte array else null if the entry does not exist
         * @throws SQLException
         */
        public byte[] getSalt(String username) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
            if (result.next()) {
                return result.getBytes("salt");
            } else {
                return null;
            }
        }

        /**
         * ArrayList Method to return all username's from the user's database.
         *
         * @return ArrayList as a string list
         * @throws SQLException
         */
        public ArrayList<String> getAllUsernames() throws SQLException {
            String retrieve = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(retrieve);
            ArrayList<String> allUsers = new ArrayList<>();
            while (result.next()){
                String username = result.getString("username");
                allUsers.add(username);
            }
            Collections.sort(allUsers);
            return allUsers;
        }
    }



    public class Billboards {

        /**
         * Creates a billboards table to store billboards
         * name the name of the billboard being added
         * msg  msg of the displaying billboard being added
         * info information about the billboard being added
         * picURL Picture url included in the billboard
         * picData data of the picture included in the billboard to be added
         * msgColour The colour of msg in the billboard
         * backColour The back colour of the billboard
         * infoColour The colour of the information of the billboard
         * username Current user adding the billboard
         * scheduled Boolean to determine if a billboard is scheduled
         * Logs confirmation that the billboard was added
         * @throws SQLException
         */
        private void CreateBillboardsTable() throws SQLException {
            statement.executeUpdate("CREATE TABLE billboards (name VARCHAR(64) UNIQUE KEY, msg VARCHAR(64), info VARCHAR(64), picURL VARCHAR(64), picDATA BLOB, msgColour VARCHAR(64), backColour VARCHAR(64), infoColour VARCHAR(64), username VARCHAR(64), scheduled BOOLEAN);");
            Log.Confirmation("Table created: billboards");
            CreateDefaultBillboard();
            getBillboard();
        }

        /**
         * Adds a default test billboard to the billboard table.
         * <p>
         * name the name of the billboard being added
         * msg  msg of the displaying billboard being added
         * info information about the billboard being added
         * picURL Picture url included in the billboard
         * picData data of the picture included in the billboard to be added
         * msgColour The colour of msg in the billboard
         * backColour The back colour of the billboard
         * infoColour The colour of the information of the billboard
         * username Current user adding the billboard
         * scheduled Boolean to determine if a billboard is scheduled
         * Logs confirmation that the billboard was added
         * @throws SQLException
         */

        public void CreateDefaultBillboard() throws SQLException {
            String name = "testBoard";
            String msg = "database test";
            String info = "admin test";
            String picURL = "test pic";
            byte[] picData = new byte[200];
            String msgColour = "Red";
            String backColour = "Green";
            String infoColour = "Blue";
            String username = "admin";
            boolean scheduled = true;
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `billboards`(name, msg, info, picURL, picData, msgColour, backColour, infoColour, username, scheduled) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, name);
            pstmt.setString(2, msg);
            pstmt.setString(3, info);
            pstmt.setString(4, picURL);
            pstmt.setBytes(5, picData);
            pstmt.setString(6, msgColour);
            pstmt.setString(7, backColour);
            pstmt.setString(8, infoColour);
            pstmt.setString(9, username);
            pstmt.setBoolean(10, scheduled);
            pstmt.executeUpdate();
            Log.Confirmation("Billboard Created: Test Board");
        }

        /**
         * Adds a new billboard to the billboard table as long as the username does not already exist
         *
         * @param name       the name of the billboard being added
         * @param msg        msg of the displaying billboard being added
         * @param info       information about the billboard being added
         * @param picURL     Picture url included in the billboard
         * @param picData    data of the picture included in the billboard to be added
         * @param msgColour  The colour of msg in the billboard
         * @param backColour The back colour of the billboard
         * @param infoColour The colour of the information of the billboard
         * @param username   Current user adding the billboard
         * @param scheduled  boolean to determine if the billboard has been scheduled
         * Checks to see if billboard already exists, otherwise adds the billboard and returns true.
         * @throws SQLException
         */

        public boolean AddBillboard(String name, String msg, String info, String picURL, byte[] picData, String msgColour, String backColour, String infoColour, String username, boolean scheduled) throws SQLException {
            if (checkForBillboard(name)) {
                return false;
            } else {
                PreparedStatement prepareAdd = connection.prepareStatement("INSERT INTO `billboards`(name, msg, info, picURL, picData, msgColour, backColour, infoColour, username, scheduled) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                prepareAdd.setString(1, name);
                prepareAdd.setString(2, msg);
                prepareAdd.setString(3, info);
                prepareAdd.setString(4, picURL);
                prepareAdd.setBytes(5, picData);
                prepareAdd.setString(6, msgColour);
                prepareAdd.setString(7, backColour);
                prepareAdd.setString(8, infoColour);
                prepareAdd.setString(9, username);
                prepareAdd.setBoolean(10, scheduled);
                prepareAdd.executeUpdate();
                return true;
            }

        }

        /**
         * Checks for a billboard in the billboard table as returning true or false depending on existance
         *
         * @param name the name of the billboard being checked for
         * @throws SQLException
         */
        public boolean checkForBillboard(String name) throws SQLException {
            ResultSet result;
            if (name == null) {
                result = statement.executeQuery("SELECT * FROM billboards;");
            } else {
                result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            }
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Method to delete a specified billboard from the billboards database.Uses boolean method.
         * @param name the name of the billboard being deleted
         * Confirms deletion of requested billboard in the log.
         * @throws SQLException
         */
        public boolean DeleteBillboard(String name) throws SQLException {
            if (checkForBillboard(name)) {
                statement.executeQuery("DELETE FROM billboards WHERE name='" + name + "';");
                if (checkForBillboard(name)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }

        /**
         * Method to retrieve all entries currently in the billboard database.
         * name the name of the billboard being added
         * msg  msg of the displaying billboard being added
         * info information about the billboard being added
         * picURL Picture url included in the billboard
         * picData data of the picture included in the billboard to be added
         * msgColour The colour of msg in the billboard
         * backColour The back colour of the billboard
         * infoColour The colour of the information of the billboard
         * username Current user adding the billboard
         * Confirms all billboards in the database via a log confirmation.
         * @throws SQLException
         */

        public void getBillboard() throws SQLException {
            String retrieve = "SELECT * FROM billboards";
            Statement query = connection.createStatement();
            ResultSet result = statement.executeQuery(retrieve);

            int count = 0;

            while (result.next()) {
                String name = result.getString("name");
                String msg = result.getString("msg");
                String info = result.getString("info");
                String picURL = result.getString("picURL");
                String msgColour = result.getString("msgColour");
                String backColour = result.getString("backColour");
                String infoColour = result.getString("infoColour");
                String username = result.getString("username");
                boolean scheduled = result.getBoolean("scheduled");

                String output = "Billboard #%d: %s - %s - %s - %s - %s - %s - %s - %s - %s";
                Log.Confirmation(String.format(output, ++count, name, msg, info, picURL, msgColour, backColour, infoColour, username, scheduled));
            }
        }
        
        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard name currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */
        

        public String getBillboardName(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getString("name");
            } else {
                return null;
            }
        }
        
        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard info currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */

        public String getBillboardInfo(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getString("info");
            } else {
                return null;
            }
        }

        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard msg currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */


        public String getBillboardMsg(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getString("msg");
            } else {
                return null;
            }
        }

        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard picURL currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */



        public String getBillboardPicURL(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getString("picURL");
            } else {
                return null;
            }
        }

        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard picData currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */

        public byte[] getBillboardPicData(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getBytes("picData");
            } else {
                return null;
            }
        }

        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard msgColour currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */

        public String getBillboardMsgColour(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getString("msgColour");
            } else {
                return null;
            }
        }

        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard backColour currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */

        public String getBillboardBackColour(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getString("backColour");
            } else {
                return null;
            }
        }

        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard infoColour currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */

        public String getBillboardInfoColour(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getString("infoColour");
            } else {
                return null;
            }
        }

        /**
         * @param name the name of the billboard
         * Method to retrieve the specified billboard username currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */

        public String getBillboardUser(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getString("username");
            } else {
                return null;
            }
        }

        /**
         * @param name the name of the billboard
         * Method to retrieve the billboard scheduling status currently in the billboard database.
         * Returns false if not found
         * @throws SQLException
         */

        public boolean getBillboardSchedule(String name) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                return result.getBoolean("scheduled");
            } else {
                return false;
            }
        }

        /**
         * Method to retrieve all of the billboards in the database, saving the names in a list.
         * Returns the list as a String list.
         * @throws SQLException
         */


        public ArrayList<String> getAllBillboards() throws SQLException {
            String retrieve = "SELECT * FROM billboards";
            ResultSet result = statement.executeQuery(retrieve);
            ArrayList<String> allBillboards = new ArrayList<>();
            while (result.next()){
                String name = result.getString("name");
                allBillboards.add(name);
            }
            return allBillboards;
        }

        public ArrayList<String> getAllBillboardsCurrent(String currentUser) throws SQLException{
            String retrieve = "SELECT * FROM billboards";
            ResultSet result = statement.executeQuery(retrieve);
            ArrayList<String> userBillboards = new ArrayList<>();
            while (result.next()){
                String username = result.getString("username");
                if (username.equals(currentUser)){
                    userBillboards.add(result.getString("name"));
                }

            }
            return userBillboards;
        }

        public boolean edit(String name, String msg, String info, String picURL, byte[] picData, String msgColour, String backColour, String infoColour, String username, Boolean scheduled) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE name = '" + name + "';");
            if (result.next()) {
                if (msg == null) {
                    msg = billboards.getBillboardMsg(name);
                }
                if (info == null) {
                    info = billboards.getBillboardInfo(name);
                }
                if (picURL == null) {
                    picURL = billboards.getBillboardPicURL(name);
                }
                if (picData == null) {
                    picData = billboards.getBillboardPicData(name);
                }
                if (msgColour == null) {
                    msgColour = billboards.getBillboardMsgColour(name);
                }
                if (backColour == null) {
                    backColour = billboards.getBillboardBackColour(name);
                }
                if (infoColour == null) {
                    infoColour = billboards.getBillboardInfoColour(name);
                }
                if (username == null) {
                    username = billboards.getBillboardUser(name);
                }
                if (scheduled = null) {
                    scheduled = billboards.getBillboardSchedule(name);
                }
                billboards.DeleteBillboard(name);
                billboards.AddBillboard(name, msg, info, picURL, picData, msgColour, backColour, infoColour, username, scheduled);
                return true;
            } else {
                return false;
            }
        }

        public boolean AddBillboardColour(Billboard billboard) throws SQLException {
            if (checkForBillboard(billboard.getName())) {
                return false;
            } else {
                PreparedStatement prepareAdd = connection.prepareStatement("INSERT INTO `billboards`(name, msg, info, picURL, picData, msgColour, backColour, infoColour, username, scheduled) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                prepareAdd.setString(1, billboard.getName());
                prepareAdd.setString(2, billboard.getMsg());
                prepareAdd.setString(3, billboard.getInfo());
                prepareAdd.setString(4, billboard.getPicUrl());
                prepareAdd.setBytes(5, billboard.getPicData());
                prepareAdd.setString(6, stringFromColor(billboard.getMsgColour()));
                prepareAdd.setString(7, stringFromColor(billboard.getBackColour()));
                prepareAdd.setString(8, stringFromColor(billboard.getInfoColour()));
                prepareAdd.executeUpdate();
                return true;
            }
        }

//        public boolean editBillboard(String name, String msg, String info, String picURL, byte[] picData, String msgColour, String backColour, String infoColour) throws SQLException {
//            return editBillboard(null, null,  null, null, null, null, null, null);

//        }



    }



    public class Scheduling {

        /**
         * Creates a new scheduling table
         *
         * @throws SQLException
         */
        private void CreateSchedulingTable() throws SQLException {
            statement.executeQuery("CREATE TABLE scheduling (name VARCHAR(64) UNIQUE KEY);");
            Log.Confirmation("Table created: scheduling");
        }
    }

}






