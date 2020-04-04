package SerializableObjects;

import java.io.Serializable;

public class TestObject implements Serializable
{
    int id;
    String username;
    String password;

    public TestObject(int id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public void showDetails()
    {
        System.out.println("Id:"+id);
        System.out.println("Username:"+username);
        System.out.println("Password:"+password);
    }
}
