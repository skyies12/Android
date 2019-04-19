package com.study.android.htmltest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    //데이터 배열 선언
    private ArrayList<ItemObject> mList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title, textView_opendt, textView_rank, textView_salesAmt;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_img = itemView.findViewById(R.id.imageView_img);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_opendt = itemView.findViewById(R.id.textView_opendt);
            textView_rank = itemView.findViewById(R.id.textView_rank);
            textView_salesAmt = itemView.findViewById(R.id.textView_salesAmt);
        }
    }

    //생성자
    public MyAdapter(ArrayList<ItemObject> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
//        holder.textView_opendt.setText("개봉 " + String.valueOf(mList.get(position).getOpenDt()));
//        holder.textView_rank.setText(String.valueOf(mList.get(position).getRank()));
//        holder.textView_salesAmt.setText("누적 " + String.valueOf(mList.get(position).getSalesAmt()));
//
//        Picasso.with(holder.itemView.getContext()).load(mList.get(position).getImg_url()).into(holder.imageView_img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
