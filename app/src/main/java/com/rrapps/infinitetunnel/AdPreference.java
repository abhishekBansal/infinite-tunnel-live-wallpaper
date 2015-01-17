package com.rrapps.infinitetunnel;

import android.app.Activity;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by abhishek on 17/01/15.
 */

public class AdPreference extends Preference {

    private AdView mAdView;

    public AdPreference(Context context, AttributeSet attrs, int defStyle) {super    (context, attrs, defStyle);}
    public AdPreference(Context context, AttributeSet attrs) {super(context, attrs);}
    public AdPreference(Context context) {super(context);}

    @Override
    protected View onCreateView(ViewGroup parent) {

        // this will create the linear layout defined in ads_layout.xml
        View view = super.onCreateView(parent);

        // the context is a PreferenceActivity
        Activity activity = (Activity)getContext();

         mAdView = (AdView)view.findViewById(R.id.adMob);

        // Initiate a generic request to load it with an ad
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("68A74D7F1A4E38CA91CED03280D3A263")
                .build();
        mAdView.loadAd(adRequest);

        return view;
    }

    public void onDestroy() {
        if(mAdView != null) {
            mAdView.destroy();
        }
    }

    public void onResume() {
        if(mAdView != null) {
            mAdView.resume();
        }
    }

    public void onPause() {
        if(mAdView != null) {
            mAdView.pause();
        }
    }
}
