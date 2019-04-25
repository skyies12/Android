package com.study.android.ex_howlong;
//내위치 기반 길찾기경로.
//그 주변에 더 가볼 관광지? or 주차장.

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
//google place
import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        PlacesListener {

    private GoogleApiClient mGoogleApiClient = null;
    private GoogleMap mGoogleMap = null;
    private Marker currentMarker = null;

    private static final String TAG = "lecture";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    private AppCompatActivity mActivity;
    boolean askPermissionOnceAgain = false;
    boolean mRequestingLocationUpdates = false;
    Location mCurrentLocatiion;
    boolean mMoveMapByUser = true;
    boolean mMoveMapByAPI = true;
    LatLng currentPosition;
    private Button button2;
    String lat = "";
    String longi = "";

    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
    //구글플레이스
    List<Marker> previous_marker = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        //googleplace관련
        previous_marker = new ArrayList<Marker>();

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlaceInformation(currentPosition);
            }
        });

        button2 = findViewById(R.id.button2);

        Log.d(TAG, "onCreate");
        mActivity = this;


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    //googleplace.
    @Override
    public void onPlacesFailure(PlacesException e) { }

    @Override
    public void onPlacesStart() { }

    @Override
    public void onPlacesSuccess(final List<Place> places) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                for (noman.googleplaces.Place place : places) {
                    LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());

                    String markerSnippet = getCurrentAddress(latLng);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(place.getName());
                    markerOptions.snippet(markerSnippet);
                    Marker item = mGoogleMap.addMarker(markerOptions);

                    previous_marker.add(item);

                }
                //중복 마커 제거
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_marker);
                previous_marker.clear();
                previous_marker.addAll(hashSet);

            }
        });
    }

    @Override
    public void onPlacesFinished() { }
    //구글맵 내위치 관련.
    @Override
    public void onResume() {

        super.onResume();

        if (mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onResume : call startLocationUpdates");
            if (!mRequestingLocationUpdates) startLocationUpdates();
        }


        //앱 정보에서 퍼미션을 허가했는지를 다시 검사해봐야 한다.
        if (askPermissionOnceAgain) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askPermissionOnceAgain = false;

                checkPermissions();
            }
        }
    }

    public void showPlaceInformation(LatLng location)
    {
        mGoogleMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(MainActivity.this)
                .key("AIzaSyCdg-GTcSZNlbGOp1iMMwiMc2n0niwt3Uo")
                .latlng(location.latitude, location.longitude)//현재 위치
                .radius(500) //500 미터 내에서 검색
                .type(PlaceType.RESTAURANT)
                .build()
                .execute();
    }// AMUSEMENT_PARK, BUS_STATION, MUSEUM, PARK, PARKING , RESTAURANT ,SUBWAY_STATION, TRAIN_STATION
    //TRAVEL_AGENCY



    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call FusedLocationApi.requestLocationUpdates");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            mRequestingLocationUpdates = true;

            mGoogleMap.setMyLocationEnabled(true);

        }

    }



    private void stopLocationUpdates() {

        Log.d(TAG,"stopLocationUpdates : LocationServices.FusedLocationApi.removeLocationUpdates");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {


        mGoogleMap = googleMap;


        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();

        //mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(20));

        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(37.576014, 126.976917))
                .title("경복궁")
                .snippet("Palace");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.576110, 126.978004))
                .title("초관처소");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.576127, 126.978197))
                .title("영군직소");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.576191, 126.976448))
                .title("수문장청");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.576596, 126.977486))
                .title("협생문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.576602, 126.976217))
                .title("용성문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.576935, 126.976837))
                .title("흥례문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.577344, 126.976889))
                .title("영제교");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.577532 , 126.976286))
                .title("유화문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.577698 , 126.976903))
                .title("근정문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.577966, 126.978130))
                .title("동정문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.578531 ,126.976941))
                .title("근정전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.578880, 126.976984))
                .title("사정문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.578964, 126.975896))
                .title("수정전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579071, 126.976989))
                .title("사정전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579110, 126.976668))
                .title("천추전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579084, 126.977360))
                .title("만춘전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579045, 126.977929))
                .title("자선당");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579036, 126.978390))
                .title("비현각");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579425, 126.976654))
                .title("경성전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579404, 126.977405))
                .title("연생전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579467, 126.977801))
                .title("내소주방");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579527, 126.978209))
                .title("외소주방");
        googleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579799, 126.978096))
                .title("생물방");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579510, 126.977024))
                .title("강녕전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579604, 126.976724))
                .title("응지당");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579570, 126.977378))
                .title("연길당");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579671, 126.977050))
                .title("양의문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579671, 126.976852))
                .title("보의당");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579737, 126.976763))
                .title("내순당");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579667, 126.977227))
                .title("승순당");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579760, 126.977346))
                .title("만통문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579749, 126.976576))
                .title("흠경각");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579879, 126.977319))
                .title("원길헌");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579849, 126.976764))
                .title("재성문");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579894, 126.976810))
                .title("함홍각");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579894, 126.976810))
                .title("교태전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.580281, 126.978084))
                .title("자경전");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579722, 126.975965))
                .title("경회루");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.579752, 126.975311))
                .title("경회지");
        mGoogleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(37.580215, 126.975375))
                .title("하향정");
        mGoogleMap.addMarker(marker).showInfoWindow();




        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){

            @Override
            public boolean onMyLocationButtonClick() {

                Log.d( TAG, "onMyLocationButtonClick : 위치에 따른 카메라 이동 활성화");
                mMoveMapByAPI = true;
                return true;
            }
        });

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                Log.d( TAG, "onMapClick :");
            }
        });


        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

            @Override
            public void onCameraMoveStarted(int i) {

                if (mMoveMapByUser == true && mRequestingLocationUpdates){

                    Log.d(TAG, "onCameraMove : 위치에 따른 카메라 이동 비활성화");
                    mMoveMapByAPI = false;
                }

                mMoveMapByUser = true;

            }
        });


        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {


            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

        currentPosition
                = new LatLng( location.getLatitude(), location.getLongitude());


        Log.d(TAG, "onLocationChanged : ");

        String markerTitle = getCurrentAddress(currentPosition);
        String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                + " 경도:" + String.valueOf(location.getLongitude());

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, markerSnippet);

        mCurrentLocatiion = location;
    }


    @Override
    protected void onStart() {

        if(mGoogleApiClient != null && mGoogleApiClient.isConnected() == false){

            Log.d(TAG, "onStart: mGoogleApiClient connect");
            mGoogleApiClient.connect();
        }

        super.onStart();
    }

    @Override
    protected void onStop() {

        if (mRequestingLocationUpdates) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            stopLocationUpdates();
        }

        if ( mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onStop : mGoogleApiClient disconnect");
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }


    @Override
    public void onConnected(Bundle connectionHint) {


        if ( mRequestingLocationUpdates == false ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                } else {

                    Log.d(TAG, "onConnected : 퍼미션 가지고 있음");
                    Log.d(TAG, "onConnected : call startLocationUpdates");
                    startLocationUpdates();
                    mGoogleMap.setMyLocationEnabled(true);
                }

            }else{

                Log.d(TAG, "onConnected : call startLocationUpdates");
                startLocationUpdates();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed");
        setDefaultLocation();
    }


    @Override
    public void onConnectionSuspended(int cause) {

        Log.d(TAG, "onConnectionSuspended");
        if (cause == CAUSE_NETWORK_LOST)
            Log.e(TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED)
            Log.e(TAG, "onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");
    }


    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        mMoveMapByUser = false;


        if (currentMarker != null) currentMarker.remove();


        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);


        currentMarker = mGoogleMap.addMarker(markerOptions);


        if ( mMoveMapByAPI ) {

            Log.d( TAG, "setCurrentLocation :  mGoogleMap moveCamera "
                    + location.getLatitude() + " " + location.getLongitude() ) ;
            // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
            mGoogleMap.moveCamera(cameraUpdate);
            lat = Double.toString(location.getLatitude());
            longi = Double.toString(location.getLongitude());

            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                    button2.setVisibility(View.VISIBLE);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), GotoActivity.class);
                            intent.putExtra("title", marker.getTitle());
                            intent.putExtra("lat", lat);
                            intent.putExtra("longi", longi);
                            startActivity(intent);
                        }
                    });
                    return false;
                }

            });
        }
    }


    public void setDefaultLocation() {

        mMoveMapByUser = false;


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";


        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mGoogleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        boolean fineLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager
                .PERMISSION_DENIED && fineLocationRationale)
            showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");

        else if (hasFineLocationPermission
                == PackageManager.PERMISSION_DENIED && !fineLocationRationale) {
            showDialogForPermissionSetting("퍼미션 거부 + Don't ask again(다시 묻지 않음) " +
                    "체크 박스를 설정한 경우로 설정에서 퍼미션 허가해야합니다.");
        } else if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {


            Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");

            if ( mGoogleApiClient.isConnected() == false) {

                Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (permsRequestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.length > 0) {

            boolean permissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (permissionAccepted) {


                if ( mGoogleApiClient.isConnected() == false) {

                    Log.d(TAG, "onRequestPermissionsResult : mGoogleApiClient connect");
                    mGoogleApiClient.connect();
                }



            } else {

                checkPermissions();
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }

    private void showDialogForPermissionSetting(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                askPermissionOnceAgain = true;

                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + mActivity.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(myAppSettings);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : 퍼미션 가지고 있음");


                        if ( mGoogleApiClient.isConnected() == false ) {

                            Log.d( TAG, "onActivityResult : mGoogleApiClient connect ");
                            mGoogleApiClient.connect();
                        }
                        return;
                    }
                }

                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle() + "\n"+ marker.getPosition(), Toast.LENGTH_SHORT).show();
        return true;
    }
}