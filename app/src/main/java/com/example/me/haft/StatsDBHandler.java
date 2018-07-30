package com.example.me.haft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatsDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "statistic.db";
    private static final String STATS_TABLE_NAME = "statistic";
    private static final String INDEX_COLUMN = "tbl_index";
    private static final String DATE_COLUMN = "date";
    private static final String WORKOUTS_COLUMN="workout_indexes";
    private static final String COUNT_COLUMN="count";
    private static final String TIMES_COLUMN="times";
    private static String DB_PATH = "";
    private final Context mContext;
    private SQLiteDatabase mDataBase;


    public StatsDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        Log.d("debug", "StatsDBHandler.java   "+"statsdbhandler created");

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }


    //Creates a empty database on the system and rewrites it with your own database.
    public void createDataBase() throws IOException {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assets
                copyDataBase();
                //Log.d("debug", "WorkoutDBHandler.java   "+"createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the
     * application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        Log.d("debug", "StatsDBHandler.java   "+dbFile + "exist   " + dbFile.exists());
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring byte stream.
     */
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream mOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] mBuffer = new byte[1024];
        int mLength;
        int i = 0;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            i++;
            mOutput.write(mBuffer, 0, mLength);
        }
        Log.d("debug", "StatsDBHandler.java>>"+"i=" + String.valueOf(i));
        //Close the streams
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        //Open the database
        String mPath = DB_PATH + DATABASE_NAME;
        //Log.d("debug", "WorkoutDBHandler.java   "+mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    //TODO:documentation
    public Cursor getTestData() {
        try {
            String sql = "SELECT * FROM " + STATS_TABLE_NAME;

            Cursor mCur = mDataBase.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            Log.e("debug", "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    //logs name of tables in database, including android-metadata and etc
    public int countTables() {

        Cursor c = mDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                Log.d("debug", "WorkoutDBHandler.java   "+"Table Name=> " + c.getString(0));
                c.moveToNext();
            }
        }
        return c.getCount();
    }

    //search in workout table with index. gets first cursor result and sets the name and
    //index of Workout to be returned.
    // used for finding workout items in learnPageFragment.
    public int getCircuitsCount(Date date) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String strDate=dateFormat.format(date);
        Log.d("debug","statsDBHandler.java>>recieved date="+strDate);
        try {
            String query = "Select "+COUNT_COLUMN+" FROM " + STATS_TABLE_NAME + " WHERE " +
                    DATE_COLUMN + "= '" + strDate + "'";

            Cursor cursor = mDataBase.rawQuery(query, null);
            int count=0;
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                count=cursor.getInt(0);
                cursor.close();
            }
            //mDataBase.close();
            return count;
        } catch (SQLException mSQLException) {
            Log.d("debug", "StatsDBHandler.java>>"+"findhandler >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public void addStats(Date date,int count,String workouts) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String strDate=dateFormat.format(date);
        ContentValues values=new ContentValues();
        try {
            String query = "Select * FROM " + STATS_TABLE_NAME + " WHERE " +
                    DATE_COLUMN + "= '" + strDate + "'";
            Cursor cursor = mDataBase.rawQuery(query, null);

            Log.d("debug","circuits="+getCircuitsCount(date));

            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                values.put(WORKOUTS_COLUMN,cursor.getString(2)+workouts);
                values.put(COUNT_COLUMN,cursor.getInt(3)+count);
                mDataBase.update(STATS_TABLE_NAME,values,
                        "where "+INDEX_COLUMN+"= "+cursor.getInt(1),null);
                cursor.close();
                Log.d("debug","updatedstats"+getCircuitsCount(date));
            }
            //mDataBase.close();

        } catch (SQLException mSQLException) {
            Log.d("debug", "StatsDBHandler.java>>"+"findhandler >>"+ mSQLException.toString());
            throw mSQLException;
        }



        /*ContentValues values = new ContentValues();
        values.put(INDEX_COLUMN, workout.getIndex());
        values.put(NAME_COLUMN, workout.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(WORKOUTS_TABLE_NAME, null, values);*/
    }

    //TODO: endtransaction? dbclose?

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*if (newVersion > oldVersion) {
            try {
                //Copy the database from assets
                copyDataBase();
                Log.d("debug", "WorkoutDBHandler.java   "+"createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
        DATABASE_VERSION = newVersion;*/

    }
}
