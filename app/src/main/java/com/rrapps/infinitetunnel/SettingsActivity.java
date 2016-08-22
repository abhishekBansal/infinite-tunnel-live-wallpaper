package com.rrapps.infinitetunnel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class SettingsActivity extends Activity {

    AdView mHeaderAdView;
    AdView mFooterAdView;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_settings);

        mHeaderAdView = (AdView)findViewById(R.id.adMob_header);
        mFooterAdView = (AdView)findViewById(R.id.adMob_footer);

        // Initiate a generic request to load it with an ad
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("68A74D7F1A4E38CA91CED03280D3A263")
                .build();
        mHeaderAdView.loadAd(adRequest);

        AdRequest adRequest2 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("68A74D7F1A4E38CA91CED03280D3A263")
                .build();
        mFooterAdView.loadAd(adRequest2);

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

    @Override
    protected void onResume() {
        super.onResume();
        mHeaderAdView.resume();
        mFooterAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHeaderAdView.pause();
        mFooterAdView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHeaderAdView.destroy();
        mFooterAdView.destroy();
    }

    private ShareActionProvider mShareActionProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

}
