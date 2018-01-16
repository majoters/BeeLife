package devs.mulham.raee.sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.kmitl57.beelife.MainActivity;
import com.project.kmitl57.beelife.R;

import com.google.android.gms.maps.GoogleMap;

import java.sql.Time;
import static devs.mulham.raee.sample.MainActivity4.ActivityLocation;
import static devs.mulham.raee.sample.MainActivity4.ActivityName;
import static devs.mulham.raee.sample.MainActivity4.ActivityTime;
import static devs.mulham.raee.sample.MainActivity4.databases;
import static devs.mulham.raee.sample.MainActivity4.listdate;

/**
 * Created by jameswich on 27/11/2560.
 */

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    List_Database list_database;
    int index;
    public Button yes, no;
    public int i=0;
    private GoogleMap map;
    public static EditText locate;
    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
        list_database=null;
    }

    public CustomDialogClass(Activity a,List_Database list_database,int i) {
        super(a);
        this.c = a;
        this.list_database=list_database;
        this.index=i;
    }

    public void show(List_Database database,int Time){
        EditText description = (EditText)findViewById(R.id.Description);
        //EditText Hours = (EditText)findViewById(R.id.Hour);
        //EditText Minutes = (EditText)findViewById(R.id.Minute);
        final TimePicker simpleTimePicker = (TimePicker) findViewById(R.id.timepicker1);
        simpleTimePicker.setIs24HourView(true);
        locate = (EditText)findViewById(R.id.Location);
        description.setText(database.getDescription());

        //Hours.setText(String.valueOf(Time/100));
        //Minutes.setText(String.valueOf(Time%100));
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        final EditText description = (EditText)findViewById(R.id.Description);
        //final EditText Hours = (EditText)findViewById(R.id.Hour);
        //final EditText Minutes = (EditText)findViewById(R.id.Minute);
        final TimePicker simpleTimePicker = (TimePicker)findViewById(R.id.timepicker1);
        simpleTimePicker.setIs24HourView(true);
        locate = (EditText)findViewById(R.id.Location);
        Button OK = (Button)findViewById(R.id.OK);
        Button Cancel = (Button)findViewById(R.id.Cancel);
        Button search = (Button)findViewById(R.id.Search);
        if(list_database!=null){
            description.setText(list_database.getDescription());
            simpleTimePicker.setCurrentHour(list_database.getTime()/100);
            simpleTimePicker.setCurrentMinute(list_database.getTime()%100);
            locate.setText(list_database.getLocationName());
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.Search = locate.getText().toString();
                Intent map = new Intent(c,MapsActivity.class);
                c.startActivities(new Intent[]{map});
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //Set Variable For set a list
                    String Hours = String.valueOf(simpleTimePicker.getCurrentHour());
                    String Minutes = String.valueOf(simpleTimePicker.getCurrentMinute());
                    int key_date = MainActivity4.listdate;
                    int hour = Integer.parseInt(Hours);
                    int minute = Integer.parseInt(Minutes);
                    int key_time = hour * 100 + minute;
                    String hr, min = "";
                    String locationName = locate.getText().toString();

                    //Perform Activity list
                    if (Integer.parseInt(Hours) < 10) {
                        hr = "0" + String.valueOf(hour);
                    } else {
                        hr = String.valueOf(hour);
                    }
                    if (Integer.parseInt(Minutes) < 10) {
                        min = "0" + String.valueOf(minute);
                    } else {
                        min = String.valueOf(minute);
                    }

                    //Add if not have yet and Update if it is a exist data
                    if(list_database==null) {
                        ActivityName.add(description.getText().toString());
                        ActivityTime.add(hr + ":" + min);
                        ActivityLocation.add(locate.getText().toString());
                        MainActivity4.databases.add(0, new List_Database(key_date, key_time, description.getText().toString(), MainActivity4.location, locationName));
                        MainActivity4.mDbAdabter_Model.createActivityList(new List_Database(key_date, key_time, description.getText().toString(), MainActivity4.location, locationName));
                    }
                    else{
                        ActivityName.set(index,description.getText().toString());
                        ActivityTime.set(index,hr+":"+min);
                        ActivityLocation.set(index,locate.getText().toString());

                        List_Database list = new List_Database(key_date, key_time, description.getText().toString(), list_database.getLocation(), locate.getText().toString());
                        MainActivity4.databases.set(SearchIndex(list_database),list);
                        MainActivity4.mDbAdabter_Model.UpdateList(list_database,list);
                    }

                    //Add Data to Database
                    cancel();

                    //Set Update to list
                    final CustomListView adapter = new CustomListView(getContext(), MainActivity4.ActivityTime, MainActivity4.ActivityName, MainActivity4.ActivityLocation);
                    MainActivity4.listView.setAdapter(adapter);
            }
        });

    }


    @Override
    public void onClick(View view) {

    }

    public int SearchIndex(List_Database list_database){
        for(int i=0;i<MainActivity4.databases.size();i++){
            if(list_database==MainActivity4.databases.get(i))
                return i;
        }
        return -1;
    }
}
