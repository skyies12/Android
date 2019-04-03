package com.study.android.ex07_imageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    ScrollView scrollView;

    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 이미지 뷰
        imageView1 = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

        imageView1.setImageResource(R.drawable.wallpaper_bliss_ktaehun12);
        imageView2.setImageResource(0);

        imageView1.invalidate();
        imageView2.invalidate();

        // 스크롤 유무만 달라질뿐, 실제로 스크롤은 한다
        scrollView = findViewById(R.id.scrollView01);
        scrollView.setVerticalFadingEdgeEnabled(true);
        scrollView.setHorizontalScrollBarEnabled(true);
    }

    public void onBtn1Clicked(View v) {
        imageUp();
    }

    public void onBtn2Clicked(View v) {
        imageDown();
    }

    private void imageDown() {
        imageView1.setImageResource(R.drawable.wallpaper_bliss_ktaehun12);
        imageView2.setImageResource(0);

        imageView1.invalidate();
        imageView2.invalidate();
    }

    private void imageUp() {
        imageView2.setImageResource(R.drawable.n20101223_10);
        imageView1.setImageResource(0);

        imageView1.invalidate();
        imageView2.invalidate();
    }
}
