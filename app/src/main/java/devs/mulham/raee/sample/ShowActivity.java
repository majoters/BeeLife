package devs.mulham.raee.sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.project.kmitl57.beelife.R;

/**
 * Created by hotmildc on 16/1/2561.
 */

public class ShowActivity extends Dialog implements android.view.View.OnClickListener{

    Activity activity;
    List_Database listDatabase;
    int n;

    public ShowActivity(Activity a,List_Database list_database,int i) {
        super(a);
        activity = a;
        listDatabase=list_database;
        n=i;
    }



    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showactivity);

        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass cdd = new CustomDialogClass(activity,listDatabase,n);
                cdd.show();
                cancel();
            }
        });

    }
}
