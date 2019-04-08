package com.study.android.ex29_asynctask;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int progressBarStatue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
    }

    public void onClick1(View v) {
        new CounterTask().execute(0);
    }

    class CounterTask extends AsyncTask<Integer, Integer, Integer> {
        protected  void onPreExecute() {

        }

        protected Integer doInBackground(Integer... value) {
            while (progressBarStatue < 100) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
               progressBarStatue++;
               publishProgress(progressBarStatue);
            }
            return progressBarStatue;
        }

        protected void onProgressUpdate(Integer... value) {
            if(progressBarStatue == 99) {
                Toast.makeText(getApplicationContext(), "성공",Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setProgress(progressBarStatue);
            }
        }

        protected void onPostExecute(Integer result) {
            if(progressBarStatue == 99) {
                progressBar.setProgress(0);
            } else {
                progressBar.setProgress(progressBarStatue);
            }
        }
    }
}
