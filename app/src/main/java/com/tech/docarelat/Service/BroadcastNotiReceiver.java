package com.tech.docarelat.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tech.docarelat.App.Config;
import com.tech.docarelat.App.MySharedPref;
import java.io.PrintStream;

public class BroadcastNotiReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
            FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
            displayFirebaseRegId();
        } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
            String stringExtra = intent.getStringExtra("message");
            AppController instance = AppController.getInstance();
            Toast.makeText(instance, "Push notification: " + stringExtra, 1).show();
        }
        displayFirebaseRegId();
    }

    public void displayFirebaseRegId() {
        String data = MySharedPref.getData(AppController.getInstance(), "regId", (String) null);
        Log.e("REG_ID", "Firebase reg id: " + data);
        if (!TextUtils.isEmpty(data)) {
            PrintStream printStream = System.out;
            printStream.println("Firebase Reg Id: " + data);
            MySharedPref.saveData(AppController.getInstance(), "regId", data);
            return;
        }
        System.out.println("Firebase Reg Id is not received yet!");
    }
}
