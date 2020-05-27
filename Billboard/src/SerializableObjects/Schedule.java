package SerializableObjects;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule implements Serializable {
    private String ScheduleName;
    private String BillboardName;
    private LocalDate date;
    private LocalTime time;
    private long duration;


    public Schedule(){
        this.ScheduleName = null;
        this.BillboardName= null;
        this.date= null;
        this.time= null;
        this.duration= 0;
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
    public long getDuration() {
        return duration;
    }
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
    public void setDuration(long duration) {
        this.duration = duration;
    }



}
