package SerializableObjects;

import java.io.Serializable;
import java.util.ArrayList;

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
}
