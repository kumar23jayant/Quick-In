package app.jitu.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Jitesh on 3/24/2017.
 */

public class Starting extends AppCompatActivity {
    ImageView pin,finger ;
    private SharedPreferences pref =null;

    @Override
    protected void onResume() {
        super.onResume();
        if(pref.getBoolean("firstrun",true))
        {
            pref.edit().putBoolean("firstrun",false).commit();
        }
        else
        {
            Intent i = new Intent(getBaseContext(),MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting);
        pref= getSharedPreferences("mypref",MODE_PRIVATE);

        pin = (ImageView)findViewById(R.id.pin) ;
        finger = (ImageView)findViewById(R.id.fin) ;
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll) ;
        ll.setBackgroundColor(Color.CYAN);
        pin.setImageResource(R.drawable.enterpin);
        if(Integer.valueOf(android.os.Build.VERSION.SDK_INT)<23)
        {
            finger.setImageResource(R.drawable.bfpp);
        }else
        {
            finger.setImageResource(R.drawable.fpp);
        }

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),pin.class) ;
                startActivity(i);


            }
        });
        finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(android.os.Build.VERSION.SDK_INT)<23)
                {
                    Toast.makeText(getBaseContext(),"Not Supported",Toast.LENGTH_SHORT).show();
                }
                else{
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("authtype",1).commit();
                    Intent i = new Intent(getBaseContext(),MainActivity.class) ;
                    startActivity(i);

                }
            }
        });
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
    @Override
    public void onPause() {
        super.onPause();


    }
}



