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

public class SessionDbManager {

    private DataBaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public SessionDbManager(Context c) {
        context = c;
    }

    public SessionDbManager open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String pass) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DataBaseHelper.KEY, name);
        contentValue.put(DataBaseHelper.PC, pass);
        database.insert(DataBaseHelper.TABLE_NAME1, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {DataBaseHelper._ID1, DataBaseHelper.KEY, DataBaseHelper.PC };
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME1, columns, null, null, null, null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }




}