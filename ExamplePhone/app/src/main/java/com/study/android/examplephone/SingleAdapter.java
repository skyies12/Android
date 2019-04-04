package com.study.android.examplephone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class SingleAdapter extends BaseAdapter {
    Context context;
    ArrayList<SingerItem> items = new ArrayList<>();

    public SingleAdapter(Context context) {
        this.context = context;
    }

    public void addItem(SingerItem item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // SingerItemView view = new SingerItemView(getApplicationContext());

        SingerItemView view = null;
        if(convertView == null) {
            view = new SingerItemView(context);
        } else {
            view = (SingerItemView)convertView;
        }

        final SingerItem item = items.get(position);

        Log.d("lecture", item.getGender());

        if(item.getGender().equals("man")) {
            view.layout1();
        } else if(item.getGender().equals("woman")) {
            view.layout2();
        }
        view.setName(item.getName());
        view.setPhone(item.getPhone());
        view.setImage(item.getsId());
        view.setGender(item.getGender());

        Button button1 = view.findViewById(R.id.call);
        button1.setFocusable(false);
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "tel:" + item.getPhone();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                context.startActivity(intent);
            }
        });

        Button button2 = view.findViewById(R.id.mms);
        button2.setFocusable(false);
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "mms:" + item.getPhone();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
