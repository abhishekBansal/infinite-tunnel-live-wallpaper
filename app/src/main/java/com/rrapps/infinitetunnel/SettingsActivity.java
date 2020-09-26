package com.rrapps.infinitetunnel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class SettingsActivity extends Activity {

    private static final String PREF_LAUNCH_COUNT = "PREF_LAUNCH_COUNT";
    AdView mHeaderAdView;
    AdView mFooterAdView;
    InterstitialAd mInterstitialAd;
    SharedPreferences mPrefs;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_settings);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        mHeaderAdView = (AdView)findViewById(R.id.adMob_header);
        mFooterAdView = (AdView)findViewById(R.id.adMob_footer);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4933881591506889/5687718250");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        // Initiate a generic request to load it with an ad
        AdRequest adRequest = new AdRequest.Builder().build();
        mHeaderAdView.loadAd(adRequest);

        AdRequest adRequest2 = new AdRequest.Builder().build();
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
        incrementLaunchCount();
        if(getLaunchCount() % 3 == 0) {
            requestNewInterstitial();
        }

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

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private int getLaunchCount() {
        return mPrefs.getInt(PREF_LAUNCH_COUNT, 0);
    }

    private void incrementLaunchCount() {
        int currentCount = getLaunchCount();
        currentCount += 1;
        mPrefs.edit().putInt(PREF_LAUNCH_COUNT, currentCount).apply();
    }

}
