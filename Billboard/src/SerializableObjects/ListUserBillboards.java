package SerializableObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ListUserBillboards implements Serializable {

    public static ArrayList<String> userBillboards;

    public ListUserBillboards() {
        this.userBillboards = new ArrayList<>();
    }

    public ListUserBillboards(ArrayList<String> userBillboards) {
        this.userBillboards = userBillboards;
    }

    /**
     * adds an item to the specified list and sorts the list alphabetically
     * @param name the string to add to the list
     */
    public static void sortAdd(String name) {
        userBillboards.add(name);
        Collections.sort(userBillboards);
    }
}
