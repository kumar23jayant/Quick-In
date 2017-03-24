package app.jitu.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import io.socket.client.*;
/**
 * Created by Jitesh on 3/23/2017.
 */
public class MySocket extends AppCompatActivity {

    JSONObject jo ;
    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private TextView textView;
    private SharedPreferences pref=null;
    String[] temp;

Cursor c;
    DbManager dbManager;
    SessionDbManager mdbmanage;
    GridView gv;
    Button bb ;
    Bundle b;
    MainAdapter ma;
    public static io.socket.client.Socket msocket;
    {
        try{
            msocket=IO.socket("http://192.168.43.74:3000");
            msocket.connect();
        }
        catch (Exception e)
        {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        c=dbManager.fetch();
         ma=new MainAdapter(c,this);
        gv.setAdapter(ma);

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysocket);
        dbManager=new DbManager(this);
        dbManager.open();
        temp=new String[2];
        if(MainActivity.check)
        {
        pref=getSharedPreferences("mypref", Context.MODE_PRIVATE);
         b = getIntent().getExtras() ;
int a=pref.getInt("count",0);

        mdbmanage=new SessionDbManager(this);
        mdbmanage.open();
        if(b.getString("ip").contains("#$@:")) {
          temp=b.getString("ip").split(":");
            Toast.makeText(this, msocket.connected() + "", Toast.LENGTH_SHORT).show();
            mdbmanage.insert(temp[1], "device" + Integer.toString(a));
            a++;
            pref.edit().putInt("count", a).commit();
        }}
        else
        {
            b=getIntent().getExtras();
            temp[1]=b.getString("key");

        }
        c=dbManager.fetch();
      // Toast.makeText(this,""+c.getInt(c.getColumnIndex(DataBaseHelper._Unique)),Toast.LENGTH_SHORT).show();

        gv=(GridView)findViewById(R.id.grid_view);
        ma=new MainAdapter(c,this);
        gv.setAdapter(ma);
        registerForContextMenu(gv);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int a= PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("authtype",0);
                if(a==0)
                {
                    Bundle b=new Bundle();
                    b.putInt("pos",position);
                    Intent i = new Intent(getBaseContext(), CheckPin.class);
                    i.putExtras(b);
                    startActivityForResult(i, 1);
                }
             else if(a==1)
                {
                    Toast.makeText(getBaseContext(),"Verify fingerprint",Toast.LENGTH_SHORT).show();
                    c.moveToPosition(position);
                    JSONObject jobj=new JSONObject();
                    try {
                        jobj.put("name",c.getString(c.getColumnIndex(DataBaseHelper.USER)));
                        jobj.put("pass",c.getString(c.getColumnIndex(DataBaseHelper.PASS)));
                        jobj.put("session",temp[1]) ;
                        switch (c.getString(c.getColumnIndex(DataBaseHelper._ID)))
                        {
                            case "Facebook":
                                jobj.put("web","facebook");
                                break;
                            case "StackOverflow":
                                jobj.put("web","stackoverflow");
                                break;
                            case "Github":
                                jobj.put("web","github");
                                break;
                            case "Pintrest":
                                jobj.put("web","pinterest");
                                break;

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                    FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);




                    // Check whether the device has a Fingerprint sensor.

                    if(!fingerprintManager.isHardwareDetected()){
                        /**
                         * An error message will be displayed if the device does not contain the fingerprint hardware.
                         * However if you plan to implement a default authentication method,
                         * you can redirect the user to a default authentication activity from here.
                         * Example:
                         * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
                         * startActivity(intent);
                         */
                        Toast.makeText(getBaseContext(),"Your Device does not have a Fingerprint Sensor",Toast.LENGTH_SHORT).show();
                    }else {
                        // Checks whether fingerprint permission is set on manifest
                        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getBaseContext(),"Fingerprint authentication permission not enabled",Toast.LENGTH_SHORT).show();
                        }else{
                            // Check whether at least one fingerprint is registered
                            if (!fingerprintManager.hasEnrolledFingerprints()) {
                                Toast.makeText(getBaseContext(),"Register at least one fingerprint in Settings",Toast.LENGTH_SHORT).show();
                            }else{
                                // Checks whether lock screen security is enabled or not
                                if (!keyguardManager.isKeyguardSecure()) {
                                    Toast.makeText(getBaseContext(),"Lock screen security not enabled in Settings",Toast.LENGTH_SHORT).show();
                                }else{
                                    generateKey();


                                    if (cipherInit()) {
                                        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                        FingerprintHandler helper = new FingerprintHandler(getBaseContext(),msocket,jobj);
                                        helper.startAuth(fingerprintManager, cryptoObject);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });





        if(msocket.connected())
        {
            Toast.makeText(this,"connected",Toast.LENGTH_SHORT).show();

        }


    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        if(v.getId()==R.id.grid_view)
        {
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu_list,menu);
        }
    }
public boolean onContextItemSelected(MenuItem item)
{
    AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    int q=info.position;

    c = dbManager.fetch() ;
    c.moveToPosition(q);
    //Toast.makeText(this,""+c.getString(c.getColumnIndex(DataBaseHelper._ID)),Toast.LENGTH_SHORT).show();

    switch (item.getItemId())
    {
        case R.id.update:  Intent i = new Intent(this,UpdateData.class) ;
                            Bundle b = new Bundle() ;
                             b.putInt("ui",c.getInt(c.getColumnIndex(DataBaseHelper._Unique))) ;
                             b.putInt("pos",q) ;
                            i.putExtras(b) ;
                               startActivity(i);
            break ;
        case R.id.delete:
           dbManager.delete(c.getInt(c.getColumnIndex(DataBaseHelper._Unique)));
            //ma.notifyDataSetChanged();
            c= dbManager.fetch() ;
            ma = new MainAdapter(c,this) ;
            gv.setAdapter(ma);
            break;

    }
    return super.onContextItemSelected(item);
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                if(result.contentEquals("ok"))
                {
                    Bundle b=data.getExtras();
                    int pos=b.getInt("pos");
                    c.moveToPosition(pos);
                    JSONObject jobj=new JSONObject();
                    try {
                        jobj.put("name",c.getString(c.getColumnIndex(DataBaseHelper.USER)));
                        jobj.put("pass",c.getString(c.getColumnIndex(DataBaseHelper.PASS)));
                        jobj.put("session",temp[1]) ;
                        switch (c.getString(c.getColumnIndex(DataBaseHelper._ID)))
                        {
                            case "Facebook":
                                jobj.put("web","facebook");
                                break;
                            case "StackOverflow":
                                jobj.put("web","stackoverflow");
                                break;
                            case "Github":
                                jobj.put("web","github");
                                break;
                            case "Pintrest":
                                jobj.put("web","pinterest");
                                break;

                        }
                        msocket.emit("cred",jobj);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add: Intent i  = new Intent(getBaseContext(),GView.class) ;
                                  startActivity(i);
        }
     return   super.onOptionsItemSelected(item) ;
    }
}
