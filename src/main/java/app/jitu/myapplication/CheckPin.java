package app.jitu.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by root on 3/24/17.
 */

public class CheckPin extends Activity {
    EditText et1;
    Bundle b;
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.checkpin);
        et1=(EditText)findViewById(R.id.pin);
        b=getIntent().getExtras();



    }
    public void verify(View v)
    {String ver=et1.getText().toString();
        if(ver!=null) {
            String pin = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("pin", null);
            if(pin==null)
            {
                Toast.makeText(this,"hjhjhjhjhj",Toast.LENGTH_SHORT).show();
            }
            else
            {
            if(ver.contentEquals(pin))
            {
               Intent returnIntent = new Intent();
                String res="ok";
                returnIntent.putExtras(b);
                returnIntent.putExtra("result",res);
                setResult(Activity.RESULT_OK,returnIntent);
               finish();
            }
            else
            {
                Toast.makeText(getBaseContext(),"Enter correct pin",Toast.LENGTH_SHORT).show();
            }
        }}
        else
        {
            Toast.makeText(getBaseContext(),"Enter the pin",Toast.LENGTH_SHORT).show();
        }
    }
}
