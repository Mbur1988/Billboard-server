package SerializableObjects;

import java.io.Serializable;
public class Schedule implements Serializable {
    private String ScheduleName;
    private String BillboardName;
    private String date;
    private String time;
    private int duration;


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
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public int getDuration() {
        return duration;
    }
    //setters
    public void setScheduleName(String scheduleName) {
        ScheduleName = scheduleName;
    }
    public void setBillboardName(String billboardName) {
        BillboardName = billboardName;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }



}
