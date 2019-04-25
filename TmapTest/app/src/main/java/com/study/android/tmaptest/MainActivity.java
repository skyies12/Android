package com.study.android.tmaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.skt.Tmap.TMapView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        RelativeLayout relativeLayout = new RelativeLayout( this );
        TMapView tMapView = new TMapView( this );

        tMapView.setSKTMapApiKey( "2f59a639-4a70-4e96-9322-de04c54c3ba2" );
        tMapView.setCompassMode( true );
        tMapView.setIconVisibility( true );
        tMapView.setZoomLevel( 15 );
        tMapView.setMapType( TMapView.MAPTYPE_STANDARD );
        tMapView.setLanguage( TMapView.LANGUAGE_KOREAN );
        tMapView.setTrackingMode( true );
        tMapView.setSightVisible( true );
        relativeLayout.addView( tMapView );
        setContentView( relativeLayout );
    }
}
