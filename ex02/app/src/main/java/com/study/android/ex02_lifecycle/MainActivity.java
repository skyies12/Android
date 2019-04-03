package com.study.android.ex02_lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lecture","Main onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lecture","Main onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lecture","Main onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lecture","Main onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lecture","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lecture","Main onDestroy");
    }

    public void onBtn1Clicked(View v) {

        Intent intent = new Intent(getApplicationContext(), NewActivity.class);
        startActivityForResult(intent, 1);

    }
    // 데이터 주고 받기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("lecture","콜백 함수 실행");
    }
}
