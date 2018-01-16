package devs.mulham.raee.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jameswich on 25/12/2560.
 */

public class ActivityDbAdapter {

    public static final String COL_ID="id";
    public static final String COL_DATE="date";
    public static final String COL_TIME="time";
    public static final String COL_DESCRIPTION="description";
    public static final String COL_LOCATION="location";

    public static int INDEX_ID=0;
    public static int INDEX_DATE=INDEX_ID+1;
    public static int INDEX_TIME=INDEX_ID+2;
    public static int INDEX_DESCRIPTION=INDEX_ID+3;
    public static int INDEX_LOCATION=INDEX_ID+4;

    private static final String TAG="ActivityListDatabase";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME="AcitivityList_Database";
    private static final String TABLE_NAME="Table_ActivityList";
    private static final int DATABASE_VERSION=1;

    private final Context mCtx;

    private static final String DATABASE_CREATE=
            "CREATE TABLE if not exists "+ TABLE_NAME+" ( "+
                    COL_ID+" INTEGER PRIMARY KEY autoincrement, "+
                    COL_DATE+" INTEGER, "+
                    COL_TIME+" INTEGER, "+
                    COL_DESCRIPTION+" TEXT, "+
                    COL_LOCATION+" TEXT );";

    public ActivityDbAdapter(Context ctx){
        this.mCtx=ctx;
    }

    public void open() throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close(){
        if(mDbHelper != null){
            mDbHelper.close();
        }
    }

    public void createActivityList(List_Database list_database){
        ContentValues values = new ContentValues();
        values.put(COL_DATE,list_database.getDate());
        values.put(COL_TIME,list_database.getTime());
        values.put(COL_DESCRIPTION,list_database.getDescription());
        values.put(COL_LOCATION,list_database.getLocationName());

        mDb.insert(TABLE_NAME,null,values);
    }

    public int NumberOfList(){
        Cursor cursor= mDb.query(TABLE_NAME, new String[]{COL_ID,
        COL_DATE,COL_TIME,COL_DESCRIPTION,COL_LOCATION},null,null,null,null,null);

        return cursor.getCount();
    }

    public ArrayList<List_Database> fecthAllList(){
        Cursor cursor=mDb.query(TABLE_NAME, new String[]{
                COL_ID,COL_DATE,COL_TIME,COL_DESCRIPTION,COL_LOCATION},null,null,null,null,null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();


            ArrayList<List_Database> List = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                List_Database list = new List_Database(cursor.getInt(INDEX_DATE), cursor.getInt(INDEX_TIME),
                        cursor.getString(INDEX_DESCRIPTION), null, cursor.getString(INDEX_LOCATION));
                List.add(list);
                cursor.moveToNext();
            }

            return List;
        }
        else{
            return null;
        }

    }
    public int SearchList(List_Database list_database){
        ArrayList<List_Database> List = new ArrayList<>();
        List=fecthAllList();

        for(int i=0;i<List.size();i++){
            if(List.get(i).getDate()==list_database.getDate()
                    &&List.get(i).getTime()==list_database.getTime()
                    &&List.get(i).getDescription().compareTo(list_database.getDescription())==0
                    &&List.get(i).getLocationName().compareTo(list_database.getLocationName())==0)
                return i+1;
        }
        return -1;
    }

    public void UpdateList(List_Database listDatabase,List_Database list_database){
        int ID = ListToID(listDatabase);
        ContentValues values = new ContentValues();
        values.put(COL_DATE,list_database.getDate());
        values.put(COL_TIME,list_database.getTime());
        values.put(COL_DESCRIPTION,list_database.getDescription());
        values.put(COL_LOCATION,list_database.getLocationName());
        mDb.update(TABLE_NAME, values,
                COL_ID + "=?",
                new String[]{String.valueOf(ID)});
    }

    public void DeleteList(int n){
        String sql = "DELETE FROM "+TABLE_NAME+" WHERE id = "+String.valueOf(n);
        mDb.execSQL(sql);
        //mDb.delete(TABLE_NAME,COL_ID+"=?",new String[]{String.valueOf(n)});
    }

    public List_Database fetchByID(int id){
        Cursor cursor=mDb.query(TABLE_NAME, new String[]{COL_ID,
        COL_DATE,COL_TIME,COL_DESCRIPTION,COL_DESCRIPTION},COL_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        return new List_Database(cursor.getInt(INDEX_DATE),
                cursor.getInt(INDEX_TIME),cursor.getString(INDEX_DESCRIPTION),null,
                cursor.getString(INDEX_LOCATION));

    }

    public int ListToID(List_Database list_database){
        Cursor cursor =mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_DATE,COL_TIME,COL_DESCRIPTION,COL_DESCRIPTION},COL_DATE+"=?",
                new String[]{String.valueOf(list_database.getDate())},null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                if(cursor.getInt(INDEX_DATE)==list_database.getDate()){
                    if(cursor.getInt(INDEX_TIME)==list_database.getTime()){
                        if(cursor.getString(INDEX_DESCRIPTION).compareTo(list_database.getDescription())==0){
                            return cursor.getInt(INDEX_ID);
                        }
                    }
                }
                cursor.moveToNext();
            }
        }
        return -1;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG,DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG,"Upgrading database from version "+oldVersion+" to "
            +newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }
    }


}
