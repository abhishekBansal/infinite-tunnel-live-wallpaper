package com.rrapps.infinitetunnel;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rrapps.infinitetunnel.model.Settings;

// Deprecated PreferenceActivity methods are used for API Level 10 (and lower) compatibility
// https://developer.android.com/guide/topics/ui/settings.html#Overview
@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity {

    CheckBoxPreference mCenterBrightPreference;
    CheckBoxPreference mSquareShapePreference;
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.settings);

        // get references to preferences
        mCenterBrightPreference = (CheckBoxPreference)findPreference(getString(R.string.pref_is_center_bright_key));
        mSquareShapePreference = (CheckBoxPreference)findPreference(getString(R.string.pref_is_square_key));
        final Settings settings = Settings.getInstance(this);

        /**
         * square shape tunnel has known issues with medium p floats
         * so disable center brightening on it if high precision is not supported
         * @see http://stackoverflow.com/questions/27868158/open-gl-different-results-on-desktop-gpu-and-mobile-gpu
         */
        if(settings.isSquareShapedTunnel() && !settings.isHighPrecisionSupported())
            mCenterBrightPreference.setEnabled(false);

        mSquareShapePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                boolean isChecked = ((CheckBoxPreference)preference).isChecked();
                /**
                 * if high precision is supported then no issues in any case
                 * or if its circular tunnel enable it, disable in all other cases
                 */
                if (settings.isHighPrecisionSupported() || !isChecked) {
                    mCenterBrightPreference.setEnabled(true);
                } else {
                    settings.setCenterBright(false);
                    mCenterBrightPreference.setEnabled(false);
                }
                return false;
            }
        });

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