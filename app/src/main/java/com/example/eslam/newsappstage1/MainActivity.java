package com.example.eslam.newsappstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private ListView newslistView;
    private NewsAdapter adapter;
    private static final String GUARDIAN_API = "http://content.guardianapis.com/search?";
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
                Uri newsuri = Uri.parse(news.getmUrl());
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String chooseBy = sharedPreferences.getString(getString(R.string.chooseBy_Key),
                getString(R.string.chooseBy_defult_value));

        Uri baseuri = Uri.parse(GUARDIAN_API);
        Uri.Builder builder = baseuri.buildUpon();
        builder.appendQueryParameter("q", "debates");
        builder.appendQueryParameter("api-key", "test");
        builder.appendQueryParameter("show-tags", "contributor");
        builder.appendQueryParameter("tag", chooseBy);
        return new NewsLoader(this, builder.toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionSetting) {
            Intent GoToSettingActivity = new Intent(this, SettingActivity.class);
            startActivity(GoToSettingActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}