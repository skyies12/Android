package com.study.android.kswtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MovieinfoActivity extends AppCompatActivity {
    TextView krTitle;
    TextView enTitle;
    TextView genre;
    TextView country;
    TextView openDt;
    TextView info;
    TextView director;
    TextView actor;
    TextView summary;
    WebView web;
    private final Handler handler = new Handler();

    ImageView imgPoster;
    InfoItem item;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_movieinfo );

        krTitle = findViewById( R.id.krTitle );
        enTitle = findViewById( R.id.enTitle );
        genre = findViewById( R.id.genre );
        country = findViewById( R.id.country );
        openDt = findViewById( R.id.openDt );
        info = findViewById( R.id.info );
        director = findViewById( R.id.director );
        actor = findViewById( R.id.actor );
        summary = findViewById( R.id.summary );

        imgPoster = findViewById( R.id.poster );

        summary.setMovementMethod(new ScrollingMovementMethod());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );

        web = findViewById(R.id.web1);
        web.clearCache(true);
        web.getSettings().setCacheMode( WebSettings.LOAD_NO_CACHE);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDefaultTextEncodingName("UTF-8");

        new InfoAsync().execute();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class InfoAsync extends AsyncTask<String, Void, InfoItem[]> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(MovieinfoActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected InfoItem[] doInBackground(String... params) {
            intent = getIntent();
            String url = intent.getStringExtra("url");
            String trailer = url.substring(22, url.length());
            //Log.d( "lecture" , trailer);

            try {
                Document doc = Jsoup.connect("https://movie.daum.net" + url).get();
                String krTitle = doc.select(".movie_summary .subject_movie .tit_movie").text();
                String enTitle = doc.select(".movie_summary .subject_movie .txt_origin").text();
                String genre = doc.select(".movie_summary .list_movie .txt_main:nth-child(2)").text();
                String country = doc.select(".movie_summary .list_movie dd:nth-child(3)").text();
                String openDt = doc.select(".movie_summary .list_movie .txt_main:nth-child(5)").text();
                String info = doc.select(".movie_summary .list_movie dd:nth-child(6)").text();
                String director = doc.select(".movie_summary .list_movie .type_ellipsis:nth-child(8) a").text();
                String actor = doc.select(".movie_summary .list_movie .type_ellipsis:nth-child(10) a").text();
                String summary = doc.select(".desc_movie p").text();

//                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                // Log.d("lecture", krTitle + "\n" + enTitle + "\n" + genre + "\n" + country + "\n" + openDt + "\n" + info + "\n" + director + "\n" + actor + "\n" + summary);

                item = new InfoItem(krTitle, enTitle, genre, country, openDt, info, director, actor, summary);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(InfoItem[] result) {
            intent = getIntent();
            String imgUrl = intent.getStringExtra("imgUrl");
            progressDialog.dismiss();

            krTitle.setText(item.getKrtitle());
            enTitle.setText(item.getEntitle());
            genre.setText(item.getGenre());
            country.setText(item.getCountry());
            openDt.setText(item.getOpenDt());
            info.setText(item.getInfo());
            director.setText("(감독) " + item.getDirector());
            actor.setText("(배우) " + item.getActor());
            Picasso.with(getApplicationContext()).load(imgUrl).into(imgPoster);
            summary.setText(item.getSummary());
            if(item.getKrtitle().equals( "생일 (2018)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/v8472YW7M7iWn8MYM88V1VM@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "헬보이 (2019)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/v19dciUgpiUgUN4pOIw4YtO@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "돈 (2019)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/v164cOoNNOhoF3qOASNmKPN@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "미성년 (2018)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/vbe5eUUU7v6rU3iUrUvUNAG@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "샤잠! (2019)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/vc2d6o2vK6fftD26GG2KnC6@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "어스 (2019)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/v488eJVJMRGM2G3TR3qVVzr@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "공포의 묘지 (2019)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/v488eJVJMRGM2G3TR3qVVzr@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "장난스런 키스 (2019)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/v47e0iJNinn3t7tnmN34npI@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "바이스 (2018)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/v439c66v0vtGV0oXw9XvQZ6@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            } else if(item.getKrtitle().equals( "파이브 피트 (2019)" )) {
                web.loadUrl( "https://kakaotv.daum.net/embed/player/cliplink/v9c965N9WF4e18kaJY8NYWe@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true" );
            }
        }
    }


//    public static void setReadMore(final TextView view, final String text, final int maxLine) {
//        final Context context = view.getContext();
//        final String expanedText = " ... 더보기";
//
//        if (view.getTag() != null && view.getTag().equals(text)) { //Tag로 전값 의 text를 비교하여똑같으면 실행하지 않음.
//            return;
//        }
//        view.setTag(text); //Tag에 text 저장
//        view.setText(text); // setText를 미리 하셔야  getLineCount()를 호출가능
//        view.post(new Runnable() { //getLineCount()는 UI 백그라운드에서만 가져올수 있음
//            @Override
//            public void run() {
//                if (view.getLineCount() >= maxLine) { //Line Count가 설정한 MaxLine의 값보다 크다면 처리시작
//
//                    int lineEndIndex = view.getLayout().getLineVisibleEnd(maxLine - 1); //Max Line 까지의 text length
//
//                    String[] split = text.split("다."); //text를 자름
//                    int splitLength = 0;
//
//                    String lessText = "";
//                    for (String item : split) {
//                        splitLength += item.length() + 1;
//                        if (splitLength >= lineEndIndex) { //마지막 줄일때!
//                            if (item.length() >= expanedText.length()) {
//                                lessText += item.substring(0, item.length() - (expanedText.length())) + expanedText;
//                            } else {
//                                lessText += item + expanedText;
//                            }
//                            break; //종료
//                        }
//                        lessText += item + "\n";
//                    }
//                    SpannableString spannableString = new SpannableString(lessText);
//                    spannableString.setSpan(new ClickableSpan() {//클릭이벤트
//                        @Override
//                        public void onClick(View v) {
//                            view.setText(text);
//                        }
//
//                        @Override
//                        public void updateDrawState(TextPaint ds) { //컬러 처리
//                            ds.setColor( ContextCompat.getColor(context, R.color.colorAccent));
//                        }
//                    }, spannableString.length() - expanedText.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    view.setText(spannableString);
//                    view.setMovementMethod( LinkMovementMethod.getInstance());
//                }
//            }
//        });
//    }
}
