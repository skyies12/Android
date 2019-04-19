package com.study.android.kswtest;

public class InfoItem {
    private String krtitle;
    private String entitle;
    private String genre;
    private String country;
    private String openDt;
    private String info;
    private String director;
    private String actor;
    private String summary;
    private String trailer;

    public InfoItem(String krtitle, String entitle, String genre, String country, String openDt, String info, String director, String actor, String summary) {
        this.krtitle = krtitle;
        this.entitle = entitle;
        this.genre = genre;
        this.country = country;
        this.openDt = openDt;
        this.info = info;
        this.director = director;
        this.actor = actor;
        this.summary = summary;
    }

    public InfoItem(String trailer) {
        this.trailer = trailer;
    }

    public String getKrtitle() {
        return krtitle;
    }

    public String getEntitle() {
        return entitle;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public String getOpenDt() {
        return openDt;
    }

    public String getInfo() {
        return info;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

    public String getSummary() { return summary; }

    public String getTrailer() { return trailer; }
}
