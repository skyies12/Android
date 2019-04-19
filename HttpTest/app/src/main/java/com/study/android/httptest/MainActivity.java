package com.study.android.httptest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ItemObject> list = new ArrayList();
    private Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        recyclerView = findViewById(R.id.recyclerview);


        //AsyncTask 작동시킴(파싱)
        new Description().execute();
    }

    public class Description extends AsyncTask<String, Void, ItemObject[]> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected ItemObject[] doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect("https://movie.daum.net/moviedb/main?movieId=120146").get();
                Elements mElementDataSize = doc.select("#mArticle .list_movie").select("li"); //필요한 녀석만 꼬집어서 지정

                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                Log.d("lecture", Integer.toString( mElementSize));

                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_title = elem.select("li .wrap_movie .info_tit a").text();
                    String my_link = elem.select("li .wrap_movie .info_tit a").attr("href");
                    String my_imgUrl = elem.select("li .info_movie .thumb_movie img").attr("src");
                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.
//                    Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
//                    String my_release = rElem.select("dd").text();
//                    Element dElem = elem.select("dt[class=tit_t2]").next().first();
                    String my_rank = elem.select("li .info_movie .thumb_movie em").text();
                    String my_opdate = elem.select("li .info_state").text();
//                    String my_salesAmt = elem.select("li .movie_info .movie_item dd:nth-child(6)").text();
                    //Log.d("test", "test" + mTitle);
                    //ArrayList에 계속 추가한다.

                    //list.add(new ItemObject(my_title, my_imgUrl, my_opdate, my_rank, my_salesAmt));
                    //list.add(new ItemObject(my_title));
                    list.add( new ItemObject(my_title, my_imgUrl, my_opdate, my_rank, my_link));
                }

                //추출한 전체 <li> 출력해 보자.
                Log.d("lecture :", "List " + mElementDataSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ItemObject[] result) {
//            ArraList를 인자로 해서 어답터와 연결한다.
            MyAdapter myAdapter = new MyAdapter(list);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//            recyclerView.setLayoutManager(layoutManager);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();

        }
    }
}
