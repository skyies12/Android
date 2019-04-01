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
       Intent intent = new Intent(getApplicationContext(), NewActivity.class);
       startActivity(intent);
   }
}
