package com.tech.docarelat.Service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tech.docarelat.App.Config;
import com.tech.docarelat.App.MySharedPref;
import java.io.PrintStream;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    public void onTokenRefresh() {
        super.onTokenRefresh();
        System.out.println("-----------------MyFirebaseInstanceIDService-------------");
        String token = FirebaseInstanceId.getInstance().getToken();
        storeRegIdInPref(token);
        sendRegistrationToServer(token);
        Intent intent = new Intent(Config.REGISTRATION_COMPLETE);
        intent.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendRegistrationToServer(String str) {
        String str2 = TAG;
        Log.e(str2, "sendRegistrationToServer: " + str);
    }

    private void storeRegIdInPref(String str) {
        PrintStream printStream = System.out;
        printStream.println("----------token---------- " + str);
        Context applicationContext = getApplicationContext();
        MySharedPref.saveData(applicationContext, "regId", "" + str);
    }
}
