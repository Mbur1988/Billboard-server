package SerializableObjects;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    // Declare class variables
    private boolean verified;
    private UUID id;
    private String username;
    private String password;
    private int access;

    /**
     * Class constructor that sets variables to default values
     */
    public User() {
        this.verified = false;
        this.id = null;
        this.username = "";
        this.password = "";
        this.access = 0;
    }

    /**
     * Class constructor that sets username and password. all other variables set to default values
     * @param username the username as a string
     * @param password the password as a string
     */
    public User(String username, String password) {
        this.verified = false;
        this.id = null;
        this.username = username;
        this.password = password;
        this.access = 0;
    }

    /**
     * Returns a boolean value to indicate if the user has been verified by the server
     * @return Whether the user is verified
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * sets the boolean value that indicates if the user has been verified by the server
     * @param verified Whether the user is verified
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * Returns the universally unique identifier assigned to the user when verified by the server. Default value null
     * @return The users UUID if verified
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the users universally unique identifier when verified by the server
     * @param id The users UUID
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the username as a string
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password as a string
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the access level of the user as an integer
     * @return access level
     */
    public int getAccess() {
        return access;
    }

    /**
     * Sets the access level of the user as an integer
     * @param access access level
     */
    public void setAccess(int access) {
        this.access = access;
    }

    /**
     * A test method used to print the users credentials to the console
     */
    public void showDetails()
    {
        System.out.println("Verified:"+verified);
        System.out.println("Id:"+id);
        System.out.println("Username:"+username);
        System.out.println("Password:"+password);
        System.out.println("Access:"+access);
    }
}