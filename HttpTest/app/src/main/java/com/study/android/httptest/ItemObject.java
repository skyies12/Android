package com.study.android.httptest;

public class ItemObject {
    private String title;
    private String img_url;
    private String openDt;
    private String rank;
    private String link;

    public ItemObject(String title) {
        this.title = title;
    }

    public ItemObject(String title, String img_url, String openDt, String rank) {
        this.title = title;
        this.img_url = img_url;
        this.openDt = openDt;
        this.rank = rank;
    }

    public ItemObject(String title, String img_url, String openDt, String rank, String link) {
        this.title = title;
        this.img_url = img_url;
        this.openDt = openDt;
        this.rank = rank;
        this.link = link;
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

    public String getLink() {
        return link;
    }
}