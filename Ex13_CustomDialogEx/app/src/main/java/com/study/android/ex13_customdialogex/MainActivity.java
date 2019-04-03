package com.study.android.ex13_customdialogex;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClicked(View v) {
        final Dialog loginDialog = new Dialog(this);
        loginDialog.setContentView(R.layout.custom_dialog);
        loginDialog.setTitle("로그인 화면");

        final EditText uId = loginDialog.findViewById(R.id.id);
        final EditText uPw = loginDialog.findViewById(R.id.pw);

        Button login = loginDialog.findViewById(R.id.button4);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uId.getText().toString().trim().length() > 0 && uPw.getText().toString().trim().length() > 0) {
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
                    loginDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(),"다시 입력하세요", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button cancel = loginDialog.findViewById(R.id.button3);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });
        loginDialog.show();
    }
    public void onBtn1Clicked(View v) {
        CustomCircleProgressDialog customCircleProgressDialog = new CustomCircleProgressDialog(MainActivity.this);
        // 주변 클릭 터치 시 프로그래서 사라지지 않게 하기 : false
        customCircleProgressDialog.setCancelable(true);
        customCircleProgressDialog.show();
    }
}
