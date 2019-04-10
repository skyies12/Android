package com.study.android.ex43_telephonyex;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressItemView extends LinearLayout {

    TextView txtName;
    Button btnTelNum;
    ImageView imageView;

    public AddressItemView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.adress_item_view, this, true);

        txtName = findViewById(R.id.txtName);
        btnTelNum = findViewById(R.id.btnTelNum);
        imageView = findViewById(R.id.imageView);
    }

    public void setName(String name) {
        txtName.setText(name);
    }

    public void setTel(String telNum) {
        btnTelNum.setText(telNum);
    }

    public void setImage(Bitmap photo) {
        imageView.setImageBitmap(photo);
    }
}
