package Handlers;

import Tools.Log;
import Tools.PropertyReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

import static java.lang.System.exit;

public class MariaDB {

    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

    private String url, schema, username,password;
    private Connection connection;
    private Statement statement;

    public MariaDB() {
        SetNetworkConfig();
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            Log.Confirmation("Database connection established");
        } catch (Exception e) {
            Log.Error("Database connection failed");
            // if database connection fails then end program
            exit(0);
        }
    }

    public static String getJdbcDriver() {
        return JDBC_DRIVER;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }

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
}
