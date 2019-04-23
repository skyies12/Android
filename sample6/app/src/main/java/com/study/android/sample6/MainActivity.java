package com.study.android.sample6;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;
    private GoogleMap mMap;
    EditText edit;
    TextView text;
    String lat;
    String longi;
    String cpId;
    XmlPullParser xpp;
    String key = "iOsw4MlgRU0JZpvuR5AkLUfkX%2FAOl0Q03HF78VRzR2g0dz6iD0esiw6HmLHKly6PVvGVP2PPgRpqtpULJBWSHg%3D%3D";

    String data;
    TextView log;
    ArrayList<MarkerItem> sampleList;
    ArrayList<MarkerItem2> sampleList2;
    MarkerItem markerItem;
    MarkerItem2 markerItem2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        sampleList = new ArrayList();
        sampleList2 = new ArrayList();
    }//mOnClick method..

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        Log.d( "lecture1", lat );
//        Log.d( "lecture2", longi );
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.257836, 126.353287), 14));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.animateCamera(CameraUpdateFactory.zoomTo( 10 ));

        setCustomMarkerView();
        getSampleMarkerItems();
    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }


    private void getSampleMarkerItems() {
        //Log.d("lecture","데이터 위도 : " + sampleList.get( 2 ).getLat());
//      markerItem.getLat();

        sampleList.add(new MarkerItem(33.257836, 126.353287, 100000));
        new Thread( new Runnable() {

            public void run() {
                // TODO Auto-generated method stub
                String queryUrl = "http://openapi.kepco.co.kr/service/evInfoService/getEvSearchList?"//요청 URL
                        + "addr=제주특별자치도&pageNo=1&numOfRows=1000&ServiceKey=" + key;

                try {
                    URL url = new URL( queryUrl );//문자열로 된 요청 url을 URL 객체로 생성.
                    InputStream is = url.openStream(); //url위치로 입력스트림 연결

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput( new InputStreamReader( is, "UTF-8" ) ); //inputstream 으로부터 xml 입력받기

                    String tag;

                    xpp.next();
                    int eventType = xpp.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                tag = xpp.getName();//테그 이름 얻어오기

                                if (tag.equals( "item" )) ;// 첫번째 검색결과
                                else if (tag.equals( "addr" )) {
                                    xpp.next();
                                } else if (tag.equals( "chargeTp" )) {
                                    xpp.next();
                                } else if (tag.equals( "cpId" )) {
                                    xpp.next();
                                    cpId = xpp.getText();
                                } else if (tag.equals( "cpNm" )) {
                                    xpp.next();
                                } else if (tag.equals( "lat" )) {
                                    xpp.next();
                                    lat = xpp.getText();  //좌표찍기
                                    Log.d( "lecture", "데이터 위도 : " + lat );
                                    MarkerItem markerItem = new MarkerItem( Double.parseDouble( lat ) );
                                    sampleList.add( markerItem );
                                } else if (tag.equals( "longi" )) {
                                    Log.d("lecture","데이터 경도 : " + longi);
                                    xpp.next();
                                    longi = xpp.getText(); //좌표찍기
                                    MarkerItem2 markerItem2 = new MarkerItem2( Double.parseDouble( longi ) );
                                    sampleList2.add( markerItem2 );
                                }
                                break;

                            case XmlPullParser.TEXT:
                                break;
                            case XmlPullParser.END_TAG:
                                tag = xpp.getName(); //테그 이름 얻어오기
                                if (tag.equals( "item" ));// 첫번째 검색결과종료..줄바꿈
                                break;
                        }
                        eventType = xpp.next();
                    }
                    Log.d("lecture", "데이터 개수 : " + Integer.toString(sampleList2.size()));
                    Log.d("lecture", "데이터 개수 : " + Integer.toString(sampleList.size()));


                    for (int i = 0; i < sampleList.size(); i++) {
                        markerItem = new MarkerItem( sampleList.get( i ).getLat(), sampleList2.get( i ).getLongi(), 100000 );
                        sampleList.add( new MarkerItem( sampleList.get(i).getLat(), sampleList2.get(i).getLongi()) );
                        Log.d( "lecture", "위도 : " + sampleList.get(i).getLat() );
                        Log.d( "lecture", "경도 : " + sampleList2.get(i).getLongi() );
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch blocke.printStackTrace();
                }//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // text.setText( data ); //TextView에 문자열  data 출력
                    }
                } );
            }
        } ).start();
//        sampleList.add(new MarkerItem(37.538523, 126.95768, 5000));

        for(MarkerItem markerItem : sampleList) {
            addMarker(markerItem, false);
        }
    }

    //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    String getXmlData() {

        StringBuffer buffer = new StringBuffer();

        String query = "%EC%A0%84%EB%A0%A5%EB%A1%9C";


        return buffer.toString();//StringBuffer 문자열 객체 반환3
    }

    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker) {
        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLongi());
        int addr = markerItem.getAddr();
        String formatted = NumberFormat.getCurrencyInstance().format((addr));

        tv_marker.setText(formatted);

        if (isSelectedMarker) {
            tv_marker.setBackgroundResource(R.drawable.marker);
            tv_marker.setTextColor(Color.WHITE);
        } else {
            tv_marker.setBackgroundResource(R.drawable.marker);
            tv_marker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(Integer.toString(addr));
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));


        return mMap.addMarker(markerOptions);

    }




    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double longi = marker.getPosition().longitude;
        int addr = Integer.parseInt(marker.getTitle());
        MarkerItem temp = new MarkerItem(lat, longi, addr);
        return addMarker(temp, isSelectedMarker);
    }



    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        changeSelectedMarker(marker);

        return true;
    }



    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }
}
