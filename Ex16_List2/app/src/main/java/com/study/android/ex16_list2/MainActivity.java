package com.study.android.ex16_list2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] names = {"땡땡이","톰","홍동","향민","하토","놀랭"};
    String[] args = {"10","20","30","40","50","60"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ArrayAdapter<String> adapter;
        // adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, names);

        ListView listView = findViewById(R.id.listView1);
       // listView.setAdapter(adapter);

     //   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       //     @Override
      //      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //          Toast.makeText(getApplicationContext(),"selected : " + names[position], Toast.LENGTH_LONG).show();
      //      }
      //  });

        final MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"selected : " + names[position], Toast.LENGTH_LONG).show();
            }
        });
    }

    // 1단계
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1-1단계
            TextView view1 = new TextView(getApplicationContext());
            view1.setText(names[position]);
            view1.setTextSize(40.0f);
            view1.setTextColor(Color.BLUE);

            // return view1;

            // 3단계
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            layout.addView(view1);

            TextView view2 = new TextView(getApplicationContext());
            view2.setText(args[position]);
            view2.setTextSize(40.0f);
            view2.setTextColor(Color.RED);

            layout.addView(view2);

            return layout;
        }
    }

}
