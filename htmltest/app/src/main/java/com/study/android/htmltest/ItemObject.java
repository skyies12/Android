package com.study.android.htmltest;

public class ItemObject {
    private String title;
    private String img_url;
    private String openDt;
    private String rank;
    private String salesAmt;

    public ItemObject(String title, String img_url, String openDt, String rank, String salesAmt) {
        this.title = title;
        this.img_url = img_url;
        this.openDt = openDt;
        this.rank = rank;
        this.salesAmt = salesAmt;
    }

    public String getTitle() {
        return title;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getOpenDt() {
        return openDt;
    }

    public String getRank() {
        return rank;
    }

    public String getSalesAmt() {
        return salesAmt;
    }
}