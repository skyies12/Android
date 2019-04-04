package com.study.android.ex20_list6;

public class SingerItem {
   private String name;
   private String telNum;
   private int sId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public SingerItem(String name, String telNum, int sId) {
        this.name = name;
        this.telNum = telNum;
        this.sId = sId;
    }
}
