package SerializableObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import static Clients.ControlPanel.ControlPanel.lists;

public class Lists implements Serializable {

    public ArrayList<String> users;
    public ArrayList<String> billboards;
    public ArrayList<String> userBillboards;
    public ArrayList<String> schedules;

    public Lists(ArrayList<String> users, ArrayList<String> billboards, ArrayList<String> userBillboards, ArrayList<String> schedules) {
        this.users = users;
        this.billboards = billboards;
        this.userBillboards = userBillboards;
        this.schedules = schedules;
    }

    /**
     * adds an item to the specified list and sorts the list alphabetically
     * @param list the list to add to
     * @param name the string to add to the list
     */
    public static void sortAdd(ArrayList<String> list, String name) {
        list.add(name);
        Collections.sort(list);
    }
}
