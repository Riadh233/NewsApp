package com.example.newsapp;
import android.content.Context;


import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class ArticlesLoader extends AsyncTaskLoader {
    private String url;

    public ArticlesLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Article> loadInBackground() {
        return QueryUtils.fetchArticleData(url);
    }
}
