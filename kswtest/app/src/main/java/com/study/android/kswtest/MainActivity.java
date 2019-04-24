package com.study.android.kswtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    private static final String TAG = "lecture";
    private static final int RC_SIGN_IN = 1001;

    private DrawerLayout drawer;
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button btnLogout;
    private TextView textivewDelete;
    private Button btnLogin;
    private Button btnSignUp;
    private Button googlemapbtn;
    private BackPressCloseHandler backPressCloseHandler;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    private SignInButton mBtnGoogleSignIn;
    private Button mBtnGoogleSignOut;
    private TextView mTxtProfileInfo;
    private ImageView mImgProfile;
    private Button btTicket;

    AdView mAdView;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

         backPressCloseHandler = new BackPressCloseHandler(this);

         //Set toolbar
         Toolbar toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         drawer = findViewById(R.id.drawer_layout);
         NavigationView navigationView = findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);


         //drawer toggle button(상단 작대기 3개 아이콘)
         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
         toggle.syncState();

        // 앱 시작되면 보여줄 첫번째 프래그먼트 설정
         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                 new TabFragment()).commit();
         navigationView.setCheckedItem(R.id.menu1);//선택됨을 표시
         if (Build.VERSION.SDK_INT >= 21) {
             // 21 버전 이상일 때
             getWindow().setStatusBarColor(Color.BLACK);
         }

         //initializing views
         textViewUserEmail = findViewById(R.id.textviewUserEmail);
         btnLogout = findViewById(R.id.buttonLogout);
         textivewDelete = findViewById(R.id.textviewDelete);
         btnLogin = findViewById(R.id.btnLogin);
         btnSignUp = findViewById(R.id.btnSignUp);
         googlemapbtn = findViewById(R.id.googlemapbtn);
         btTicket = findViewById( R.id.ticket );

         //initializing firebase authentication object
         firebaseAuth = FirebaseAuth.getInstance();
         //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
//         if(firebaseAuth.getCurrentUser() != null) {
//             finish();
//             startActivity(new Intent(this, LoginActivity.class));
//         }



         //유저가 있다면, null이 아니면 계속 진행
         //FirebaseUser user = firebaseAuth.getCurrentUser();
//         if(user == null) {
//             textViewUserEmail.setText("로그인 해주세요");
//             btnLogin.setVisibility(View.VISIBLE);
//             btnSignUp.setVisibility(View.VISIBLE);
//             btnLogout.setVisibility( View.GONE );
//             textivewDelete.setVisibility(View.GONE);
//             mBtnGoogleSignIn.setVisibility(View.VISIBLE);
//             mBtnGoogleSignOut.setVisibility(View.GONE);
//         } else {
//             textViewUserEmail.setText("반갑습니다.\n"+ user.getEmail());
//             btnLogin.setVisibility(View.GONE);
//             btnSignUp.setVisibility(View.GONE);
//             btnLogout.setVisibility(View.VISIBLE);
//             textivewDelete.setVisibility(View.VISIBLE);
//             mBtnGoogleSignIn.setVisibility(View.VISIBLE);
//             mBtnGoogleSignOut.setVisibility(View.GONE);
//             // firebaseAuth.signOut();
//
//         }
         //textViewUserEmail의 내용을 변경해 준다.

         //logout button event
         btnLogout.setOnClickListener(this);
         textivewDelete.setOnClickListener(this);

         String bannerid = getResources().getString(R.string.ad_unit_id_1);
         MobileAds.initialize(getApplicationContext(), bannerid);
         // 테스트 광고 부르기
         mAdView = (AdView) findViewById(R.id.adView);
         AdRequest adRequest = new AdRequest.Builder().
                 addTestDevice("DAFDC12B6484CAC52C6240B1CFA62D7D").build();
         mAdView.loadAd(adRequest);

         initViews();
         initFirebaseAuth();
    }

    public void Ticketbtn(View v) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.cgv.android.movieapp");
        if(intent == null) {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);

            builder.setMessage("CGV 앱을 다운 받으시겠습니까?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("알림")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.cgv.android.movieapp"));
                            startActivity(i);
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            android.support.v7.app.AlertDialog alert =builder.create();
            alert.show();
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void MapbtnClicked(View v) {
         Intent intent = new Intent( getApplicationContext(),  GoogleMapActivity.class);
         startActivity( intent );
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
    private void initViews() {
        mBtnGoogleSignIn = findViewById( R.id.btn_google_signin);
        mBtnGoogleSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        } );
        mTxtProfileInfo = findViewById( R.id.textviewUserEmail );
        mImgProfile = findViewById( R.id.txt_profile );
        mBtnGoogleSignOut = findViewById( R.id.btn_google_signout );
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gsio = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi( Auth.GOOGLE_SIGN_IN_API, gsio)
                .build();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateProfile();
            }
        };
    }

    public void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent( mGoogleApiClient );
        startActivityForResult( intent, RC_SIGN_IN );
    }

    public void signOut(View v) {
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut( mGoogleApiClient ).setResultCallback( new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateProfile();
            }
        } );
    }

    private void updateProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // 비 로그인 상태
            textViewUserEmail.setText("로그인 해주세요");
            mBtnGoogleSignIn.setVisibility(View.VISIBLE);
            mBtnGoogleSignOut.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            btnSignUp.setVisibility(View.VISIBLE);
            mImgProfile.setImageResource( R.mipmap.ic_launcher);
            btTicket.setVisibility( View.GONE );
        } else {
            // 로그인 상태
            textViewUserEmail.setText("반갑습니다.\n"+ user.getEmail());
            mBtnGoogleSignIn.setVisibility(View.GONE);
            mBtnGoogleSignOut.setVisibility(View.VISIBLE);
            mTxtProfileInfo.setVisibility(View.VISIBLE);
            mImgProfile.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            btnSignUp.setVisibility(View.GONE);
            btTicket.setVisibility( View.VISIBLE );

            String userName = user.getDisplayName(); // 채팅에 사용 될 닉네임 설정
            String userEmail = user.getEmail();
            StringBuilder profile = new StringBuilder();
            profile.append(userEmail);
            mTxtProfileInfo.setText(profile);
            Uri userPhoto = user.getPhotoUrl();
            if (userPhoto != null){
                Picasso.with(this).load(userPhoto).into(mImgProfile);
            } else {
                mImgProfile.setImageResource( R.mipmap.ic_launcher_round);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener( mAuthListener );
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener( mAuthListener );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                updateProfile();
            }
        }
    }

    public void btnLogin(View v) {
        finish();
        Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
        startActivity( intent );
    }

    public void btnSignUp(View v) {
        finish();
        Intent intent = new Intent( getApplicationContext(), SignUpActivity.class );
        startActivity( intent );
    }


    @Override
    public void onClick(View view) {
        if (view == btnLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        //회원탈퇴를 클릭하면 회원정보를 삭제한다. 삭제전에 컨펌창을 하나 띄워야 겠다.
        if(view == textivewDelete) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
            alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(MainActivity.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });
                        }
                    }
            );
            alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "취소", Toast.LENGTH_LONG).show();
                }
            });
            alert_confirm.show();
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

     //메뉴 선택 이벤트
     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()){
             case R.id.menu1://drawer_menu.xml에 설정된 id
                 //프래그먼트를 교체해 준다.
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                         new TabFragment()).commit();
                 break;
             case R.id.menu2:
                 Toast.makeText(this, "준비중입니다.", Toast.LENGTH_LONG).show();
                 break;
//                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                         new SearchFragment()).commit();
//                 break;
//             case R.id.menu3:
//                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                         new TVFragment()).commit();
//                 break;

             case R.id.login:
                 Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
                 startActivity( intent );
                 break;
             case R.id.signup:
                 intent = new Intent( getApplicationContext(), SignUpActivity.class );
                 startActivity( intent );
                 break;
         }
         //drawer close
         drawer.closeDrawer(GravityCompat.START);
         return true;
     }

}
