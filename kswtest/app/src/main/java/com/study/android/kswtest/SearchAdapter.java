package com.study.android.kswtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    ArrayList<SearchItem> mList;
    private LayoutInflater mInflate;
    private Context mContext;
    private FirebaseAuth firebaseAuth;

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
        private Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_img = itemView.findViewById(R.id.imageView_img);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_director = itemView.findViewById(R.id.textView_director);
            textView_actor = itemView.findViewById(R.id.textView_actor);
            textView_userRating = itemView.findViewById(R.id.textView_userRating);
            button = itemView.findViewById( R.id.reviewbtn );
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
        if(String.valueOf(mList.get(position).getActor()).length() > 15) {
            holder.textView_actor.setText("출연 배우 : " + String.valueOf(mList.get(position).getActor()).substring( 0, 14 ).replace( "|",", " ));
        } else {
            holder.textView_actor.setText("출연 배우 : " + String.valueOf(mList.get(position).getActor()));
        }

        holder.textView_userRating.setText("평점 : " + String.valueOf(mList.get(position).getUserRating()));
        if (mList.get(position).getImage().equals( "" )) {
            holder.imageView_img.setImageResource( R.drawable.noimage );
        } else {
            Picasso.with(holder.itemView.getContext()).load(mList.get(position).getImage()).into(holder.imageView_img);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                firebaseAuth = FirebaseAuth.getInstance();
                //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
                if(firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(context, "로그인 후 이용가능합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    String userEmail = user.getEmail();
                    if(user.getEmail() == null) {
                        Toast.makeText(context, "잠시 후 다시 눌러주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(context,ReviewActivity.class);
                        intent.putExtra("chatName", String.valueOf(mList.get(position).getTitle()).replace("<b>","").replace( "</b>","" ));
                        intent.putExtra("userName", userEmail);
                        context.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
