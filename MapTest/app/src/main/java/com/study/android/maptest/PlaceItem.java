package com.study.android.maptest;

public class PlaceItem {
    private String placeTitle;
    private String placeDistance;

    public PlaceItem() {

    }

    public PlaceItem(String placeTitle, String placeDistance) {
        this.placeTitle = placeTitle;
        this.placeDistance = placeDistance;
    }

    public PlaceItem(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPlaceDistance() {
        return placeDistance;
    }

    public void setPlaceDistance(String placeDistance) {
        this.placeDistance = placeDistance;
    }
}
