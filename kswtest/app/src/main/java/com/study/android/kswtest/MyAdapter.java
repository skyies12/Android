package com.study.android.kswtest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    //데이터 배열 선언
    private ArrayList<ItemObject> mList;
    private LayoutInflater mInflate;
    private Context mContext;
    private FirebaseAuth firebaseAuth;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title, textView_opendt, textView_rank, textView_salesAmt;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_img = itemView.findViewById(R.id.imageView_img);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_opendt = itemView.findViewById(R.id.textView_opendt);
            textView_rank = itemView.findViewById(R.id.textView_rank);

        }
    }

    //constructor
    public MyAdapter(Context context, ArrayList<ItemObject> myData) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mList = myData;
    }

    //constructor

    public MyAdapter(ArrayList<ItemObject> myData, Context context) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mList = myData;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        holder.textView_opendt.setText(String.valueOf(mList.get(position).getOpenDt().replace("・", "\n" )));
        holder.textView_rank.setText(String.valueOf(mList.get(position).getRank()));
//        holder.textView_salesAmt.setText("누적 " + String.valueOf(mList.get(position).getSalesAmt()));
//
        Picasso.with(holder.itemView.getContext()).load(mList.get(position).getImg_url().substring(41 , mList.get(position).getImg_url().length())).into(holder.imageView_img);
        // Log.d("lecture", mList.get(position).getImg_url() );

        // 리스트 클릭
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                // Toast.makeText(context, String.valueOf(mList.get(position).getTitle()), Toast.LENGTH_LONG).show();
                firebaseAuth = FirebaseAuth.getInstance();
                //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
                if(firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(context, "로그인 후 이용가능합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, MovieinfoActivity.class);
                    intent.putExtra("url", String.valueOf(mList.get(position).getLink()));
                    intent.putExtra("imgUrl" ,mList.get(position).getImg_url().substring(41 , mList.get(position).getImg_url().length()));
                    context.startActivity( intent );
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
