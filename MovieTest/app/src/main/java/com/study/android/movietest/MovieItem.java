package com.study.android.movietest;

public class MovieItem {
    private int rank;
    private String movieNm;
    private String openDt;
    private long salesAmt;  //int? long으로 해주면 되나???
    private String rankOldAndNew;

    //
    public MovieItem(int rank, String movieNm, String openDt, long salesAmt, String rankOldAndNew) {
        this.rank = rank;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.salesAmt = salesAmt;
        this.rankOldAndNew = rankOldAndNew;
    }



    //사용자가 입력할일은 없으니 set은 필요가 없고 get만 필요한듯 하다.

    public int getRank() {
        return rank;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public String getOpenDt() {
        return openDt;
    }

    public long getSalesAmt() {
        return salesAmt;
    }

    public String getRankOldAndNew() {
        return rankOldAndNew;
    }
}