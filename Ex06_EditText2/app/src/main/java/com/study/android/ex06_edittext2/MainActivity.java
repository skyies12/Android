package com.study.android.ex06_edittext2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    EditText inputMsg;
    String strAmount = ""; // 임시 저장값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputMsg = findViewById(R.id.etMessage);

        inputMsg.addTextChangedListener(watcher);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            strAmount = s.toString();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG,s.toString() + ":" + strAmount);

            if(!s.toString().equals(strAmount)) { // stackoverflow를 막기위해,
                strAmount = makeStringComma(s.toString().replace(",",""));
                inputMsg.setText(strAmount);
                inputMsg.setSelection(inputMsg.getText().length(), inputMsg.getText().length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    protected String makeStringComma(String str) {
        if(str.length() == 0) {
            return "";
        }
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("###,##0");
        return format.format(value);
    }
}
