package Server.Handlers;

import Tools.HashCredentials;
import Tools.Log;
import Tools.PropertyReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
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
     * Returns the JDBC driver used by MariaDB class
     *
     * @return JDBC driver as string
     */
    public static String getJdbcDriver() {
        return JDBC_DRIVER;
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
     * Gets the URL used by the MariaDB class instance
     *
     * @return URL as string
     */
    public String getUrl() {
        return url;
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
     * Gets the schema used by the MariaDB class instance
     *
     * @return Schema as string
     */
    public String getSchema() {
        return schema;
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
     * Gets the userName used by the MariaDB class instance
     *
     * @return Username as string
     */
    public String getUsername() {
        return username;
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
     * Sets the connection to be used by the MariaDB class instance
     *
     * @param connection
     */
    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the connection used by the MariaDB class instance
     *
     * @return Connection
     */
    private Connection getConnection() {
        return connection;
    }

    /**
     * Sets the statement to be used by the MariaDB class instance
     *
     * @param statement
     */
    private void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * Gets the statement used by the MariaDB class instance
     *
     * @return Statement
     */
    private Statement getStatement() {
        return statement;
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
            Log.Confirmation("Database connection established");
        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Log.Error("Database connection failed due to:\n" + e);
            e.printStackTrace();
            // if database connection fails then end program
            exit(0);
        }
    }

    /**
     * Closes the current database connection
     */
    public void Disconnect() {
        try {
            statement.close();
            connection.close();
            Log.Warning("Database connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
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
            users.CreateUsersTable();
        }
        else if (CheckForTable("users") && !users.CheckForUsers()) {
            users.CreateDefaultUser();
        }
        if (!CheckForTable("billboards")) {
            billboards.CreateBillboardsTable();
        }
        if (!CheckForTable("scheduling")&& scheduling!=null) {
            scheduling.CreateSchedulingTable();
        }
    }

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

    public class Users {

        /**
         * Creates a new users table and default admin user
         *
         * @throws SQLException
         */
        private void CreateUsersTable() throws SQLException {
            statement.executeUpdate("CREATE TABLE users (username VARCHAR(64) UNIQUE KEY, password VARCHAR(64), access INT NOT NULL, salt VARBINARY(10));");
            Log.Confirmation("Table created: users");
            CreateDefaultUser();
        }

        private void CreateDefaultUser() throws SQLException {
            String username = "admin";
            String password = HashCredentials.Hash("default");
            int access = 5;
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

        private boolean CheckForUser(String username) throws SQLException {
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

        private boolean CheckForUsers() throws SQLException {
            return CheckForUser(null);
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
        public boolean AddUser(String username, String password, Integer access, byte[] salt) throws SQLException {
            if (CheckForUser(username)) {
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
         * Returns the password of an entry provided that it exists
         *
         * @param username the username of the entry as String
         * @return the password as a string else null if the entry does not exist
         * @throws SQLException
         */
        public String GetUserPassword(String username) throws SQLException {
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
        public Integer GetUserAccess(String username) throws SQLException {
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
        public byte[] GetUserSalt(String username) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
            if (result.next()) {
                return result.getBytes("salt");
            } else {
                return null;
            }
        }

//        /**
//         * Edits existing user fields; password, access and salt
//         *
//         * @param username Username of the user to edit
//         * @param password New password as string
//         * @param access new access level as integer
//         * @param salt new salt as byte array
//         * @return boolean value true if operation was successful else false
//         * @throws SQLException
//         */
//        public boolean EditUser(String username, String password, Integer access, byte[] salt) throws SQLException {
//            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
//            if (result.next()) {
//                if (password != null)
//                    statement.executeQuery("UPDATE users SET password='" + password + "' WHERE username='" + username + "';");
//                if (access != null)
//                    statement.executeQuery("UPDATE users SET access='" + access + "' WHERE username='" + username + "';");
//                if (salt != null)
//                    statement.executeQuery("UPDATE users SET salt='" + salt + "' WHERE username='" + username + "';");
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        /**
//         * Edits existing user password field
//         *
//         * @param username Username of the user to edit
//         * @param password New password as string
//         * @return boolean value true if operation was successful else false
//         * @throws SQLException
//         */
//        public boolean EditUser(String username, String password) throws SQLException {
//            return EditUser(username, password, null, null);
//        }
//
//        /**
//         * Edits existing user access field
//         *
//         * @param username Username of the user to edit
//         * @param access new access level as integer
//         * @return boolean value true if operation was successful else false
//         * @throws SQLException
//         */
//        public boolean EditUser(String username, Integer access) throws SQLException {
//            return EditUser(username, null, access, null);
//        }
//
//        /**
//         * Edits existing user salt field
//         *
//         * @param username Username of the user to edit
//         * @param salt new salt as byte array
//         * @return boolean value true if operation was successful else false
//         * @throws SQLException
//         */
//        public boolean EditUser(String username, byte[] salt) throws SQLException {
//            return EditUser(username, null, null, salt);
//        }
//
//        /**
//         * Edits existing user fields; access and salt
//         *
//         * @param username Username of the user to edit
//         * @param access new access level as integer
//         * @param salt new salt as byte array
//         * @return boolean value true if operation was successful else false
//         * @throws SQLException
//         */
//        public boolean EditUser(String username, Integer access, byte[] salt) throws SQLException {
//            return EditUser(username, null, access, salt);
//        }
//
//        /**
//         * Edits existing user fields; password and salt
//         *
//         * @param username Username of the user to edit
//         * @param password New password as string
//         * @param salt new salt as byte array
//         * @return boolean value true if operation was successful else false
//         * @throws SQLException
//         */
//        public boolean EditUser(String username, String password, byte[] salt) throws SQLException {
//            return EditUser(username, password, null, salt);
//        }
//
//        /**
//         * Edits existing user fields; password and access
//         *
//         * @param username Username of the user to edit
//         * @param password New password as string
//         * @param access new access level as integer
//         * @return boolean value true if operation was successful else false
//         * @throws SQLException
//         */
//        public boolean EditUser(String username, String password, Integer access) throws SQLException {
//            return EditUser(username, password, access, null);
//        }

        /**
         * Deletes existing user field
         *
         * @param username Username of the user to delete
         * @return boolean value true if operation was successful else false
         * @throws SQLException
         */
        public boolean DeleteUser(String username) throws SQLException {
            if (CheckForUser(username)) {
                statement.executeQuery("DELETE FROM users WHERE username='" + username + "';");
                if (CheckForUser(username)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public class Billboards {

        /**
         * Creates a new billboards table
         *
         * @throws SQLException
         */
        private void CreateBillboardsTable() throws SQLException {
            statement.executeQuery("CREATE TABLE billboards (billboardName VARCHAR(64), msg VARCHAR(64), info VARCHAR(64), picURL VARCHAR(64), picDATA BLOB(MAX), msgColour VARCHAR(64), backColour VARCHAR(64), infoColour VARCHAR(64) );");
            Log.Confirmation("Table created: billboards");
        }

        /**
         * Adds a Billboard to the Billboard table as long as it does not already exist
         * @param billboardName The name of the billboard to be stored
         * @param msg the billboard message of the entry as String
         * @param info billboard information of the entry as String
         * @param picURL picture URL of the entry as a String
         * @param picDATA picture data of the entry as a Byte
         * @param msgColour Colour of the message of the entry as a String
         * @param backColour Colour of the background of the entry as a String
         * @param infoColour information colour of the entry as a String
         * @return true if the entry is successful and false if an entry already exists with the same billboard name
         * @throws SQLException
         */

        public boolean addBillboardName(String billboardName, String msg, String info, String picURL, byte[] picDATA, String msgColour, String backColour, String infoColour ) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE billboardName = '" + billboardName + "';");
            if (result.next()) {
                return false;
            } else {
                statement.executeQuery("INSERT INTO billboards VALUES ('" + billboardName + "', '" + msg + "', '" + info + "', '" + picURL + "', '" + picDATA + "', '" + msgColour + "', '" + backColour + "', '" + infoColour + "');");
                return true;
            }
        }

        /**
         * Edits existing user fields; billboardName, msg, info, picURL, msgColour, backColour, infoColour
         *
         * @param billboardName to edit by user
         * @param msg to edit by user
         * @param info to edit by user
         * @param picURL to edit by user
         * @param backColour to edit by user
         * @return infoColour to edit by user
         * @return boolean value true if operation was successful else false
         * @throws SQLException
         */

        public boolean EditBillboardName(String billboardName, String msg, String info, String picURL, byte[] picDATA, String msgColour, String backColour, String infoColour) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE billboardName = '" + billboardName + "';");
            if (result.next()) {
                if (billboardName != null)
                    statement.executeQuery("UPDATE billboards SET billboardName ='" + billboardName + "'WHERE billboardName='" + billboardName + "';'");
                if (msg != null)
                    statement.executeQuery("UPDATE billboards SET msg ='" + msg + "' WHERE billboardName='" + billboardName + "';");
                if (info != null)
                    statement.executeQuery("UPDATE billboards SET info ='" + info + "' WHERE billboardName='" + billboardName + "';");
                if (picURL != null)
                    statement.executeQuery("UPDATE billboards SET picURL ='" + picURL + "' WHERE billboardName='" + billboardName + "';");
               // if (picDATA != null)
                  //  statement.executeQuery("UPDATE Billboards SET picDATA ='" + picDATA + "' WHERE billboardName='" + billboardName + "';");
                if (msgColour != null)
                    statement.executeQuery("UPDATE billboards SET msgColour ='" + msgColour + "' WHERE billboardName='" + billboardName + "';");
                if (backColour != null)
                    statement.executeQuery("UPDATE billboards SET backColour ='" + backColour + "' WHERE billboardName='" + billboardName + "';");
                if (infoColour != null)
                    statement.executeQuery("UPDATE billboards SET infoColour ='" + infoColour + "' WHERE billboardName='" + billboardName + "';");
                return true;

            } else {
                return false;
            }
        }

        public String Getmsg(String billboardName, String msg) throws SQLException {
            ResultSet result = statement.executeQuery("SELECT * FROM billboards WHERE billboardName = '" + billboardName + "';");
            if (result.next()) {
                return result.getString("msg");
            } else {
                return null;
            }
        }




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
