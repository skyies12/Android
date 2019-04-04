package com.study.android.examplephone;

public class SingerItem {
    private String name;
    private String phone;
    private String gender;
    private int sId;

    public SingerItem() {

    }

    public SingerItem(String name, String phone, int sId, String gender) {
        this.name = name;
        this.phone = phone;
        this.sId = sId;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
