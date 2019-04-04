package com.study.android.ex19_list5;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;
    ImageView imageView;

    public SingerItemView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_view, this, true);

        textView1 = findViewById(R.id.blue);
        textView2 = findViewById(R.id.red);
        imageView = findViewById(R.id.imageView2);
    }

    public void setName(String name) {
        textView1.setText(name);
    }

    public void setAge(String age) {
        textView2.setText(age);
    }

    public void setImage(int imgNum) {
        imageView.setImageResource(imgNum);
    }
}
