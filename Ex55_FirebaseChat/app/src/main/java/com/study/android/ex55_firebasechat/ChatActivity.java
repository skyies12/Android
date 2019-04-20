package com.study.android.ex55_firebasechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String CHAT_NAME;
    private String USER_NAEM;

    private ListView chat_view;
    private EditText chat_edit;
    private RatingBar rating;
    private ChatDTO chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_view = findViewById(R.id.chat_view);
        chat_edit = findViewById(R.id.chat_edit);
        rating = findViewById(R.id.ratingBar1);


        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAEM = intent.getStringExtra("userName");

        rating.setStepSize((float) 0.5);        //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감
        rating.setRating((float) 0);      // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다
        rating.setIsIndicator(false);           //true - 별점만 표시 사용자가 변경 불가 , false - 사용자가 변경가능

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                chat = new ChatDTO(USER_NAEM, chat_edit.getText().toString(), Float.toString(rating));
                //Log.d( TAG, "rating : " + Float.toString(rating));
                // 데이터 푸쉬
            }
        });

        openChat(CHAT_NAME);
        //closeChat(CHAT_NAME);
    }

    public void onBtn1Clicked(View v) {
        if(chat_edit.getText().toString().equals("")) {
            return;
        }
        chat = new ChatDTO(USER_NAEM, chat_edit.getText().toString(), chat.getRating());
        databaseReference.child("review").child(CHAT_NAME).push().setValue(chat);
        //databaseReference.child("review").child(CHAT_NAME).push().setValue(rating);
        // 입력창 초기화
        chat_edit.setText("");
        // chatDTO를 이용하여 데이터를 묶는다.
    }

    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.add("아이디 : " + chatDTO.getUserName() + "\n" + "리뷰 : " +  chatDTO.getMessage() + "\n" + "평점 : " + chatDTO.getRating());

        //int i = Integer.parseInt(chatDTO.getMessage());
        //int result = Integer.parseInt(chatDTO.getMessage());
    }

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_view.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등.. 리스너 관리
        databaseReference.child("review").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addMessage(dataSnapshot, adapter);
                // Log.d(TAG,"s : " + s);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
