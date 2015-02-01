package com.dtu.engifest.newsfeed;

/**
 * Created by sikkavayu2013 on 15-01-2015.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.dtu.engifest.newsfeed.adapter.FeedListAdapter;
import com.dtu.engifest.newsfeed.data.FeedItem;
import com.dtu.engifest.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class NewsFeedActivity extends ActionBarActivity {
    private static final String TAG = NewsFeedActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private SmoothProgressBar progressBar;
   private LinearLayout errorLayout;
    private List<FeedItem> feedItems,updatedFeedItems;

    private String URL_FEED = "http://engifesttest.comlu.com/news";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        listView = (ListView) findViewById(R.id.list);
        progressBar = (SmoothProgressBar) findViewById(R.id.google_now);
        errorLayout=(LinearLayout) findViewById(R.id.error);
        feedItems = new ArrayList<FeedItem>();
        updatedFeedItems = new ArrayList<FeedItem>();
        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        final Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } if (NetworkUtil.isNetworkConnected(this)){
            progressBar.setVisibility(View.VISIBLE);

            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        updateJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    progressBar.setVisibility(View.GONE);
                    if (entry==null)
                        errorLayout.setVisibility(View.VISIBLE);
                }
            });


            AppController.getInstance().addToRequestQueue(jsonReq);
        }
if (entry==null&&!NetworkUtil.isNetworkConnected(this)){
progressBar.setVisibility(View.GONE);
    errorLayout.setVisibility(View.VISIBLE);
}
    }


    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));


                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));


                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            progressBar.setVisibility(View.GONE);
            listAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));


                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));


                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                updatedFeedItems.add(item);
            }

            progressBar.setVisibility(View.GONE);
            feedItems.clear();
            feedItems.addAll(updatedFeedItems);
            listAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
