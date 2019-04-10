package com.study.android.ex43_telephonyex;

import android.graphics.Bitmap;

public class AddressItem {
    private String Name;
    private String Telnum;
    private Bitmap ResId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelnum() {
        return Telnum;
    }

    public void setTelnum(String telnum) {
        Telnum = telnum;
    }

    public Bitmap getResId() {
        return ResId;
    }

    public void setResId(Bitmap resId) {
        ResId = resId;
    }

    public AddressItem(String name, String telnum, Bitmap resId) {
        Name = name;
        Telnum = telnum;
        ResId = resId;
    }
}
