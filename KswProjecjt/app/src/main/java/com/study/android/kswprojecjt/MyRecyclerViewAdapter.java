package com.study.android.kswprojecjt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerViewHolders>{
    private static final String TAG = "lecture";
    private ArrayList<Movie> mDataset;
    private LayoutInflater mInflate;
    private Context mContext;
    public static StringBuilder searchResult;
    private String imgurl;

    //constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<Movie> myData) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mDataset = myData;
    }

    //constructor

    public MyRecyclerViewAdapter(ArrayList<Movie> myData, Context context) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mDataset = myData;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.card_view, parent, false);
        RecyclerViewHolders viewHolder = new RecyclerViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, final int position) {
        holder.tv_rank.setText(String.valueOf(mDataset.get(position).getRank()));
        holder.tv_movieNm.setText((mDataset.get(position).getMovieNm()));
        holder.tv_openDt.setText("개봉일 : " + mDataset.get(position).getOpenDt());
        holder.tv_salesAmt.setText("관객수 : " + String.valueOf(mDataset.get(position).getSalesAmt())); //형변환필요
        if(String.valueOf(mDataset.get(position).getRankOldAndNew()).equals("NEW")) {
            holder.tv_oldnew.setText(String.valueOf(mDataset.get(position).getRankOldAndNew()));
        }
        // 리스트 클릭
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context, mDataset.get(position).getMovieNm(), Toast.LENGTH_LONG).show();
            }
        });
        //searchNaver(mDataset.get(position).getMovieNm());
    }


    @Override
    public int getItemCount() {
        return this.mDataset.size();
    }


    //뷰홀더 - 따로 클래스 파일로 만들어도 된다.
    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        public TextView tv_rank, tv_movieNm, tv_openDt, tv_salesAmt, tv_oldnew;
        public ImageView iv_photo;

        public RecyclerViewHolders(View view) {
            super(view);

            tv_rank = view.findViewById(R.id.tv_rank);
            tv_movieNm = view.findViewById(R.id.tv_movieNm);
            tv_openDt = view.findViewById(R.id.tv_opneDt);
            tv_salesAmt = view.findViewById(R.id.tv_salesAmt);
            tv_oldnew = view.findViewById(R.id.tv_on);
            iv_photo = view.findViewById( R.id.imageView3 );

        }

        public void img(String image) {
            try {
                URL url = new URL("https://ssl.pstatic.net/imgmovie/mdi/mit110/1830/183069_P01_112200.jpg");
                URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(bis);
                bis.close();
                iv_photo.setImageBitmap(bm);
            } catch (Exception e) {
            }
        }
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
                    String apiURL = "https://openapi.naver.com/v1/search/movie?query=" + text + "&display=" + display + "&yearfrom=2018&yearto=2019"; // json 결과
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
                    Log.d(TAG,data );
                    String[] array;
                    array = data.split("\"");
                    String[] title = new String[display];
                    String[] image = new String[display];

                    int k = 0;
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals("title"))
                            title[k] = array[i + 2];
                        if (array[i].equals("image"))
                            image[k] = array[i + 2];
                    }

                    Log.d(TAG, "title 잘나오니: " + title[0] +"/" + image[0]);
                    // title[0], link[0], bloggername[0] 등 인덱스 값에 맞게 검색결과를 변수화하였다.
                    setImgurl(image[0]);
                } catch (Exception e) {
                    Log.d(TAG, "error : " + e);
                }

            }
        }.start();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
