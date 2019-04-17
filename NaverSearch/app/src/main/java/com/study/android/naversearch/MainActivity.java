package com.study.android.naversearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    EditText editText;
    public static StringBuilder searchResult;
    TextView textView;
    SearchAdapter adapter;
    NaverSearchTask task;
    private ProgressDialog progressDialog ;
    ArrayList<SearchItem> itemArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        editText = findViewById( R.id.editText );

        itemArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview1);

    }

    public void onclick(View v) {
        String text1 = editText.getText().toString();

        itemArrayList.clear();
        task = new NaverSearchTask();
        task.execute(text1);
        InputMethodManager mgr = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    class NaverSearchTask extends AsyncTask<String, Void, SearchItem[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로딩중...");
            progressDialog.show();
        }

        @Override
        protected SearchItem[] doInBackground(String... search) {
            final String clientId = "QwPXPDxSQzUag7Unj8wr";//애플리케이션 클라이언트 아이디값";
            final String clientSecret = "kVSgd8btuw";//애플리케이션 클라이언트 시크릿값";
            //final int display = 5; // 보여지는 검색결과의 수
            String result = "";

            try {
                String text = URLEncoder.encode(search[0], "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/search/movie?query=" + text + "&display=30&yearfrom=2000&yearto=2019"; // json 결과
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
                Log.d("lecture", "display : " + data);
                JSONObject jobj = new JSONObject(data);
                String display_string = jobj.getString("display");
                int display = Integer.parseInt(display_string);
                Log.d("lecture", "display : " + display);

                // Log.d(TAG,data );
                String[] array = data.split("\"");

                String[] title = new String[display];
                String[] image = new String[display];
                String[] director = new String[display];
                String[] actor = new String[display];
                String[] userRating = new String[display];

                // Log.d( TAG, data);
                int k = 0;
                for (int i = 0; i < array.length; i++) {

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
                try {
                    for (int i=0; i< display;i++) {
                        SearchItem item = new SearchItem(title[i], image[i] , director[i], actor[i], userRating[i]);
                        itemArrayList.add( new SearchItem(title[i], image[i] , director[i], actor[i], userRating[i]) );
                        //Log.d( TAG, director[i].substring( 0, director[i].length()-1 ));
                        // adapter.addItem(item);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                SearchItem[] post = itemArrayList.toArray( new SearchItem[itemArrayList.size()] );

            } catch (Exception e) {
                Log.d(TAG, "error : " + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(SearchItem[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            SearchAdapter myAdapter = new SearchAdapter(itemArrayList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);
        }
    }

}
