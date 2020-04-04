package SerializableObjects;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private boolean verified;
    private UUID id;
    private String username;
    private String password;
    private int access;

    public User() {
        this.verified = false;
        this.id = null;
        this.username = "";
        this.password = "";
        this.access = 0;
    }

    public User(String username, String password) {
        this.verified = false;
        this.id = null;
        this.username = username;
        this.password = password;
        this.access = 0;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public void showDetails()
    {
        System.out.println("Verified:"+verified);
        System.out.println("Id:"+id);
        System.out.println("Username:"+username);
        System.out.println("Password:"+password);
        System.out.println("Access:"+access);
    }
}