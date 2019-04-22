package com.study.android.maptest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class PlaceAdapter extends BaseAdapter {
    Context context;
    ArrayList<PlaceItem> items = new ArrayList<>();

    public PlaceAdapter(Context context) {
        this.context = context;
    }

    public void addItem(PlaceItem item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }

    public void removeItem(PlaceItem item) {
        items.remove(item);
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

        PlaceItemView view = null;
        if(convertView == null) {
            view = new PlaceItemView(context);
        } else {
            view = (PlaceItemView)convertView;
        }

        PlaceItem item = items.get(position);
        view.setMovieName(item.getPlaceTitle());
        view.setDistance(item.getPlaceDistance());
        return view;
    }
}
