package com.example.eslam.newsappstage1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Eslam on 3/22/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<News> loadInBackground() {

        if (url == null) {
            return null;

        }

        List<News> newsList = QueryUtils.ExtractNewsData(url);
        return newsList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}