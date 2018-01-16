package devs.mulham.raee.sample;

import android.location.Address;

import java.util.ArrayList;

/**
 * Created by jameswich on 28/11/2560.
 */

public class List_Database {

    private int Date;
    private int Time ;
    private String Description;
    private String locationName;
    private Address location;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List_Database(int Date,int Time,String Description,Address location,String locationName){
        this.Date=Date;
        this.Time=Time;
        this.Description=Description;
        this.location=location;
        this.locationName=locationName;
    }


    public int getDate(){return Date;}

    public void setDate(int Date){this.Date = Date;}

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }
}
