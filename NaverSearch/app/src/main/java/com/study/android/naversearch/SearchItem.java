package com.study.android.naversearch;

public class SearchItem {
    private String title;
    private String image;
    private String director;
    private String actor;
    private String userRating;
    public SearchItem() {

    }

    public SearchItem(String title, String image, String director, String actor, String userRating) {
        this.title = title;
        this.image = image;
        this.director = director;
        this.actor = actor;
        this.userRating = userRating;
    }
//
//    public SearchItem(String title, String director, String actor, String userRating) {
//        this.title = title;
//        this.director = director;
//        this.actor = actor;
//        this.userRating = userRating;
//    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

    public String getUserRating() {
        return userRating;
    }
}
