package com.study.android.movietest1;

import java.net.URL;

public class MovieItem {
    private String title;
    private String directorNm;
    private String actorNm;
    private String plot;
    private URL kmdbUrl;
    private URL posterUrl;

    public MovieItem(String title, String directorNm, String actorNm, String plot, URL kmdbUrl, URL posterUrl) {
        this.title = title;
        this.directorNm = directorNm;
        this.actorNm = actorNm;
        this.plot = plot;
        this.kmdbUrl = kmdbUrl;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDirectorNm() {
        return directorNm;
    }

    public String getActorNm() {
        return actorNm;
    }

    public String getPlot() {
        return plot;
    }

    public URL getKmdbUrl() {
        return kmdbUrl;
    }

    public URL getPosterUrl() {
        return posterUrl;
    }
}

