package com.study.android.ex54_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    SingerAdapter adapter;
    RecyclerView mRecyclerView;
    int nCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SingerAdapter(this);

        SingerItem item1 = new SingerItem("나루토", "20", R.drawable.face1);
        adapter.addItem(item1);

        SingerItem item2 = new SingerItem("몽롱이", "26", R.drawable.face2);
        adapter.addItem(item2);

        SingerItem item3 = new SingerItem("톰톰톰", "30", R.drawable.face3);
        adapter.addItem(item3);

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 데이터 수평 스크롤
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
//        mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);

        adapter.setItemClick(new SingerAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                SingerItem item = (SingerItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "selected : " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        nCount = 1;
    }

    public void onBtnClicked(View v) {
        nCount++;
        SingerItem item = new SingerItem("홍동동" + nCount, "20", R.drawable.face1);
        adapter.addItem(item);

        adapter.notifyDataSetChanged();
    }
}
