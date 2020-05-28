package SerializableObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ListUsers implements Serializable {

    public static ArrayList<String> users;

    public ListUsers() {
        this.users = new ArrayList<>();
    }

    public ListUsers(ArrayList<String> userBillboards) {
        this.users = userBillboards;
    }

    /**
     * adds an item to the specified list and sorts the list alphabetically
     * @param name the string to add to the list
     */
    public static void sortAdd(String name) {
        users.add(name);
        Collections.sort(users);
    }
}
