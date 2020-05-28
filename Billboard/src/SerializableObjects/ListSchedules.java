package SerializableObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ListSchedules implements Serializable {

    public static ArrayList<String> schedules;

    public ListSchedules() {
        this.schedules = new ArrayList<>();
    }

    public ListSchedules(ArrayList<String> userBillboards) {
        this.schedules = userBillboards;
    }

    /**
     * adds an item to the specified list and sorts the list alphabetically
     * @param name the string to add to the list
     */
    public static void sortAdd(String name) {
        schedules.add(name);
        Collections.sort(schedules);
    }
}
