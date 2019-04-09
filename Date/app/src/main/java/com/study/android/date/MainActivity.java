package com.study.android.date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;

    Button button1;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView3 = findViewById(R.id.year);
        textView4 = findViewById(R.id.month);
        textView5 = findViewById(R.id.day);

        button1 = findViewById(R.id.button2);
        button = findViewById(R.id.button);


        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        if(month < 10 || day < 10) {
            button.setText("0" + month + "/0" + day + "/" + year);
            button1.setText("0" + month + "/0" + day + "/" + year);
        } else {
            button.setText(month + "/" + day + "/" + year);
            button1.setText(month + "/" + day + "/" + year);
        }
    }

    public void btn1OnClicked(View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                AlertDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if(month < 10) {
                            button1.setText("0" + (month + 1) + "/0" + dayOfMonth + "/" + year);
                            calDateBetweenAandB(year + "-" + "0" + (month + 1) + "-" + dayOfMonth);
                        } else {
                            button1.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                            calDateBetweenAandB(year + "-" + (month + 1) + "-" + dayOfMonth);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private int getMonthsDifference(Date date1, Date date2){

        /* 해당년도에 12를 곱해서 총 개월수를 구하고 해당 월을 더 한다. */
        int month1 = date1.getYear() * 12 + date1.getMonth();
        int month2 = date2.getYear() * 12 + date2.getMonth();

        return month2 - month1;
    }

    public void calDateBetweenAandB(String date)
    {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        String date1 = "";
        if(month < 10) {
            date1 = year + "-" + "0" + month + "-" + day;
        } else {
            date1 = year + "-" + month + "-" + day;
        }

        String date2 = date;

        Log.d(TAG,date2);
        Log.d(TAG,date1);

        try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
            Date FirstDate = format.parse(date1);
            Date SecondDate = format.parse(date2);

            // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
            // 연산결과 -950400000. long type 으로 return 된다.
            long calDate = SecondDate.getTime() - FirstDate.getTime();

            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            long calDateDays = calDate / ( 24*60*60*1000);

            calDateDays = Math.abs(calDateDays);
            String s1 = date1.substring(0, 4);
            String s2 = date2.substring(0, 4);
            String sm1 = date1.substring(5, 7);
            String sm2 = date2.substring(5, 7);

            int yea = Integer.parseInt(s2) - Integer.parseInt(s1);
            String to = Integer.toString(yea);

            textView3.setText(to);
            Log.d(TAG,"두 날짜의 연도 차이: "+ to);
            if(yea == 0) {
                int mon = Integer.parseInt(sm2) - Integer.parseInt(sm1);
                String to1 = Integer.toString(mon);
                textView4.setText(to1);
                Log.d(TAG,"두 날짜의 월 차이: "+to1);
            } else {

                String to2 = Integer.toString(getMonthsDifference(FirstDate, SecondDate));
                Log.d(TAG,"두 날짜의 월 차이: "+to2);
                textView4.setText(to2);
            }
            Log.d(TAG,"두 날짜의 날짜 차이: "+calDateDays);
            String to3 = Long.toString(calDateDays);
            textView5.setText(to3);
        }
        catch(ParseException e)
        {
            // 예외 처리
        }
    }

}
