package com.study.android.kswtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NaverSearchTask extends AsyncTask<String, Void, SearchItem[]> {
    private static final String TAG = "lecture";

    private Context mContext;
    private ProgressDialog progressDialog ;
    private NaverTaskCompleted mCallback;
    private ArrayList<SearchItem> itemArrayList = new ArrayList();
    public static StringBuilder searchResult;

    //이렇게 하는게 맞나?
    //public MyAsyncTask(MyRecyclerViewAdapter adapter, RecyclerView recyclerView, ArrayList<Movie> movieList, Context context){

    public NaverSearchTask(Context context, NaverTaskCompleted callback){
        this.mContext = context;
        this.mCallback = (NaverTaskCompleted) callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("로딩중...");
        progressDialog.show();
    }

    @Override
    protected SearchItem[] doInBackground(String... params) {
        final String clientId = "QwPXPDxSQzUag7Unj8wr";//애플리케이션 클라이언트 아이디값";
        final String clientSecret = "kVSgd8btuw";//애플리케이션 클라이언트 시크릿값";
        final int yearfrom = 2000;
        final int yearto = 2019;

        try {
            String text = URLEncoder.encode(params[0], "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie?query=" + text + "&yearfrom=" + yearfrom + "&yearto=" + yearto + "&display=30"; // json 결과
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

            searchResult = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                searchResult.append(inputLine + "\n");
            }
            br.close();
            con.disconnect();

            String data = searchResult.toString();
            JSONObject jobj = new JSONObject(data);
            String display_string = jobj.getString("display");
            int display = Integer.parseInt(display_string);
            //Log.d(TAG, data);
            // Log.d(TAG, Integer.toString( display ));
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
            // title[0], link[0], bloggername[0] 등 인덱스 값에 맞게 검색결과를 변수화하였다.
            // textView.setText("제목 : " + title[0] + "\n포스터 : " + image[0] + "\n감독 : " + director[0] + "\n배우 : " + actor[0] + "\n평점 : " + userRating[0]);
            //textView.setText( data );

            for (int i=0; i< display;i++) {
                SearchItem item = new SearchItem(title[i], image[i] , director[i], actor[i], userRating[i]);
                itemArrayList.add( new SearchItem(title[i], image[i] , director[i], actor[i], userRating[i]) );
                // adapter.addItem(item);
            }
            SearchItem[] post = itemArrayList.toArray( new SearchItem[itemArrayList.size()] );

            return post;
        } catch (Exception e) {
            Log.d(TAG, "error : " + e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(SearchItem[] result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        mCallback.onNaverTaskComplete(result);
    }
}
