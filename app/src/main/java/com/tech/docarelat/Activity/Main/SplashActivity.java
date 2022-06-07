package com.tech.docarelat.Activity.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.tech.docarelat.Activity.CareGiver.GiverHomeActivity;
import com.tech.docarelat.Activity.ClientService.ClientHomeActivity;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 2;
    private static int SPLASH_TIME_OUT = 3000;
    String[] mPermission = {"android.permission.GET_ACCOUNTS", "android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.CAMERA", "android.permission.CALL_PHONE"};

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash);
        try {
            if (ActivityCompat.checkSelfPermission(this, this.mPermission[0]) == 0 && ActivityCompat.checkSelfPermission(this, this.mPermission[1]) == 0 && ActivityCompat.checkSelfPermission(this, this.mPermission[2]) == 0 && ActivityCompat.checkSelfPermission(this, this.mPermission[3]) == 0 && ActivityCompat.checkSelfPermission(this, this.mPermission[4]) == 0 && ActivityCompat.checkSelfPermission(this, this.mPermission[5]) == 0 && ActivityCompat.checkSelfPermission(this, this.mPermission[6]) == 0 && ActivityCompat.checkSelfPermission(this, this.mPermission[7]) == 0) {
                if (ActivityCompat.checkSelfPermission(this, this.mPermission[8]) == 0) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (MySharedPref.getData(SplashActivity.this, "ldata", (String) null) != null) {
                                String data = MySharedPref.getData(SplashActivity.this, "type", (String) null);
                                if (data == null) {
                                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                                    SplashActivity.this.finish();
                                } else if (data.equals("CLIENT")) {
                                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, ClientHomeActivity.class));
                                    SplashActivity.this.finish();
                                } else {
                                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, GiverHomeActivity.class));
                                    SplashActivity.this.finish();
                                }
                            } else {
                                SplashActivity.this.startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                                SplashActivity.this.finish();
                            }
                        }
                    }, (long) SPLASH_TIME_OUT);
                    return;
                }
            }
            ActivityCompat.requestPermissions(this, this.mPermission, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        Log.e("Req Code", "" + i);
        if (i != 2) {
            return;
        }
        if (iArr.length == 9 && iArr[0] == 0 && iArr[1] == 0 && iArr[2] == 0 && iArr[3] == 0 && iArr[4] == 0 && iArr[5] == 0 && iArr[6] == 0 && iArr[7] == 0 && iArr[8] == 0) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (MySharedPref.getData(SplashActivity.this, "ldata", (String) null) != null) {
                        String data = MySharedPref.getData(SplashActivity.this, "type", (String) null);
                        if (data == null) {
                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                            SplashActivity.this.finish();
                        } else if (data.equals("CLIENT")) {
                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, ClientHomeActivity.class));
                            SplashActivity.this.finish();
                        } else {
                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, GiverHomeActivity.class));
                            SplashActivity.this.finish();
                        }
                    } else {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                        SplashActivity.this.finish();
                    }
                }
            }, (long) SPLASH_TIME_OUT);
            return;
        }
        Toast.makeText(this, "Denied", 0).show();
        finish();
    }
}
