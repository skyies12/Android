package com.study.android.excalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText num1;
    EditText num2;
    TextView resultText;

    double reNum1;
    double reNum2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("초간단계산기");

        resultText = findViewById(R.id.textView3);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
    }

    public void plus(View v) {
        String rNum1 = num1.getText().toString();
        String rNum2 = num2.getText().toString();

        if(rNum1.length() == 0 || rNum2.length() == 0) {
            Toast.makeText(getApplicationContext(),"값을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        } else {
            reNum1 = Double.parseDouble(rNum1);
            reNum2 = Double.parseDouble(rNum2);

            double result = reNum1 + reNum2;

            resultText.setText("계산 결과 : " + result);
        }
    }

    public void minus(View v) {
        String rNum1 = num1.getText().toString();
        String rNum2 = num2.getText().toString();

        if(rNum1.length() == 0 || rNum2.length() == 0) {
            Toast.makeText(getApplicationContext(),"값을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        } else {
            reNum1 = Double.parseDouble(rNum1);
            reNum2 = Double.parseDouble(rNum2);

            double result = reNum1 - reNum2;

            resultText.setText("계산 결과 : " + result);
        }
    }

    public void gob(View v) {
        String rNum1 = num1.getText().toString();
        String rNum2 = num2.getText().toString();

        if(rNum1.length() == 0 || rNum2.length() == 0) {
            Toast.makeText(getApplicationContext(),"값을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        } else {
            reNum1 = Double.parseDouble(rNum1);
            reNum2 = Double.parseDouble(rNum2);

            double result = reNum1 * reNum2;

            resultText.setText("계산 결과 : " + result);
        }

    }

    public void na(View v) {
        String rNum1 = num1.getText().toString();
        String rNum2 = num2.getText().toString();

        if(rNum1.length() == 0 || rNum2.length() == 0) {
            Toast.makeText(getApplicationContext(),"값을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        } else {
            reNum1 = Double.parseDouble(rNum1);
            reNum2 = Double.parseDouble(rNum2);

            if(reNum2 == 0 || reNum1 == 0) {
                Toast.makeText(getApplicationContext(),"0을 제외한 값을 입력하세요",Toast.LENGTH_SHORT).show();
                return;
            } else {
                double result = reNum1 / reNum2;

                resultText.setText("계산 결과 : " + result);
            }
        }
    }

    public void nare(View v) {
        String rNum1 = num1.getText().toString();
        String rNum2 = num2.getText().toString();

        if(rNum1.length() == 0 || rNum2.length() == 0) {
            Toast.makeText(getApplicationContext(),"값을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        } else {
            reNum1 = Double.parseDouble(rNum1);
            reNum2 = Double.parseDouble(rNum2);

            double result = reNum1 % reNum2;

            resultText.setText("계산 결과 : " + result);
        }
    }
}
