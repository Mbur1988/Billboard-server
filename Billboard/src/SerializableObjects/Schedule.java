package SerializableObjects;

import java.io.Serializable;
import java.time.LocalTime;

/**
 *      Schedule
 *      This class is used in other classes to store and create a schedule to be used in the database and to implement the different Billboards at different times for durations.
 *      This class contains -
 *      String - a name for the schedule which is used to identify the schedule
 *      String - Billboard name, used to identify the Billboard  to be displayed
 *      Local Date - Used to get the date and set the date of the Billboard to be displayed
 *      Local Time - Used to set the time accuratly to display the image.
 *      Duration - Used to control the time that the billboard will be displayed.
 */
public class Schedule implements Serializable {
    // Setting the local variables that will be associated with the class
    private String ScheduleName;
    private String BillboardName;
    private String day;
    private LocalTime time;
    private int duration;
    private int recur;

    //blank constructor
    public Schedule(){
        this.ScheduleName = null;
        this.BillboardName = null;
        this.day = null;
        this.time = null;
        this.duration = 0;
        this.recur = 0;
    }

    public Schedule(String ScheduleName, String BillboardName, String day, LocalTime time, int duration, int recur) {
        this.ScheduleName = ScheduleName;
        this.BillboardName = BillboardName;
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.recur = recur;
    }

    //getter
    public String getScheduleName() {
        return ScheduleName;
    }
    public String getBillboardName() {
        return BillboardName;
    }
    public String getDay() {
        return day;
    }
    public LocalTime getTime() {
        return time;
    }
    public int getDuration() {
        return duration;
    }
    public int getRecur() {
        return recur;
    }
    //setters
    public void setScheduleName(String scheduleName) {
        ScheduleName = scheduleName;
    }
    public void setBillboardName(String billboardName) {
        BillboardName = billboardName;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void setDuration(int duration) {this.duration = duration;}
    public void setRecur(int recur) {this.recur = recur;}
}
