package com.study.android.ex05_edittext1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    TextView textView1;

    EditText edId;
    EditText edPw;
    EditText edYear;
    EditText edMonth;
    EditText edDay;

    String sId;
    String sPw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView);

        edId = findViewById(R.id.ideditText);
        edPw = findViewById(R.id.pwedit_text);
        edYear = findViewById(R.id.yearedittext);
        edMonth = findViewById(R.id.monthedittext);
        edDay = findViewById(R.id.dayedittext);

        // 패스워드 입력시 글자 체크
       edPw.addTextChangedListener(watcher);
    }

    // 키보드 내리기
    public void onKeydownClicked(View v) {
        InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    // 로그인 버튼
    public void onLoginClicked(View v) {
        sId = edId.getText().toString();
        sPw = edPw.getText().toString();

        if(sId.length() < 3) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("알림")
                    .setMessage("아이디를 입력해주세요")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("닫기", null)
                    .show();
            edId.requestFocus();
            return;
        } else if(sPw.length() < 5) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("알림")
                    .setMessage("비밀번호를 정확히 입력해주세요")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("닫기", null)
                    .show();
            edPw.requestFocus();
            return;
        } else if(sId.equals("skyies12") && sPw.equals("skkies12")) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("알림")
                    .setMessage("아이디 확인")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("닫기", null)
                    .show();
            edId.requestFocus();
            return;
        }
    }

    TextWatcher watcher = new TextWatcher() {
        String beforeText;

        @Override
        public void beforeTextChanged(CharSequence str, int start, int count, int after) {
            beforeText = str.toString();
            Log.d(TAG,"beforeTextChanged : " + beforeText);
        }

        @Override
        public void onTextChanged(CharSequence str, int start, int before, int count) {
            byte[] bytes = null;
            try {
                //bytes = str.toString().getBytes("KSC5601");
                bytes = str.toString().getBytes("8859_1");
                int strCount = bytes.length;
                textView1.setText(strCount + " / 8 바이트");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            Log.d(TAG,"afterTextChanged : " + str);
            try {
                byte[] strbytes = str.getBytes("8859_1");
                if(strbytes.length > 8) {
                    edPw.setText(beforeText);
                    edPw.setSelection(beforeText.length()-1, beforeText.length()-1);
                    // s.delete(s.length()-2,s.length()-2);
                }
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
    };

}
