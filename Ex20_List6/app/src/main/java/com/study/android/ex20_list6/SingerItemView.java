package com.study.android.ex20_list6;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {
    TextView textView1;
    Button button1;
    ImageView imageView1;

    public SingerItemView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_view, this, true);

        textView1 = findViewById(R.id.textView1);
        button1 = findViewById(R.id.button1);
        imageView1 = findViewById(R.id.imageView1);
    }

    public void setName(String name) {
        textView1.setText(name);
    }

    public void setTel(String telnum) {
        button1.setText(telnum);
    }

    public void setImage(int imgNum) {
        imageView1.setImageResource(imgNum);
    }
}
