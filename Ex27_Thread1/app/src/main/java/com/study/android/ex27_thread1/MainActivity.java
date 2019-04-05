package com.study.android.ex27_thread1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView1;
    Button button1;
    ProgressHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  3. 추가한 클래스를 이용한 핸들러 변수 만들기
        handler = new ProgressHandler();
        textView1 = findViewById(R.id.textView1);
        button1 = findViewById(R.id.button);
    }

    public void onClick(View v) {
        button1.setEnabled(false);

        RequestThread thread = new RequestThread();
        thread.start();
    }

    class RequestThread extends Thread {
        public void run() {
            for (int i = 0; i < 20; i++) {
                Log.d("lecture", "RequestThread .. " + i);
                // 1. 쓰레드에서 메인쓰레드이 객체로의 접근은 불가능
               // textView1.setText("RequestThread .. " + i);

                // 4. 핸들로에 전달할 메시지 작성
                Message msg = handler.obtainMessage();

                Bundle bundle = new Bundle();
                bundle.putString("data1", "RequestThread .. " + i);
                bundle.putString("data2", String.valueOf(i));
                msg.setData(bundle);

                handler.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }
    // 2. 핸들러 클래스 생성
    class ProgressHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // 5. 핸들러에 메시지가 전달되면 원하는 동작 처리
            Bundle bundle = msg.getData();
            String data1 = bundle.getString("data1");
            String data2 = bundle.getString("data2");

            textView1.setText(data1);

            if(data2.equals("19")) {
                textView1.setText("쓰레드 테스트");
                button1.setEnabled(true);
            } else {
                button1.setEnabled(false);
            }
        }
    }
}
