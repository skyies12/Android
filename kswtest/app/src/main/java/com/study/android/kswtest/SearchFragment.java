package com.study.android.kswtest;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private ArrayList<SearchItem> movieList;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editText;
    ImageButton button;

    private static final String TAG = "lecture";

    private static final int REQUEST_CODE = 1000;
    public static String PACKAGE_NAME;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        movieList = new ArrayList<SearchItem>();

        recyclerView = view.findViewById(R.id.recyclerView);
        editText = view.findViewById(R.id.etSearch);
        PACKAGE_NAME = getContext().getPackageName();

        editText.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ((MainActivity)getActivity()).hideAd( v );
                } else {
                    ((MainActivity)getActivity()).showAd( v );
                }
            }
        } );


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String str = editText.getText().toString();

                    if(str.equals("")) {
                        editText.setHint("영화 제목 입력해주세요." );
                    } else {
                        new NaverSearchTask( getContext(), new NaverTaskCompleted() {
                            @Override
                            public void onNaverTaskComplete(SearchItem[] result) {
                                for (SearchItem p : result) {
                                    movieList.add(p);
                                }
                                adapter = new SearchAdapter(movieList, getContext() );
                                recyclerView.setAdapter(adapter);
                            }
                        } ).execute( str );
                    }
                    movieList.clear();
                    editText.clearFocus();
                    downKeyboard(getContext(), editText);
                    return true;
                }
                return false;
            }
        });

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        button = view.findViewById( R.id.audiobtn );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 음성인식의 실행
                    // 음성 인식의 실행 (1)
                    Intent intent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, PACKAGE_NAME);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

                    // 검색 결과를 보여주는 갯수
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성 검색");

                    startActivityForResult(intent, REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    Log.d(TAG, "ActivityNotFoundException");
                }
            }
        } );
        return view;
    }

    public static void downKeyboard(Context context, EditText editText) {
        InputMethodManager mInputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
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

            editText.setText(str);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String[] rs = new String[text.size()];
                    text.toArray(rs);

                    editText.setText(rs[0]);

                        String str = editText.getText().toString();

                        if(str.equals("")) {
                            editText.setHint("영화 제목 입력해주세요." );
                        } else {
                            new NaverSearchTask( getContext(), new NaverTaskCompleted() {
                                @Override
                                public void onNaverTaskComplete(SearchItem[] result) {
                                    for (SearchItem p : result) {
                                        movieList.add(p);
                                    }
                                    adapter = new SearchAdapter(movieList, getContext() );
                                    recyclerView.setAdapter(adapter);
                                }
                            } ).execute( str );
                        }
                        movieList.clear();
                        editText.clearFocus();
                        downKeyboard(getContext(), editText);
                }
                break;
        }
    }

}
