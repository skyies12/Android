package com.study.android.exradio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class
MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mp;
    int playbackPosition = 0;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    int i = 1;
   TextView textView;

    private Button startbutton;
    private Button pausebutton;
    private Button stopbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.imageButton);
        button2 = findViewById(R.id.imageButton2);
        button3 = findViewById(R.id.imageButton3);
        button4 = findViewById(R.id.imageButton4);
        button5 = findViewById(R.id.imageButton5);
        button6 = findViewById(R.id.imageButton6);
        button7 = findViewById(R.id.imageButton7);
        button8 = findViewById(R.id.imageButton8);
        button9 = findViewById(R.id.imageButton9);
        button10 = findViewById(R.id.imageButton10);

        textView = findViewById(R.id.editText);

        startbutton = findViewById(R.id.button);
        stopbutton = findViewById(R.id.button4);
        pausebutton = findViewById(R.id.button5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);

    }
   @Override
    public void onClick(View v) {
       String temp = "";

       temp = ((Button) v).getTag().toString();
       temp = textView.getText() + temp;
       //Log.d("lecture", temp);
       textView.setText(temp);
    }

    public void onBtn1Clicked(View v) {

        String temp = textView.getText().toString();
        Log.d("lecture", temp);
        if(temp.equals("0001")) {
            mp = MediaPlayer.create(this, R.raw.song);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0002")) {
            mp = MediaPlayer.create(this, R.raw.black);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0003")) {
            mp = MediaPlayer.create(this, R.raw.basick);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0004")) {
            mp = MediaPlayer.create(this, R.raw.cheetah);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0005")) {
            mp = MediaPlayer.create(this, R.raw.crush);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0006")) {
            mp = MediaPlayer.create(this, R.raw.lonely);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0007")) {
            mp = MediaPlayer.create(this, R.raw.rooftop);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0008")) {
            mp = MediaPlayer.create(this, R.raw.kassy);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0009")) {
            mp = MediaPlayer.create(this, R.raw.spring);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        } else if(temp.equals("0010")) {
            mp = MediaPlayer.create(this, R.raw.fallin);
            mp.seekTo(0);
            mp.start();

            stopbutton.setVisibility(View.VISIBLE);
            pausebutton.setVisibility(View.GONE);
            startbutton.setVisibility(View.GONE);
        }
    }
    public void onBtn3Clicked(View v) {
        String temp = textView.getText().toString();
        Log.d("lecture", temp);
        if(mp != null) {
            if(temp.equals("0001")) {
                mp = MediaPlayer.create(this, R.raw.song);
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0002")) {
                mp = MediaPlayer.create(this, R.raw.black);
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0003")) {
                mp = MediaPlayer.create(this, R.raw.basick
                );
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0004")) {
                mp = MediaPlayer.create(this, R.raw.cheetah);
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0005")) {
                mp = MediaPlayer.create(this, R.raw.crush);
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0006")) {
                mp = MediaPlayer.create(this, R.raw.lonely);
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0007")) {
                mp = MediaPlayer.create(this, R.raw.rooftop);
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0008")) {
                mp = MediaPlayer.create(this, R.raw.kassy);
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0009")) {
                mp = MediaPlayer.create(this, R.raw.spring);
                mp.seekTo(0);
                mp.start();
            } else if(temp.equals("0010")) {
                mp = MediaPlayer.create(this, R.raw.fallin);
                mp.seekTo(0);
                mp.start();
            }else {
                mp.seekTo(playbackPosition);
                mp.start();
            }
        }

        stopbutton.setVisibility(View.VISIBLE);
        startbutton.setVisibility(View.GONE);
        pausebutton.setVisibility(View.GONE);

    }


    public void onBtn2Clicked(View v) {
        if(mp != null) {
            mp.pause();
            playbackPosition = mp.getCurrentPosition();
            textView.setText("");

        }

        stopbutton.setVisibility(View.GONE);
        startbutton.setVisibility(View.GONE);
        pausebutton.setVisibility(View.VISIBLE);
    }
}
