package com.study.android.movietest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<MovieItem> itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Movie");

        itemArrayList = new ArrayList<>(); //데이터터

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MyAsyncTask mProcessTask = new MyAsyncTask();
        mProcessTask.execute();
    }

    //AsyncTask 생성 - 모든 네트워크 로직을 여기서 작성해 준다.
    public class MyAsyncTask extends AsyncTask<String, Void, MovieItem[]> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        //OkHttp 객체생성
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            //show dialog
            progressDialog.show();
        }

        @Override
        protected MovieItem[] doInBackground(String... params) {

            //파라미터를 더해 주거나 authentication header를 추가할 수 있다.
            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=8dba7bdefdaded72068428af435555d4&targetDt=20190411").newBuilder();
//            urlBuilder.addQueryParameter("key","8dba7bdefdaded72068428af435555d4");
//            urlBuilder.addQueryParameter("targetDt","20190411"); //날짜는 현재 날짜를 계산해서 어제날짜로 실시간 변경해 줘야 한다.
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                //여기서 해야 하나???
                //gson을 이용해서 json을 자바 객체로 변환한다.
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                //제공되는 오픈API데이터에서 어떤 항목을 가여올지 설정해야 하는데.... 음~
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("boxOfficeResult")
                        .getAsJsonObject().get("dailyBoxOfficeList"); //원하는 항목(?)까지 찾아 들어가야 한다.
                MovieItem[] posts = gson.fromJson(rootObject, MovieItem[].class);

                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MovieItem[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //요청결과를 여기서 처리한다. 화면에 출력하기등...

            //아니면 여기서 해야 하나? JSON 오브젝트로 변환하기
            if(result.length > 0){
                for (MovieItem post: result){
                    itemArrayList.add(post);
                }
            }
            //Adapter setting
            mAdapter = new Adapter(itemArrayList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}// MainActivity class


