package com.study.android.naversearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SearchView extends LinearLayout {
    TextView tvTitle;
    ImageView tvImage;
    TextView tvDirector;
    TextView tvActor;
    TextView tvUserRating;

    public SearchView(Context context) {
        super(context);

        LayoutInflater inflater =(LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        inflater.inflate( R.layout.item_layout, this, true );

        tvTitle = findViewById(R.id.textView_title);
        tvImage = findViewById(R.id.imageView_img);
        tvDirector = findViewById(R.id.textView_director);
        tvActor = findViewById(R.id.textView_actor);
        tvUserRating = findViewById(R.id.textView_userRating);
    }


    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTvImage(String image) {
        if(image.equals("")) {
            tvImage.setImageResource( R.drawable.face1 );
        } else {
            Picasso.with(getContext()).load(image).into(tvImage);
        }
    }

    public void setTvDirector(String director) {
        tvDirector.setText(director);
    }

    public void setTvActor(String actor) {
        tvActor.setText(actor);
    }

    public void setTvUserRating(String userRating) {
        tvUserRating.setText(userRating);
    }
}
