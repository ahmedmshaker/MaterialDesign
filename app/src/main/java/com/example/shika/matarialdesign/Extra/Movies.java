package com.example.shika.matarialdesign.Extra;

import java.util.Date;

/**
 * Created by shika on 8/23/2015.
 */
public class Movies {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getAudienceScore() {
        return AudienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        AudienceScore = audienceScore;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUrlThumbnail() {
        return UrlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        UrlThumbnail = urlThumbnail;
    }

    private long id;
    private String title;
    private Date releaseDate;
    private int AudienceScore;
    private String synopsis;
    private String UrlThumbnail;

}
