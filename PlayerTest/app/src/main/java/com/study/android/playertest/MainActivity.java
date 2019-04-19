package com.study.android.playertest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 선언
        Button bt1 = (Button) findViewById (R.id.button1);

        //버튼 이벤트처리
        bt1.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        //뷰 인텐트 사용
        Intent it = new Intent(Intent.ACTION_VIEW);
        //재생할 동영상 주소
        Uri uri = Uri.parse("https://kakaotv.daum.net/embed/player/cliplink/v8472YW7M7iWn8MYM88V1VM@my?service=daum_movie&autoplay=true&profile=HIGH&playsinline=true");
        //재생할 동영상주소와 동영상코덱 설정
        it.setDataAndType(uri, "video/mp4");
        //액티비티 실행
        startActivity(it);



    }
}