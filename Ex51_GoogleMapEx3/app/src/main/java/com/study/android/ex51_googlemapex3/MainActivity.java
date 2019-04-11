package com.study.android.ex51_googlemapex3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private static final LatLng SEOUL   = new LatLng(37.566535, 126.977969);
    private static final LatLng DAEJEON = new LatLng(36.350412, 127.384548);
    private static final LatLng SUWEON  = new LatLng(37.263573, 127.028601);
    private static final LatLng BUSAN   = new LatLng(35.179554, 129.075642);
    private static final LatLng KWANGJU = new LatLng(35.159545, 126.852601);
    private static final LatLng HOME = new LatLng(33.493041399999996, 126.47709259999999);

    SupportMapFragment mapFragment;
    GoogleMap map;
    MarkerOptions myLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "GoogleMap is ready");

                map = googleMap;

                requestMapDraw();
            }
        });

        // 권한 체크 후 사용자에 의해 취소되었다면 다시 요청
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestMapDraw() {
        // 폴리라인 그리기
        PolylineOptions myLine = new PolylineOptions().width(5).color(Color.RED);

        map.addPolyline((myLine).add(SEOUL, HOME));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(HOME, 15));

        if(myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(HOME);
            myLocationMarker.title("***내 위치***\n");
            myLocationMarker.snippet("GPS로 확인할 위치");
            map.addMarker(myLocationMarker);
        } else {
            myLocationMarker.position(HOME);
        }

    }

}
