package com.study.android.ex42_service2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    AlarmManager am;
    Intent intent;
    PendingIntent receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        // 예약에 의해 호출될 BR 지정
        intent = new Intent(this, AlarmReceiver.class);
        receiver = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void onBtn1Clicked(View v) {
        // 알림 시간 10초후
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);

        // 알람 등록
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), receiver);
        Log.d(TAG, "시작");
    }

    public void onBtn2Clicked(View v) {
        // 10초당 한번 알람 등록 : 24 * 60 * 60 * 1000
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, receiver);
    }

    public void onBtn3Clicked(View v) {
        am.cancel(receiver);
    }
//    알람 시간을 설정하는 4가지 타입이 있다
//
//    RTC_WAKEUP :  인자로 넘겨진, 시간을 기준으로 알람이 알람이 동작하여, pendingIntent를 전달합니다.
//            RTC : 이름에서 볼 수 있듯이, 위와 똑같지만, sleep모드에 들어간 기계를 깨우지는 않습니다.
//            ELAPSED_REALTIME_WAKEUP :  안드로이드 기계가 부팅된 시점을 기준으로 알람이 울립니다.
//            ELAPSED_REALTIME : ELAPSED_REALTIME_WAKEUP과 같지만, sleep모드에 들어갔다면 깨우지는 않습니다

}
