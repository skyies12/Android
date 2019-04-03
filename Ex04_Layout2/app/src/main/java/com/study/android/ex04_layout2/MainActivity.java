package com.study.android.ex04_layout2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    ImageView imageView;
    boolean imageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        imageView = findViewById(R.id.imageView);

        imageSelected = false;
    }

    public void btn1Clicked(View v) {
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
    }

    public void btn2Clicked(View v) {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
        layout3.setVisibility(View.GONE);
    }

    public void btn3Clicked(View v) {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.VISIBLE);
    }

    public void btn6Clicked(View v) {
        if(imageSelected) {
            imageView.setImageResource(R.drawable.house);
            imageSelected = false;
        } else {
            imageView.setImageResource(R.drawable.car);
            imageSelected = true;
        }
    }
}
