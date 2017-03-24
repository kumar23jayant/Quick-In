package app.jitu.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 3/24/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "accounts";
    public static final String TABLE_NAME1 = "session";

    // Table columns
    public static final String _ID = "_id";
    public static final String USER = "username";
    public static final String PASS = "password";
    public static final String _Unique="_uid";
    public static final String _ID1 = "_id1";
    public static final String KEY = "key";
    public static final String PC = "pcname";


    static final String DB_NAME = "Mydatabase.DB";

    static final int DB_VERSION = 4;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +_Unique+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ _ID + " TEXT NOT NULL "
            + " , " + USER + " TEXT NOT NULL, " + PASS + " TEXT NOT NULL);";
    private static final String CREATE_TABLE1= "create table " + TABLE_NAME1 + "(" +_ID1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY + " TEXT NOT NULL "
            + " , " + PC + " TEXT NOT NULL );";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }


}
