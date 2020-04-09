package Handlers;

import Tools.Log;
import Tools.PropertyReader;

import java.io.IOException;
import java.sql.*;

import static java.lang.System.exit;

public class MariaDB {

    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

    private String url, schema, username, password;
    private Connection connection;
    private Statement statement;

    /**
     * MariaDB class constructor
     * Sets network configuration variables; url, schema, username and password
     */
    public MariaDB() {
        SetNetworkConfig();
    }

    /**
     * Returns the JDBC driver used by MariaDB class
     * @return JDBC driver as string
     */
    public static String getJdbcDriver() {
        return JDBC_DRIVER;
    }

    /**
     * Sets the URL to be used by the MariaDB class instance
     * @param url URL as string
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the URL used by the MariaDB class instance
     * @return URL as string
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the schema to be used by the MariaDB class instance
     * @param schema
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Gets the schema used by the MariaDB class instance
     * @return Schema as string
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the userName to be used by the MariaDB class instance
     * @param userName
     */
    public void setUsername(String userName) {
        this.username = userName;
    }

    /**
     * Gets the userName used by the MariaDB class instance
     * @return Username as string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the password to be used by the MariaDB class instance
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password used by the MariaDB class instance
     * @return Password as string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the connection to be used by the MariaDB class instance
     * @param connection
     */
    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the connection used by the MariaDB class instance
     * @return Connection
     */
    private Connection getConnection() {
        return connection;
    }

    /**
     * Sets the statement to be used by the MariaDB class instance
     * @param statement
     */
    private void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * Gets the statement used by the MariaDB class instance
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
            Class.forName(JDBC_DRIVER);
            // create full url string for connection in the form:
            // jdbc:mysql://<HOST>:<PORT>/<DATABASE_NAME>
            String fullURL = url + "/" + schema;
            connection = DriverManager.getConnection(fullURL, username, password);
            statement = connection.createStatement();
            Log.Confirmation("Database connection established");
        }
        catch (ClassNotFoundException | SQLException e) {
            Log.Error("Database connection failed");
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
