package SerializableObjects;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
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
 *
 *
 */


public class Schedule implements Serializable {
    // Setting the local variables that will be associated with the class
    private String          ScheduleName;
    private String          BillboardName;
    private LocalDate       date;
    private LocalTime       time;
    private Duration        duration;

    //blank constructor
    public Schedule(){
        this.ScheduleName = null;
        this.BillboardName= null;
        this.date=          null;
        this.time=          null;
        this.duration=      null;
    }


    //getter
    public String getScheduleName() {
        return ScheduleName;
    }
    public String getBillboardName() {
        return BillboardName;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public Duration getDuration() {return duration;}
    //setters
    public void setScheduleName(String scheduleName) {
        ScheduleName = scheduleName;
    }
    public void setBillboardName(String billboardName) {
        BillboardName = billboardName;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setDuration(Duration duration) {this.duration = duration;}



}
