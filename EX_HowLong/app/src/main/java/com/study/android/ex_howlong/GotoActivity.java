package com.study.android.ex_howlong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class GotoActivity extends AppCompatActivity {
    private static final String TAG="lecture";

    WebView web;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goto);

        intent = getIntent();
        String title = intent.getStringExtra("title");
        String lat = intent.getStringExtra("lat");
        String longi = intent.getStringExtra("longi");

        Log.d (TAG, title);
        Log.d (TAG, lat);
        Log.d (TAG, longi);

        web = findViewById(R.id.web1);
        web.clearCache(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDefaultTextEncodingName("UTF-8");
        web.setHorizontalScrollBarEnabled(false);
        web.setVerticalScrollBarEnabled(false);

        web.loadUrl("http://192.168.0.90:8081/JspServer/tmap.jsp?lat=" + lat + "&longi=" + longi + "&title="+ title);

    }
}
