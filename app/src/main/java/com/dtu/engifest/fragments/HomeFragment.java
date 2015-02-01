package com.dtu.engifest.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dtu.engifest.FeedbackActivity;
import com.dtu.engifest.R;
import com.dtu.engifest.about.AboutActivity;
import com.dtu.engifest.events.EventsActivity;
import com.dtu.engifest.gallery.GalleryActivity;
import com.dtu.engifest.newsfeed.NewsFeedActivity;
import com.dtu.engifest.sponsors.SponsorsActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.Random;

/**
 * Created by naman on 16/12/14.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    int[] photos={R.drawable.photo6, R.drawable.switchthefunkup,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4,R.drawable.photo5};
    KenBurnsView imageView;
    Button newsfeed, events, about, gallery, sponsors, contactus;
    private boolean isInFront;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        imageView =(KenBurnsView) v.findViewById(R.id.kenBurns);
        newsfeed = (Button) v.findViewById(R.id.btn_news_feed);
        events = (Button) v.findViewById(R.id.btn_events);
        about = (Button) v.findViewById(R.id.btn_about);
        gallery = (Button) v.findViewById(R.id.btn_gallery);
        sponsors = (Button) v.findViewById(R.id.btn_sponsors);
        contactus = (Button) v.findViewById(R.id.btn_contactus);
        newsfeed.setOnClickListener(this);
        events.setOnClickListener(this);
        about.setOnClickListener(this);
        gallery.setOnClickListener(this);
        sponsors.setOnClickListener(this);
        contactus.setOnClickListener(this);

        //handler to change images after certain time
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                // change images randomly
                Random ran=new Random();
                int i=ran.nextInt(photos.length);


                //to avoid out of memory errors
                if (isInFront)
                imageView.setImageResource(photos[i]);
                Drawable oriDrawable = imageView.getDrawable();

                // set callback to null
                oriDrawable.setCallback(null);
                System.gc();

                i++;
                if(i>photos.length-1)
                {
                    i=0;
                }
                handler.postDelayed(this, 7000);  //for interval...
            }
        };
        handler.postDelayed(runnable, 7000); //for initial delay..


        return v;
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_news_feed:
                Intent o =new Intent(getActivity(), NewsFeedActivity.class);
                startActivity(o);
                break;
            case R.id.btn_events:
                Intent j =new Intent(getActivity(), EventsActivity.class);
                startActivity(j);
                break;
            case R.id.btn_about:
                Intent k =new Intent(getActivity(), AboutActivity.class);
                startActivity(k);
                break;
            case R.id.btn_gallery:
                Intent m =new Intent(getActivity(), GalleryActivity.class);
                startActivity(m);
                break;
            case R.id.btn_sponsors:
                Intent l =new Intent(getActivity(), SponsorsActivity.class);
                startActivity(l);
                break;
            case R.id.btn_contactus:
                Intent n =new Intent(getActivity(), FeedbackActivity.class);
                startActivity(n);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        isInFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isInFront = false;
    }
}


