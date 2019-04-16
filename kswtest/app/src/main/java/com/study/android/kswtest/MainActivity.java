package com.study.android.kswtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
         setTitle("Movie Culture");

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

         //앱 시작되면 보여줄 첫번째 프래그먼트 설정
         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                 new MovieFragment()).commit();
         navigationView.setCheckedItem(R.id.menu1);//선택됨을 표시
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

     //메뉴 선택 이벤트
     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()){
             case R.id.menu1://drawer_menu.xml에 설정된 id
                 //프래그먼트를 교체해 준다.
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                         new MovieFragment()).commit();
                 break;
             case R.id.menu2:
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                         new SearchFragment()).commit();
                 break;
//             case R.id.menu3:
//                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                         new TVFragment()).commit();
//                 break;
             case R.id.share:
                 Toast.makeText(this, "준비중입니다.", Toast.LENGTH_LONG).show();
                 break;
             case R.id.send:
                 Toast.makeText(this, "준비중입니다.", Toast.LENGTH_LONG).show();
                 break;
         }
         //drawer close
         drawer.closeDrawer(GravityCompat.START);
         return true;
     }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate( R.menu.main, menu );
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected( item );
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
//        drawer.closeDrawer( GravityCompat.START );
//        return true;
//    }
}
