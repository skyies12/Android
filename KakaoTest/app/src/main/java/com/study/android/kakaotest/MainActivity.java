package com.study.android.kakaotest;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 1;
    boolean permissionCheck = false;

    public void onRequestPermission() {
        int permissionReadStorage = ContextCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE );
        int permissionWriteStorage = ContextCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE );

        if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_CODE );
        } else {
            permissionCheck = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_CODE:
                for(int i=0; i < permissions.length;i++) {
                    String permissioin = permissions[i];
                    int grantResult = grantResults[i];
                    if(permissioin.equals( Manifest.permission.READ_EXTERNAL_STORAGE )) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText( this, "허용했으니 가능함", Toast.LENGTH_SHORT ).show();
                            permissionCheck = true;
                        } else {
                            Toast.makeText( this, "허용하지 않으면 공유 못함", Toast.LENGTH_SHORT ).show();
                            permissionCheck = false;
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    public void shareKakao() {
        try {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink( this );
            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            // 메시지 추가
            kakaoBuilder.addText( "카카오링크 테스트" );

            String url = "https://ih3.googleusercontent.com/4FMghyiNYU73ECn5bHOKGOX1Nv_A5J7z2eRjHGIGxtQtK7L-fyNVuqcvyq6C1vUxgPP=w300-rw";
            kakaoBuilder.addImage( url, 160, 160 );

            kakaoBuilder.addAppButton( "앱 실행 혹은 다운로드" );

            kakaoLink.sendMessage( kakaoBuilder, this );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btn(View v) {
        shareKakao();
    }

    public void btn2(View v) {
        shareFacebook();
    }

    public void btn3(View v) {
        shareInstagram();
    }

    public void shareFacebook() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle( "페이스북 공요 링크입니다." )
                .setImageUrl( Uri.parse( "https://ih3.googleusercontent.com/hmVeH1KmKDy1ozUlrjtYMHpzSDrBv9NSBZODPLzR8HdBip9kx3wn_sXmHr3wepCHXA=rw"))
                .setContentUrl(Uri.parse( "https://play.google.com/store/apps/details?id=com.handykim.nbit.everytimerfree"))
                .setContentDescription( "문장1, 문장2, 문장3, 문장4" )
                .build();
        ShareDialog shareDialog = new ShareDialog( this );
        shareDialog.show( content, ShareDialog.Mode.FEED );
    }

    public void shareInstagram() {
        onRequestPermission();

        if(permissionCheck) {
            Bitmap bm = BitmapFactory.decodeResource( getResources(), R.drawable.com_facebook_auth_dialog_background );
            String storage = Environment.getExternalStorageDirectory().getAbsolutePath();
            String fileName = "1_Skyscraper.jpg";

            String folderPath = "/Samsung/";
            String fullPath = storage + folderPath;
            Log.d( "lecture", fullPath );
            File filePath;

            try {
                filePath = new File( fullPath );
                if(!filePath.isDirectory()) {
                    filePath.mkdir();
                }
                FileOutputStream fos = new FileOutputStream( fullPath + fileName );
//                bm.compress( Bitmap.CompressFormat.PNG, 100, fos );
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent share = new Intent( Intent.ACTION_SEND );
            share.setType( "image/*" );
            Uri uri = Uri.fromFile( new File( fullPath, fileName ) );
            try {
                share.putExtra( Intent.EXTRA_STREAM, uri );
                share.putExtra( Intent.EXTRA_TEXT, "텍스트는 지원하지 않음" );
                share.setPackage( "com.instagram.android" );
                startActivity( share );
            } catch (ActivityNotFoundException e) {
                Toast.makeText( this, "인스타 안깔림", Toast.LENGTH_SHORT ).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG, "key_hash=" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
