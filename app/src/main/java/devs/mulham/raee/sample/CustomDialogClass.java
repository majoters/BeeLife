package devs.mulham.raee.sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.project.kmitl57.beelife.R;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jameswich on 27/11/2560.
 */

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private GoogleMap map;
    public static EditText locate;
    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
    }

    public void show(List_Database database,int Time){
        EditText description = (EditText)findViewById(R.id.Description);
        EditText Hours = (EditText)findViewById(R.id.Hour);
        EditText Minutes = (EditText)findViewById(R.id.Minute);
        locate = (EditText)findViewById(R.id.Location);
        description.setText(database.getDescription());
        Hours.setText(String.valueOf(Time/100));
        Minutes.setText(String.valueOf(Time%100));
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        final EditText description = (EditText)findViewById(R.id.Description);
        final EditText Hours = (EditText)findViewById(R.id.Hour);
        final EditText Minutes = (EditText)findViewById(R.id.Minute);
        locate = (EditText)findViewById(R.id.Location);
        Button OK = (Button)findViewById(R.id.OK);
        Button search = (Button)findViewById(R.id.Search);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.Search = locate.getText().toString();
                Intent map = new Intent(c,MapsActivity.class);
                c.startActivities(new Intent[]{map});
            }
        });

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int key_date= MainActivity4.listdate.getDate()*10000+(MainActivity4.listdate.getMonth()+1)*100+ MainActivity4.listdate.getYear()%100;
                int hour = Integer.parseInt(Hours.getText().toString());
                int minute = Integer.parseInt(Minutes.getText().toString());
                int key_time=hour*100+minute;
                String locationName = locate.getText().toString();
                MapsActivity.Search = locationName;
                String val = "Description : "+description.getText().toString()+"\nTime : "+String.valueOf(hour)+":"+String.valueOf(minute)+"\n Latitue : "+ MainActivity4.location.getLatitude()+" Longtitude : "+ MainActivity4.location.getLongitude();
                MainActivity4.values.add(0,val);
                MainActivity4.databases.add(0,new List_Database(key_date,key_time,description.getText().toString(), MainActivity4.location,locationName));
                cancel();
            }
        });

    }


    @Override
    public void onClick(View view) {

    }
}
