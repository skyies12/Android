package com.study.android.kswtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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

        String url = "https://movie.daum.net/boxoffice/weekly";

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

//        mLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(mLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);




//        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
//        mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//        mSwipeRefreshLayout.setColorSchemeResources(
//                android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light
//        );

        return view;
    }
}

