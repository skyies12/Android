package com.study.android.kswtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<ItemObject> movieList;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_movie, container, false);

        movieList = new ArrayList<ItemObject>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        String url = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&query=%EB%B0%95%EC%8A%A4%EC%98%A4%ED%94%BC%EC%8A%A4%20%ED%9D%A5%ED%96%89%EC%88%9C%EC%9C%84";

        new Description( getContext(), new TaskCompleted() {
            @Override
            public void onTaskComplete(ItemObject[] result) {
                for (ItemObject p : result) {
                    movieList.add(p);
                }
                adapter = new MyAdapter(movieList, getContext() );
                recyclerView.setAdapter(adapter);
            }
        } ).execute( url );

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        return view;
    }
}

