package com.study.android.examplephone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SingleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SingleAdapter(this);
        final SingerItem item1 = new SingerItem("권다연", "010-7605-3085", R.drawable.b, 1);
        adapter.addItem(item1);

        SingerItem item2 = new SingerItem("김남연", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item2);

        SingerItem item3 = new SingerItem("김승욱", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item3);

        SingerItem item4 = new SingerItem("김용재", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item4);

        SingerItem item5 = new SingerItem("김태윤", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item5);

        SingerItem item6 = new SingerItem("김혜진", "010-7605-3085", R.drawable.b, 1);
        adapter.addItem(item6);

        SingerItem item7 = new SingerItem("박상원", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item7);

        SingerItem item8 = new SingerItem("박아름", "010-7605-3085", R.drawable.b, 1);
        adapter.addItem(item8);

        SingerItem item9 = new SingerItem("백지선", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item9);

        SingerItem item10 = new SingerItem("양승준", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item10);

        SingerItem item11 = new SingerItem("오건우", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item11);

        SingerItem item12 = new SingerItem("유재남", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item12);

        SingerItem item13 = new SingerItem("윤영로", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item13);

        SingerItem item14 = new SingerItem("이병헌", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item14);

        SingerItem item15 = new SingerItem("이서현", "010-7605-3085", R.drawable.b, 1);
        adapter.addItem(item15);

        SingerItem item16 = new SingerItem("이인회", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item16);

        SingerItem item17 = new SingerItem("이재환", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item17);

        SingerItem item18 = new SingerItem("이철연", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item18);

        SingerItem item19 = new SingerItem("장희준", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item19);

        SingerItem item20 = new SingerItem("정수인", "010-7605-3085", R.drawable.b, 1);
        adapter.addItem(item20);

        SingerItem item21 = new SingerItem("정의만", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item21);

        SingerItem item22 = new SingerItem("조준근", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item22);

        SingerItem item23 = new SingerItem("지동원", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item23);

        SingerItem item24 = new SingerItem("황병운", "010-7605-3085", R.drawable.b, 0);
        adapter.addItem(item24);

        SingerItem item25 = new SingerItem("최소라", "010-7605-3085", R.drawable.ic_launcher_background, 2);
        adapter.addItem(item25);

        final ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final SingerItem item = (SingerItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(),"selected : " + item.getName(), Toast.LENGTH_LONG).show();

                adapter.notifyDataSetChanged();
            }
        });
    }
}
