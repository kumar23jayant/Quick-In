package app.jitu.myapplication;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;




import com.google.android.gms.vision.barcode.Barcode;



import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

public class MainActivity extends AppCompatActivity {
    Button scanbtn;
    TextView tv;

ImageView scanqr,skip;
    Cursor c;
    SessionDbManager dbmanager;

    String res;
    public static  boolean check=false;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private SharedPreferences pref =null;

    @Override
    protected void onRestart() {
        super.onRestart();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        dbmanager=new SessionDbManager(this);
        dbmanager.open();
        c=dbmanager.fetch();
        pref= getSharedPreferences("mypref",MODE_PRIVATE);

       scanqr = (ImageView)findViewById(R.id.scanqr) ;
         skip= (ImageView)findViewById(R.id.skip) ;
         tv=(TextView)findViewById(R.id.tv);
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.activity_main) ;

        ll.setBackgroundColor(Color.CYAN);
        Toast.makeText(this,Integer.toString(c.getCount()),Toast.LENGTH_SHORT).show();
        if(c.getCount()==0)
        {
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,330);
            lp.setMargins(200,0,0,0);
                    scanqr.setLayoutParams(lp);
            LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50);
            lp.gravity= Gravity.CENTER;
            tv.setLayoutParams(lp1);
        }
        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);


            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(MainActivity.this,DeviceList.class);
                check=false;
                startActivity(i);

            }
        });


       // scanbtn = (Button) findViewById(R.id.scanbtn);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
       /* scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");

                res = barcode.displayValue;

                Bundle b = new Bundle();
                b.putString("ip", res);

                Intent i = new Intent(getBaseContext(), MySocket.class);
                i.putExtras(b);
                check=true;
                startActivity(i);


            }
        }
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
}