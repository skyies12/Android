package com.study.android.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "lecture";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived() 호출됨.");

        Log.d(TAG, "From: " + remoteMessage.getFrom());
       // String from = remoteMessage.getFrom();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, "Message data payload: " + data.toString());
            String title = data.get("title");
            String contents = data.get("message");

            // sendToActivity(getApplicationContext(), contents,title);
            sendNotification(title, contents);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String notiTitle = remoteMessage.getNotification().getTitle();
            String notiBody = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Title: " + notiTitle);
            Log.d(TAG, "Message Notification Body: " + notiBody);

//            sendToActivity(getApplicationContext(), notiBody, notiTitle);
//            sendNotification(notiBody);
        }
    }

    private void sendToActivity(Context context, String contents, String title) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("contents", contents);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(intent);
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
