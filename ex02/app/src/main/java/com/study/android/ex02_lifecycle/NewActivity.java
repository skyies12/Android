package com.study.android.ex02_lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Log.d("lecture","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lecture","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lecture","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lecture","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lecture","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lecture","onDestroy");
    }

    public void onBtn1Clicked(View v) {
        finish();
    }
}
