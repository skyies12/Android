package com.study.android.ex19_list5;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.study.android.ex19_list5.SingerItem;
import com.study.android.ex19_list5.SingerItemView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    SingleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SingleAdapter(this);
        final SingerItem item1 = new SingerItem("홍길동", "20", R.drawable.face1);
        adapter.addItem(item1);

        SingerItem item2 = new SingerItem("이순신", "25", R.drawable.face2);
        adapter.addItem(item2);

        SingerItem item3 = new SingerItem("김유신", "30", R.drawable.face3);
        adapter.addItem(item3);

        final ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final SingerItem item = (SingerItem)adapter.getItem(position);
               // Toast.makeText(getApplicationContext(),"selected : " + item.getName(), Toast.LENGTH_LONG).show();

                adapter.removeItem(item);
                adapter.notifyDataSetChanged();
            }
        });

        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
    }

    public void onBtnClicked(View v) {
        String inputName = editText1.getText().toString();
        String inputAge = editText2.getText().toString();

        SingerItem item = new SingerItem(inputName, inputAge, R.drawable.face1);
        adapter.addItem(item);
        adapter.notifyDataSetChanged();
    }

    public void onBtn1Clicked(View v) {
        String inputName = editText1.getText().toString();
        String inputAge = editText2.getText().toString();

        final SingerItem item = new SingerItem(inputName, inputAge, R.drawable.face1);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("삭제하시겠습니까?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("알림")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.removeItem(item);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert =builder.create();
        alert.show();
    }

}
