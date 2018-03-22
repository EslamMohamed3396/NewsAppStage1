package com.example.eslam.newsappstage1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eslam on 3/21/2018.
 */

public final class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String RESPONSE = "response";

    private QueryUtils() {
    }

    private static URL CreatUrl(String Stringurl) {

        URL url = null;
        try {
            url = new URL(Stringurl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error In Method CreateUrl : ", e);
        }
        return url;
    }

    private static String ReadFromStream(InputStream inputStream) throws IOException {
        String Line = "";
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            while ((Line = bufferedReader.readLine()) != null) {
                builder.append(Line);
            }
        }
        return builder.toString();
    }

    private static String MakeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == urlConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = ReadFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error In Method MakeHttpRequest : ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static List<News> extractFeatureFromJson(String Stringjson) {
        if (TextUtils.isEmpty(Stringjson)) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(Stringjson);
            JSONObject response = root.getJSONObject(RESPONSE);
            JSONArray result = response.getJSONArray("results");
            for (int i = 0; i < result.length(); i++) {
                JSONObject currentNews = result.getJSONObject(i);
                String title = currentNews.getString("webTitle");
                String section = currentNews.getString("sectionName");
                String url = currentNews.getString("webUrl");
                String date = currentNews.getString("webPublicationDate");
                JSONArray tags = currentNews.getJSONArray("tags");
                if (tags.length() > 0) {
                    for (int k = 0; k < tags.length(); k++) {
                        JSONObject currentAuthor = tags.getJSONObject(k);
                        String author = currentAuthor.getString("webTitle");
                        newsList.add(new News(title, author, section, date, url));
                        break;
                    }

                } else {
                    newsList.add(new News(title, section, date, url));
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error In Method extractFeatureFromJson : ", e);

        }
        return newsList;
    }

    public static List<News> ExtractNewsData(String murl) {
        String jsonresponse = null;
        URL url = CreatUrl(murl);
        try {
            Thread.sleep(2000);
            jsonresponse = MakeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error In Method ExtractNewsData : ", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<News> newsList = extractFeatureFromJson(jsonresponse);
        return newsList;
    }
}
