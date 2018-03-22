package com.example.eslam.newsappstage1;

/**
 * Created by Eslam on 3/21/2018.
 */

public class News {
    private String mTitle, mSection, mDate, mUrl;
    private String mAuthor = HAS_AUTHOR;
    private static String HAS_AUTHOR = "No Author";


    public News(String mTitle, String mAuthor, String mSection, String mDate, String mUrl) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mSection = mSection;
        this.mDate = mDate;
        this.mUrl = mUrl;
    }

    public News(String mTitle, String mSection, String mDate, String mUrl) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mDate = mDate;
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAutor() {
        return mAuthor;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public boolean HasAuthor() {
        return mTitle != HAS_AUTHOR;
    }
}
