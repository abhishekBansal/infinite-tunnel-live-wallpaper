package com.rrapps.infinitetunnel;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rrapps.infinitetunnel.model.Settings;

/**
 * Created by abhishek on 03/02/15.
 */
public class SettingsFragment extends PreferenceFragment {

    CheckBoxPreference mCenterBrightPreference;
    CheckBoxPreference mSquareShapePreference;
    CheckBoxPreference mIsWarpModePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);


        // get references to preferences
        mCenterBrightPreference = (CheckBoxPreference)findPreference(getString(R.string.pref_is_center_bright_key));
        mSquareShapePreference = (CheckBoxPreference)findPreference(getString(R.string.pref_is_square_key));
        mIsWarpModePreference = (CheckBoxPreference)findPreference(getString(R.string.pref_is_warp_mode_key));

        final Settings settings = Settings.getInstance(this.getActivity());

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
        Tracker t = ((InfiniteTunnelApplication) getActivity().getApplication()).getTracker(
                InfiniteTunnelApplication.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName("SettingsScreen");
        // Enable Display Features.
        t.enableAdvertisingIdCollection(true);
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
