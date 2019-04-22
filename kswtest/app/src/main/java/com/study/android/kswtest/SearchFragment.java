package com.study.android.kswtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private ArrayList<SearchItem> movieList;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        movieList = new ArrayList<SearchItem>();

        recyclerView = view.findViewById(R.id.recyclerView);
        editText = view.findViewById(R.id.etSearch);

        editText.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ((MainActivity)getActivity()).hideAd( v );
                } else {
                    ((MainActivity)getActivity()).showAd( v );
                }
            }
        } );


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String str = editText.getText().toString();

                    if(str.equals("")) {
                        editText.setHint("영화 제목 입력해주세요." );
                    } else {
                        new NaverSearchTask( getContext(), new NaverTaskCompleted() {
                            @Override
                            public void onNaverTaskComplete(SearchItem[] result) {
                                for (SearchItem p : result) {
                                    movieList.add(p);
                                }
                                adapter = new SearchAdapter(movieList, getContext() );
                                recyclerView.setAdapter(adapter);
                            }
                        } ).execute( str );
                    }
                    movieList.clear();
                    editText.clearFocus();
                    downKeyboard(getContext(), editText);
                    return true;
                }
                return false;
            }
        });

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    public static void downKeyboard(Context context, EditText editText) {
        InputMethodManager mInputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
