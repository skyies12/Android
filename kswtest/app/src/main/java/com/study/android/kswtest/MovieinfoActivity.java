package com.study.android.kswtest;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MovieinfoActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_movieinfo );

        textView = findViewById( R.id.textView );
        Intent intent = getIntent();
        String data = intent.getStringExtra( "title" );
        textView.setText( data );
        //actionBar 객체를 가져올 수 있다.

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
