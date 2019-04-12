package com.study.android.ex54_recyclerview;

public class SingerItem {
   private String name;
   private String age;
   private int sId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public SingerItem(String name, String age, int sId) {
        this.name = name;
        this.age = age;
        this.sId = sId;
    }
}
