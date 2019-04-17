package com.study.android.kswtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    ArrayList<SearchItem> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    //constructor
    public SearchAdapter(Context context, ArrayList<SearchItem> myData) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mList = myData;
    }

    //constructor

    public SearchAdapter(ArrayList<SearchItem> myData, Context context) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mList = myData;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title, textView_director, textView_actor, textView_userRating;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_img = itemView.findViewById(R.id.imageView_img);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_director = itemView.findViewById(R.id.textView_director);
            textView_actor = itemView.findViewById(R.id.textView_actor);
            textView_userRating = itemView.findViewById(R.id.textView_userRating);
        }
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.from(parent.getContext()).inflate(R.layout.search_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, final int position) {
        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()).replace("<b>","").replace( "</b>","" ));
        holder.textView_director.setText("감독 : " + String.valueOf(mList.get(position).getDirector()));
        if(String.valueOf(mList.get(position).getActor()).length() > 20) {
            holder.textView_actor.setText("출연 배우 : " + String.valueOf(mList.get(position).getActor()).substring( 0, 17 ).replace( "|",", " ));
        } else {
            holder.textView_actor.setText("출연 배우 : " + String.valueOf(mList.get(position).getActor()));
        }

        holder.textView_userRating.setText("평점 : " + String.valueOf(mList.get(position).getUserRating()));
        if (mList.get(position).getImage().equals( "" )) {
            holder.imageView_img.setImageResource( R.drawable.face1 );
        } else {
            Picasso.with(holder.itemView.getContext()).load(mList.get(position).getImage()).into(holder.imageView_img);
        }


        // 리스트 클릭
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context, String.valueOf(mList.get(position).getTitle()), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
