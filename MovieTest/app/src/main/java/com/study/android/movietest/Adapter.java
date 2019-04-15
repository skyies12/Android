package com.study.android.movietest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<MovieItem> mDataset;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_rank, tv_movieNm, tv_openDt, tv_salesAmt, tv_oldnew;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);

            tv_rank = view.findViewById(R.id.tv_rank);
            tv_movieNm = view.findViewById(R.id.tv_movieNm);
            tv_openDt = view.findViewById(R.id.tv_opneDt);
            tv_salesAmt = view.findViewById(R.id.tv_salesAmt);
            tv_oldnew = view.findViewById(R.id.tv_on);
        }
    }

    //생성자 - 전달되는 데이터타입에 유의하자.
    public Adapter(ArrayList<MovieItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_rank.setText(String.valueOf(mDataset.get(position).getRank()));
        holder.tv_movieNm.setText((mDataset.get(position).getMovieNm()));
        holder.tv_openDt.setText("개봉일 : " + mDataset.get(position).getOpenDt());
        holder.tv_salesAmt.setText("관객수 : " + String.valueOf(mDataset.get(position).getSalesAmt())); //형변환필요
        if(String.valueOf(mDataset.get(position).getRankOldAndNew()).equals("NEW")) {
            holder.tv_oldnew.setText(String.valueOf(mDataset.get(position).getRankOldAndNew()));
        }
        // 리스트 클릭
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context, mDataset.get(position).getMovieNm(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}