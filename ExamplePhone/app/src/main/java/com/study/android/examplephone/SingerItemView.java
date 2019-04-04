package com.study.android.examplephone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SingerItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;
    ImageView imageView;
    String gender = "";
    LayoutInflater inflater;


    public SingerItemView(Context context) {
        super(context);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflater.inflate(R.layout.singer_item_view_man, this, true);


        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView2);
    }

    public void setName(String name) {
        textView1.setText(name);
    }
    public void setPhone(String phone) {textView2.setText(phone);}

    public void setImage(int imgNum) {
        imageView.setImageResource(imgNum);
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void layout1() {
        inflater.inflate(R.layout.singer_item_view_man, this, true);
    }

    public void layout2() {
        inflater.inflate(R.layout.singer_item_view_woman, this, true);
    }
}
