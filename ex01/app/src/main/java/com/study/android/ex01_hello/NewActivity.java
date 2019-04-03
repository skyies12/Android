package com.study.android.ex01_hello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {
    String sName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        // 인텐트에 전달된 데이터 구하기
        Intent intent = getIntent();
        sName = intent.getStringExtra("CustomerName");
    }

    // 버튼1
    // 토스트로 전달된 데이터 출력
    public void onBtn1Clicked(View v) {
        Toast.makeText(getApplicationContext(),"CustomerName : " + sName, Toast.LENGTH_SHORT).show();
    }

    // 버튼2
    // 현재 창 종료
    public void onBtn2Clicked(View v) {
        Intent intent = new Intent();
        intent.putExtra("BackData","강감찬");
        setResult(10, intent);
        finish();
    }
}
