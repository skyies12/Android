package com.study.android.ex31_httpex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnGet;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGet = findViewById(R.id.button);
        btnPost = findViewById(R.id.button2);
    }

    public void onBtnGet(View v) {
        Intent intent = new Intent(MainActivity.this, HttpGetActivity.class);
        startActivity(intent);
    }

    public void onBtnPost(View v) {
        Intent intent = new Intent(MainActivity.this, HttpPostActivity.class);
        startActivity(intent);
    }
}
