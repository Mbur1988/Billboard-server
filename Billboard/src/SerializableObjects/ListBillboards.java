package SerializableObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ListBillboards implements Serializable {

    public static ArrayList<String> billboards;

    public ListBillboards() {
        this.billboards = new ArrayList<>();
    }

    public ListBillboards(ArrayList<String> userBillboards) {
        this.billboards = userBillboards;
    }

    /**
     * adds an item to the specified list and sorts the list alphabetically
     * @param name the string to add to the list
     */
    public static void sortAdd(String name) {
        billboards.add(name);
        Collections.sort(billboards);
    }
}
