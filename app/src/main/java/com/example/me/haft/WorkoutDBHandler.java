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


public class WorkoutDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "seven.db";
    private static final String WORKOUTS_TABLE_NAME = "workouts";
    private static final String EXERCISES_TABLE_NAME = "exercises";
    private static final String INDEX_COLUMN = "tbl_index";
    private static final String NAME_COLUMN = "name";
    private static final String COLUMN_IconId = "iconId";
    private static final String COLUMN_ImageId = "imageId";
    private static final String COLUMN_Description = "description";
    private static final String EXERCISES_SEQUENCE = "exercises_sequence";
    private static int DATABASE_VERSION = 1;
    private static String DB_PATH = "";
    private final Context mContext;
    private SQLiteDatabase mDataBase;

    public WorkoutDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory
            , int version) {
        super(context, "seven.sql", factory, version);

        Log.d("debug", "WorkoutDBHandler.java   "+"workoutdbhandler created");

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
        DATABASE_VERSION++;
        //TODO:remove version++
        //Log.d("debug","WorkoutDBHandler.java   "+"db version="+String.valueOf(DATABASE_VERSION));
        //Log.d("debug","WorkoutDBHandler.java   "+"DB_PATH="+DB_PATH);
        //Log.d("debug","WorkoutDBHandler.java   "+mContext.getDatabasePath("seven2.sql").toString());
    }

    /*
     * Creates a empty database on the system and rewrites it with your own database.
     * */
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
        Log.d("debug", "WorkoutDBHandler.java   "+dbFile + "exist   " + dbFile.exists());
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
        Log.d("debug", "WorkoutDBHandler.java   "+"i=" + String.valueOf(i));
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

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }


    public Cursor getTestData() {
        try {

            String sql = "SELECT * FROM " + WORKOUTS_TABLE_NAME;

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


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String CREATE_TABLE="CREATE TABLE "+WORKOUTS_TABLE_NAME+"("+
                INDEX_COLUMN+"INTEGER PRIMARY KEY UNIQUE,"
        +NAME_COLUMN+"VARCHAR,"
        +COLUMN_IconId+"TEXT,"
        +COLUMN_ImageId+"TEXT,"
        +COLUMN_Description+"TEXT)";

        db.execSQL(CREATE_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                //Copy the database from assets
                copyDataBase();
                Log.d("debug", "WorkoutDBHandler.java   "+"createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
        DATABASE_VERSION = newVersion;
    }

    public String loadHandler() {


        String result = "";
        String query = "SELECT * FROM " + WORKOUTS_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = mDataBase.rawQuery(query, null);
        //Cursor cursor=mDataBase.query(null,null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }



    //search in workout table with index. gets first cursor result and sets the name and
    //index of Workout to be returned.
    //used for finding workout items in learnPageFragment.
    public Workout findWorkoutHandler(int index) {
        try {
            String query = "Select * FROM " + WORKOUTS_TABLE_NAME + " WHERE " +
                    INDEX_COLUMN + "= '" + String.valueOf(index) + "'";

            Cursor cursor = mDataBase.rawQuery(query, null);

            Workout workout = new Workout();
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                workout.setIndex(Integer.parseInt(cursor.getString(0)));
                workout.setName(cursor.getString(1));
                cursor.close();
            } else {
                workout = null;
            }
            //mDataBase.close();
            return workout;
        } catch (SQLException mSQLException) {
            Log.d("debug", "WorkoutDBHandler.java   "+"findhandler >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    //searches in workout table by name. finds the workout. reads exercises_sequences column
    //gets exercises from exercise table by calling getWorkout() and returns workoutSet.
    public WorkoutSet getWorkoutSet(String workoutSetName) {
        char[] sequence;
        try {
            String query = "Select " + EXERCISES_SEQUENCE + " FROM " + WORKOUTS_TABLE_NAME
                    + " WHERE " + NAME_COLUMN + "= '" + workoutSetName + "'";
            Cursor cursor = mDataBase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                sequence = cursor.getString(0).toCharArray();
            } else {
                sequence = null;
            }
        } catch (SQLException mSQLException) {
            Log.e("debug", "getWorkoutSet>> setworkout >>" + mSQLException.toString());
            throw mSQLException;
        }
        WorkoutSet workoutSet = new WorkoutSet();
        if (sequence != null && sequence.length > 0) {
            for (int i = 0; i < sequence.length; i++) {
                Workout workout = getWorkout(Integer.parseInt(String.valueOf(sequence[i])));
                if (workout != null) {
                    workoutSet.add(workout);
                }
            }
        }
        return workoutSet;
    }

    //searches in workout table by index. finds the workout. reads exercises_sequences column
    //gets exercises from exercise table by calling getWorkout() and returns workoutSet.
    public WorkoutSet getWorkoutSet(int workoutSetIndex) {
        char[] sequence;
        try {
            String query = "Select " + EXERCISES_SEQUENCE + " FROM " + WORKOUTS_TABLE_NAME
                    + " WHERE " + INDEX_COLUMN + "= '" + workoutSetIndex + "'";
            Cursor cursor = mDataBase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                sequence = cursor.getString(0).toCharArray();
            } else {
                sequence = null;
            }
        } catch (SQLException mSQLException) {
            Log.e("debug", "getWorkoutSet>> setworkout >>" + mSQLException.toString());
            throw mSQLException;
        }
        WorkoutSet workoutSet = new WorkoutSet();
        if (sequence != null && sequence.length > 0) {
            for (int i = 0; i < sequence.length; i++) {
                Workout workout = getWorkout(Integer.parseInt(String.valueOf(sequence[i])));

                if (workout != null) {
                    workoutSet.add(workout);
                }
            }
        }
        return workoutSet;
    }

    //searches the exercise table by index and returns workout
    public Workout getWorkout(int index){
        Workout workout = new Workout();
        try {
            String query = "Select * FROM " + EXERCISES_TABLE_NAME
                    + " WHERE " + INDEX_COLUMN + "= '" + index + "'";
            Cursor cursor = mDataBase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                workout.setIndex(Integer.parseInt(cursor.getString(0)));
                workout.setName(cursor.getString(1));
                if (cursor.getString(2)!=null)
                    workout.setIconId(Integer.parseInt(cursor.getString(2)));
                if (cursor.getString(3)!=null)
                    workout.setImageId(Integer.parseInt(cursor.getString(3)));
                workout.setDescription(cursor.getString(4));
            } else {
                workout = null;
            }
        } catch (SQLException mSQLException) {
            Log.d("debug", "WorkoutDBHandler.java   "+"getWorkoutSet" +
                    ">> findSequence >>" + mSQLException.toString());
            throw mSQLException;
        }
        return workout;
    }


    //searches workout table and returns a workoutSet of all of its items
    public WorkoutSet getWorkoutSets() {
        try {
            String query = "Select * FROM " + WORKOUTS_TABLE_NAME;
            Cursor cursor = mDataBase.rawQuery(query, null);

            WorkoutSet workoutSet=new WorkoutSet();

            while(cursor.moveToNext()){
                Workout workout = new Workout();
                workout.setIndex(Integer.parseInt(cursor.getString(0)));
                workout.setName(cursor.getString(1));
                workoutSet.add(workout);
            }

            cursor.close();

            return workoutSet;
        } catch (SQLException mSQLException) {
            Log.d("debug", "WorkoutDBHandler.java   "+"findhandler >>" + mSQLException.toString());
            throw mSQLException;
        }
    }
    

    //returns number of workouts in workout table
    public int countWorkoutsHandler() {
        String query = "Select * FROM " + WORKOUTS_TABLE_NAME;
        Cursor cursor = mDataBase.rawQuery(query, null);
        return cursor.getCount();
    }

    public void addHandler(Workout workout) {
        ContentValues values = new ContentValues();
        values.put(INDEX_COLUMN, workout.getIndex());
        values.put(NAME_COLUMN, workout.getName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(WORKOUTS_TABLE_NAME, null, values);
        db.close();
    }

    public boolean deleteHandler(int index) {
        boolean result = false;
        String query = "Select*FROM" + WORKOUTS_TABLE_NAME + "WHERE" + INDEX_COLUMN + "= '"
                + String.valueOf(index) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Workout workout = new Workout();
        if (cursor.moveToFirst()) {
            workout.setIndex(Integer.parseInt(cursor.getString(0)));
            db.delete(WORKOUTS_TABLE_NAME, INDEX_COLUMN + "=?",
                    new String[]{
                            String.valueOf(workout.getIndex())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


    public boolean updateHandler(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(INDEX_COLUMN, ID);
        args.put(NAME_COLUMN, name);
        return db.update(WORKOUTS_TABLE_NAME, args, INDEX_COLUMN + "="
                + ID, null) > 0;
    }

    //TODO: correct methods names
    //TODO: fix iconId and ImageId
}
