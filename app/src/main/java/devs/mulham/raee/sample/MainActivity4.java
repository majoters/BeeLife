package devs.mulham.raee.sample;

import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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
    ListView listView ;
    public static String a = new String();
    static ArrayList<String> values = new ArrayList<>();
    static ArrayList<String> values_filter = new ArrayList<>();
    static ArrayList<List_Database> databases = new ArrayList<>();
    public  static Date listdate ;
    public static Address location=null;
    static int id_date=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final ArrayList<String> a = new ArrayList<>();
        /** end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 12);

        /** start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, -7);

        final Calendar defaultDate = Calendar.getInstance();
        defaultDate.add(Calendar.MONTH, -1);
        defaultDate.add(Calendar.DAY_OF_WEEK, +5);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values_filter);
        listView.setAdapter(adapter);

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
                Toast.makeText(MainActivity4.this, DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
                Log.d("Selected Item: ", DateFormat.getDateInstance().format(date));
                listdate=horizontalCalendar.getSelectedDate();
                values_filter.clear();
                int key= MainActivity4.listdate.getDate()*10000+(MainActivity4.listdate.getMonth()+1)*100+ MainActivity4.listdate.getYear()%100;
                for(int i=0;i<databases.size();i++){
                    for(int j=i+1;j<databases.size();j++){
                        if(databases.get(i).getTime()>databases.get(j).getTime()&&i!=j){
                            int swap = databases.get(i).getTime();
                            String swap2 = values.get(i);
                            databases.get(i).setTime(databases.get(j).getTime());
                            databases.get(j).setTime(swap);
                            values.set(i,values.get(j));
                            values.set(j,swap2);

                        }
                    }
                }
                for(int i=0; i < values.size();i++){
                    if(databases.get(i).getID()== key){
                        values_filter.add(values.get(i));
                        id_date=databases.get(i).getID();
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalCalendar.goToday(true);
                //values.add(0,horizontalCalendar.getSelectedDate().toString());
               // Intent map =new Intent(MainActivity4.this,MapsActivity.class);
                //MainActivity4.this.startActivity(map);
                CustomDialogClass cdd = new CustomDialogClass(MainActivity4.this);
                cdd.show();

            }
        });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               //Intent a = new Intent(MainActivity4.this,MapsActivity.class);
               //MainActivity4.this.startActivity(a);
               TextView txt6 = (TextView)findViewById(R.id.textView6);
               List_Database dataset=null ;
               int Time=0,Time2=0,J=0;
               int mul=100;
               for(int k=0;k<3;J++){
                   if(values_filter.get(i).charAt(J)==':'){
                       k++;
                   }
               }
               txt6.setText(values_filter.get(i).charAt(J));

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

}
