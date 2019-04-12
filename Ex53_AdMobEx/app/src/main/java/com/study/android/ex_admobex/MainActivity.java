package com.study.android.ex_admobex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화
        String bannerid = getResources().getString(R.string.ad_unit_id_1);
        MobileAds.initialize(getApplicationContext(), bannerid);
        // 테스트 광고 부르기
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("B8552282147B0482A9E9A0963BB3D685").build();
        mAdView.loadAd(adRequest);
    }

    public void hideAd(View v) {
        if(mAdView.isEnabled()) {
            mAdView.setEnabled(false);
        }
        if(mAdView.getVisibility() != View.INVISIBLE) {
            mAdView.setVisibility(View.INVISIBLE);
        }
    }

    public void showAd(View v) {
        if(mAdView.isEnabled()) {
            mAdView.setEnabled(true);
        }
        if(mAdView.getVisibility() != View.VISIBLE) {
            mAdView.setVisibility(View.VISIBLE);
        }
    }
}
