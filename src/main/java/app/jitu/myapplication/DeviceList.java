package app.jitu.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 3/24/17.
 */

public class DeviceList extends ListActivity{
    private SimpleCursorAdapter adapter;
    SessionDbManager dbmanager;
Cursor c;
    final String[] from = new String[] { "pcname"
             };

    final int[] to = new int[] { R.id.pc };
    ArrayList<String> al;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devicelist);
        dbmanager=new SessionDbManager(this);
        dbmanager.open();
       c=dbmanager.fetch();
        ListView lv=getListView();

        al=new ArrayList<String>();
        c.moveToFirst();
        String e=c.getString(c.getColumnIndex(DataBaseHelper.PC));
       // Toast.makeText(this,e,Toast.LENGTH_SHORT).show();
        al.add(e);
        while(c.moveToNext())
        {
            String et=c.getString(c.getColumnIndex(DataBaseHelper.PC));
           // Toast.makeText(this,e,Toast.LENGTH_SHORT).show();
         al.add(et);

        }
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
        lv.setAdapter(aa);
        //Toast.makeText(this,al.get(0),Toast.LENGTH_SHORT).show();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c.moveToPosition(position);
                String session=c.getString(c.getColumnIndex(DataBaseHelper.KEY));
                Bundle b=new Bundle();
                Intent i=new Intent(DeviceList.this,MySocket.class);
                b.putString("key",session);
                i.putExtras(b);
                MainActivity.check=false;
                startActivity(i);

            }
        });

    }
}
