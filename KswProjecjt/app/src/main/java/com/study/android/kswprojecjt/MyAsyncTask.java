package com.study.android.kswprojecjt;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
    private Context mContext;
    private ProgressDialog progressDialog ;
    private TaskCompleted mCallback;

    //이렇게 하는게 맞나?
    //public MyAsyncTask(MyRecyclerViewAdapter adapter, RecyclerView recyclerView, ArrayList<Movie> movieList, Context context){
    public MyAsyncTask(Context context, TaskCompleted callback){
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
    protected Movie[] doInBackground(String... params) {
        String url = params[0];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonElement rootObject = parser.parse(response.body().charStream())
                    .getAsJsonObject().get("boxOfficeResult")
                    .getAsJsonObject().get("dailyBoxOfficeList");;
            Movie[] posts = gson.fromJson(rootObject, Movie[].class);
            return posts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);
        progressDialog.dismiss();
        mCallback.onTaskComplete(movies);
    }
}