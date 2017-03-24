package app.jitu.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 3/24/17.
 */

public class MainAdapter extends BaseAdapter {
    public Cursor c;
    LayoutInflater inflator=null;
    public MainAdapter(Cursor cursor,Context ct)
    {
     c=cursor;
        inflator=(LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return c.getCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
        {
            vi=inflator.inflate(R.layout.mainview,null);

        }
        c.moveToPosition(position);
        TextView tv=(TextView)vi.findViewById(R.id.tv);
        ImageView iv=(ImageView)vi.findViewById(R.id.iv);
        switch (c.getString(c.getColumnIndex(DataBaseHelper._ID)))
        {
            case "Facebook":iv.setImageResource(R.drawable.fb);
                tv.setText("Facebook");
                break;
            case "StackOverflow":iv.setImageResource(R.drawable.sof);
                tv.setText("StackOverflow");
                break;
            case "Github":iv.setImageResource(R.drawable.github);
                tv.setText("Github");
                break;
            case "Pintrest":iv.setImageResource(R.drawable.pininterest);
                tv.setText("Twitter");
                break;
        }
        return vi;
    }
}
