package com.rrapps.infinitetunnel;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rrapps.infinitetunnel.model.Settings;

// Deprecated PreferenceActivity methods are used for API Level 10 (and lower) compatibility
// https://developer.android.com/guide/topics/ui/settings.html#Overview
@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity {

    CheckBoxPreference mCenterBrightPreference;
    CheckBoxPreference mSquareShapePreference;
    CheckBoxPreference mIsWarpModePreference;
    AdPreference mHeaderAdPreference;
    AdPreference mFooterAdPreference;
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.settings);

        // get references to preferences
        mCenterBrightPreference = (CheckBoxPreference)findPreference(getString(R.string.pref_is_center_bright_key));
        mSquareShapePreference = (CheckBoxPreference)findPreference(getString(R.string.pref_is_square_key));
        mIsWarpModePreference = (CheckBoxPreference)findPreference(getString(R.string.pref_is_warp_mode_key));
        mHeaderAdPreference = (AdPreference)findPreference(getString(R.string.header_ad_pref_key));
        mFooterAdPreference = (AdPreference)findPreference(getString(R.string.footer_ad_pref_key));
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

        if(mIsWarpModePreference.isChecked())
            mSquareShapePreference.setEnabled(false);

        mIsWarpModePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                boolean isChecked = ((CheckBoxPreference)preference).isChecked();
                /**
                 * if its warp mode then no point of toggling between square and cylindrical
                 */
                if(isChecked) {
                    mSquareShapePreference.setEnabled(false);
                } else {
                    mSquareShapePreference.setEnabled(true);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(InfiniteTunnelApplication.LogTag, "Destroying ad views");
        mHeaderAdPreference.onDestroy();
        mFooterAdPreference.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHeaderAdPreference.onResume();
        mFooterAdPreference.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHeaderAdPreference.onPause();
        mFooterAdPreference.onPause();
    }
}