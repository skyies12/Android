package com.study.android.ex11_sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    EditText tvID;
    EditText tvPwd;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor = pref.edit();

        tvID = findViewById(R.id.editText);
        tvPwd = findViewById(R.id.editText2);

        //

        String id = pref.getString("id", "");
        String pwd = pref.getString("pwd", "");


        Log.d("lecture","id : " + id);

        tvID.setText(id);
        tvPwd.setText(pwd);

        // 체크박스 이벤트
        checkBox = findViewById(R.id.checkBox);

        checkBox.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    String sId = tvID.getText().toString();
                    String sPwd = tvPwd.getText().toString();

                    editor.putString("id",sId);
                    editor.commit();
                    MyUtil.AlertShow(MainActivity.this, "아이디 저장","알림");
                } else {
                    // TODO : CheckBox is unchecked.
                }
            }
        });

    }

    // 버튼 이벤트
    public void btnLoginClicked(View v) {
        String sId = tvID.getText().toString();
        String sPwd = tvPwd.getText().toString();

        editor.putString("id",sId);
        editor.putString("pwd",sPwd);
        editor.commit();
    }
}
