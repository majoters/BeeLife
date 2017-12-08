package devs.mulham.raee.sample;

import android.location.Address;

import java.util.ArrayList;

/**
 * Created by jameswich on 28/11/2560.
 */

public class List_Database {

    private int ID;
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

    public List_Database(int ID,int Time,String Description,Address location,String locationName){
        this.ID=ID;
        this.Time=Time;
        this.Description=Description;
        this.location=location;
        this.locationName=locationName;
    }


    public int getID(){return ID;}

    public void setID(int id){ID = id;}

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
