package com.rrapps.infinitetunnel;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

// Deprecated PreferenceActivity methods are used for API Level 10 (and lower) compatibility
// https://developer.android.com/guide/topics/ui/settings.html#Overview
@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity {

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.settings);
        // google analytics setup

        // Get tracker.
        Tracker t = ((InfiniteTunnelApplication) getApplication()).getTracker(
                InfiniteTunnelApplication.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName("SettingsScreen");
        // Enable Display Features.
        t.enableAdvertisingIdCollection(true);
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }
}