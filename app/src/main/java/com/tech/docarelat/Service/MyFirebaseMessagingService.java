package com.tech.docarelat.Service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tech.docarelat.Activity.CareGiver.DeligetsNearYouActivity;
import com.tech.docarelat.Activity.Main.SplashActivity;
import com.tech.docarelat.App.Config;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = TAG;
        Log.e(str, "From: " + remoteMessage.getFrom());
        System.out.println("------------------------In MyFirebaseMessagingService---------------------------------");
        if (remoteMessage != null) {
            if (remoteMessage.getNotification() != null) {
                String str2 = TAG;
                Log.e(str2, "Notification Body: " + remoteMessage.getNotification().getBody());
                handleNotification(remoteMessage.getNotification().getBody());
            }
            if (remoteMessage.getData().size() > 0) {
                String str3 = TAG;
                Log.e(str3, "Data Payload: " + remoteMessage.getData().toString());
                try {
                    handleDataMessage(new JSONObject(remoteMessage.getData().toString()));
                } catch (Exception e) {
                    String str4 = TAG;
                    Log.e(str4, "Exception: " + e.getMessage());
                }
            }
        }
    }

    private void handleNotification(String str) {
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent intent2 = new Intent(Config.PUSH_NOTIFICATION);
            intent2.putExtra("message", str);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
            showNotificationMessage(getApplicationContext(), "WeCare", "You have a new message", "Timestamp", intent);
            return;
        }
        showNotificationMessage(getApplicationContext(), "WeCare", "You have a new message", "Timestamp", intent);
    }

    private void handleDataMessage(JSONObject jSONObject) {
        String str = TAG;
        Log.e(str, "push json: " + jSONObject.toString());
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        Intent intent2 = new Intent(getApplicationContext(), DeligetsNearYouActivity.class);
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("message");
            String string = jSONObject2.getString("key");
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent intent3 = new Intent(Config.PUSH_NOTIFICATION);
                intent3.putExtra("message", jSONObject2.toString());
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent3);
                if (string.equals("Request for booking")) {
                    intent2.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "You Have A New Request For Booking", "Timestamp", intent2);
                } else if (string.equals("Request Reject")) {
                    intent.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "Your Request Rejected by CareGiver", "Timestamp", intent);
                } else if (string.equals("Request Accepted")) {
                    intent.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "Your Request Accepted by CareGiver", "Timestamp", intent);
                } else if (string.equals("Request Complete")) {
                    intent.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "Your Request Complete by CareGiver", "Timestamp", intent);
                } else if (string.equals("crone job")) {
                    intent.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "Now you need to go on Job", "Timestamp", intent);
                }
            } else {
                Log.e("hello like", "else");
                intent.putExtra("message", jSONObject2.toString());
                if (string.equals("Request for booking")) {
                    intent2.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "You Have A New Request For Booking", "Timestamp", intent2);
                } else if (string.equals("Request Reject")) {
                    intent.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "Your Request Rejected by CareGiver", "Timestamp", intent);
                } else if (string.equals("Request Accepted")) {
                    intent.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "Your Request Accepted by CareGiver", "Timestamp", intent);
                } else if (string.equals("Request Complete")) {
                    intent.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "Your Request Complete by CareGiver", "Timestamp", intent);
                } else if (string.equals("crone job")) {
                    intent.putExtra("message", "" + jSONObject2);
                    showNotificationMessage(getApplicationContext(), "WeCare", "Now you need to go on Job", "Timestamp", intent);
                }
            }
        } catch (JSONException e) {
            String str2 = TAG;
            Log.e(str2, "Json Exception: " + e.getMessage());
        } catch (Exception e2) {
            String str3 = TAG;
            Log.e(str3, "Exception: " + e2.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String str, String str2, String str3, Intent intent) {
        this.notificationUtils = new NotificationUtils(context);
        intent.setFlags(268468224);
        this.notificationUtils.showNotificationMessage(str, str2, intent);
    }

    private void showNotificationMessageWithBigImage(Context context, String str, String str2, String str3, Intent intent, String str4) {
        this.notificationUtils = new NotificationUtils(context);
        intent.setFlags(268468224);
        this.notificationUtils.showNotificationMessage(str, str2, intent, str4);
    }
}
