package com.rrapps.infinitetunnel;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class SettingsActivity extends Activity {

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_settings);

        getFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, new SettingsFragment())
            .commit();

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
