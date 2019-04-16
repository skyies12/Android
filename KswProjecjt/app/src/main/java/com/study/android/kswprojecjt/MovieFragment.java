package com.study.android.kswprojecjt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private  MyRecyclerViewAdapter adapter;
    private ArrayList<Movie> movieList;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView tvDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.movie_fragment, container, false);

        movieList = new ArrayList<Movie>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String date = df.format(new Date());
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, date.length());
        // 형 변환
        int dNum = Integer.parseInt(day);
        dNum = dNum - 1;
        day = Integer.toString(dNum);

        Log.d("lecture", year + month + day);

        tvDate = view.findViewById(R.id.textView3);
        tvDate.setText(year + "-" + month + "-" + day);

        //Asynctask - OKHttp
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=8dba7bdefdaded72068428af435555d4&targetDt=" + year + month + day;

        new MyAsyncTask(getContext(), new TaskCompleted() {
            @Override
            public void onTaskComplete(Movie[] result) {
                for(Movie p : result){
                    movieList.add(p);
                }
                adapter = new MyRecyclerViewAdapter(movieList, getContext());
                recyclerView.setAdapter(adapter);
            }
        }).execute(url);

        // recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        return view;
    }
}
