package app.jitu.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


/**
 * Created by whit3hawks on 11/16/16.
 */
@TargetApi(23)
@SuppressLint("NewApi")
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;

    public static io.socket.client.Socket msock;
    JSONObject j ;
    // Constructor
    public FingerprintHandler(Context mContext,io.socket.client.Socket msocket,JSONObject jj) {
        context = mContext;
        msock = msocket ;
        j = jj ;

    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
        Toast.makeText(context,"Fingerprint Authentication Failed.",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
        Toast.makeText(context,"Fingerprint Authentication succeeded.",Toast.LENGTH_SHORT).show();
        msock.emit("cred",j);
    }


    public void update(String e, Boolean success){

    }
}
