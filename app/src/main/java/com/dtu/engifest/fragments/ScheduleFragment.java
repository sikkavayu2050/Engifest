package com.dtu.engifest.fragments;

/**
 * Created by naman on 17/12/14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.dtu.engifest.schedule.ScheduleAdapter;
import com.dtu.engifest.schedule.ScheduleItem;
import com.dtu.engifest.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class ScheduleFragment extends Fragment{

    private ListView listView;
    private ScheduleAdapter listAdapter;
    private List<ScheduleItem> feedItems,updatedFeedItems;
    private SmoothProgressBar progressBar;
    LinearLayout errorLayout;
    private String URL_SCHEDULE = "http://engifest.dce.edu/api/schedule.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        listView = (ListView) v.findViewById(R.id.list);
        progressBar=(SmoothProgressBar) v.findViewById(R.id.google_now);
        errorLayout=(LinearLayout) v.findViewById(R.id.error);
        feedItems = new ArrayList<ScheduleItem>();
        updatedFeedItems= new ArrayList<ScheduleItem>();
        listAdapter = new ScheduleAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        final Cache.Entry entry = cache.get(URL_SCHEDULE);
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

        } if (NetworkUtil.isNetworkConnected(getActivity())){

            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_SCHEDULE, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    if (response != null) {
                        updateJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    if (entry==null)
                        errorLayout.setVisibility(View.VISIBLE);

                }
            });


            AppController.getInstance().addToRequestQueue(jsonReq);
        }
        if (entry==null&&!NetworkUtil.isNetworkConnected(getActivity())){
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);

        }
return v;

    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("schedule");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                ScheduleItem item = new ScheduleItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
                item.setLocation(feedObj.getString("location"));
                item.setDate(feedObj.getString("date"));
                item.setTime(feedObj.getString("time"));

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
            JSONArray feedArray = response.getJSONArray("schedule");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                ScheduleItem item = new ScheduleItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
                item.setLocation(feedObj.getString("location"));
                item.setDate(feedObj.getString("date"));
                item.setTime(feedObj.getString("time"));

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
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

}
