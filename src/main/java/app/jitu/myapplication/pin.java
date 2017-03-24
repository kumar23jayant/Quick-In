package app.jitu.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 3/24/17.
 */

public class pin extends Activity {
    EditText et1, et2;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.pp);
        et1 = (EditText) findViewById(R.id.pin);
        et2 = (EditText) findViewById(R.id.rpin);

    }

    public void savepin(View v) {
        if(et1.getText().toString()!=null)
        {
        if (et1.getText().toString().contentEquals(et2.getText().toString())) {
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("pin", et1.getText().toString()).commit();
            Toast.makeText(this, "you have made the pin", Toast.LENGTH_SHORT).show();

            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("authtype",0).commit();
            Intent i =new Intent(getBaseContext(),MainActivity.class);
            startActivity(i);

        } else {
            Toast.makeText(this, "both pins are not same", Toast.LENGTH_SHORT).show();
        }}

    }
}