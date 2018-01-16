package devs.mulham.raee.sample;

import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.project.kmitl57.beelife.R;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class MainActivity4 extends AppCompatActivity {

    private HorizontalCalendar horizontalCalendar;
    private ActivityDbAdapter mDbAdapter;
    public static ActivityDbAdapter mDbAdabter_Model;
    public static ListView listView ;
    public static String a = new String();
    public static ArrayList<String> ActivityName = new ArrayList<>();
    public static ArrayList<String> ActivityTime = new ArrayList<>();
    public static ArrayList<String> ActivityLocation = new ArrayList<>();
    static ArrayList<List_Database> values_filter = new ArrayList<>();
    static ArrayList<List_Database> databases = new ArrayList<>();
    public  static int listdate ;
    public static Address location=null;
    static int id_date=0;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        txt=(TextView)findViewById(R.id.textView6);
        /** end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 12);

        /** start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, -7);

        final Calendar defaultDate = Calendar.getInstance();
        defaultDate.add(Calendar.MONTH, -1);
        defaultDate.add(Calendar.DAY_OF_WEEK, +5);

        final CustomListView adapter = new CustomListView(getApplicationContext(),ActivityTime,ActivityName,ActivityLocation);
        listView.setAdapter(adapter);

        //Create Database
        mDbAdapter = new ActivityDbAdapter(getApplicationContext());
        mDbAdapter.open();
        mDbAdabter_Model=mDbAdapter;

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .showDayName(true)
                .showMonthName(true)
                .selectedDateBackground(ContextCompat.getDrawable(this, R.drawable.sample_selected_background))
                .defaultSelectedDate(defaultDate.getTime())
                .textColor(Color.LTGRAY, Color.WHITE)
                .build();
        horizontalCalendar.goToday(false);
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                //Toast.makeText(MainActivity4.this, DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
                Log.d("Selected Item: ", DateFormat.getDateInstance().format(date));
                listdate=date.getDate()*10000+(date.getMonth()+1)*100+ date.getYear()%100;
                values_filter.clear();
                ActivityLocation.clear();
                ActivityName.clear();
                ActivityTime.clear();
                adapter.notifyDataSetChanged();

                if(mDbAdapter.fecthAllList()!=null){
                    databases=mDbAdapter.fecthAllList();
                }

                for(int i=0;i<databases.size();i++){
                    if(databases.get(i).getDate()==listdate){
                        values_filter.add(databases.get(i));
                    }
                }

                for(int i=0;i<values_filter.size();i++){
                    for(int j=0;j<values_filter.size()-1;j++){
                        if(values_filter.get(j).getTime()>values_filter.get(j+1).getTime()){
                            List_Database swap=values_filter.get(j);
                            values_filter.set(j,values_filter.get(j+1));
                            values_filter.set(j+1,swap);
                        }
                    }
                }

                for(int i=0;i<values_filter.size();i++){
                    String time="";
                    if(values_filter.get(i).getTime()/100<10)
                        time+="0"+String.valueOf(values_filter.get(i).getTime()/100)+":";
                    else
                        time+=String.valueOf(values_filter.get(i).getTime()/100)+":";
                    if(values_filter.get(i).getTime()%100<10)
                        time+="0"+String.valueOf(values_filter.get(i).getTime()%100);
                    else
                        time+=String.valueOf(values_filter.get(i).getTime()%100);
                    ActivityTime.add(time);
                    ActivityName.add(values_filter.get(i).getDescription());
                    ActivityLocation.add(values_filter.get(i).getLocationName());
                }
                final CustomListView adapter = new CustomListView(getApplicationContext(),ActivityTime,ActivityName,ActivityLocation);
                listView.setAdapter(adapter);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass cdd = new CustomDialogClass(MainActivity4.this);
                cdd.show();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                mDbAdapter.DeleteList(mDbAdapter.ListToID(values_filter.get(position)));
                                //mDbAdabter_Model.DeleteList();
                                txt.setText(String.valueOf(mDbAdapter.NumberOfList()));
                                Toast.makeText(getApplicationContext(),String.valueOf(values_filter.get(position)
                                        .getDescription()),Toast.LENGTH_LONG).show();
                                databases.remove(SearchIndex(values_filter.get(position)));
                                values_filter.remove(position);
                                ActivityLocation.remove(position);
                                ActivityName.remove(position);
                                ActivityTime.remove(position);
                                final CustomListView adapter = new CustomListView(getApplicationContext(),ActivityTime,ActivityName,ActivityLocation);
                                listView.setAdapter(adapter);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do your No progress
                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity4.this);
                ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;
            }
        });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /*TextView txt6 = (TextView)findViewById(R.id.textView6);
               txt6.setText(ActivityTime.get(0));
               /*CustomDialogClass cdd = new CustomDialogClass(MainActivity4.this,values_filter.get(i),i);
               cdd.show();*/
               ShowActivity cdd = new ShowActivity(MainActivity4.this,values_filter.get(i),i);
               cdd.show();

           }
       });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int SearchIndex(List_Database list_database){
        for(int i=0;i<MainActivity4.databases.size();i++){
            if(list_database==MainActivity4.databases.get(i))
                return i;
        }
        return -1;
    }

}
