package com.study.android.kswtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Description extends AsyncTask<String, Void, ItemObject[]> {

    private Context mContext;
    private ProgressDialog progressDialog ;
    private TaskCompleted mCallback;
    private ArrayList<ItemObject> list = new ArrayList();

    //이렇게 하는게 맞나?
    //public MyAsyncTask(MyRecyclerViewAdapter adapter, RecyclerView recyclerView, ArrayList<Movie> movieList, Context context){

    public Description(Context context, TaskCompleted callback){
        this.mContext = context;
        this.mCallback = (TaskCompleted) callback;
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
    protected ItemObject[] doInBackground(String... params) {
        String url = params[0];
        try {
            Document doc = Jsoup.connect(url).get();
            Elements mElementDataSize = doc.select("._content:nth-child(1) ul").select("li"); //필요한 녀석만 꼬집어서 지정
            int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

            Log.d("lecture", Integer.toString(mElementSize));

            for(Element elem : mElementDataSize) { //이렇게 요긴한 기능이
                //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                String my_title = elem.select( "li .movie_info a strong" ).text();
//                    String my_link = elem.select("li div[class=thumb] a").attr("href");
                String my_imgUrl = elem.select( "li div[class=thumb] a img" ).attr( "src" );
                //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.
//                    Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
//                    String my_release = rElem.select("dd").text();
//                    Element dElem = elem.select("dt[class=tit_t2]").next().first();
                String my_rank = elem.select( "li .movie_info a span span" ).text();
                String my_opdate = elem.select( "li .movie_info .movie_item dd:nth-child(2)" ).text();
                String my_salesAmt = elem.select( "li .movie_info .movie_item dd:nth-child(4)" ).text();
                //Log.d("test", "test" + mTitle);
                //ArrayList에 계속 추가한다.
                list.add(new ItemObject(my_title, my_imgUrl, my_opdate, my_rank, my_salesAmt));
            }
            ItemObject[] post = list.toArray(new ItemObject[list.size()]);
            // Log.d("lecture", );
            return post;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ItemObject[] result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        mCallback.onTaskComplete(result);
    }
}
