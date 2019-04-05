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


    public SingerItemView(Context context, int kind) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflater.inflate(R.layout.singer_item_view_man, this, true);
        if(kind == 1) {
            inflater.inflate(R.layout.singer_item_view_woman, this, true);
            textView1 = findViewById(R.id.textView);
            textView2 = findViewById(R.id.textView2);
            imageView = findViewById(R.id.imageView2);
        } else if(kind == 0){
            inflater.inflate(R.layout.singer_item_view_man, this, true);
            textView1 = findViewById(R.id.textView);
            textView2 = findViewById(R.id.textView2);
            imageView = findViewById(R.id.imageView2);
        } else {
            inflater.inflate(R.layout.singer_item_view_noimage, this, true);
            textView1 = findViewById(R.id.textView);
            textView2 = findViewById(R.id.textView2);
            imageView = findViewById(R.id.imageView2);
        }

    }

    public void setName(String name) {
        textView1.setText(name);
    }
    public void setPhone(String phone) {textView2.setText(phone);}
    public void setImage(int imgNum) {
        imageView.setImageResource(imgNum);
    }

}
