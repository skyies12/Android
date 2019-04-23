package com.study.android.ex46_recognizespeechex;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "lecture";
    public static String PACKAGE_NAME;

    private static final int REQUEST_CODE = 1000;
    TextView textView1;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        textView1 = findViewById(R.id.textView);
        editText = findViewById( R.id.editText );
    }

    public void onBtn1Clicked(View v) {
        try {
            // 음성인식의 실행
            // 음성 인식의 실행 (1)
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, MainActivity.PACKAGE_NAME);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

            // 검색 결과를 보여주는 갯수
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성 검색");

            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "ActivityNotFoundException");
        }
    }
    private RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onError(int error) {
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "권한 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트워크 타음아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "BUSY";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버이상";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "시간초과";
                    break;
                default:
                    message = "알수없음";
                    break;
            }
            Log.d(TAG, "SPEECH ERROR : " + message);
        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onResults(Bundle bundle) {
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            String str = "";
            for (int i = 0; i < matches.size(); i++) {
                str = str + matches.get(i) + "\n";
                Log.d(TAG, "onResult text : " + matches.get(i));
            }

            textView1.setText(str);
        }

        @Override
        public void onRmsChanged(float v) {
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
        }

        @Override
        public void onPartialResults(Bundle bundle) {
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
        }

    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String[] rs = new String[text.size()];
                    text.toArray(rs);

                    editText.setText(rs[0]);
                }
                break;
        }
    }
}
