package app.jitu.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 3/24/17.
 */

public class UpdateData  extends AppCompatActivity {
    EditText et1,et2 ;

    ImageView img ;
    DbManager dbManager ;
    Cursor c ;
    int l ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.adddata);
        et1 = (EditText)findViewById(R.id.username) ;
        et2= (EditText)findViewById(R.id.password) ;
        img  =(ImageView)findViewById(R.id.imageview)   ;

        Bundle b = getIntent().getExtras();
         l = b.getInt("ui") ;
         int pos = b.getInt("pos") ;
        dbManager=new DbManager(this);
        dbManager.open();
        c=dbManager.fetch();
        c.moveToPosition(pos) ;
        et1.setText(c.getString(c.getColumnIndex(DataBaseHelper.USER)));
        et2.setText(c.getString(c.getColumnIndex(DataBaseHelper.PASS)));
        switch(c.getString(c.getColumnIndex(DataBaseHelper._ID)))
        {
            case "Facebook": img.setImageResource(R.drawable.fb);
                break ;
            case "StackOverflow":img.setImageResource(R.drawable.sof);break ;
            case "Github": img.setImageResource(R.drawable.github);break ;
            case "Pintrest": img.setImageResource(R.drawable.pininterest);break ;
        }

    }
    public void save(View v)
    {if(isEmailValid(et1.getText().toString())) {
        if(!(et1.getText().toString().matches("") || et2.getText().toString().matches(""))) {

               dbManager.update(l,et1.getText().toString(),et2.getText().toString()) ;
            Toast.makeText(getBaseContext(), " Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(getBaseContext(), "Please enter both the fields", Toast.LENGTH_SHORT).show();
        }
    }else {
        Toast.makeText(getBaseContext(), "Please enter valid email address", Toast.LENGTH_SHORT).show();
    }

}
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}