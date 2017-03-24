package app.jitu.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

/**
 * Created by root on 3/24/17.
 */

public class DbManager {

    private DataBaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DbManager(Context c) {
        context = c;
    }

    public DbManager open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String id,String name, String pass) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DataBaseHelper._ID,id);
        contentValue.put(DataBaseHelper.USER, name);
        contentValue.put(DataBaseHelper.PASS, pass);
        database.insert(DataBaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {DataBaseHelper._Unique, DataBaseHelper._ID, DataBaseHelper.USER, DataBaseHelper.PASS };
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String pass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.USER, name);
        contentValues.put(DataBaseHelper.PASS, pass);
        int i = database.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper._Unique + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DataBaseHelper.TABLE_NAME,  DataBaseHelper._Unique+"=" + _id, null);
    }


}