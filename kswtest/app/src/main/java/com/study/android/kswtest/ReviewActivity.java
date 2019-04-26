package com.study.android.kswtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String CHAT_NAME;
    private String USER_NAEM;

    private ListView chat_view;
    private EditText chat_edit;
    private RatingBar rating;
    private ReviewDTO chat;
    private BackPressCloseHandler backPressCloseHandler;
    private ReviewKey reviewKey;
    private ArrayList<ReviewKey> list;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        chat_view = findViewById(R.id.chat_view);
        chat_edit = findViewById(R.id.chat_edit);
        rating = findViewById(R.id.ratingBar1);
        tvTitle = findViewById( R.id.tvtitle );

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAEM = intent.getStringExtra("userName");

        tvTitle.setText(CHAT_NAME);

        rating.setStepSize((float) 0.5);        //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감
        rating.setRating((float) 1.0);      // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다
        rating.setIsIndicator(false);           //true - 별점만 표시 사용자가 변경 불가 , false - 사용자가 변경가능
        if (rating.getRating() == (float) 1.0) {
            chat = new ReviewDTO(USER_NAEM, chat_edit.getText().toString(), "1.0");
        }
            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {
                    chat = new ReviewDTO(USER_NAEM, chat_edit.getText().toString(), Float.toString(rating));
                    //Log.d( TAG, "rating : " + Float.toString(rating));
                    // 데이터 푸쉬
                }
            });

        openChat(CHAT_NAME);
        //closeChat(CHAT_NAME);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        backPressCloseHandler = new BackPressCloseHandler(this);

        list = new ArrayList<>();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBtn1Clicked(View v) {
        if(chat_edit.getText().toString().equals("")) {
            return;
        }

        chat = new ReviewDTO(USER_NAEM, chat_edit.getText().toString(), chat.getRating());

        databaseReference.child("review").child(CHAT_NAME).push().setValue(chat);

        //databaseReference.child("review").child(CHAT_NAME).push().setValue(rating);
        // 입력창 초기화
        InputMethodManager mInputMethodManager = (InputMethodManager)ReviewActivity.this.getSystemService( Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(chat_edit.getWindowToken(), 0);
        chat_edit.setText("");
        // chatDTO를 이용하여 데이터를 묶는다.
    }

    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ReviewDTO chatDTO = dataSnapshot.getValue(ReviewDTO.class);
        adapter.add("아이디 : " + chatDTO.getUserName() + "\n" + "리뷰 : " +  chatDTO.getMessage() + "\n" + "평점 : " + chatDTO.getRating());
        list.add(new ReviewKey(dataSnapshot.getKey()));
        //int i = Integer.parseInt(chatDTO.getMessage());
        //int result = Integer.parseInt(chatDTO.getMessage());
    }

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_view.setAdapter(adapter);


        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등.. 리스너 관리
        databaseReference.child("review").child(chatName).orderByValue().addChildEventListener(new ChildEventListener() {
            String[] datakey;

            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                addMessage(dataSnapshot, adapter);
                adapter.notifyDataSetChanged();
                chat_view.setSelection(adapter.getCount()-1);

                // Log.d(TAG,key);
                chat_view.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                        // 8. 클릭한 아이템의 문자열을 가져와서
                        final String selected_item = (String)parent.getItemAtPosition(position);
                        String[] userid = selected_item.split("\n");

                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        final String userEmail = user.getEmail();

                        //Toast.makeText( getApplicationContext(), list.get(position).getKey(), Toast.LENGTH_SHORT ).show();

                        //Toast.makeText( getApplicationContext(), databaseReference.child("review").child(CHAT_NAME).toString(), Toast.LENGTH_SHORT ).show();
                        if (userid[0].substring(6,userid[0].length()).equals(userEmail)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                            //Log.d( TAG, "data키 : " + list.get( position ).getKey());

                            builder.setMessage("삭제하시겠습니까?")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("알림")
                                    .setCancelable(false)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.child("review").child(CHAT_NAME).child(list.get(position).getKey()).removeValue();
                                            adapter.remove(selected_item);
                                            adapter.notifyDataSetChanged();
                                            dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert =builder.create();
                            alert.show();
                        } else {
                            Toast.makeText( getApplicationContext(), "삭제 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                       // Log.d( "lecture", "id : " + userid[0] + "\nuserEmail : " + userEmail);

                         //9. 해당 아이템을 ArrayList 객체에서 제거하고
                       // adapter.remove(selected_item);

                        // 10. 어댑터 객체에 변경 내용을 반영시켜줘야 에러가 발생하지 않습니다.
                    }

                } );
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
