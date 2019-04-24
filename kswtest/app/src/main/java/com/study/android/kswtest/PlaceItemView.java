package com.study.android.kswtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;
    Button button;

    public PlaceItemView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.google_map_layout, this, true);

        textView1 = findViewById(R.id.movieitem);
        textView2 = findViewById(R.id.Distance);
        button = findViewById( R.id.btTicket );
    }

    public void setMovieName(String setMovieName) {

        textView1.setText(setMovieName);
        if(setMovieName.contains("CGV")) {
            button.setOnClickListener( new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("com.cgv.android.movieapp");
                    if(intent == null) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());

                        builder.setMessage("CGV 앱을 다운 받으시겠습니까?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("알림")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                                        i.setData( Uri.parse("https://play.google.com/store/apps/details?id=com.cgv.android.movieapp"));
                                        getContext().startActivity(i);
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
                        getContext().startActivity(intent);
                    }
                }
            } );
        } else if(setMovieName.contains("롯데시네마")) {
            button.setOnClickListener( new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("kr.co.lottecinema.lcm");
                    if(intent == null) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());

                        builder.setMessage("롯데시네마 앱을 다운 받으시겠습니까?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("알림")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                                        i.setData( Uri.parse("https://play.google.com/store/apps/details?id=kr.co.lottecinema.lcm"));
                                        getContext().startActivity(i);
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
                        getContext().startActivity(intent);
                    }
                }
            } );
        } else if(setMovieName.contains("메가박스")) {
            button.setOnClickListener( new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("com.megabox.mop");
                    if(intent == null) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());

                        builder.setMessage("메가박스 앱을 다운 받으시겠습니까?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("알림")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                                        i.setData( Uri.parse("https://play.google.com/store/apps/details?id=com.megabox.mop"));
                                        getContext().startActivity(i);
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
                        getContext().startActivity(intent);
                    }
                }
            } );
        } else {
            button.setOnClickListener( new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText( getContext(), "예매 정보가 없습니다.", Toast.LENGTH_SHORT ).show();
                }
            } );
        }
    }
    public void setDistance(String setDistance) {
        textView2.setText(setDistance);
    }
}
