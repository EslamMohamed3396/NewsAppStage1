package com.example.eslam.newsappstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Eslam on 3/21/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> newsobjects) {
        super(context, 0, newsobjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News currentnews = getItem(position);
        View rootview = convertView;
        if (rootview == null) {
            rootview = LayoutInflater.from(getContext()).inflate(R.layout.details_of_list, parent, false);
        }

        TextView Title = (TextView) rootview.findViewById(R.id.title);
        Title.setText(currentnews.getmTitle());

        TextView firstAuthorView = (TextView) rootview.findViewById(R.id.author);
        if (currentnews.HasAuthor()) {
            firstAuthorView.setText(currentnews.getmAutor());
        } else {
            firstAuthorView.setVisibility(View.GONE);

        }
        TextView Section = (TextView) rootview.findViewById(R.id.section);
        Section.setText(currentnews.getmSection());
        TextView Date = (TextView) rootview.findViewById(R.id.date);
        Date.setText(currentnews.getmDate());
        return rootview;
    }
}