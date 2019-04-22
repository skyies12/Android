package com.study.android.kswtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlaceItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;

    public PlaceItemView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.google_map_layout, this, true);

        textView1 = findViewById(R.id.movieitem);
        textView2 = findViewById(R.id.Distance);
    }

    public void setMovieName(String setMovieName) {
        textView1.setText(setMovieName);
    }
    public void setDistance(String setDistance) {
        textView2.setText(setDistance);
    }
}
