package com.study.android.sample6;

/**
 * Created by TedPark on 16. 4. 26..
 */
public class MarkerItem {

    double lat;
    double longi;
    int addr;
    String name;

    public MarkerItem(double lat, double longi, String name) {
        this.lat = lat;
        this.longi = longi;
        this.name = name;
    }

    public MarkerItem(double lat, double longi) {
        this.lat = lat;
        this.longi = longi;
    }

    public MarkerItem(double lat) {
        this.lat = lat;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}