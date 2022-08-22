package com.example.newsapp;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.JsonReader;
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

public class QueryUtils {
    public static Context context;
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    public QueryUtils(Context context) {
        this.context = context;
    }

    public static ArrayList<Article> fetchArticleData(String requestUrl) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        ArrayList<Article> articles = extractEarthquakes(jsonResponse);

        return articles;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "problem in creating url", e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Article> extractEarthquakes(String articleJson) {
        if (TextUtils.isEmpty(articleJson))
            return null;

        ArrayList<Article> articles = new ArrayList<>();
        try {
            JSONObject jsonRootObject = new JSONObject(articleJson);
            JSONObject jsonResponseObj = jsonRootObject.getJSONObject("response");
            JSONArray resultsArray = jsonResponseObj.optJSONArray("results");

            if (resultsArray.length() > 0) {
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject result = resultsArray.getJSONObject(i);
                    String time;

                    try {
                        time = result.getString("webPublicationDate");
                    } catch (JSONException e) {
                        time = "";
                        e.printStackTrace();
                    }

                    String title = result.getString("webTitle");
                    String url = result.getString("webUrl");

                    JSONArray tags = result.optJSONArray("tags");
                    String imageWriter = "";
                    String writer = "";

                    for (int j = 0; j < tags.length(); j++) {
                        try {
                            JSONObject tagsObject = tags.getJSONObject(j);
                            String firstNameWriter = tagsObject.getString("firstName");
                            String lastNameWriter = tagsObject.getString("lastName");
                            writer = firstNameWriter + " " + lastNameWriter;
                            imageWriter = tagsObject.getString("bylineImageUrl");
                        } catch (JSONException e) {
                            imageWriter = "";
                            writer = "";
                            e.printStackTrace();
                        }
                    }
                    String section = result.getString("sectionName");

                    String imageNews;
                    try {
                        JSONObject fields = result.getJSONObject("fields");
                        imageNews = fields.getString("thumbnail");
                    } catch (JSONException e) {
                        imageNews = "";
                        e.printStackTrace();
                    }
                    Article article = new Article(section, title, writer, time, imageNews, imageWriter, url);
                    articles.add(article);
                }
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return articles;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
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
}
