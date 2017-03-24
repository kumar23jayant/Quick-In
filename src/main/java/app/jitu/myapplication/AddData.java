package app.jitu.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 3/24/17.
 */

public class AddData extends Activity {
    EditText et1,et2 ;

    ImageView img ;
    int update =0 ;
    DbManager dbmanager;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddata);
dbmanager=new DbManager(this);
        dbmanager.open();
        et1 = (EditText)findViewById(R.id.username) ;
        et2= (EditText)findViewById(R.id.password) ;
        img  =(ImageView)findViewById(R.id.imageview)   ;

         b = getIntent().getExtras() ;

        switch(b.getInt("pos"))
        {
            case 0: img.setImageResource(R.drawable.fb);
                   break ;
            case 1:img.setImageResource(R.drawable.sof);break ;
            case 2: img.setImageResource(R.drawable.github);break ;
            case 3: img.setImageResource(R.drawable.pininterest);break ;
        }

    }

    public void save(View v)
    {   if(isEmailValid(et1.getText().toString())) {
        if (!(et1.getText().toString().matches("") || et2.getText().toString().matches(""))) {
            switch (b.getInt("pos")) {
                case 0:


                    dbmanager.insert("Facebook", et1.getText().toString(), et2.getText().toString());
                    break;
                case 1:

                    dbmanager.insert("StackOverflow", et1.getText().toString(), et2.getText().toString());
                    break;
                case 2:

                    dbmanager.insert("Github", et1.getText().toString(), et2.getText().toString());
                    break;
                case 3:

                    dbmanager.insert("Pintrest", et1.getText().toString(), et2.getText().toString());
                    break;
            }

            Toast.makeText(getBaseContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
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
