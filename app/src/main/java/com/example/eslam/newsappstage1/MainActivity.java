package com.example.eslam.newsappstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private ListView newslistView;
    private NewsAdapter adapter;
    private static final String GUARDIAN_API = "http://content.guardianapis.com/search?q=debates&api-key=test";
    private static final int NEWS_LOADER_ID = 1;
    private TextView text_state;
    private ConnectivityManager connectivityManager;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        text_state = (TextView) findViewById(R.id.txt_state);

        newslistView = (ListView) findViewById(R.id.list_vew);

        newslistView.setEmptyView(text_state);

        adapter = new NewsAdapter(this, new ArrayList<News>());

        newslistView.setAdapter(adapter);

        newslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = adapter.getItem(position);
                Uri newsuri = Uri.parse(news.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsuri);
                startActivity(intent);
            }
        });

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            LoaderManager manager = getLoaderManager();
            manager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            text_state.setText(R.string.Internet);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, GUARDIAN_API);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        progressBar.setVisibility(View.GONE);
        text_state.setText(R.string.No_News);
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}