package com.study.android.kswtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 2000;


//    private Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Intent intent;
//
//            switch (msg.what) {
//                case STOPSPLASH :
//                    intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.fade_in, R.anim.hold);
//                    finish();
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Handler handler = new Handler(  );
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( IntroActivity.this, MainActivity.class );
                startActivity( intent );

                finish();
            }
        }, 2000 );
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        Message msg = new Message();
//        msg.what = STOPSPLASH;
//        handler.sendMessageDelayed(msg, SPLASHTIME);
//    }
}
