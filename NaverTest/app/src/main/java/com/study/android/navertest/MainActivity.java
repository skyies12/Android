package com.study.android.navertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    public static StringBuilder searchResult;
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        searchNaver("어벤저스");
    }

    public void searchNaver(final String searchObject) { // 검색어 = searchObject로 ;
        final String clientId = "QwPXPDxSQzUag7Unj8wr";//애플리케이션 클라이언트 아이디값";
        final String clientSecret = "kVSgd8btuw";//애플리케이션 클라이언트 시크릿값";
        final int display = 5; // 보여지는 검색결과의 수

        // 네트워크 연결은 Thread 생성 필요
        new Thread() {

            @Override
            public void run() {

                try {
                    String text = URLEncoder.encode(searchObject, "UTF-8");
                    String apiURL = "https://openapi.naver.com/v1/search/movie?query=" + text + "&display=" + display; // json 결과
                    // Json 형태로 결과값을 받아옴.
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    con.connect();
                    BufferedReader br;
                    int responseCode = con.getResponseCode();

                    if(responseCode==200) { // 정상 호출
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    StringBuilder searchResult = new StringBuilder();
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        searchResult.append(inputLine + "\n");
                    }
                    br.close();
                    con.disconnect();

                    String data = searchResult.toString();

                    // Log.d(TAG,data );
                    String[] array;
                    array = data.split("\"");

                    String[] title = new String[display];
                    String[] image = new String[display];
                    String[] director = new String[display];
                    String[] actor = new String[display];
                    String[] userRating = new String[display];

                    int k = 0;
                    for (int i = 0; i < array.length; i++) {
                        //Log.d(TAG, Integer.toString(i));
                        if (array[i].equals("title"))
                            title[k] = array[i + 2];
                        if (array[i].equals("image"))
                            image[k] = array[i + 2];
                        if (array[i].equals("director"))
                            director[k] = array[i + 2];
                        if (array[i].equals("actor"))
                            actor[k] = array[i + 2];
                        if (array[i].equals("userRating")) {
                            userRating[k] = array[i + 2];
                            k++;
                        }
                    }
                    //Log.d(TAG, "title : " + title[0] + title[1] + title[2]);
                    //Log.d(TAG, data);

                    // title[0], link[0], bloggername[0] 등 인덱스 값에 맞게 검색결과를 변수화하였다.

                    // textView.setText("제목 : " + title[0] + "\n포스터 : " + image[0] + "\n감독 : " + director[0] + "\n배우 : " + actor[0] + "\n평점 : " + userRating[0]);
                    //textView.setText( data );

                } catch (Exception e) {
                    Log.d(TAG, "error : " + e);
                }

            }
        }.start();
    }
}
