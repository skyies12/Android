package com.study.android.ex42_service2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    int playbackPosition = 0;
    private static final String TAG = "lecture";

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "지정된 시간입니다.", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "지정된 시간입니다.");

//        mp = MediaPlayer.create(MainActivity.class, R.raw.old_pop);
//        mp.seekTo(0);
//        mp.start();
    }
}
