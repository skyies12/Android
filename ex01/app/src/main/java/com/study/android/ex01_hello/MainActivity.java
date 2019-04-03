package com.study.android.ex01_hello;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    private static final Integer nActivity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"로그 출력");
                Toast.makeText(getApplicationContext(),"긴 토스트", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onBtn1Clicked(View v) {
        Log.d(TAG, "로그 출력");
        Toast.makeText(getApplicationContext(), "토스트", Toast.LENGTH_LONG).show();
    }


   public void onBtn2Clicked(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
        startActivity(intent);
   }

   public void onBtn3Clicked(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:01076053085"));
        startActivity(intent);
   }

   public void onBtn4Clicked(View v) {
       EditText editText = findViewById(R.id.editText);
       String str = editText.getText().toString();

       TextView textView = findViewById(R.id.textView);
       textView.setText(str);

       Toast.makeText(getApplicationContext(), "EditText : " + str, Toast.LENGTH_LONG).show();
   }

   public void onBtn5Clicked(View v) {
       EditText editText = findViewById(R.id.editText);
       String str = editText.getText().toString();

       Intent intent = new Intent(getApplicationContext(), NewActivity.class);
       // intent.putExtra("CustomerName",str); EditText 데이터 Extra에 넣기

       intent.putExtra("CustomerName","홍길동");
       // startActivity(intent);
       startActivityForResult(intent, nActivity);
   }
   // 데이터 주고 받기
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG,"콜 백 함수 호출");

        if(requestCode == 1 && resultCode == 10) {
            String sData = "";
            String str = "OnActivityResult() called : " + requestCode + " : " + resultCode;

            sData = data.getStringExtra("BackData");
            str = str + " : " + sData;

            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
   }
}
